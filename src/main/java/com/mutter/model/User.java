package com.mutter.model;

public class User {
    private String username;
    private String password; // Password hash, can be null for Google users
    private String googleId; // Google's unique ID for the user

    // Constructor for traditional username/password user
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.googleId = null; // Explicitly set googleId to null
    }
    
    // Constructor for Google user (password might be null)
    public User(String username, String password, String googleId) {
        this.username = username;
        this.password = password; // Could be null if only Google login
        this.googleId = googleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
} 