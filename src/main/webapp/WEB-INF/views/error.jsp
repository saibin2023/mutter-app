<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>エラー - Mutter</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/WEB-INF/fragments/header.jspf" %>

    <div class="container error-page-container">
        <h1><c:out value="${requestScope['jakarta.servlet.error.status_code']}" /> エラー</h1>
        
        <div class="error-message" style="margin-top: 20px; margin-bottom: 30px;">
            <c:choose>
                <c:when test="${requestScope['jakarta.servlet.error.status_code'] == 404}">
                    お探しのページは見つかりませんでした。
                </c:when>
                <c:when test="${requestScope['jakarta.servlet.error.status_code'] == 403}">
                     この操作を行う権限がありません。
                </c:when>
                <c:when test="${requestScope['jakarta.servlet.error.status_code'] == 500}">
                     サーバー内部でエラーが発生しました。
                </c:when>
                <c:otherwise>
                    予期せぬエラーが発生しました: <c:out value="${requestScope['jakarta.servlet.error.message']}" />
                </c:otherwise>
            </c:choose>
        </div>
        
        <a href="${pageContext.request.contextPath}/post" class="btn btn-primary">投稿一覧に戻る</a>
    </div>
</body>
</html> 