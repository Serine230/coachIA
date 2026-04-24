package com.coachia.dao.impl;

import com.coachia.dao.DatabaseConnection;
import com.coachia.model.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDAOImpl {

    private Connection connection;

    public TaskDAOImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    // ─── CRÉER UNE TÂCHE ──────────────────────
    public Task create(Task task) {
        String sql = "INSERT INTO tasks (user_id, title, description, category, priority, status, due_date, estimated_minutes, ai_generated, parent_task_id) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, task.getUserId());
            stmt.setString(2, task.getTitle());
            stmt.setString(3, task.getDescription());
            stmt.setString(4, task.getCategory().name());
            stmt.setString(5, task.getPriority().name());
            stmt.setString(6, task.getStatus().name());
            stmt.setDate(7, task.getDueDate() != null ? Date.valueOf(task.getDueDate()) : null);
            stmt.setInt(8, task.getEstimatedMinutes());
            stmt.setBoolean(9, task.isAiGenerated());
            if (task.getParentTaskId() != null) {
                stmt.setInt(10, task.getParentTaskId());
            } else {
                stmt.setNull(10, Types.INTEGER);
            }
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) task.setId(keys.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    // ─── TOUTES LES TÂCHES D'UN UTILISATEUR ───
    public List<Task> findByUserId(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id = ? ORDER BY due_date ASC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tasks.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    // ─── TÂCHES PAR DATE ──────────────────────
    public List<Task> findByDate(int userId, LocalDate date) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id = ? AND due_date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tasks.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    // ─── TROUVER PAR ID ───────────────────────
    public Optional<Task> findById(int id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // ─── MODIFIER UNE TÂCHE ───────────────────
    public Task update(Task task) {
        String sql = "UPDATE tasks SET title=?, description=?, category=?, priority=?, status=?, due_date=?, estimated_minutes=?, actual_minutes=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getCategory().name());
            stmt.setString(4, task.getPriority().name());
            stmt.setString(5, task.getStatus().name());
            stmt.setDate(6, task.getDueDate() != null ? Date.valueOf(task.getDueDate()) : null);
            stmt.setInt(7, task.getEstimatedMinutes());
            stmt.setInt(8, task.getActualMinutes());
            stmt.setInt(9, task.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    // ─── SUPPRIMER UNE TÂCHE ──────────────────
    public void delete(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ─── MAPPER ResultSet → Task ──────────────
    private Task mapResultSet(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setUserId(rs.getInt("user_id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setCategory(Task.Category.valueOf(rs.getString("category")));
        task.setPriority(Task.Priority.valueOf(rs.getString("priority")));
        task.setStatus(Task.Status.valueOf(rs.getString("status")));
        Date dueDate = rs.getDate("due_date");
        if (dueDate != null) task.setDueDate(dueDate.toLocalDate());
        task.setEstimatedMinutes(rs.getInt("estimated_minutes"));
        task.setActualMinutes(rs.getInt("actual_minutes"));
        task.setAiGenerated(rs.getBoolean("ai_generated"));
        int parentId = rs.getInt("parent_task_id");
        if (!rs.wasNull()) task.setParentTaskId(parentId);
        return task;
    }
}