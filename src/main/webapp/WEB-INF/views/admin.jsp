<%@ page import="users.User" %>
<%@ page import="models.Course" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Admin</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../partials/navbar.jsp" />

<a href="${pageContext.request.contextPath}/admins">Admin</a>
<a href="${pageContext.request.contextPath}/instructors">Instructors</a>
<a href="${pageContext.request.contextPath}/students">Students</a>
<a href="${pageContext.request.contextPath}/courses">Courses</a>

<form action="${pageContext.request.contextPath}/studentCourses" method="GET">
    <label for="studentId">
        Student ID:
        <div class="form-group">
            <label for="studentSelect">Select a student:</label>
            <select class="form-control" id="studentSelect" name="studentId">
                <option selected disabled>Select a student</option>
                <%
                    List<User> students = (List<User>) request.getAttribute("students");
                    for (User student : students) {
                %>
                <option value="<%= student.getId() %>" name="studentId" id="studentId"><%= student.getId() %> - <%= student.getName() %></option>
                <% } %>
            </select>
        </div>
    </label>
    <button>Get courses</button>
</form>

<form action="${pageContext.request.contextPath}/courseStudents" method="GET">
    <label for="courseId">
        Course ID:
        <div class="form-group">
            <label for="courseSelect">Select a course:</label>
            <select class="form-control" id="courseSelect" name="courseId">
                <option selected disabled>Select a course</option>
                <%
                    List<Course> courses = (List<Course>) request.getAttribute("courses");
                    for (Course course : courses) {
                %>
                <option value="<%= course.getId() %>" name="courseId" id="courseId"><%= course.getId() %> - <%= course.getTitle() %></option>
                <% } %>
            </select>
        </div>
    </label>
    <button>Get students</button>
</form>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
