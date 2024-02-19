package models;

import users.User;
import users.UserType;

public class Instructor extends User {
    public Instructor(String id, String password, String name) {
        super(id, password, name, UserType.INSTRUCTOR);
    }
}
