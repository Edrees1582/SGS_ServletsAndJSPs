<%@ page import="users.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<nav class="navbar bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand">Student Grading System</a>
        <% if (session.getAttribute("user") != null) { %>
        <%= session.getAttribute("user").toString() %>
        <div class="d-flex">
            <% User user = (User) session.getAttribute("user"); %>
            User name: <%= user.getName() %> (<%= user.getUserType() %>)
            <form action="${pageContext.request.contextPath}/logout" method="POST">
                <button class="btn btn-danger">Logout</button>
            </form>
        </div>

        <% } else {
            response.sendRedirect("/login");
        } %>
    </div>
</nav>
