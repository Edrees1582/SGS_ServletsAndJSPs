package dao;

import models.Course;
import models.Enrollment;
import models.User;

import java.util.List;

public interface EnrollmentDao {
    List<Course> getStudentCourses(String studentId);
    List<User> getCourseStudents(String courseId);
    List<Enrollment> getAll();
    void save(String courseId, String studentId);
    void delete(String courseId, String studentId);
    void deleteByStudent(String studentId);
    void deleteByCourse(String courseId);
}
