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

@WebServlet(urlPatterns = "/instructor")
public class InstructorServlet extends HttpServlet {
    private MySQLCourseDao mySQLCourseDao;
    private MySQLUserDao mySQLUserDao;

    @Override
    public void init() {
        mySQLCourseDao = new MySQLCourseDao();
        mySQLUserDao = new MySQLUserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            request.setAttribute("courses", mySQLCourseDao.getAllByInstructorId(user.getId()));
            request.setAttribute("students", mySQLUserDao.getAll(UserType.STUDENT));
            request.getRequestDispatcher("/WEB-INF/views/instructor.jsp").forward(request, response);
        }
        else response.sendRedirect("/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user.getUserType() == UserType.ADMIN) {
            if (request.getParameter("instructorAction").equals("update")) {

            }
            else if (request.getParameter("instructorAction").equals("delete")) {
                mySQLUserDao.delete(request.getParameter("instructorId"), UserType.INSTRUCTOR);
                response.sendRedirect("/admin");
            }
        }
        else response.sendRedirect("/errorHandler?errorCode=403&errorMessage=Not authorized");
    }
}

