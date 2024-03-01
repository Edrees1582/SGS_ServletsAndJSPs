package servlets;

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
        User user = Authentication.authenticateUser(request.getParameter("userId"), request.getParameter("password"));
        if (user != null) {
            System.out.println(user);
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/" + user.getUserType().toString().toLowerCase());
        }
        else response.sendRedirect("/login");
    }
}
