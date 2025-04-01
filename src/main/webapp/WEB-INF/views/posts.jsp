<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>投稿一覧 - Mutter</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/WEB-INF/fragments/header.jspf" %>

    <div class="container">
        <div class="action-header">
            <h1>投稿一覧</h1>
        </div>
        
        <ul class="post-list">
            <c:forEach items="${posts}" var="post">
                <li class="post-item">
                    <a href="${pageContext.request.contextPath}/post/view/${post.id}" class="post-title">${post.title}</a>
                    <%-- Display image if available --%>
                    <c:if test="${not empty post.imagePath}">
                        <img src="${pageContext.request.contextPath}/${post.imagePath}" alt="投稿画像" class="post-image-preview">
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