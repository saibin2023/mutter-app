package com.mutter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types; // Import Types for setting NULL

import org.mindrot.jbcrypt.BCrypt;

import com.mutter.model.User;
import com.mutter.util.DatabaseUtil; // Import BCrypt

public class UserDAO {

    // Method to find a user by username
    public User getUserByUsername(String username) throws SQLException {
        // Include google_id in the select
        String sql = "SELECT username, password, google_id FROM users WHERE username = ?";
        User user = null;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, username);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"), // Fetches the stored hash (can be null)
                        resultSet.getString("google_id") // Fetch google_id
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user by username: " + username + " - " + e.getMessage());
            throw e;
        }
        return user;
    }

    // Method to find a user by Google ID
    public User getUserByGoogleId(String googleId) throws SQLException {
        String sql = "SELECT username, password, google_id FROM users WHERE google_id = ?";
        User user = null;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, googleId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"), // Can be null
                        resultSet.getString("google_id")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user by Google ID: " + googleId + " - " + e.getMessage());
            throw e;
        }
        return user;
    }

    // Method to create a new user (handles both traditional and Google sign-up)
    public boolean createUser(User user) throws SQLException {
        // Updated SQL to include google_id
        String sql = "INSERT INTO users (username, password, google_id) VALUES (?, ?, ?)";
        boolean rowInserted = false;

        // Check if username exists (for traditional signup)
        // For Google signup, we might rely on google_id uniqueness check or handle merge later
        if (user.getGoogleId() == null && getUserByUsername(user.getUsername()) != null) {
            System.err.println("Username already exists (traditional signup attempt): " + user.getUsername());
            return false; 
        }
        // Check if google_id exists (for Google signup)
        if (user.getGoogleId() != null && getUserByGoogleId(user.getGoogleId()) != null) {
             System.err.println("Google ID already exists: " + user.getGoogleId());
             // Potentially link account or log in existing user here in a real app
             return false; // For now, just prevent duplicate Google ID entries
        }

        String passwordToStore = null;
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            // Hash password only if it's provided (traditional signup)
             passwordToStore = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        }

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, user.getUsername());
            
            if (passwordToStore != null) {
                statement.setString(2, passwordToStore);
            } else {
                statement.setNull(2, Types.VARCHAR); // Set password to NULL for Google users
            }
            
            if (user.getGoogleId() != null) {
                 statement.setString(3, user.getGoogleId());
            } else {
                 statement.setNull(3, Types.VARCHAR); // Set google_id to NULL for traditional users
            }
            
            rowInserted = statement.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating user: " + user.getUsername() + " - " + e.getMessage());
            if (e.getErrorCode() == 1062) { // MySQL duplicate entry (could be username or google_id)
                System.err.println("Duplicate entry detected during user creation.");
                return false; 
            } else {
                throw e;
            }
        }
        return rowInserted;
    }
} 