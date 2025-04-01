package com.mutter.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.mutter.dao.PostDAO;
import com.mutter.model.Post;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/post/*")
public class PostServlet extends HttpServlet {
    private PostDAO postDAO;

    @Override
    public void init() throws ServletException {
        postDAO = new PostDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // 显示所有帖子
                List<Post> posts = postDAO.getAllPosts();
                request.setAttribute("posts", posts);
                request.getRequestDispatcher("/WEB-INF/views/posts.jsp").forward(request, response);
            } else if (pathInfo.equals("/new")) {
                // 显示发帖表单
                request.getRequestDispatcher("/WEB-INF/views/new_post.jsp").forward(request, response);
            } else if (pathInfo.startsWith("/view/")) {
                // 查看单个帖子
                int id = Integer.parseInt(pathInfo.substring(6));
                Post post = postDAO.getPost(id);
                if (post != null) {
                    request.setAttribute("post", post);
                    request.getRequestDispatcher("/WEB-INF/views/view_post.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Post not found");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid path");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        com.mutter.model.User user = (com.mutter.model.User) session.getAttribute("user");
        String author = user.getUsername();

        if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            request.setAttribute("error", "タイトルと内容は必須です。");
            request.getRequestDispatcher("/WEB-INF/views/new_post.jsp").forward(request, response);
            return;
        }

        try {
            Post post = new Post(title, content, author);
            postDAO.createPost(post);
            response.sendRedirect(request.getContextPath() + "/post");
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
} 