<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="POST">
  <label for="userType">Login as:</label>
  <select class="form-select" id="userType" name="userType">
    <option value="admin">Admin</option>
    <option value="instructor">Instructor</option>
    <option value="student">Student</option>
  </select>

  <br /><br />

  <label>
    User id:
    <input type="text" placeholder="user id" name="userId" required>
  </label>

  <label>
    Password:
    <input type="password" placeholder="password" name="password" required>
  </label>

  <button>Login</button>
</form>
</body>
</html>
