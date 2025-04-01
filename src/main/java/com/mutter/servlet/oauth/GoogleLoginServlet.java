package com.mutter.servlet.oauth;

import java.io.IOException;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.mutter.config.OAuthConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login/google")
public class GoogleLoginServlet extends HttpServlet {

    private GoogleAuthorizationCodeFlow flow;

    @Override
    public void init() throws ServletException {
        try {
            // Load client secrets (In a real app, load from a secure location)
             GoogleClientSecrets.Details web = new GoogleClientSecrets.Details();
             web.setClientId(OAuthConfig.GOOGLE_CLIENT_ID);
             web.setClientSecret(OAuthConfig.GOOGLE_CLIENT_SECRET);
             GoogleClientSecrets clientSecrets = new GoogleClientSecrets().setWeb(web);

            flow = new GoogleAuthorizationCodeFlow.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    clientSecrets,
                    OAuthConfig.SCOPES)
                    .setAccessType("offline") // Request refresh token if needed later
                    .build();
        } catch (Exception e) {
            throw new ServletException("Failed to initialize Google Authorization Flow", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Generate the URL to redirect the user to Google for authentication
        String url = flow.newAuthorizationUrl()
                         .setRedirectUri(OAuthConfig.GOOGLE_REDIRECT_URI)
                         .build();
        
        // Redirect the user
        resp.sendRedirect(url);
    }
} 