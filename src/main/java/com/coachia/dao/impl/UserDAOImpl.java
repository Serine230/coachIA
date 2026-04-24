package com.coachia.dao.impl;

import com.coachia.dao.DatabaseConnection;
import com.coachia.model.User;

import java.sql.*;
import java.util.Optional;

public class UserDAOImpl {

    private Connection connection;

    public UserDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    // ─── CRÉER UN UTILISATEUR ──────────────────
    public User create(User user) {
        String sql = "INSERT INTO users (username, email, password_hash, work_rhythm, goals) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());
            stmt.setString(4, user.getWorkRhythm());
            stmt.setString(5, user.getGoals());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                user.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // ─── TROUVER PAR EMAIL ─────────────────────
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setWorkRhythm(rs.getString("work_rhythm"));
                user.setGoals(rs.getString("goals"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // ─── TROUVER PAR ID ────────────────────────
    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setWorkRhythm(rs.getString("work_rhythm"));
                user.setGoals(rs.getString("goals"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}