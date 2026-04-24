package com.coachia.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Task {

    public enum Category { ETUDES, TRAVAIL, PERSONNEL }
    public enum Priority { FAIBLE, MOYENNE, HAUTE, URGENTE }
    public enum Status { TODO, EN_COURS, TERMINE, ANNULE }

    private int id;
    private int userId;
    private String title;
    private String description;
    private Category category;
    private Priority priority;
    private Status status;
    private LocalDate dueDate;
    private int estimatedMinutes;
    private int actualMinutes;
    private boolean aiGenerated;
    private Integer parentTaskId;
    private LocalDateTime createdAt;

    // Constructeur vide
    public Task() {
        this.status = Status.TODO;
        this.priority = Priority.MOYENNE;
        this.actualMinutes = 0;
        this.aiGenerated = false;
    }

    // Constructeur rapide
    public Task(int userId, String title, Category category) {
        this();
        this.userId = userId;
        this.title = title;
        this.category = category;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public int getEstimatedMinutes() { return estimatedMinutes; }
    public void setEstimatedMinutes(int estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }

    public int getActualMinutes() { return actualMinutes; }
    public void setActualMinutes(int actualMinutes) { this.actualMinutes = actualMinutes; }

    public boolean isAiGenerated() { return aiGenerated; }
    public void setAiGenerated(boolean aiGenerated) { this.aiGenerated = aiGenerated; }

    public Integer getParentTaskId() { return parentTaskId; }
    public void setParentTaskId(Integer parentTaskId) { this.parentTaskId = parentTaskId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Task{id=" + id + ", title='" + title + "', status=" + status + ", priority=" + priority + "}";
    }
}