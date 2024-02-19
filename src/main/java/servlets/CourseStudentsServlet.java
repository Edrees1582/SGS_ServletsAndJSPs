package servlets;

import dao.MySQLCourseDao;
import dao.MySQLEnrollmentDao;
import dao.MySQLUserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Course;
import models.Student;
import users.User;
import users.UserType;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/courseStudents")
public class CourseStudentsServlet extends HttpServlet {
    private MySQLEnrollmentDao mySQLEnrollmentDao;
    private MySQLCourseDao mySQLCourseDao;
    private MySQLUserDao mySQLUserDao;

    public void init() {
        mySQLEnrollmentDao = new MySQLEnrollmentDao();
        mySQLCourseDao = new MySQLCourseDao();
        mySQLUserDao = new MySQLUserDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && (user.getUserType() == UserType.ADMIN || user.getUserType() == UserType.INSTRUCTOR)) {
            Course course = mySQLCourseDao.get(request.getParameter("courseId"));

            if (user.getUserType() == UserType.INSTRUCTOR && !course.getInstructorId().equals(user.getId()))
                response.sendError(401, "Not authorized");

            request.setAttribute("course", course);

            List<Student> students = mySQLEnrollmentDao.getCourseStudents(request.getParameter("courseId"));
            request.setAttribute("students", students);

            request.setAttribute("instructor", mySQLUserDao.get(course.getInstructorId(), UserType.INSTRUCTOR));

            request.getRequestDispatcher("/WEB-INF/views/students.jsp").forward(request, response);
        }
        else {
            response.sendError(401, "Not authorized");
        }
    }
}
