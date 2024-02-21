package dao;

import models.Course;
import models.User;
import models.UserType;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLUserDao implements UserDao<User> {
    private final DBUtil dbUtil;
    public MySQLUserDao() {
        try {
            dbUtil = new DBUtil();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User get(String id, UserType userType) {
        try (Connection connection = dbUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;

            if (userType.equals(UserType.ADMIN))
                resultSet = statement.executeQuery("select * from admins where ID = '" + id + "';");
            else if (userType.equals(UserType.INSTRUCTOR))
                resultSet = statement.executeQuery("select * from instructors where ID = '" + id + "';");
            else if (userType.equals(UserType.STUDENT))
                resultSet = statement.executeQuery("select * from students where ID = '" + id + "';");

            if (resultSet.next())
                return new User(id, resultSet.getString("password"), resultSet.getString("name"), userType);

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll(UserType userType) {
        try (Connection connection = dbUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            List<User> users = new ArrayList<>();

            if (userType.equals(UserType.ADMIN)) {
                resultSet = statement.executeQuery("select * from admins;");
                while (resultSet.next())
                    users.add(new User(resultSet.getString("id"), resultSet.getString("password"), resultSet.getString("name"), userType));
            }
            else if (userType.equals(UserType.INSTRUCTOR)) {
                resultSet = statement.executeQuery("select * from instructors;");
                while (resultSet.next())
                    users.add(new User(resultSet.getString("id"), resultSet.getString("password"), resultSet.getString("name"), userType));
            }
            else if (userType.equals(UserType.STUDENT)) {
                resultSet = statement.executeQuery("select * from students;");
                while (resultSet.next())
                    users.add(new User(resultSet.getString("id"), resultSet.getString("password"), resultSet.getString("name"), userType));
            }

            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(String id, String password, String name, UserType userType) {
        try (Connection connection = dbUtil.getConnection()) {
            String saveSql = switch (userType) {
                case ADMIN -> "insert into admins values (?, ?, ?);";
                case INSTRUCTOR -> "insert into instructors values (?, ?, ?);";
                case STUDENT -> "insert into students values (?, ?, ?);";
            };

            PreparedStatement preparedStatement = connection.prepareCall(saveSql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(String id, String password, String name, UserType userType) {
        try (Connection connection = dbUtil.getConnection()) {
            String updateSql = switch (userType) {
                case ADMIN -> "update admins set password = ?, name = ? where id = ?;";
                case INSTRUCTOR -> "update instructors set password = ?, name = ? where id = ?;";
                case STUDENT -> "update students set password = ?, name = ? where id = ?;";
            };

            PreparedStatement updatePreparedStatement = connection.prepareCall(updateSql);
            updatePreparedStatement.setString(1, password);
            updatePreparedStatement.setString(2, name);
            updatePreparedStatement.setString(3, id);

            updatePreparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String id, UserType userType) {
        try (Connection connection = dbUtil.getConnection()) {
            String deleteSql = switch (userType) {
                case ADMIN -> "delete from admins where id = ?;";
                case INSTRUCTOR -> "delete from instructors where id = ?;";
                case STUDENT -> "delete from students where id = ?;";
            };
            
            PreparedStatement deletePreparedStatement = connection.prepareCall(deleteSql);
            deletePreparedStatement.setString(1, id);

            if (userType == UserType.STUDENT) {
                MySQLEnrollmentDao mySQLEnrollmentDao = new MySQLEnrollmentDao();
                List<Course> courses = mySQLEnrollmentDao.getStudentCourses(id);
                for (Course course : courses) mySQLEnrollmentDao.delete(course.getId(), id);
            }
            else if (userType == UserType.INSTRUCTOR) {
                MySQLCourseDao mySQLCourseDao = new MySQLCourseDao();
                List<Course> courses = mySQLCourseDao.getAllByInstructorId(id);
                for (Course course : courses) mySQLCourseDao.delete(course.getId());
            }

            deletePreparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
