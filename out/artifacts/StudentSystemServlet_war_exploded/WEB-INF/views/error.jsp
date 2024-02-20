<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../partials/navbar.jsp" />

<div class="container">
    <h1>Error</h1>
    <%
        String errorCode = (String) request.getAttribute("errorCode");
        String errorMessage = (String) request.getAttribute("errorMessage");
    %>

    <p>Error code: <%= errorCode %></p>
    <p>Error message: <%= errorMessage %></p>

    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Home</a>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>