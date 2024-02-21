package servlets;

import dao.MySQLEnrollmentDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import models.UserType;

import java.io.IOException;

@WebServlet(urlPatterns = "/studentEnrollment")
public class StudentEnrollmentServlet extends HttpServlet {
    private MySQLEnrollmentDao mySQLEnrollmentDao;

    @Override
    public void init() {
        mySQLEnrollmentDao = new MySQLEnrollmentDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null && user.getUserType() == UserType.INSTRUCTOR) {
            if (request.getParameter("enrollment").equals("enroll"))
                mySQLEnrollmentDao.save(request.getParameter("courseId"), request.getParameter("studentId"));
            else if (request.getParameter("enrollment").equals("unEnroll"))
                mySQLEnrollmentDao.delete(request.getParameter("courseId"), request.getParameter("studentId"));
            response.sendRedirect("/course/" + request.getParameter("courseId"));
        }
        else if (user == null) response.sendRedirect("/login");
        else response.sendRedirect("/errorHandler?errorCode=403&errorMessage=Not authorized");
    }
}
