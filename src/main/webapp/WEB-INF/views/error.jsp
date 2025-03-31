<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>エラー</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            text-align: center;
        }
        h1 {
            color: #d32f2f;
        }
        .error-message {
            color: #666;
            margin: 20px 0;
        }
        a {
            color: #4CAF50;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>エラーが発生しました</h1>
        <div class="error-message">
            <c:choose>
                <c:when test="${pageContext.errorData.statusCode == 404}">
                    ページが見つかりませんでした。
                </c:when>
                <c:when test="${pageContext.errorData.statusCode == 500}">
                    サーバーエラーが発生しました。
                </c:when>
                <c:otherwise>
                    予期せぬエラーが発生しました。
                </c:otherwise>
            </c:choose>
        </div>
        <a href="${pageContext.request.contextPath}/login">トップページに戻る</a>
    </div>
</body>
</html> 