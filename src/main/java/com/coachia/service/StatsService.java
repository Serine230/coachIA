package com.coachia.service;

import com.coachia.dao.impl.ProductivityDAO;
import com.coachia.dao.impl.TaskDAOImpl;
import com.coachia.model.Task;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsService {

    private TaskDAOImpl taskDAO;
    private ProductivityDAO productivityDAO;

    public StatsService(TaskDAOImpl taskDAO, ProductivityDAO productivityDAO) {
        this.taskDAO = taskDAO;
        this.productivityDAO = productivityDAO;
    }

    // ── TÂCHES TERMINÉES PAR SEMAINE ──────────────────────
    public int getCompletedTasksThisWeek(int userId) throws SQLException {
        List<Task> tasks = taskDAO.findByUser(userId);
        int count = 0;
        java.time.LocalDate oneWeekAgo = java.time.LocalDate.now().minusDays(7);
        for (Task t : tasks) {
            if ("Terminé".equals(t.getStatus()) &&
                    t.getDueDate() != null &&
                    t.getDueDate().isAfter(oneWeekAgo)) {
                count++;
            }
        }
        return count;
    }

    // ── TAUX DE COMPLÉTION GLOBAL ─────────────────────────
    public double getGlobalCompletionRate(int userId) throws SQLException {
        return productivityDAO.getCompletionRate(userId);
    }

    // ── TEMPS MOYEN PAR CATÉGORIE ─────────────────────────
    public Map<Integer, Double> getAvgTimeByCategory(int userId) throws SQLException {
        List<Task> tasks = taskDAO.findByUser(userId);
        Map<Integer, Long> totalTime = new HashMap<>();
        Map<Integer, Integer> count = new HashMap<>();

        for (Task t : tasks) {
            int catId = t.getCategoryId();
            totalTime.put(catId, totalTime.getOrDefault(catId, 0L));
            count.put(catId, count.getOrDefault(catId, 0) + 1);
        }

        Map<Integer, Double> avgTime = new HashMap<>();
        for (int catId : totalTime.keySet()) {
            avgTime.put(catId, (double) totalTime.get(catId) / count.get(catId));
        }
        return avgTime;
    }
}