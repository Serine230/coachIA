package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import model.User;
import service.AuthService;
import dao.UserDAO;

public class ProfilView {

    private Scene scene;

    public ProfilView() {

        UserDAO userDAO = new UserDAO();
        User user = LoginView.getUtilisateurConnecte();

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

        Label titre = new Label("👤 Mon Profil");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titre.setTextFill(Color.WHITE);

        header.getChildren().addAll(btnRetour, titre);

        // ===== CONTENU =====
        VBox contenu = new VBox(20);
        contenu.setPadding(new Insets(40));
        contenu.setAlignment(Pos.TOP_CENTER);
        contenu.setStyle("-fx-background-color: #F9F9F9;");

        // Avatar
        Label avatar = new Label("👤");
        avatar.setFont(Font.font("Arial", 60));

        // Nom
        Label nomLabel = new Label("Nom :");
        nomLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextField nomField = new TextField(user != null ? user.getNom() : "");
        nomField.setMaxWidth(300);
        nomField.setStyle("-fx-padding: 10; -fx-font-size: 14;");

        // Email
        Label emailLabel = new Label("Email :");
        emailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextField emailField = new TextField(user != null ? user.getEmail() : "");
        emailField.setMaxWidth(300);
        emailField.setStyle("-fx-padding: 10; -fx-font-size: 14;");

        // Message
        Label messageLabel = new Label("");
        messageLabel.setFont(Font.font("Arial", 12));

        // Bouton sauvegarder
        Button sauvegarderBtn = new Button("💾 Sauvegarder");
        sauvegarderBtn.setMaxWidth(300);
        sauvegarderBtn.setStyle(
                "-fx-background-color: #6C63FF;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 8;"
        );

        sauvegarderBtn.setOnAction(e -> {
            if (user != null) {
                user.setNom(nomField.getText().trim());
                user.setEmail(emailField.getText().trim());
                boolean succes = userDAO.updateProfile(user);
                if (succes) {
                    messageLabel.setTextFill(Color.GREEN);
                    messageLabel.setText("✅ Profil mis à jour !");
                } else {
                    messageLabel.setTextFill(Color.RED);
                    messageLabel.setText("❌ Erreur lors de la mise à jour.");
                }
            }
        });

        contenu.getChildren().addAll(
                avatar,
                nomLabel, nomField,
                emailLabel, emailField,
                messageLabel,
                sauvegarderBtn
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