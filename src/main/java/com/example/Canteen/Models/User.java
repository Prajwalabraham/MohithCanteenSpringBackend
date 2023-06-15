package com.example.Canteen.Models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        // Hash the password before setting it
        this.password = hashPassword(password);
    }

    public String getPassword() {
        return password;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
