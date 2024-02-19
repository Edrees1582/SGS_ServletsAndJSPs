<%@ page import="users.User" %>
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
