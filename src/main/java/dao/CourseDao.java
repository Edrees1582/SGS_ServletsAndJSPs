package dao;

import models.Course;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;

public interface CourseDao {
    Course get(String id);
    List<Course> getAll();
    List<Course> getAllByInstructorId(String instructorId);
    void save(String courseId, String courseTitle, String instructorId);
    void update(String updateValue, String id, int updateOption);
    void delete(String id);
}
