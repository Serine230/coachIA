package model;

import java.time.LocalDate;

public class Task {

    public enum Priorite { BASSE, MOYENNE, HAUTE }
    public enum Statut { A_FAIRE, EN_COURS, TERMINE }

    private int id;
    private String titre;
    private String description;
    private Priorite priorite;
    private Statut statut;
    private LocalDate dateLimite;
    private int userId;
    private int categoryId;

    // Constructeur vide
    public Task() {
        this.priorite = Priorite.MOYENNE;
        this.statut = Statut.A_FAIRE;
    }

    // Constructeur complet
    public Task(int id, String titre, String description,
                Priorite priorite, Statut statut,
                LocalDate dateLimite, int userId, int categoryId) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.priorite = priorite;
        this.statut = statut;
        this.dateLimite = dateLimite;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    // Constructeur sans id
    public Task(String titre, String description,
                Priorite priorite, Statut statut,
                LocalDate dateLimite, int userId, int categoryId) {
        this.titre = titre;
        this.description = description;
        this.priorite = priorite;
        this.statut = statut;
        this.dateLimite = dateLimite;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    // Getters
    public int getId() { return id; }
    public String getTitre() { return titre; }
    public String getDescription() { return description; }
    public Priorite getPriorite() { return priorite; }
    public Statut getStatut() { return statut; }
    public LocalDate getDateLimite() { return dateLimite; }
    public int getUserId() { return userId; }
    public int getCategoryId() { return categoryId; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitre(String titre) { this.titre = titre; }
    public void setDescription(String description) { this.description = description; }
    public void setPriorite(Priorite priorite) { this.priorite = priorite; }
    public void setStatut(Statut statut) { this.statut = statut; }
    public void setDateLimite(LocalDate dateLimite) { this.dateLimite = dateLimite; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    @Override
    public String toString() {
        return titre;
    }
}