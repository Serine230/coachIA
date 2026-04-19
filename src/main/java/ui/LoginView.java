package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class LoginView {

    private Scene scene;

    public LoginView() {
        // ===== TITRE =====
        Label titre = new Label("🧠 Coach Productivité IA");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titre.setTextFill(Color.web("#6C63FF"));

        Label sousTitre = new Label("Connectez-vous pour continuer");
        sousTitre.setFont(Font.font("Arial", 14));
        sousTitre.setTextFill(Color.GRAY);

        // ===== CHAMPS =====
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(300);
        emailField.setStyle("-fx-padding: 10; -fx-font-size: 14;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.setMaxWidth(300);
        passwordField.setStyle("-fx-padding: 10; -fx-font-size: 14;");

        // ===== MESSAGE ERREUR =====
        Label erreurLabel = new Label("");
        erreurLabel.setTextFill(Color.RED);
        erreurLabel.setFont(Font.font("Arial", 12));

        // ===== BOUTON LOGIN =====
        Button loginBtn = new Button("Se connecter");
        loginBtn.setMaxWidth(300);
        loginBtn.setStyle(
                "-fx-background-color: #6C63FF;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 8;"
        );

        // Action bouton login
        loginBtn.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (email.isEmpty() || password.isEmpty()) {
                erreurLabel.setText("⚠️ Veuillez remplir tous les champs.");
            } else {
                // TODO Jour 2 : relier à AuthService
                erreurLabel.setTextFill(Color.GREEN);
                erreurLabel.setText("✅ Connexion en cours...");
                SceneManager.switchTo("main");
            }
        });

        // ===== LIEN INSCRIPTION =====
        Hyperlink inscriptionLink = new Hyperlink("Pas encore de compte ? S'inscrire");
        inscriptionLink.setOnAction(e -> {
            // TODO Jour 2 : afficher écran inscription
            erreurLabel.setTextFill(Color.BLUE);
            erreurLabel.setText("Inscription — à implémenter Jour 2");
        });

        // ===== LAYOUT =====
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(60));
        layout.getChildren().addAll(
                titre,
                sousTitre,
                new Separator(),
                emailField,
                passwordField,
                erreurLabel,
                loginBtn,
                inscriptionLink
        );

        layout.setStyle("-fx-background-color: #F9F9F9;");

        this.scene = new Scene(layout, 900, 600);
    }

    public Scene getScene() {
        return scene;
    }
}