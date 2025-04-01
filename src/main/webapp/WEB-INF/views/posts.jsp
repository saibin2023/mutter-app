<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>投稿一覧 - Mutter</title>
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
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .new-post-btn {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
        }
        .new-post-btn:hover {
            background-color: #45a049;
        }
        .post-list {
            list-style: none;
            padding: 0;
        }
        .post-item {
            border-bottom: 1px solid #eee;
            padding: 15px 0;
        }
        .post-item:last-child {
            border-bottom: none;
        }
        .post-title {
            font-size: 18px;
            color: #333;
            margin-bottom: 5px;
            text-decoration: none;
        }
        .post-title:hover {
            color: #0066cc;
        }
        .post-meta {
            color: #666;
            font-size: 14px;
        }
        /* Style for post image preview */
        .post-image-preview {
            max-width: 200px; /* Limit image size in list view */
            max-height: 150px;
            margin-top: 10px;
            display: block;
        }
        .logout-btn {
            background-color: #f44336;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
        }
        .logout-btn:hover {
            background-color: #da190b;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>投稿一覧</h1>
            <div>
                <a href="${pageContext.request.contextPath}/post/new" class="new-post-btn">新規投稿</a>
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">ログアウト</a>
            </div>
        </div>
        
        <ul class="post-list">
            <c:forEach items="${posts}" var="post">
                <li class="post-item">
                    <a href="${pageContext.request.contextPath}/post/view/${post.id}" class="post-title">${post.title}</a>
                    <%-- Display image if available --%>
                    <c:if test="${not empty post.imagePath}">
                        <img src="${pageContext.request.contextPath}/${post.imagePath}" alt="Post image" class="post-image-preview">
                    </c:if>
                    <div class="post-meta">
                        投稿者：${post.author} | 投稿日時：${post.createTime}
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</body>
</html> 