package com.cremaplace.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private List<String> roles;
    private int failedLoginAttempts;

    // Constructors, Getters, Setters
    public User() {}

    public User(String username, String email, String password, String phoneNumber, List<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
        this.failedLoginAttempts = 0;
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhoneNumber() { return phoneNumber; }
    public List<String> getRoles() { return roles; }
    public int getFailedLoginAttempts() { return failedLoginAttempts; }

    public void setFailedLoginAttempts(int failedLoginAttempts) { this.failedLoginAttempts = failedLoginAttempts; }
    public void setPassword(String password) { this.password = password; }
}
