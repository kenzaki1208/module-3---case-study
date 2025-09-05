package org.example.student_management.app.dao;

import org.example.student_management.app.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDAO implements IUserDAO{
    private String jdbcUrl = "jdbc:mysql://localhost:3306/QLSV?useSSL=false";
    private String user = "root";
    private String password = "dinhduy561";

    private static final String INSERT_USER_SQL =
            "INSERT INTO users(username, userAccount, email, password, role) VALUES (?, ?, ?, ?, 'user')";
    private static final String SELECT_USER_BY_USERNAME_SQL =
            "SELECT * FROM users WHERE userAccount = ? AND password = ?";
    private static final String CHECK_USER_EXISTS_SQL =
            "SELECT id FROM users WHERE userAccount = ?";
    private static final String CREATE_TABLE_IF_NOT_EXISTS_SQL =
            "CREATE TABLE IF NOT EXISTS `users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    "  `username` VARCHAR(50) NOT NULL UNIQUE,\n" +
                    "  `userAccount` VARCHAR(50) NOT NULL UNIQUE,\n" +
                    "  `email` VARCHAR(50) NOT NULL,\n" +
                    "  `password` VARCHAR(255) NOT NULL,\n" +
                    "  `role` VARCHAR(20) NOT NULL DEFAULT 'users',\n" +
                    "   `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String DELETE_USERS_SQL = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE_USERS_SQL =
            "UPDATE users SET username=?, userAccount=?, email=?, password=?, role=? WHERE id=?";

    public UserDAO() {
        ensureTableExists();
    }

    private void ensureTableExists() {
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(CREATE_TABLE_IF_NOT_EXISTS_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void registerUser (User user) {
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL)
            )
        {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getUserAccount());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User login(String userAccount, String password) {
        User user = null;
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_USERNAME_SQL)
        )
        {
            ps.setString(1, userAccount);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("userAccount"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean userExists(String userAccount) {
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(CHECK_USER_EXISTS_SQL)
        )
        {
            ps.setString(1, userAccount);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void insertUser(User user) throws Exception {
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL)
        )
        {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getUserAccount());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.executeUpdate();
        }
    }

    @Override
    public User selectUser(int id) throws Exception {
        User user = null;
        try
                (
                        Connection connection = getConnection();
                        PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_ID)
                )
        {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("userAccount"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        }
        return user;
    }

    @Override
    public boolean deleteUser(int id) throws Exception {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_USERS_SQL)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateUser(User user) throws Exception {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_USERS_SQL)) {
            ps.setString(1, user.getUsername());
            ps.setString(3, user.getUserAccount());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getRole());
            ps.setInt(7, user.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<User> selectAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("userAccount"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                ));
            }
        }
        return users;
    }
}
