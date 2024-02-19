package servlets;

import dao.MySQLCourseDao;
import dao.MySQLEnrollmentDao;
import dao.MySQLUserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Course;
import models.Student;
import users.User;
import users.UserType;
import util.Authentication;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private MySQLUserDao mySQLUserDao;
    private MySQLCourseDao mySQLCourseDao;

    public void init() {
        mySQLUserDao = new MySQLUserDao();
        mySQLCourseDao = new MySQLCourseDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
        else if (user.getUserType() == UserType.ADMIN) {
            List<User> students = mySQLUserDao.getAll(UserType.STUDENT);
            request.setAttribute("students", students);

            List<Course> courses = mySQLCourseDao.getAll();
            request.setAttribute("courses", courses);

            request.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(request, response);
        }
        else if (user.getUserType() == UserType.INSTRUCTOR) {
            List<Course> courses = mySQLCourseDao.getAllByInstructorId(user.getId());
            request.setAttribute("courses", courses);

            request.getRequestDispatcher("/WEB-INF/views/instructor.jsp").forward(request, response);
        }
        else if (user.getUserType() == UserType.STUDENT) {
            request.getRequestDispatcher("/WEB-INF/views/student.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("POST request to /login");
        Authentication authentication = new Authentication();
        if (request.getParameter("userType").equals("admin")) {
            User user = authentication.authenticateUser(1, request.getParameter("userId"), request.getParameter("password"));
            if (user != null) {
                System.out.println(user);
                request.setAttribute("user", user);

                List<User> students = mySQLUserDao.getAll(UserType.STUDENT);
                request.setAttribute("students", students);

                List<Course> courses = mySQLCourseDao.getAll();
                request.setAttribute("courses", courses);

                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                request.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(request, response);
            }
            else {
                response.sendRedirect("/login");
            }
        }
        else if (request.getParameter("userType").equals("instructor")) {
            User user = authentication.authenticateUser(2, request.getParameter("userId"), request.getParameter("password"));
            if (user != null) {
                System.out.println(user);

                List<Course> courses = mySQLCourseDao.getAllByInstructorId(user.getId());
                request.setAttribute("courses", courses);

                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/views/instructor.jsp").forward(request, response);
            }
            else {
                response.sendRedirect("/login");
            }
        }
        else if (request.getParameter("userType").equals("student")) {
            User user = authentication.authenticateUser(3, request.getParameter("userId"), request.getParameter("password"));
            if (user != null) {
                System.out.println(user);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("/student/" + user.getId());
            }
            else {
                response.sendRedirect("/login");
            }
        }
    }
}
