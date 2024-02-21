package servlets;

import dao.MySQLCourseDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import models.UserType;

import java.io.IOException;

@WebServlet(urlPatterns = "/createCourse")
public class CreateCourseServlet extends HttpServlet {
    private MySQLCourseDao mySQLCourseDao;

    @Override
    public void init() {
        mySQLCourseDao = new MySQLCourseDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null && user.getUserType() == UserType.ADMIN) {
            mySQLCourseDao.save(request.getParameter("newCourseId"), request.getParameter("newTitle"), request.getParameter("instructorId"));
            response.sendRedirect("/admin");
        }
        else if (user == null) response.sendRedirect("/login");
        else response.sendRedirect("/errorHandler?errorCode=403&errorMessage=Not authorized");
    }
}
