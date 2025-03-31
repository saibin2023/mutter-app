<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>投稿一覧</title>
    <style>
        body {
            font-family: Arial, sans-serif;
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
        .post-form {
            margin-bottom: 30px;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 5px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="text"], textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        textarea {
            height: 100px;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
        .post {
            border-bottom: 1px solid #eee;
            padding: 15px 0;
        }
        .post:last-child {
            border-bottom: none;
        }
        .post-title {
            font-size: 1.2em;
            margin-bottom: 10px;
            color: #333;
        }
        .post-meta {
            font-size: 0.9em;
            color: #666;
            margin-bottom: 10px;
        }
        .post-content {
            color: #444;
        }
        .error {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h2>投稿一覧</h2>
            <div>
                ようこそ、${sessionScope.user.username}さん
                <a href="${pageContext.request.contextPath}/login" style="margin-left: 10px;">ログアウト</a>
            </div>
        </div>

        <div class="post-form">
            <h3>新規投稿</h3>
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            <form action="${pageContext.request.contextPath}/posts" method="post">
                <div class="form-group">
                    <label for="title">タイトル：</label>
                    <input type="text" id="title" name="title" required>
                </div>
                <div class="form-group">
                    <label for="content">内容：</label>
                    <textarea id="content" name="content" required></textarea>
                </div>
                <button type="submit">投稿する</button>
            </form>
        </div>

        <div class="posts">
            <c:forEach items="${posts}" var="post">
                <div class="post">
                    <div class="post-title">${post.title}</div>
                    <div class="post-meta">
                        投稿者：${post.author} | 
                        投稿日時：${post.createTime}
                    </div>
                    <div class="post-content">${post.content}</div>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html> 