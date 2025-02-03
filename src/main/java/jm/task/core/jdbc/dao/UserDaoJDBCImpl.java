package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(45), " +
            "lastName VARCHAR(45), " +
            "age TINYINT)";
    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS users";
    private static final String INSERT_INTO = "INSERT INTO users (name, lastName, age) VALUES (?,?,?)";
    private static final String DELETE_FROM = "DELETE FROM users WHERE id = ?";
    private static final String SELECT = "SELECT id, name, lastName, age FROM users";
    private static final String TRUNCATE = "TRUNCATE TABLE users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void dropUsersTable() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE_SQL)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " - добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void removeUserById(long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM)) {
            preparedStatement.setLong(1, id);
            int res = preparedStatement.executeUpdate();

            if (res > 0) {
            } else {
                System.out.println("Пользователь не найден");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(userList);
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(TRUNCATE)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
