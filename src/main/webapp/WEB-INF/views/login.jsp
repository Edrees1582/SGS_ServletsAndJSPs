<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand">Student Grading System</a>
  </div>
</nav>

<div class="container">
  <form action="${pageContext.request.contextPath}/login" method="POST">
    <label for="userType">Login as:</label>
    <select class="form-select" id="userType" name="userType" required>
      <option value="admin">Admin</option>
      <option value="instructor">Instructor</option>
      <option value="student">Student</option>
    </select>

    <label for="userId">User id:</label>
    <input type="text" placeholder="user id" name="userId" id="userId" required>

    <label for="password">Password:</label>
    <input type="password" placeholder="password" name="password" id="password" required>

    <button class="btn btn-primary">Login</button>
  </form>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
