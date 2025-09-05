package org.example.student_management.app.dao;

import org.example.student_management.app.model.User;

import java.util.List;

public interface IUserDAO {
    void registerUser(User user);
    User login(String userAccount, String password);
    boolean userExists(String userAccount);
    void insertUser(User user) throws Exception;
    User selectUser(int id) throws Exception;
    boolean deleteUser(int id) throws Exception;
    boolean updateUser(User user) throws Exception;
    List<User> selectAllUsers() throws Exception;
}
