package servlets;

import dao.MySQLGradeDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.User;
import models.UserType;

import java.io.IOException;


@WebServlet(urlPatterns = "/editGrade")
public class GradeServlet extends HttpServlet {
    private MySQLGradeDao mySQLGradeDao;

    @Override
    public void init() {
        mySQLGradeDao = new MySQLGradeDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null && user.getUserType() == UserType.INSTRUCTOR) {
            request.getRequestDispatcher("/WEB-INF/views/editGrade.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null && user.getUserType() == UserType.INSTRUCTOR) {
            mySQLGradeDao.update(request.getParameter("courseId"), request.getParameter("studentId"), Double.parseDouble(request.getParameter("grade")));
            response.sendRedirect("/course/" + request.getParameter("courseId"));
        }
        else if (user == null) response.sendRedirect("/login");
        else response.sendRedirect("/errorHandler?errorCode=403&errorMessage=Not authorized");
    }
}
