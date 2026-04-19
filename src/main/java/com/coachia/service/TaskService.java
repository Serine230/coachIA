package com.coachia.service;

import com.coachia.dao.impl.TaskDAOImpl;
import com.coachia.model.Task;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TaskService {

    private TaskDAOImpl taskDAO;

    public TaskService() {
        this.taskDAO = new TaskDAOImpl();
    }

    // ─── CRÉER UNE TÂCHE ──────────────────────
    public Task createTask(Task task) {
        return taskDAO.create(task);
    }

    // ─── TOUTES LES TÂCHES D'UN USER ──────────
    public List<Task> getUserTasks(int userId) {
        return taskDAO.findByUserId(userId);
    }

    // ─── TÂCHES PAR DATE ──────────────────────
    public List<Task> getTasksByDate(int userId, LocalDate date) {
        return taskDAO.findByDate(userId, date);
    }

    // ─── MODIFIER UNE TÂCHE ───────────────────
    public Task updateTask(Task task) {
        return taskDAO.update(task);
    }

    // ─── SUPPRIMER UNE TÂCHE ──────────────────
    public void deleteTask(int id) {
        taskDAO.delete(id);
    }

    // ─── MARQUER COMME TERMINÉE ───────────────
    public Task completeTask(int taskId) {
        Optional<Task> taskOpt = taskDAO.findById(taskId);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setStatus(Task.Status.TERMINE);
            return taskDAO.update(task);
        }
        throw new RuntimeException("Tâche introuvable !");
    }

    // ─── STATISTIQUES PAR STATUT ──────────────
    public Map<String, Long> getCompletionStats(int userId) {
        List<Task> tasks = taskDAO.findByUserId(userId);
        Map<String, Long> stats = new HashMap<>();
        stats.put("TODO", tasks.stream().filter(t -> t.getStatus() == Task.Status.TODO).count());
        stats.put("EN_COURS", tasks.stream().filter(t -> t.getStatus() == Task.Status.EN_COURS).count());
        stats.put("TERMINE", tasks.stream().filter(t -> t.getStatus() == Task.Status.TERMINE).count());
        stats.put("ANNULE", tasks.stream().filter(t -> t.getStatus() == Task.Status.ANNULE).count());
        return stats;
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
}