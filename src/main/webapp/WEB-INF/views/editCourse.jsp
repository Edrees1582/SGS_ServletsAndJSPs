<%@ page import="models.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Edit course</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../partials/navbar.jsp" />

<div class="container">
    <form action="${pageContext.request.contextPath}/course/" method="POST">
        <label>
            <label for="courseId">Course id:</label>
            <input type="text" name="courseId" id="courseId" value="<%= request.getParameter("courseId") %>" disabled>
            <input type="hidden" name="courseId" value="<%= request.getParameter("courseId") %>">

            <label for="title">Title:</label>
            <input type="text" name="title" id="title" value="<%= request.getParameter("title") %>">

            <label for="instructorId">
                <div class="form-group">
                    <label for="instructorId">Select an instructor:</label>
                    <select class="form-control" id="instructorId" name="instructorId" required>
                        <option selected disabled value="">Select an instructor</option>
                        <%
                            List<User> instructors = (List<User>) request.getAttribute("instructors");
                            for (User instructor : instructors) {
                                if (instructor.getId().equals(request.getParameter("instructorId"))) {
                        %>
                                <option value="<%= instructor.getId() %>" name="instructorId" selected><%= instructor.getId() %> - <%= instructor.getName() %></option>
                            <% } else { %>
                                <option value="<%= instructor.getId() %>" name="instructorId"><%= instructor.getId() %> - <%= instructor.getName() %></option>
                            <% } %>
                        <% } %>
                    </select>
                </div>
            </label>
        </label>
        <button class="btn btn-success" name="courseAction" value="update">Save</button>
    </form>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/course/<%= request.getParameter("courseId") %>">Cancel</a>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
