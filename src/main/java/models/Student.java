package models;

import users.User;
import users.UserType;

public class Student extends User {
    public Student(String id, String password, String name) {
        super(id, password, name, UserType.STUDENT);
    }
}
