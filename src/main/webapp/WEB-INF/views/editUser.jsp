<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Edit user</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../partials/navbar.jsp" />

<div class="container">
    <form action="${pageContext.request.contextPath}/<%= request.getAttribute("userType") %>" method="POST">
        <label for="userId">User id:</label>
        <input type="text" id="userId" value="<%= request.getAttribute("userId") %>" disabled>
        <input type="hidden" name="userId" value="<%= request.getAttribute("userId") %>">

        <label for="password">Password:</label>
        <input type="text" name="password" id="password" value="">
        <input type="hidden" name="password" value="<%= request.getAttribute("password") %>">

        <label for="name">Name:</label>
        <input type="text" name="name" id="name" value="<%= request.getAttribute("name") %>">
        <button class="btn btn-success" name="<%= request.getAttribute("userType") %>Action" value="update">Save</button>
    </form>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin">Cancel</a>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
