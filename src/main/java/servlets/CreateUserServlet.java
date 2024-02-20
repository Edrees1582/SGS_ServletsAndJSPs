package servlets;

import dao.MySQLUserDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.UserType;


import java.io.IOException;

@WebServlet(urlPatterns = "/createUser")
public class CreateUserServlet extends HttpServlet {
    private MySQLUserDao mySQLUserDao;

    @Override
    public void init() {
        mySQLUserDao = new MySQLUserDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        UserType chosenNewUserType = switch (request.getParameter("newUserType")) {
            case "admin" -> UserType.ADMIN;
            case "instructor" -> UserType.INSTRUCTOR;
            case "student" -> UserType.STUDENT;
            default -> throw new IllegalStateException("Unexpected value: " + request.getParameter("newUserType"));
        };

        mySQLUserDao.save(request.getParameter("newUserId"), request.getParameter("newPassword"), request.getParameter("newName"), chosenNewUserType);
        response.sendRedirect("/admin");
    }
}
