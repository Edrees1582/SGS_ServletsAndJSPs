<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="models.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Course students</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../partials/navbar.jsp" />

<div class="container">
    <% Course course = (Course) request.getAttribute("course");
       User user = (User) session.getAttribute("user");
    %>
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
            <% if (user.getUserType() == UserType.INSTRUCTOR) { %>
            <th scope="col">Grade</th>
            <th></th>
            <% } %>
        </tr>
        </thead>
        <tbody>
        <%
            List<User> students = (List<User>) request.getAttribute("students");
            HashMap<String, Grade> grades = (HashMap<String, Grade>) request.getAttribute("grades");
            for (User student : students) {
        %>
        <tr>
            <td><%= student.getId() %></td>
            <td><%= student.getName() %></td>
            <% if (user.getUserType() == UserType.INSTRUCTOR) { %>
            <td><%= grades.get(student.getId()).getGrade() %>
                <form action="${pageContext.request.contextPath}/editGrade" method="GET">
                    <input type="hidden" name="courseId" value="<%= course.getId() %>">
                    <input type="hidden" name="studentId" value="<%= student.getId() %>">
                    <input type="hidden" name="grade" value="<%= grades.get(student.getId()).getGrade() %>">
                    <button class="btn btn-secondary">Edit</button>
                </form>
            </td>

            <td>
                <form action="${pageContext.request.contextPath}/studentEnrollment" method="POST">
                    <input type="hidden" name="courseId" value="<%= course.getId() %>">
                    <input type="hidden" name="studentId" value="<%= student.getId() %>">
                    <input type="hidden" value="unEnroll" name="enrollment">
                    <button class="btn btn-danger">Un-enroll</button>
                </form>
            </td>
            <% } %>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<% if (user.getUserType() == UserType.INSTRUCTOR) { %>
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

<div class="container">
    <form action="${pageContext.request.contextPath}/studentEnrollment" method="POST">
        <label for="studentId">
            <div class="form-group">
                <label for="studentSelectEnroll">Select a student:</label>
                <select class="form-control" id="studentSelectEnroll" name="studentId" required>
                    <option selected disabled value="">Select a student</option>
                    <%
                        List<User> allOtherStudents = (List<User>) request.getAttribute("allOtherStudents");
                        for (User student : allOtherStudents) {
                    %>
                        <option value="<%= student.getId() %>" name="studentId" id="studentId"><%= student.getId() %> - <%= student.getName() %></option>
                    <% } %>
                </select>
            </div>
        </label>
        <input type="hidden" value="<%= course.getId() %>" name="courseId">
        <input type="hidden" value="enroll" name="enrollment">
        <button class="btn btn-success">Enroll student</button>
    </form>
</div>
<% } %>

<% if (user.getUserType() == UserType.ADMIN) { %>
<div class="container">
    <form action="${pageContext.request.contextPath}/courseForm" method="GET">
        <input type="hidden" value="<%= course.getId() %>" name="courseId">
        <input type="hidden" value="<%= course.getTitle() %>" name="title">
        <input type="hidden" value="<%= course.getInstructorId() %>" name="instructorId">
        <button class="btn btn-warning">Update course</button>
    </form>
</div>
<% } %>

<div class="container">
    <a class="btn btn-secondary" href="/<%= user.getUserType().toString().toLowerCase() %>">Back</a>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
