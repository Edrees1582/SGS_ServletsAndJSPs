<%@ page import="models.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Course" %>
<%@ page import="users.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Course students</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../partials/navbar.jsp" />

<div class="container">
    <% Course course = (Course) request.getAttribute("course"); %>
    <h2>Course details:</h2>
    <p>ID: <%= course.getId() %></p>
    <p>Title: <%= course.getTitle() %></p>
    <p>Instructor ID: <%= course.getInstructorId() %></p>
    <p>Instructor name: <%= ((User) request.getAttribute("instructor")).getName() %></p>
    <h2>Students List</h2>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Student ID</th>
            <th scope="col">Name</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Student> students = (List<Student>) request.getAttribute("students");
            for (Student student : students) {
        %>
        <tr>
            <td><%= student.getId() %></td>
            <td><%= student.getName() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
