<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="models.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Student</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../partials/navbar.jsp" />
<div class="container">
    <% User student = (User) request.getAttribute("student"); %>
    <h2>Student details:</h2>
    <p>ID: <%= student.getId() %></p>
    <p>Name: <%= student.getName() %></p>
    <h2>Courses List</h2>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Course ID</th>
            <th scope="col">Title</th>
            <th scope="col">Instructor ID</th>
            <th scope="col">Instructor name</th>
            <th scope="col">Grade</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Course> courses = (List<Course>) request.getAttribute("courses");
            HashMap<String, User> instructors = (HashMap<String, User>) request.getAttribute("instructors");
            HashMap<String, Grade> grades = (HashMap<String, Grade>) request.getAttribute("grades");
            for (Course course : courses) {
        %>
        <tr>
            <td><%= course.getId() %></td>
            <td><%= course.getTitle() %></td>
            <td><%= course.getInstructorId() %></td>
            <td><%= instructors.get(course.getInstructorId()).getName() %></td>
            <td><%= grades.get(course.getId()).getGrade() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<div class="container">
    <h2>Grades statistics</h2>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Average</th>
            <th scope="col">Median</th>
            <th scope="col">Highest</th>
            <th scope="col">Lowest</th>
        </tr>
        </thead>
        <tbody>
        <%
            Statistics statistics = (Statistics) request.getAttribute("statistics");
        %>
        <tr>
            <td><%= String.format("%.2f", statistics.getAverage()) %></td>
            <td><%= String.format("%.2f", statistics.getMedian()) %></td>
            <td><%= String.format("%.2f", statistics.getHighest()) %></td>
            <td><%= String.format("%.2f", statistics.getLowest()) %></td>
        </tr>
        </tbody>
    </table>
</div>

<% User user = (User) request.getSession().getAttribute("user"); %>

<% if (user.getUserType() == UserType.ADMIN) { %>
<div class="container">
    <form action="${pageContext.request.contextPath}/student/" method="POST">
        <input type="hidden" value="<%= student.getId() %>" name="studentId">
        <button class="btn btn-warning" name="studentAction" value="updateForm">Update student</button>
    </form>
</div>
<% } %>

<% if (user.getUserType() != UserType.STUDENT) {%>
<div class="container">
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin">Back</a>
</div>
<% } %>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
