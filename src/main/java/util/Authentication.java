package util;

import models.User;
import models.UserType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Authentication {
    private final DBUtil dbUtil;

    public Authentication() {
        dbUtil = new DBUtil();
    }

    public User authenticateUser(int chosenUserType, String id, String password) {
        try (Connection connection = dbUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            User user = null;

            if (chosenUserType == 1) {
                resultSet = statement.executeQuery("select * from admins where ID = '" + id + "' AND password = '" + password + "';");
                if (resultSet.next()) user = new User(id, password, resultSet.getString("name"), UserType.ADMIN);
            }
            else if (chosenUserType == 2) {
                resultSet = statement.executeQuery("select * from instructors where ID = '" + id + "' AND password = '" + password + "';");
                if (resultSet.next()) user = new User(id, password, resultSet.getString("name"), UserType.INSTRUCTOR);
            }
            else if (chosenUserType == 3) {
                resultSet = statement.executeQuery("select * from students where ID = '" + id + "' AND password = '" + password + "';");
                if (resultSet.next()) user = new User(id, password, resultSet.getString("name"), UserType.STUDENT);
            }

            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
