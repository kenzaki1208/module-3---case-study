package org.example.student_management.app.model;

public class User {
    private int id;
    private String username;
    private String userAccount;
    private String email;
    private String password;
    private String role;

    public User() {}

    public User(int id, String username, String userAccount, String email,String password, String role) {
        this.id = id;
        this.username = username;
        this.userAccount = userAccount;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(int id, String userAccount, String password, String role) {
        this.id = id;
        this.userAccount = userAccount;
        this.password = password;
        this.role = role;
    }

    public User(int id, String username, String userAccount, String email, String password, String role, Object o) {
        this.id = id;
        this.username = username;
        this.userAccount = userAccount;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
