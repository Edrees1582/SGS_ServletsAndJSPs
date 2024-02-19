package servlets;

import dao.MySQLEnrollmentDao;
import dao.MySQLUserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Course;
import users.User;
import users.UserType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet(urlPatterns = "/student/*")
public class StudentServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && user.getUserType() == UserType.STUDENT && user.getId().equals(request.getPathInfo().substring(1))) {
            MySQLEnrollmentDao mySQLEnrollmentDao = new MySQLEnrollmentDao();
            MySQLUserDao mySQLUserDao = new MySQLUserDao();

            request.setAttribute("student", user);

            List<Course> courses = mySQLEnrollmentDao.getStudentCourses(user.getId());
            request.setAttribute("courses", courses);

            HashMap<String, User> instructors = new HashMap<>();

            for (Course course : courses) {
                instructors.put(course.getInstructorId(), mySQLUserDao.get(course.getInstructorId(), UserType.INSTRUCTOR));
            }

            request.setAttribute("instructors", instructors);

            request.getRequestDispatcher("/WEB-INF/views/student.jsp").forward(request, response);
        }
        else {
            response.sendError(401, "Not authorized");
        }
    }
}
