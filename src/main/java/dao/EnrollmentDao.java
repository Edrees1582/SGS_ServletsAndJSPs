package dao;

import models.Course;
import models.Enrollment;
import models.Student;

import java.io.DataInputStream;
import java.util.List;

public interface EnrollmentDao {
    List<Course> getStudentCourses(String studentId);
    List<Student> getCourseStudents(String courseId);
    List<Enrollment> getAll();
    void save(String courseId, String studentId);
    void delete(String courseId, String studentId);
}
