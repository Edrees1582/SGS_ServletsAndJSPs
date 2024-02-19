package models;

import users.User;
import users.UserType;

public class Admin extends User {
    public Admin(String id, String password, String name) {
        super(id, password, name, UserType.ADMIN);
    }
}
