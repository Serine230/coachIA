package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import service.AuthService;

public class InscriptionView {

    private Scene scene;

    public InscriptionView() {

        AuthService authService = new AuthService();

        // ===== TITRE =====
        Label titre = new Label("🧠 Créer un compte");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titre.setTextFill(Color.web("#6C63FF"));

        Label sousTitre = new Label("Rejoignez le Coach IA de Productivité");
        sousTitre.setFont(Font.font("Arial", 14));
        sousTitre.setTextFill(Color.GRAY);

        // ===== CHAMPS =====
        TextField nomField = new TextField();
        nomField.setPromptText("Nom complet");
        nomField.setMaxWidth(300);
        nomField.setStyle("-fx-padding: 10; -fx-font-size: 14;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(300);
        emailField.setStyle("-fx-padding: 10; -fx-font-size: 14;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe (min. 6 caractères)");
        passwordField.setMaxWidth(300);
        passwordField.setStyle("-fx-padding: 10; -fx-font-size: 14;");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirmer le mot de passe");
        confirmPasswordField.setMaxWidth(300);
        confirmPasswordField.setStyle("-fx-padding: 10; -fx-font-size: 14;");

        // ===== MESSAGE =====
        Label messageLabel = new Label("");
        messageLabel.setFont(Font.font("Arial", 12));

        // ===== BOUTON INSCRIPTION =====
        Button inscriptionBtn = new Button("Créer mon compte");
        inscriptionBtn.setMaxWidth(300);
        inscriptionBtn.setStyle(
                "-fx-background-color: #6C63FF;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 8;"
        );

        // Action bouton inscription
        inscriptionBtn.setOnAction(e -> {
            String nom = nomField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();

            // Validations
            if (nom.isEmpty() || email.isEmpty() ||
                    password.isEmpty() || confirmPassword.isEmpty()) {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("⚠️ Veuillez remplir tous les champs.");
                return;
            }

            if (!authService.isEmailValide(email)) {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("⚠️ Email invalide.");
                return;
            }

            if (!authService.isMotDePasseValide(password)) {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("⚠️ Mot de passe trop court (min. 6 caractères).");
                return;
            }

            if (!password.equals(confirmPassword)) {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("⚠️ Les mots de passe ne correspondent pas.");
                return;
            }

            // Inscription
            boolean succes = authService.inscrire(nom, email, password);
            if (succes) {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("✅ Compte créé ! Redirection...");
                SceneManager.switchTo("login");
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("❌ Email déjà utilisé.");
            }
        });

        // ===== LIEN LOGIN =====
        Hyperlink loginLink = new Hyperlink("Déjà un compte ? Se connecter");
        loginLink.setOnAction(e -> SceneManager.switchTo("login"));

        // ===== LAYOUT =====
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.getChildren().addAll(
                titre,
                sousTitre,
                new Separator(),
                nomField,
                emailField,
                passwordField,
                confirmPasswordField,
                messageLabel,
                inscriptionBtn,
                loginLink
        );

        layout.setStyle("-fx-background-color: #F9F9F9;");
        this.scene = new Scene(layout, 900, 600);
    }

    public Scene getScene() {
        return scene;
    }
}