package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class TaskView {

    private Scene scene;

    public TaskView() {

        // ===== HEADER =====
        HBox header = new HBox(15);
        header.setPadding(new Insets(20, 30, 20, 30));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #2D2D4E;");

        Button btnRetour = new Button("← Retour");
        btnRetour.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13;" +
                        "-fx-cursor: hand;"
        );
        btnRetour.setOnAction(e -> SceneManager.switchTo("main"));

        Label titre = new Label("📋 Mes Tâches");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titre.setTextFill(Color.WHITE);

        header.getChildren().addAll(btnRetour, titre);

        // ===== BARRE AJOUT TACHE =====
        HBox barreAjout = new HBox(10);
        barreAjout.setPadding(new Insets(20, 30, 10, 30));
        barreAjout.setAlignment(Pos.CENTER_LEFT);

        TextField nouvelleTache = new TextField();
        nouvelleTache.setPromptText("Titre de la nouvelle tâche...");
        nouvelleTache.setPrefWidth(350);
        nouvelleTache.setStyle("-fx-padding: 8; -fx-font-size: 13;");

        Button btnAjouter = new Button("+ Ajouter");
        btnAjouter.setStyle(
                "-fx-background-color: #6C63FF;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13;" +
                        "-fx-padding: 8 16;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 6;"
        );

        barreAjout.getChildren().addAll(nouvelleTache, btnAjouter);

        // ===== LISTE DES TACHES =====
        ListView<String> listeTaches = new ListView<>();
        listeTaches.setStyle("-fx-font-size: 14;");
        VBox.setVgrow(listeTaches, Priority.ALWAYS);

        // Message vide
        Label messageVide = new Label("Aucune tâche pour le moment. Ajoutez-en une ! 👆");
        messageVide.setTextFill(Color.GRAY);
        messageVide.setFont(Font.font("Arial", 14));

        // Action bouton ajouter
        btnAjouter.setOnAction(e -> {
            String texte = nouvelleTache.getText().trim();
            if (!texte.isEmpty()) {
                listeTaches.getItems().add("📌 " + texte);
                nouvelleTache.clear();
                messageVide.setVisible(false);
            }
        });

        // Aussi avec la touche Entrée
        nouvelleTache.setOnAction(e -> btnAjouter.fire());

        // ===== CONTENU =====
        VBox contenu = new VBox(10);
        contenu.setPadding(new Insets(10, 30, 30, 30));
        contenu.setStyle("-fx-background-color: #F9F9F9;");
        VBox.setVgrow(contenu, Priority.ALWAYS);

        contenu.getChildren().addAll(
                barreAjout,
                messageVide,
                listeTaches
        );

        // ===== ROOT =====
        VBox root = new VBox();
        root.getChildren().addAll(header, contenu);
        VBox.setVgrow(contenu, Priority.ALWAYS);

        this.scene = new Scene(root, 900, 600);
    }

    public Scene getScene() {
        return scene;
    }
}