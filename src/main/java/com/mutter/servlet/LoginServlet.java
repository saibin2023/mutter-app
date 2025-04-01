package com.mutter.servlet;

import java.io.IOException;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import com.mutter.dao.UserDAO;
import com.mutter.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        // Set response encoding
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Check if redirected from successful signup
        if ("success".equals(req.getParameter("signup"))) {
            req.setAttribute("message", "登録が成功しました！ログインしてください。"); // Japanese
        }

        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        // Set character encoding first
        req.setCharacterEncoding("UTF-8");
        // Also set response encoding
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        String username = req.getParameter("username");
        String submittedPassword = req.getParameter("password");

        if (username == null || username.trim().isEmpty() || submittedPassword == null || submittedPassword.isEmpty()) {
             req.setAttribute("error", "ユーザー名とパスワードは必須です。"); // Japanese
             req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
             return;
        }

        try {
            User user = userDAO.getUserByUsername(username.trim());
            
            // Check if user exists and compare hashed password
            if (user != null && user.getPassword() != null && BCrypt.checkpw(submittedPassword, user.getPassword())) { // Added null check for password
                // Passwords match!
                HttpSession session = req.getSession();
                // Store the User object WITHOUT the password hash in session for security
                User sessionUser = new User(user.getUsername(), null, user.getGoogleId()); // Include googleId if present
                session.setAttribute("user", sessionUser); 
                resp.sendRedirect(req.getContextPath() + "/post");
            } else {
                // Invalid username or password doesn't match
                req.setAttribute("error", "ユーザー名またはパスワードが間違っています。"); // Japanese
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            log("Database error during login for user: " + username, e);
            req.setAttribute("error", "ログイン中にエラーが発生しました。後でもう一度お試しください。"); // Japanese
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            // Handle cases where the stored password hash is invalid (e.g., corrupted)
            log("Invalid stored password format for user: " + username, e);
            req.setAttribute("error", "ログインエラーが発生しました。サポートにお問い合わせください。"); // Japanese
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
} 