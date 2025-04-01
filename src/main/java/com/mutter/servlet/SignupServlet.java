package com.mutter.servlet;

import java.io.IOException;
import java.sql.SQLException;

import com.mutter.dao.UserDAO;
import com.mutter.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

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
        // Forward to the signup JSP page
        req.getRequestDispatcher("/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        // Set request and response encoding
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Basic validation (should be more robust in a real app)
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            req.setAttribute("error", "ユーザー名とパスワードは必須です。"); // Japanese
            req.getRequestDispatcher("/signup.jsp").forward(req, resp);
            return;
        }
        
        username = username.trim();
        User newUser = new User(username, password);

        try {
            boolean created = userDAO.createUser(newUser);
            if (created) {
                // Redirect to login page after successful signup
                resp.sendRedirect(req.getContextPath() + "/login?signup=success");
            } else {
                // createUser returns false if user already exists (based on UserDAO implementation)
                req.setAttribute("error", "このユーザー名は既に使用されています。別のユーザー名を選択してください。"); // Japanese
                req.getRequestDispatcher("/signup.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            // Log the exception and show a generic error
            log("Database error during signup for user: " + username, e);
            req.setAttribute("error", "登録中にエラーが発生しました。後でもう一度お試しください。"); // Japanese
            req.getRequestDispatcher("/signup.jsp").forward(req, resp);
        }
    }
} 