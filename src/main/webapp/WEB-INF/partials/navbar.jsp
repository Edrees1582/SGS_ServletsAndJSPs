<%@ page import="models.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="bg-dark-subtle">
    <nav class="navbar bg-body-tertiary ">
        <div class="container-fluid">
            <a class="navbar-brand">Student Grading System</a>
            <% if (session.getAttribute("user") != null) { %>
            <div class="d-flex">
                <% User user = (User) session.getAttribute("user"); %>
                <div class="nav-link disabled" aria-disabled="true">Logged in as: <%= user.getId() %> - <%= user.getName() %> (<%= user.getUserType() %>)</div>
                <form action="${pageContext.request.contextPath}/logout" method="POST">
                    <button class="btn btn-danger">Logout</button>
                </form>
            </div>
            <% } else {
                response.sendRedirect("/login");
            } %>
        </div>
    </nav>
</div>
