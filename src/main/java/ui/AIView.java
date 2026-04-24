package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import service.GeminiService;

public class AIView {

    private Scene scene;

    public AIView() {

        GeminiService geminiService = new GeminiService();

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

        Label titre = new Label("🤖 Assistant IA");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titre.setTextFill(Color.WHITE);

        header.getChildren().addAll(btnRetour, titre);

        // ===== CONTENU =====
        VBox contenu = new VBox(20);
        contenu.setPadding(new Insets(30));
        contenu.setStyle("-fx-background-color: #F9F9F9;");
        VBox.setVgrow(contenu, Priority.ALWAYS);

        // ===== SÉLECTION FONCTION =====
        Label fonctionLabel = new Label("Choisir une fonction IA :");
        fonctionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        ComboBox<String> fonctionBox = new ComboBox<>();
        fonctionBox.getItems().addAll(
                "✂️ Découper une tâche",
                "🎯 Reformuler un objectif",
                "📅 Conseils de planification",
                "⚡ Proposer des priorités"
        );
        fonctionBox.setValue("✂️ Découper une tâche");
        fonctionBox.setMaxWidth(400);

        // ===== CHAMP INPUT =====
        Label inputLabel = new Label("Votre texte :");
        inputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextArea inputArea = new TextArea();
        inputArea.setPromptText("Écrivez ici votre tâche ou objectif...");
        inputArea.setMaxWidth(600);
        inputArea.setPrefRowCount(4);
        inputArea.setStyle("-fx-font-size: 13;");

        // ===== BOUTON =====
        Button btnAnalyser = new Button("🚀 Analyser avec l'IA");
        btnAnalyser.setStyle(
                "-fx-background-color: #6C63FF;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14;" +
                        "-fx-padding: 10 20;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 8;"
        );

        // ===== RÉSULTAT =====
        Label resultLabel = new Label("Résultat :");
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setMaxWidth(600);
        resultArea.setPrefRowCount(8);
        resultArea.setStyle("-fx-font-size: 13; -fx-background-color: white;");
        resultArea.setWrapText(true);

        Label loadingLabel = new Label("");
        loadingLabel.setTextFill(Color.web("#6C63FF"));
        loadingLabel.setFont(Font.font("Arial", 13));

        // ===== ACTION BOUTON =====
        btnAnalyser.setOnAction(e -> {
            String input = inputArea.getText().trim();

            if (input.isEmpty()) {
                loadingLabel.setText("⚠️ Veuillez entrer un texte !");
                return;
            }

            loadingLabel.setText("⏳ L'IA analyse votre demande...");
            resultArea.setText("");
            btnAnalyser.setDisable(true);

            // Appel API dans un thread séparé
            new Thread(() -> {
                String resultat;
                String fonction = fonctionBox.getValue();

                if (fonction.contains("Découper")) {
                    resultat = geminiService.decouperTache(input);
                } else if (fonction.contains("Reformuler")) {
                    resultat = geminiService.reformulerObjectif(input);
                } else if (fonction.contains("planification")) {
                    resultat = geminiService.conseilsPlanification(input);
                } else {
                    resultat = geminiService.proposerPriorites(input);
                }

                final String finalResultat = resultat;
                javafx.application.Platform.runLater(() -> {
                    resultArea.setText(finalResultat);
                    loadingLabel.setText("✅ Analyse terminée !");
                    btnAnalyser.setDisable(false);
                });
            }).start();
        });

        contenu.getChildren().addAll(
                fonctionLabel,
                fonctionBox,
                inputLabel,
                inputArea,
                btnAnalyser,
                loadingLabel,
                resultLabel,
                resultArea
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