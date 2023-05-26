package com.example.Canteen.Models;
public class User {
    private Long id;
    private String username;
    private String password;
    private String role = "user"; // Default role set to "user"

    public User() {
        // Empty constructor needed for JSON deserialization
    }

    // Getter methods

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    // Setter methods

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
