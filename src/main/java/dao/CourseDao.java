package dao;

import models.Course;

import java.util.List;

public interface CourseDao {
    Course get(String id);
    List<Course> getAll();
    List<Course> getAllByInstructorId(String instructorId);
    void save(String courseId, String courseTitle, String instructorId);
    void update(String id, String newId, String title, String instructorId);
    void delete(String id);
}
