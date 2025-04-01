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
        
        <%-- Display generic error message from servlet --%>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div> 
        </c:if>
        <%-- Display success message from signup redirect --%>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
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
            <button type="submit" class="btn btn-primary">ログイン</button>
        </form>
        
        <hr/>
        
        <%-- Google Sign-In Button --%>
        <div class="text-center mt-3">
             <a href="${pageContext.request.contextPath}/login/google" class="btn btn-google">
                 <%-- Assuming you might add an icon library later --%>
                 <%-- <i class="fab fa-google"></i> --%> Googleでサインイン
             </a>
        </div>
        
        <%-- Link to signup page --%>
        <p class="mt-3 text-center">
            アカウントをお持ちでないですか？ <a href="${pageContext.request.contextPath}/signup">新規登録はこちら</a>
        </p>
    </div>
</body>
</html> 