<%@ page import="models.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Course" %>
<%@ page import="users.User" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Student courses</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../partials/navbar.jsp" />

<div class="container">
    <% User student = (User) request.getAttribute("student"); %>
    <h2>Student details:</h2>
    <p>ID: <%= student.getId() %></p>
    <p>Title: <%= student.getName() %></p>
    <h2>Courses List</h2>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Course ID</th>
            <th scope="col">Title</th>
            <th scope="col">Instructor ID</th>
            <th scope="col">Instructor name</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Course> courses = (List<Course>) request.getAttribute("courses");
            HashMap<String, User> instructors = (HashMap<String, User>) request.getAttribute("instructors");
            for (Course course : courses) {
        %>
        <tr>
            <td><%= course.getId() %></td>
            <td><%= course.getTitle() %></td>
            <td><%= course.getInstructorId() %></td>
            <td><%= instructors.get(course.getInstructorId()).getName() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
