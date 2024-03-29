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
        User user = (User) request.getSession().getAttribute("user");

        if (user != null && user.getUserType() == UserType.ADMIN) {
            request.setAttribute("students", mySQLUserDao.getStudents());
            request.setAttribute("instructors", mySQLUserDao.getInstructors());
            request.setAttribute("courses", mySQLCourseDao.getAll());
            request.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(request, response);
        }
        else if (user == null) response.sendRedirect("/login");
        else response.sendRedirect("/errorHandler?errorCode=403&errorMessage=Not authorized");
    }
}
