package servlets;

import dao.MySQLCourseDao;
import dao.MySQLEnrollmentDao;
import dao.MySQLGradeDao;
import dao.MySQLUserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.*;
import util.GradesStatistics;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet(urlPatterns = "/course/*")
public class CourseServlet extends HttpServlet {
    private MySQLEnrollmentDao mySQLEnrollmentDao;
    private MySQLCourseDao mySQLCourseDao;
    private MySQLUserDao mySQLUserDao;
    private MySQLGradeDao mySQLGradeDao;

    @Override
    public void init() {
        mySQLEnrollmentDao = new MySQLEnrollmentDao();
        mySQLCourseDao = new MySQLCourseDao();
        mySQLUserDao = new MySQLUserDao();
        mySQLGradeDao = new MySQLGradeDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String enteredId = "";

        if (request.getPathInfo() != null && !request.getPathInfo().equals("/"))
            enteredId = request.getPathInfo().substring(1);

        Course course = mySQLCourseDao.get(enteredId);

        if (course == null) {
            response.sendRedirect("/errorHandler?errorCode=404&errorMessage=Course not found");
            return;
        }

        if (user != null && (user.getUserType() == UserType.ADMIN || (user.getUserType() == UserType.INSTRUCTOR) && user.getId().equals(course.getInstructorId()))) {
            HashMap<String, Grade> grades = new HashMap<>();
            List<User> students = mySQLEnrollmentDao.getCourseStudents(enteredId);

            for (User student : students) {
                grades.put(student.getId(), mySQLGradeDao.get(course.getId(), student.getId()));
            }

            request.setAttribute("course", course);
            request.setAttribute("students",  students);
            request.setAttribute("grades", grades);

            List<User> allStudents = mySQLEnrollmentDao.getCourseStudents(course.getId());
            List<User> allOtherStudents = mySQLUserDao.getStudents();

            for (User student : allStudents) {
                for (User otherStudent : allOtherStudents) {
                    if (student.getId().equals(otherStudent.getId())) {
                        allOtherStudents.remove(otherStudent);
                        break;
                    }
                }
            }

            Statistics statistics = GradesStatistics.getCourseStatistics(course.getId());

            request.setAttribute("statistics", statistics);

            request.setAttribute("allOtherStudents",  allOtherStudents);

            request.setAttribute("instructor", mySQLUserDao.get(course.getInstructorId()));
            request.getRequestDispatcher("/WEB-INF/views/course.jsp").forward(request, response);
        }
        else if (user == null) response.sendRedirect("/login");
        else if (user.getUserType() == UserType.ADMIN) response.sendRedirect("/errorHandler?errorCode=404&errorMessage=Empty course id");
        else response.sendRedirect("/errorHandler?errorCode=403&errorMessage=Not authorized");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            if (request.getParameter("courseAction").equals("get") && (user.getUserType() == UserType.ADMIN || user.getUserType() == UserType.INSTRUCTOR))
                response.sendRedirect("/course/" + request.getParameter("courseId"));
            else if (request.getParameter("courseAction").equals("update") && user.getUserType() == UserType.ADMIN) {
                mySQLCourseDao.update(request.getParameter("courseId"), request.getParameter("title"), request.getParameter("instructorId"));
                response.sendRedirect("/course/" + request.getParameter("courseId"));
            }
            else if (request.getParameter("courseAction").equals("delete") && user.getUserType() == UserType.ADMIN) {
                mySQLCourseDao.delete(request.getParameter("courseId"));
                response.sendRedirect("/admin");
            }
            else response.sendRedirect("/errorHandler?errorCode=403&errorMessage=Not authorized");
        }
        else response.sendRedirect("/login");
    }
}
