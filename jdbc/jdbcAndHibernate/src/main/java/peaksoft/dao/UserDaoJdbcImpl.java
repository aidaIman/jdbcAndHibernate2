package peaksoft.dao;

import peaksoft.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static peaksoft.util.Util.connect;

public class UserDaoJdbcImpl implements UserDao {

    public UserDaoJdbcImpl() {

    }

    public void createUsersTable() {
        try {
            String SQL = "CREATE TABLE IF NOT EXISTS users " +
                    "(id serial PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "lastName VARCHAR(50) NOT NULL," +
                    "age smallint NOT NULL)";
            Connection conn = connect();
            Statement statement = conn.createStatement();
            System.out.println("Created table users");
            statement.executeUpdate(SQL);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void dropUsersTable() {
        try {
            String SQL = "DROP TABLE IF EXISTS users";
            Connection conn = connect();
            Statement statement = conn.createStatement();
            System.out.println("Drop table users");
            statement.executeUpdate(SQL);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = "INSERT INTO users (name, lastName, age) values (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            System.out.println("Added user " + name);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeUserById(long id) {
        String SQL = "DELETE FROM users WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setInt(1, (int) id);
            System.out.println("Removed user id = " + id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String SQL = "SELECT * FROM users";
        try (Connection conn = connect();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL)) {
            while (resultSet.next()) {
                userList.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return userList;
    }

    public void cleanUsersTable() {
        String SQLDelete = "TRUNCATE TABLE users";
        try (Connection conn = connect();
             Statement statement = conn.createStatement()) {
            System.out.println("Cleaned table users");
            statement.executeUpdate(SQLDelete);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}