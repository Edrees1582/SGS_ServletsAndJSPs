<%@ page import="models.Course" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Instructor</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../partials/navbar.jsp" />
<div class="container">
    <h2>Courses List</h2>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Course ID</th>
            <th scope="col">Title</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Course> courses = (List<Course>) request.getAttribute("courses");
            for (Course course : courses) {
        %>
        <tr>
            <td><%= course.getId() %></td>
            <td><%= course.getTitle() %></td>
            <td>
                <form action="${pageContext.request.contextPath}/course/" method="POST">
                    <input type="hidden" name="courseId" value="<%= course.getId() %>">
                    <button class="btn btn-success" name="courseAction" value="get">Get course</button>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
