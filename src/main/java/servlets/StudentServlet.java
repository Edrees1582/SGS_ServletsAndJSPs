package servlets;

import dao.MySQLEnrollmentDao;
import dao.MySQLGradeDao;
import dao.MySQLUserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Course;
import models.Grade;
import models.Statistics;
import models.User;
import models.UserType;
import util.GradesStatistics;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet(urlPatterns = "/student/*")
public class StudentServlet extends HttpServlet {
    private MySQLEnrollmentDao mySQLEnrollmentDao;
    private MySQLUserDao mySQLUserDao;
    private MySQLGradeDao mySQLGradeDao;

    @Override
    public void init() {
        mySQLEnrollmentDao = new MySQLEnrollmentDao();
        mySQLUserDao = new MySQLUserDao();
        mySQLGradeDao = new MySQLGradeDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String enteredId = "";

        if (request.getPathInfo() != null && !request.getPathInfo().equals("/"))
            enteredId = request.getPathInfo().substring(1);

        if (user != null && (user.getUserType() == UserType.STUDENT || (!enteredId.isEmpty() && user.getUserType() == UserType.ADMIN))) {
            User student;

            if (user.getUserType() == UserType.ADMIN)
                student = mySQLUserDao.get(enteredId, UserType.STUDENT);
            else student = user;

            if (student == null) {
                response.sendRedirect("/errorHandler?errorCode=404&errorMessage=Student not found");
                return;
            }

            request.setAttribute("student", student);

            List<Course> courses = mySQLEnrollmentDao.getStudentCourses(student.getId());
            request.setAttribute("courses", courses);

            HashMap<String, User> instructors = new HashMap<>();
            HashMap<String, Grade> grades = new HashMap<>();

            for (Course course : courses) {
                instructors.put(course.getInstructorId(), mySQLUserDao.get(course.getInstructorId(), UserType.INSTRUCTOR));
                grades.put(course.getId(), mySQLGradeDao.get(course.getId(), student.getId()));
            }

            Statistics statistics = GradesStatistics.getStudentStatistics(student.getId());

            request.setAttribute("instructors", instructors);
            request.setAttribute("grades", grades);
            request.setAttribute("statistics", statistics);

            request.getRequestDispatcher("/WEB-INF/views/student.jsp").forward(request, response);
        }
        else if (user == null) response.sendRedirect("/login");
        else if (user.getUserType() == UserType.ADMIN) response.sendRedirect("/errorHandler?errorCode=404&errorMessage=Empty student id");
        else response.sendRedirect("/errorHandler?errorCode=403&errorMessage=Not authorized");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null && user.getUserType() == UserType.ADMIN) {
            if (request.getParameter("studentAction").equals("get"))
                response.sendRedirect("/student/" + request.getParameter("studentId"));
            else if (request.getParameter("studentAction").equals("updateForm")) {
                User student = mySQLUserDao.get(request.getParameter("studentId"), UserType.STUDENT);
                request.setAttribute("userType", "student");
                request.setAttribute("userId", student.getId());
                request.setAttribute("password", student.getPassword());
                request.setAttribute("name", student.getName());
                request.getRequestDispatcher("/WEB-INF/views/editUser.jsp").forward(request, response);
            }
            else if (request.getParameter("studentAction").equals("update")) {
                mySQLUserDao.update(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"), UserType.STUDENT);
                response.sendRedirect("/student/" + request.getParameter("userId"));
            }
            else if (request.getParameter("studentAction").equals("delete")) {
                mySQLUserDao.delete(request.getParameter("studentId"), UserType.STUDENT);
                response.sendRedirect("/admin");
            }
        }
        else if (user == null) response.sendRedirect("/login");
        else response.sendRedirect("/errorHandler?errorCode=403&errorMessage=Not authorized");
    }
}
