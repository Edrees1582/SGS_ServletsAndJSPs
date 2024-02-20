package servlets;

import dao.MySQLCourseDao;
import dao.MySQLUserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.UserType;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {
    private MySQLUserDao mySQLUserDao;
    private MySQLCourseDao mySQLCourseDao;

    @Override
    public void init() {
        mySQLUserDao = new MySQLUserDao();
        mySQLCourseDao = new MySQLCourseDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            request.setAttribute("students", mySQLUserDao.getAll(UserType.STUDENT));
            request.setAttribute("instructors", mySQLUserDao.getAll(UserType.INSTRUCTOR));
            request.setAttribute("courses", mySQLCourseDao.getAll());
            request.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(request, response);
        }
        else response.sendRedirect("/login");
    }
}
