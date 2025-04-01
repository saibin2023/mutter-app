package com.mutter.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.mutter.dao.PostDAO;
import com.mutter.model.Post;
import com.mutter.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/post/*")
@MultipartConfig
public class PostServlet extends HttpServlet {
    private PostDAO postDAO;
    private static final String UPLOAD_DIR = "uploads/images";

    @Override
    public void init() throws ServletException {
        postDAO = new PostDAO();
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Post> posts = postDAO.getAllPosts();
                request.setAttribute("posts", posts);
                request.getRequestDispatcher("/WEB-INF/views/posts.jsp").forward(request, response);
            } else if (pathInfo.equals("/new")) {
                request.getRequestDispatcher("/WEB-INF/views/new_post.jsp").forward(request, response);
            } else if (pathInfo.startsWith("/view/")) {
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
            log("Database error in doGet", e);
            throw new ServletException("Database error", e);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid post ID format");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        User user = (User) session.getAttribute("user");
        String author = user.getUsername();
        String imagePath = null;

        if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            request.setAttribute("error", "タイトルと内容は必須です。");
            request.getRequestDispatcher("/WEB-INF/views/new_post.jsp").forward(request, response);
            return;
        }

        try {
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                String submittedFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                if (submittedFileName != null && !submittedFileName.isEmpty()) {
                    String uniqueID = UUID.randomUUID().toString();
                    String fileExtension = submittedFileName.substring(submittedFileName.lastIndexOf("."));
                    String fileName = uniqueID + fileExtension;
                    
                    String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }
                    
                    File file = new File(uploadPath + File.separator + fileName);
                    try (InputStream fileContent = filePart.getInputStream()) {
                        Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        imagePath = UPLOAD_DIR + "/" + fileName;
                    } catch (IOException e) {
                        log("Error saving uploaded file: " + fileName, e);
                        request.setAttribute("error", "画像のアップロード中にエラーが発生しました。");
                        request.getRequestDispatcher("/WEB-INF/views/new_post.jsp").forward(request, response);
                        return;
                    }
                }
            }

            Post post = new Post(title, content, author, imagePath);
            postDAO.createPost(post);
            response.sendRedirect(request.getContextPath() + "/post");

        } catch (SQLException e) {
            log("Database error in doPost", e);
            throw new ServletException("Database error", e);
        } catch (Exception e) {
            log("Error processing multipart request", e);
            request.setAttribute("error", "リクエストの処理中にエラーが発生しました。");
            request.getRequestDispatcher("/WEB-INF/views/new_post.jsp").forward(request, response);
        }
    }
} 