package servlets;

import dao.MySQLEnrollmentDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        if (request.getParameter("enrollment").equals("enroll"))
            mySQLEnrollmentDao.save(request.getParameter("courseId"), request.getParameter("studentId"));
        else if (request.getParameter("enrollment").equals("unEnroll"))
            mySQLEnrollmentDao.delete(request.getParameter("courseId"), request.getParameter("studentId"));
        response.sendRedirect("/course/" + request.getParameter("courseId"));
    }
}
