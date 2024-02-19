package dao;

import users.UserType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.List;

public interface UserDao<T> {
    T get(String id, UserType userType);
    List<T> getAll(UserType userType);
    void save(String id, String password, String name, UserType userType);
    void update(String updateValue, String id, UserType userType, int updateOption);
    void delete(String id, UserType userType);
}
