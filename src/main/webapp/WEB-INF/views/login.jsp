<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ログイン - Mutter</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/WEB-INF/fragments/header.jspf" %>

    <%-- Use login-container for specific width --%>
    <div class="container login-container">
        <h2>ログイン</h2>
        <c:if test="${not empty error}">
            <%-- Use new error style --%>
            <div class="error-message">${error}</div> 
        </c:if>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="username">ユーザー名：</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">パスワード：</label>
                <input type="password" id="password" name="password" required>
            </div>
             <%-- Use new button style --%>
            <button type="submit" class="btn btn-primary">ログイン</button>
        </form>
    </div>
</body>
</html> 