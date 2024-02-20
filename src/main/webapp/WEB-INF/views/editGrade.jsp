<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit grade</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../partials/navbar.jsp" />

<div class="container">
    <form action="${pageContext.request.contextPath}/editGrade" method="POST">
        <label>
            Grade
            <input type="text" name="grade" value="<%= request.getParameter("grade") %>" required min="0.0" max="100.0">
            <input type="hidden" name="courseId" value="<%= request.getParameter("courseId") %>">
            <input type="hidden" name="studentId" value="<%= request.getParameter("studentId") %>">
        </label>
        <button class="btn btn-success">Save</button>
    </form>
    <a class="btn btn-secondary" href="/course/<%= request.getParameter("courseId") %>">Cancel</a>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
