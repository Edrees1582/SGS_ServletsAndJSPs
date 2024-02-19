package dao;

import models.Grade;

import java.io.DataInputStream;
import java.util.List;

public interface GradeDao {
    Grade get(String courseId, String studentId);
    List<Grade> getStudentGrades(String studentId);
    List<Grade> getCourseGrades(String courseId);
    void save(String courseId, String studentId, double grade);
    void update(String courseId, String studentId, double updatedGrade);
    void delete(String courseId, String studentId);
}
