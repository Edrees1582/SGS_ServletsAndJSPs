<%@ page import="models.User" %>
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

<div class="container">
    <div class="container">
        Add new user:
        <form action="${pageContext.request.contextPath}/createUser" method="POST">
            <label for="newUserType">User type:</label>
            <select class="form-select" id="newUserType" name="newUserType" required>
                <option value="instructor">Instructor</option>
                <option value="student">Student</option>
            </select>

            <label for="newUserId">User id:</label>
            <input type="text" placeholder="user id" name="newUserId" id="newUserId" required>

            <label for="newName">Name:</label>
            <input type="text" placeholder="name" name="newName" id="newName" required>

            <label for="newPassword">Password:</label>
            <input type="text" placeholder="password" name="newPassword" id="newPassword" required>

            <button class="btn btn-success">Add new user</button>
        </form>
    </div>

    <div class="container">
        Add new course:
        <form action="${pageContext.request.contextPath}/createCourse" method="POST">
            <label for="newCourseId">Course id:</label>
            <input type="text" placeholder="course id" name="newCourseId" id="newCourseId" required>

            <label for="newTitle">Title:</label>
            <input type="text" placeholder="title" name="newTitle" id="newTitle" required>

            <label for="instructorId">
                <div class="form-group">
                    <label for="instructorId">Select an instructor:</label>
                    <select class="form-control" id="instructorId" name="instructorId" required>
                        <option selected disabled value="">Select an instructor</option>
                        <%
                            List<User> instructors = (List<User>) request.getAttribute("instructors");
                            for (User instructor : instructors) {
                        %>
                        <option value="<%= instructor.getId() %>" name="instructorId"><%= instructor.getId() %> - <%= instructor.getName() %></option>
                        <% } %>
                    </select>
                </div>
            </label>

            <button class="btn btn-success">Add new course</button>
        </form>
    </div>

    <div class="container">
        <form action="${pageContext.request.contextPath}/instructor" method="POST">
            <label for="instructorIdInfo">
                <div class="form-group">
                    <label for="instructorIdInfo">Select an instructor:</label>
                    <select class="form-control" id="instructorIdInfo" name="instructorId" required>
                        <option selected disabled value="">Select an instructor</option>
                        <%
                            for (User instructor : instructors) {
                        %>
                        <option value="<%= instructor.getId() %>" name="instructorId"><%= instructor.getId() %> - <%= instructor.getName() %></option>
                        <% } %>
                    </select>
                </div>
            </label>
            <button class="btn btn-warning" name="instructorAction" value="updateForm">Update instructor</button>
            <button class="btn btn-danger" name="instructorAction" value="delete">Delete instructor</button>
        </form>
    </div>

    <div class="container">
        <form action="${pageContext.request.contextPath}/student/" method="POST">
            <label for="studentId">
                <div class="form-group">
                    <label for="studentId">Select a student:</label>
                    <select class="form-control" id="studentId" name="studentId" required>
                        <option selected disabled value="">Select a student</option>
                        <%
                            List<User> students = (List<User>) request.getAttribute("students");
                            for (User student : students) {
                        %>
                        <option value="<%= student.getId() %>" name="studentId"><%= student.getId() %> - <%= student.getName() %></option>
                        <% } %>
                    </select>
                </div>
            </label>
            <button class="btn btn-success" name="studentAction" value="get">Get student</button>
            <button class="btn btn-danger" name="studentAction" value="delete">Delete student</button>
        </form>
    </div>

    <div class="container">
        <form action="${pageContext.request.contextPath}/course/" method="POST">
            <label for="courseId">
                <div class="form-group">
                    <label for="courseSelect">Select a course:</label>
                    <select class="form-control" id="courseSelect" name="courseId" required>
                        <option selected disabled value="">Select a course</option>
                        <%
                            List<Course> courses = (List<Course>) request.getAttribute("courses");
                            for (Course course : courses) {
                        %>
                        <option value="<%= course.getId() %>" name="courseId" id="courseId"><%= course.getId() %> - <%= course.getTitle() %></option>
                        <% } %>
                    </select>
                </div>
            </label>
            <button class="btn btn-success" name="courseAction" value="get">Get course</button>
            <button class="btn btn-danger" name="courseAction" value="delete">Delete course</button>
        </form>
    </div>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
