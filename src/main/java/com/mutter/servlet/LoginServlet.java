package com.mutter.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.mutter.model.User;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        // Set response encoding
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

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
        String password = req.getParameter("password");

        ServletContext context = getServletContext();
        Map<String, User> users = (Map<String, User>) context.getAttribute("users");
        
        if (users == null) {
            users = new HashMap<>();
            // 添加测试用户
            users.put("admin", new User("admin", "password"));
            context.setAttribute("users", users);
        }

        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/post");
        } else {
            req.setAttribute("error", "ユーザー名またはパスワードが間違っています。");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
} 