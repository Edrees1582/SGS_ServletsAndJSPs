package models;

import java.io.*;

public class User implements Serializable {
    private final String id;
    private final String password;
    private final String name;
    private final UserType userType;

    public User(String id, String password, String name, UserType userType) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return "+------------+---------------------------+---------------------------+\n" +
                String.format("| %-10s | %-25s | %-25s |\n", ("ID: " + id), ("Name: " + name), ("User type: ") + userType) +
                "+------------+---------------------------+---------------------------+\n";
    }
}
