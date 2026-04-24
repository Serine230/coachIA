package com.coachia.model;

public class User {
    private int id;
    private String username;
    private String email;
    private String passwordHash;
    private String workRhythm;
    private String goals;

    // Constructeur vide
    public User() {}

    // Constructeur complet
    public User(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getWorkRhythm() { return workRhythm; }
    public void setWorkRhythm(String workRhythm) { this.workRhythm = workRhythm; }

    public String getGoals() { return goals; }
    public void setGoals(String goals) { this.goals = goals; }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', email='" + email + "'}";
    }
}