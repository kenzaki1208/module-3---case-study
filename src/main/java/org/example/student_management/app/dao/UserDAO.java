package org.example.student_management.app.dao;

import org.example.student_management.app.model.User;

import java.sql.*;

public class UserDAO implements IUserDAO{
    private String jdbcUrl = "jdbc:mysql://localhost:3306/QLSV?useSSL=false";
    private String user = "root";
    private String password = "dinhduy561";

    private static final String INSERT_USER_SQL =
            "INSERT INTO users(username, password, role) VALUES (?, ?, 'user')";
    private static final String SELECT_USER_BY_USERNAME_SQL =
            "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final String CHECK_USER_EXISTS_SQL =
            "SELECT id FROM users WHERE username = ?";
    private static final String CREATE_TABLE_IF_NOT_EXISTS_SQL =
            "CREATE TABLE IF NOT EXISTS `users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    "  `username` VARCHAR(50) NOT NULL UNIQUE,\n" +
                    "  `password` VARCHAR(255) NOT NULL,\n" +
                    "  `role` VARCHAR(20) NOT NULL DEFAULT 'users',\n" +
                    "   `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

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
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User login(String username, String password) {
        User user = null;
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_USERNAME_SQL)
        )
        {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
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
    public boolean userExists(String username) {
        try (
                Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(CHECK_USER_EXISTS_SQL)
        )
        {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
