package com.coachia.model;

public class Category {

    private int id;
    private String name;
    private String color;   // ex: "#FF6B6B" pour colorer dans l'UI
    private int userId;     // chaque user a ses propres catégories

    // ── Constructeurs ──────────────────────────────────────
    public Category() {}

    public Category(int id, String name, String color, int userId) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.userId = userId;
    }

    // Constructeur sans id (pour la création avant INSERT)
    public Category(String name, String color, int userId) {
        this.name = name;
        this.color = color;
        this.userId = userId;
    }

    // ── Getters & Setters ──────────────────────────────────
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    // ── toString (utile pour debug) ────────────────────────
    @Override
    public String toString() {
        return "Category{id=" + id + ", name='" + name + "', color='" + color + "'}";
    }
}