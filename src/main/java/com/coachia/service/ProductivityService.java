package com.coachia.service;

import com.coachia.dao.impl.TaskDAOImpl;
import com.coachia.model.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductivityService {

    private TaskDAOImpl taskDAO;

    public ProductivityService() {
        this.taskDAO = new TaskDAOImpl();
    }

    // ─── TAUX DE COMPLÉTION ───────────────────
    public double getCompletionRate(int userId) {
        List<Task> tasks = taskDAO.findByUserId(userId);
        if (tasks.isEmpty()) return 0.0;
        long done = tasks.stream()
                .filter(t -> t.getStatus() == Task.Status.TERMINE)
                .count();
        return (double) done / tasks.size() * 100;
    }

    // ─── TEMPS TOTAL PASSÉ (en minutes) ───────
    public int getTotalTimeSpent(int userId) {
        return taskDAO.findByUserId(userId)
                .stream()
                .mapToInt(Task::getActualMinutes)
                .sum();
    }

    // ─── TÂCHES TERMINÉES CETTE SEMAINE ───────
    public long getTasksCompletedThisWeek(int userId) {
        LocalDate oneWeekAgo = LocalDate.now().minusDays(7);
        return taskDAO.findByUserId(userId)
                .stream()
                .filter(t -> t.getStatus() == Task.Status.TERMINE)
                .filter(t -> t.getDueDate() != null &&
                        t.getDueDate().isAfter(oneWeekAgo))
                .count();
    }

    // ─── TÂCHES PAR CATÉGORIE ─────────────────
    public Map<String, Long> getTasksByCategory(int userId) {
        return taskDAO.findByUserId(userId)
                .stream()
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().name(),
                        Collectors.counting()
                ));
    }

    // ─── TÂCHES EN RETARD ─────────────────────
    public List<Task> getOverdueTasks(int userId) {
        LocalDate today = LocalDate.now();
        return taskDAO.findByUserId(userId)
                .stream()
                .filter(t -> t.getDueDate() != null &&
                        t.getDueDate().isBefore(today) &&
                        t.getStatus() != Task.Status.TERMINE)
                .collect(Collectors.toList());
    }

    // ─── NOMBRE DE TÂCHES PAR STATUT ──────────
    public Map<String, Long> getStatsByStatus(int userId) {
        return taskDAO.findByUserId(userId)
                .stream()
                .collect(Collectors.groupingBy(
                        t -> t.getStatus().name(),
                        Collectors.counting()
                ));
    }
}