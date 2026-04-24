package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import model.User;

public class MainView {

    private Scene scene;

    public MainView() {

        User user = LoginView.getUtilisateurConnecte();

        // ===== MENU LATERAL =====
        VBox menuLateral = new VBox(10);
        menuLateral.setPrefWidth(200);
        menuLateral.setPadding(new Insets(30, 15, 30, 15));
        menuLateral.setStyle("-fx-background-color: #2D2D4E;");

        Label appNom = new Label("🧠 Coach IA");
        appNom.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        appNom.setTextFill(Color.WHITE);

        Label userNom = new Label(user != null ? "👋 " + user.getNom() : "👋 Utilisateur");
        userNom.setFont(Font.font("Arial", 13));
        userNom.setTextFill(Color.LIGHTGRAY);

        Button btnTaches      = new Button("📋  Mes Tâches");
        Button btnStats       = new Button("📊  Statistiques");
        Button btnIA          = new Button("🤖  Assistant IA");
        Button btnProfil      = new Button("👤  Profil");
        Button btnDeconnexion = new Button("🚪  Déconnexion");

        String styleBtnMenu =
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 10 15;" +
                        "-fx-cursor: hand;" +
                        "-fx-alignment: CENTER-LEFT;" +
                        "-fx-max-width: infinity;";

        btnTaches.setStyle(styleBtnMenu);
        btnStats.setStyle(styleBtnMenu);
        btnIA.setStyle(styleBtnMenu);
        btnProfil.setStyle(styleBtnMenu);
        btnDeconnexion.setStyle(styleBtnMenu);

        btnTaches.setMaxWidth(Double.MAX_VALUE);
        btnStats.setMaxWidth(Double.MAX_VALUE);
        btnIA.setMaxWidth(Double.MAX_VALUE);
        btnProfil.setMaxWidth(Double.MAX_VALUE);
        btnDeconnexion.setMaxWidth(Double.MAX_VALUE);

        // Actions navigation
        btnTaches.setOnAction(e -> SceneManager.switchTo("tasks"));
        btnStats.setOnAction(e -> SceneManager.switchTo("stats"));
        btnIA.setOnAction(e -> SceneManager.switchTo("ai"));
        btnProfil.setOnAction(e -> SceneManager.switchTo("profil"));
        btnDeconnexion.setOnAction(e -> SceneManager.switchTo("login"));

        menuLateral.getChildren().addAll(
                appNom,
                userNom,
                new Separator(),
                btnTaches,
                btnStats,
                btnIA,
                btnProfil,
                btnDeconnexion
        );

        // ===== CONTENU PRINCIPAL =====
        VBox contenu = new VBox(20);
        contenu.setPadding(new Insets(40));
        contenu.setStyle("-fx-background-color: #F9F9F9;");

        Label bienvenue = new Label("👋 Bonjour " + (user != null ? user.getNom() : "") + " !");
        bienvenue.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        bienvenue.setTextFill(Color.web("#2D2D4E"));

        Label sousTitre = new Label("Que voulez-vous faire aujourd'hui ?");
        sousTitre.setFont(Font.font("Arial", 14));
        sousTitre.setTextFill(Color.GRAY);

        // ===== CARTES RESUME =====
        HBox cartes = new HBox(20);
        cartes.getChildren().addAll(
                creerCarte("📋 Tâches", "0 tâches en cours", "#6C63FF"),
                creerCarte("✅ Terminées", "0 cette semaine", "#2ECC71"),
                creerCarte("🤖 IA", "Prêt à vous aider", "#E67E22")
        );

        contenu.getChildren().addAll(
                bienvenue,
                sousTitre,
                new Separator(),
                cartes
        );

        // ===== LAYOUT PRINCIPAL =====
        HBox root = new HBox();
        root.getChildren().addAll(menuLateral, contenu);
        HBox.setHgrow(contenu, Priority.ALWAYS);

        this.scene = new Scene(root, 900, 600);
    }

    private VBox creerCarte(String titre, String valeur, String couleur) {
        VBox carte = new VBox(8);
        carte.setPadding(new Insets(20));
        carte.setPrefWidth(180);
        carte.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);"
        );

        Label titreLbl = new Label(titre);
        titreLbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        titreLbl.setTextFill(Color.web(couleur));

        Label valeurLbl = new Label(valeur);
        valeurLbl.setFont(Font.font("Arial", 12));
        valeurLbl.setTextFill(Color.GRAY);

        carte.getChildren().addAll(titreLbl, valeurLbl);
        return carte;
    }

    public Scene getScene() {
        return scene;
    }
}