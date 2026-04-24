package dao;

import model.Task;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/coachIA";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public boolean create(Task task) {
        String sql = "INSERT INTO tasks (titre, description, priorite, statut, date_limite, user_id, category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitre());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getPriorite().name());
            stmt.setString(4, task.getStatut().name());
            stmt.setDate(5, task.getDateLimite() != null ?
                    Date.valueOf(task.getDateLimite()) : null);
            stmt.setInt(6, task.getUserId());
            stmt.setInt(7, task.getCategoryId());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur create task: " + e.getMessage());
            return false;
        }
    }

    public List<Task> findByUser(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id = ? ORDER BY date_limite ASC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setTitre(rs.getString("titre"));
                task.setDescription(rs.getString("description"));
                task.setPriorite(Task.Priorite.valueOf(rs.getString("priorite")));
                task.setStatut(Task.Statut.valueOf(rs.getString("statut")));
                Date d = rs.getDate("date_limite");
                if (d != null) task.setDateLimite(d.toLocalDate());
                task.setUserId(rs.getInt("user_id"));
                task.setCategoryId(rs.getInt("category_id"));
                tasks.add(task);
            }

        } catch (SQLException e) {
            System.out.println("Erreur findByUser: " + e.getMessage());
        }
        return tasks;
    }

    public boolean update(Task task) {
        String sql = "UPDATE tasks SET titre=?, description=?, priorite=?, statut=?, date_limite=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitre());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getPriorite().name());
            stmt.setString(4, task.getStatut().name());
            stmt.setDate(5, task.getDateLimite() != null ?
                    Date.valueOf(task.getDateLimite()) : null);
            stmt.setInt(6, task.getId());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur update task: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, taskId);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur delete task: " + e.getMessage());
            return false;
        }
    }
}