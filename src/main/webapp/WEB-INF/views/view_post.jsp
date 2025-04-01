<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${post.title} - Mutter</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .post {
            margin-bottom: 20px;
        }
        .post-title {
            font-size: 24px;
            color: #333;
            margin-bottom: 10px;
        }
        .post-meta {
            color: #666;
            font-size: 14px;
            margin-bottom: 15px;
        }
        .post-content {
            color: #444;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            color: #0066cc;
            text-decoration: none;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="post">
            <h1 class="post-title">${post.title}</h1>
            <div class="post-meta">
                投稿者：${post.author} | 投稿日時：${post.createTime}
            </div>
            <div class="post-content">
                ${post.content}
            </div>
        </div>
        <a href="${pageContext.request.contextPath}/post" class="back-link">投稿一覧に戻る</a>
    </div>
</body>
</html> 