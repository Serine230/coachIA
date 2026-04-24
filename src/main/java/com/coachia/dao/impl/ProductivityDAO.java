package com.coachia.dao.impl;

import java.sql.*;

public class ProductivityDAO {

    private Connection conn;

    public ProductivityDAO(Connection conn) {
        this.conn = conn;
    }

    // ── ENREGISTRER LE TEMPS ──────────────────────────────
    public void logTime(int taskId, int userId, long timeSpentSeconds) throws SQLException {
        String sql = "INSERT INTO productivity_log (task_id, user_id, time_spent, logged_at) VALUES (?, ?, ?, NOW())";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, taskId);
        ps.setInt(2, userId);
        ps.setLong(3, timeSpentSeconds);
        ps.executeUpdate();
    }

    // ── TAUX DE COMPLÉTION ────────────────────────────────
    public double getCompletionRate(int userId) throws SQLException {
        String sql = "SELECT " +
                "COUNT(*) AS total, " +
                "SUM(CASE WHEN status = 'Terminé' THEN 1 ELSE 0 END) AS done " +
                "FROM tasks WHERE user_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int total = rs.getInt("total");
            int done  = rs.getInt("done");
            if (total == 0) return 0.0;
            return (done * 100.0) / total;
        }
        return 0.0;
    }
}