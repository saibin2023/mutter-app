package com.mutter.servlet.oauth;

import java.io.IOException;
import java.sql.SQLException;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
// Import UserInfo explicitly if needed, depends on google-api-client version/structure
// import com.google.api.services.oauth2.model.Userinfo;
// import com.google.api.services.oauth2.Oauth2; // Need Oauth2 service client

import com.mutter.config.OAuthConfig;
import com.mutter.dao.UserDAO;
import com.mutter.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/oauth2/callback/google")
public class GoogleCallbackServlet extends HttpServlet {

    private GoogleAuthorizationCodeFlow flow;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        try {
             GoogleClientSecrets.Details web = new GoogleClientSecrets.Details();
             web.setClientId(OAuthConfig.GOOGLE_CLIENT_ID);
             web.setClientSecret(OAuthConfig.GOOGLE_CLIENT_SECRET);
             GoogleClientSecrets clientSecrets = new GoogleClientSecrets().setWeb(web);

            flow = new GoogleAuthorizationCodeFlow.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    clientSecrets,
                    OAuthConfig.SCOPES)
                    .setAccessType("offline")
                    .build();
        } catch (Exception e) {
            throw new ServletException("Failed to initialize Google Authorization Flow", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Check for errors from Google
        String error = req.getParameter("error");
        if (error != null) {
            log("Error received from Google OAuth: " + error);
            resp.sendRedirect(req.getContextPath() + "/login?error=google_failed");
            return;
        }

        // Get the authorization code
        String code = req.getParameter("code");
        if (code == null) {
            log("Authorization code not found in Google callback.");
             resp.sendRedirect(req.getContextPath() + "/login?error=google_failed");
            return;
        }

        try {
            // Exchange code for token
            GoogleAuthorizationCodeTokenRequest tokenRequest = flow.newTokenRequest(code)
                    .setRedirectUri(OAuthConfig.GOOGLE_REDIRECT_URI);
            GoogleTokenResponse tokenResponse = tokenRequest.execute();

            // Get user info from ID token (preferred way)
            GoogleIdToken idToken = tokenResponse.parseIdToken();
            GoogleIdToken.Payload payload = idToken.getPayload();
            String googleId = payload.getSubject(); // Google's unique ID for the user
            String email = payload.getEmail();
            boolean emailVerified = payload.getEmailVerified();
            String name = (String) payload.get("name");
            // String pictureUrl = (String) payload.get("picture"); // If needed
            // String locale = (String) payload.get("locale"); // If needed
            // String familyName = (String) payload.get("family_name"); // If needed
            // String givenName = (String) payload.get("given_name"); // If needed

            if (!emailVerified) {
                 log("Google email not verified for: " + email);
                 resp.sendRedirect(req.getContextPath() + "/login?error=email_not_verified");
                 return;
            }

            // Find or create user in our database
            User user = userDAO.getUserByGoogleId(googleId);
            if (user == null) {
                // User not found by Google ID, try to create one
                // Use email prefix as username, or generate one if needed
                String username = email.split("@")[0]; // Simple username generation
                // Check if generated username exists, append number if it does (simplistic collision handling)
                int suffix = 1;
                String originalUsername = username;
                while(userDAO.getUserByUsername(username) != null) {
                    username = originalUsername + suffix++;
                }

                user = new User(username, null, googleId); // No password for Google users
                boolean created = userDAO.createUser(user);
                if (!created) {
                    // This might happen if username collided despite checks, or other DB error
                     log("Failed to create new user in DB for Google ID: " + googleId + " with username: " + username);
                     resp.sendRedirect(req.getContextPath() + "/login?error=registration_failed");
                     return;
                }
                 log("Created new user from Google Sign-In: " + username);
            }

            // Establish session
            HttpSession session = req.getSession();
            User sessionUser = new User(user.getUsername(), null, user.getGoogleId()); // Store without hash
            session.setAttribute("user", sessionUser);
            log("User logged in via Google: " + user.getUsername());

            // Redirect to the main posts page
            resp.sendRedirect(req.getContextPath() + "/post");

        } catch (SQLException e) {
             log("Database error during Google callback processing.", e);
             resp.sendRedirect(req.getContextPath() + "/login?error=db_error");
        } catch (IOException e) {
            log("IOException during token exchange or user info retrieval.", e);
            resp.sendRedirect(req.getContextPath() + "/login?error=google_comm_error");
        } catch (Exception e) { // Catch unexpected errors
             log("Unexpected error during Google callback processing.", e);
             resp.sendRedirect(req.getContextPath() + "/login?error=internal_error");
        }
    }
} 