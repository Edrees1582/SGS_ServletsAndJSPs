package servlets;

import dao.MySQLUserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import models.UserType;

import java.io.IOException;

@WebServlet(urlPatterns = "/courseForm")
public class CourseFormServlet extends HttpServlet {
    private MySQLUserDao mySQLUserDao;

    @Override
    public void init() {
        mySQLUserDao = new MySQLUserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null && user.getUserType() == UserType.ADMIN) {
            request.setAttribute("instructors", mySQLUserDao.getAll(UserType.INSTRUCTOR));
            request.getRequestDispatcher("/WEB-INF/views/editCourse.jsp").forward(request, response);
        }
        else if (user == null) response.sendRedirect("/login");
        else response.sendRedirect("/errorHandler?errorCode=403&errorMessage=Not authorized");
    }
}