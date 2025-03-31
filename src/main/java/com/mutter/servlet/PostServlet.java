package com.mutter.servlet;

import com.mutter.model.Post;
import com.mutter.model.User;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/posts")
public class PostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        ServletContext context = getServletContext();
        List<Post> posts = (List<Post>) context.getAttribute("posts");
        
        if (posts == null) {
            posts = new ArrayList<>();
            context.setAttribute("posts", posts);
        }
        
        req.setAttribute("posts", posts);
        req.getRequestDispatcher("/WEB-INF/views/posts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String title = req.getParameter("title");
        String content = req.getParameter("content");

        if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            req.setAttribute("error", "タイトルと内容を入力してください。");
            doGet(req, resp);
            return;
        }

        ServletContext context = getServletContext();
        List<Post> posts = (List<Post>) context.getAttribute("posts");
        if (posts == null) {
            posts = new ArrayList<>();
            context.setAttribute("posts", posts);
        }

        int newId = posts.size() + 1;
        Post post = new Post(newId, title, content, user.getUsername());
        posts.add(post);

        resp.sendRedirect(req.getContextPath() + "/posts");
    }
} 