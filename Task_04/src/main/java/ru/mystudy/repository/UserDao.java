package ru.mystudy.repository;

import org.springframework.stereotype.Repository;
import ru.mystudy.dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public User getById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long recId = resultSet.getLong("id");
                String recUsername = resultSet.getString("username");
                return new User(recId, recUsername);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> list = new ArrayList<>();
            while (resultSet.next()) {
                long recId = resultSet.getLong("id");
                String recUsername = resultSet.getString("username");
                list.add(new User(recId, recUsername));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long recId = resultSet.getLong("id");
                String recUsername = resultSet.getString("username");
                return new User(recId, recUsername);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User create(User user) {
        String sql = "INSERT INTO users (username) Values (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            int rows = preparedStatement.executeUpdate();
            if (rows != 1) {
                throw new SQLException("insert no 1 record: " + rows);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getByUsername(user.getUsername());
    }

    public User update(User user) {
        String sql = "UPDATE users SET username = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setLong(2, user.getId());
            int rows = preparedStatement.executeUpdate();
            if (rows != 1) {
                throw new SQLException("update no 1 record: " + rows);
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(User user) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
