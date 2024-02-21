package dao;

import models.UserType;

import java.util.List;

public interface UserDao<T> {
    T get(String id, UserType userType);
    List<T> getAll(UserType userType);
    void save(String id, String password, String name, UserType userType);
    void update(String id, String password, String name, UserType userType);
    void delete(String id, UserType userType);
}
