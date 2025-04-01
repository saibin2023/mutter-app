<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新規投稿 - Mutter</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/WEB-INF/fragments/header.jspf" %>

    <div class="container">
        <h1>新規投稿</h1>
        
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/post" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="title">タイトル：</label>
                <input type="text" id="title" name="title" required>
            </div>
            <div class="form-group">
                <label for="content">内容：</label>
                <textarea id="content" name="content" required></textarea>
            </div>
            <div class="form-group">
                <label for="image">画像 (任意)：</label>
                <input type="file" id="image" name="image" accept="image/*">
            </div>
            <button type="submit" class="btn btn-success">投稿</button>
            <a href="${pageContext.request.contextPath}/post" class="btn btn-secondary" style="margin-left: 10px;">キャンセル</a>
        </form>
    </div>
</body>
</html> 