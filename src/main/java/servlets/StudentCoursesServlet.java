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
import java.util.HashMap;
import java.util.List;

@WebServlet(urlPatterns = "/studentCourses")
public class StudentCoursesServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && user.getUserType() == UserType.ADMIN) {
            MySQLEnrollmentDao mySQLEnrollmentDao = new MySQLEnrollmentDao();
            MySQLUserDao mySQLUserDao = new MySQLUserDao();

            User student = mySQLUserDao.get(request.getParameter("studentId"), UserType.STUDENT);
            request.setAttribute("student", student);

            List<Course> courses = mySQLEnrollmentDao.getStudentCourses(request.getParameter("studentId"));
            request.setAttribute("courses", courses);

            HashMap<String, User> instructors = new HashMap<>();

            for (Course course : courses) {
                instructors.put(course.getInstructorId(), mySQLUserDao.get(course.getInstructorId(), UserType.INSTRUCTOR));
            }

            request.setAttribute("instructors", instructors);

            request.getRequestDispatcher("/WEB-INF/views/courses.jsp").forward(request, response);
        }
        else {
            response.sendError(401, "Not authorized");
        }
    }
}
