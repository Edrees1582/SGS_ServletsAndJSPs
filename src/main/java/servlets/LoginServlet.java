package servlets;

import dao.MySQLCourseDao;
import dao.MySQLUserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import models.UserType;
import util.Authentication;

import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private Authentication authentication;

    @Override
    public void init() {
        authentication = new Authentication();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        else if (user.getUserType() == UserType.ADMIN) response.sendRedirect("/admin");
        else if (user.getUserType() == UserType.INSTRUCTOR) response.sendRedirect("/instructor");
        else if (user.getUserType() == UserType.STUDENT) response.sendRedirect("/student");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        int chosenUserTypeInt = switch (request.getParameter("userType")) {
            case "admin" -> 1;
            case "instructor" -> 2;
            case "student" -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + request.getParameter("userType"));
        };

        User user = authentication.authenticateUser(chosenUserTypeInt, request.getParameter("userId"), request.getParameter("password"));
        if (user != null) {
            System.out.println(user);
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/" + request.getParameter("userType"));
        }
        else response.sendRedirect("/login");
    }
}
