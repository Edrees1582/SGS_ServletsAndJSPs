package dao;

import models.User;
import models.UserType;

import java.util.List;

public interface UserDao {
    User get(String id);
    List<User> getAll();
    List<User> getStudents();
    List<User> getInstructors();
    void save(String id, String password, String name, UserType userType);
    void update(String id, String password, String name);
    void delete(String id);
}
