package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import model.Task;
import model.User;
import service.TaskService;

public class TaskFormView {

    private Scene scene;

    public TaskFormView() {

        TaskService taskService = new TaskService();
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
        btnRetour.setOnAction(e -> SceneManager.switchTo("tasks"));

        Label titre = new Label("➕ Nouvelle Tâche");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titre.setTextFill(Color.WHITE);

        header.getChildren().addAll(btnRetour, titre);

        // ===== FORMULAIRE =====
        VBox form = new VBox(15);
        form.setPadding(new Insets(40));
        form.setAlignment(Pos.TOP_CENTER);
        form.setStyle("-fx-background-color: #F9F9F9;");

        // Titre
        TextField titreField = new TextField();
        titreField.setPromptText("Titre de la tâche *");
        titreField.setMaxWidth(400);
        titreField.setStyle("-fx-padding: 10; -fx-font-size: 14;");

        // Description
        TextArea descField = new TextArea();
        descField.setPromptText("Description (optionnel)");
        descField.setMaxWidth(400);
        descField.setPrefRowCount(3);
        descField.setStyle("-fx-font-size: 13;");

        // Priorité
        ComboBox<String> prioriteBox = new ComboBox<>();
        prioriteBox.getItems().addAll("BASSE", "MOYENNE", "HAUTE");
        prioriteBox.setValue("MOYENNE");
        prioriteBox.setMaxWidth(400);

        // Date limite
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Date limite");
        datePicker.setMaxWidth(400);

        // Message
        Label messageLabel = new Label("");
        messageLabel.setFont(Font.font("Arial", 12));

        // Bouton sauvegarder
        Button sauvegarderBtn = new Button("💾 Sauvegarder la tâche");
        sauvegarderBtn.setMaxWidth(400);
        sauvegarderBtn.setStyle(
                "-fx-background-color: #6C63FF;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 10;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 8;"
        );

        sauvegarderBtn.setOnAction(e -> {
            String titreText = titreField.getText().trim();

            if (titreText.isEmpty()) {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("⚠️ Le titre est obligatoire !");
                return;
            }

            Task task = new Task();
            task.setTitre(titreText);
            task.setDescription(descField.getText().trim());
            task.setPriorite(Task.Priorite.valueOf(prioriteBox.getValue()));
            task.setStatut(Task.Statut.A_FAIRE);
            task.setDateLimite(datePicker.getValue());
            task.setUserId(user != null ? user.getId() : 0);
            task.setCategoryId(0);

            boolean succes = taskService.creerTache(task);
            if (succes) {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("✅ Tâche créée !");
                SceneManager.switchTo("tasks");
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("❌ Erreur lors de la création.");
            }
        });

        form.getChildren().addAll(
                new Label("Titre *"),
                titreField,
                new Label("Description"),
                descField,
                new Label("Priorité"),
                prioriteBox,
                new Label("Date limite"),
                datePicker,
                messageLabel,
                sauvegarderBtn
        );

        // ===== ROOT =====
        VBox root = new VBox();
        root.getChildren().addAll(header, form);
        VBox.setVgrow(form, Priority.ALWAYS);

        this.scene = new Scene(root, 900, 600);
    }

    public Scene getScene() {
        return scene;
    }
}