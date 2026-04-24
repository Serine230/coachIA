package model;

public class Category {

    private int id;
    private String name;
    private String color;
    private int userId;

    // Constructeur vide
    public Category() {}

    // Constructeur complet
    public Category(int id, String name, String color, int userId) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.userId = userId;
    }

    // Constructeur sans id (pour création)
    public Category(String name, String color, int userId) {
        this.name = name;
        this.color = color;
        this.userId = userId;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getColor() { return color; }
    public int getUserId() { return userId; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setColor(String color) { this.color = color; }
    public void setUserId(int userId) { this.userId = userId; }

    @Override
    public String toString() {
        return name;
    }
}