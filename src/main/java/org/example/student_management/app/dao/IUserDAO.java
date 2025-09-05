package org.example.student_management.app.dao;

import org.example.student_management.app.model.User;

public interface IUserDAO {
    void registerUser(User user);
    User login(String username, String password);
    boolean userExists(String username);
}
