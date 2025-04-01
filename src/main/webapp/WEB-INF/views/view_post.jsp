<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${post.title} - Mutter</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/WEB-INF/fragments/header.jspf" %>

    <div class="container post-view"> <%-- Add post-view class for specific styling --%>
        <div class="post">
            <h1 class="post-title">${post.title}</h1>
            <div class="post-meta">
                投稿者：${post.author} | 投稿日時：${post.createTime}
            </div>
            <%-- Display image if available --%>
            <c:if test="${not empty post.imagePath}">
                <img src="${pageContext.request.contextPath}/${post.imagePath}" alt="投稿画像" class="post-image">
            </c:if>
            <div class="post-content">
                <%-- Use JSTL out tag to prevent potential XSS from post content --%>
                <c:out value="${post.content}" escapeXml="false"/>
            </div>
        </div>
        
        <%-- Use post-actions class for layout --%>
        <div class="post-actions">
            <a href="${pageContext.request.contextPath}/post" class="back-link">← 投稿一覧に戻る</a>
            
            <%-- Delete Button/Form - Only show if logged in user is the author --%>
            <c:if test="${sessionScope.user != null && sessionScope.user.username == post.author}">
                <form action="${pageContext.request.contextPath}/post" method="post" style="display: inline;" onsubmit="return confirmDelete();">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="postId" value="${post.id}">
                    <%-- Use new button style --%>
                    <button type="submit" class="btn btn-danger">削除</button>
                </form>
            </c:if>
        </div>
    </div>
    
    <script>
        function confirmDelete() {
            return confirm("この投稿を本当に削除しますか？ この操作は元に戻せません。");
        }
    </script>
</body>
</html> 