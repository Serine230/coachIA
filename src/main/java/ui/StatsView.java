package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import model.Task;
import model.User;
import service.TaskService;
import java.util.List;

public class StatsView {

    private Scene scene;

    public StatsView() {

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
        btnRetour.setOnAction(e -> SceneManager.switchTo("main"));

        Label titre = new Label("📊 Statistiques");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titre.setTextFill(Color.WHITE);

        header.getChildren().addAll(btnRetour, titre);

        // ===== CONTENU =====
        VBox contenu = new VBox(25);
        contenu.setPadding(new Insets(30));
        contenu.setStyle("-fx-background-color: #F9F9F9;");
        VBox.setVgrow(contenu, Priority.ALWAYS);

        // Charger les tâches
        int totalTaches = 0;
        int tachesTerminees = 0;
        int tachesEnCours = 0;
        int tachesAFaire = 0;

        if (user != null) {
            List<Task> tasks = taskService.getTachesParUser(user.getId());
            totalTaches = tasks.size();
            for (Task t : tasks) {
                if (t.getStatut() == Task.Statut.TERMINE) tachesTerminees++;
                else if (t.getStatut() == Task.Statut.EN_COURS) tachesEnCours++;
                else tachesAFaire++;
            }
        }

        // ===== CARTES STATS =====
        Label statsLabel = new Label("Vue d'ensemble");
        statsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        statsLabel.setTextFill(Color.web("#2D2D4E"));

        HBox cartes = new HBox(20);
        cartes.getChildren().addAll(
                creerCarte("📋 Total", String.valueOf(totalTaches), "#6C63FF"),
                creerCarte("✅ Terminées", String.valueOf(tachesTerminees), "#2ECC71"),
                creerCarte("🔄 En cours", String.valueOf(tachesEnCours), "#E67E22"),
                creerCarte("📝 À faire", String.valueOf(tachesAFaire), "#E74C3C")
        );

        // ===== GRAPHIQUE =====
        Label graphLabel = new Label("Répartition des tâches");
        graphLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        graphLabel.setTextFill(Color.web("#2D2D4E"));

        // PieChart
        PieChart pieChart = new PieChart();
        if (tachesTerminees > 0)
            pieChart.getData().add(new PieChart.Data("Terminées", tachesTerminees));
        if (tachesEnCours > 0)
            pieChart.getData().add(new PieChart.Data("En cours", tachesEnCours));
        if (tachesAFaire > 0)
            pieChart.getData().add(new PieChart.Data("À faire", tachesAFaire));
        if (totalTaches == 0)
            pieChart.getData().add(new PieChart.Data("Aucune tâche", 1));

        pieChart.setMaxHeight(250);
        pieChart.setLegendVisible(true);

        // ===== BARCHART =====
        Label barLabel = new Label("Tâches par priorité");
        barLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        barLabel.setTextFill(Color.web("#2D2D4E"));

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setMaxHeight(200);
        barChart.setLegendVisible(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        if (user != null) {
            List<Task> tasks = taskService.getTachesParUser(user.getId());
            long haute = tasks.stream()
                    .filter(t -> t.getPriorite() == Task.Priorite.HAUTE).count();
            long moyenne = tasks.stream()
                    .filter(t -> t.getPriorite() == Task.Priorite.MOYENNE).count();
            long basse = tasks.stream()
                    .filter(t -> t.getPriorite() == Task.Priorite.BASSE).count();

            series.getData().add(new XYChart.Data<>("Haute", haute));
            series.getData().add(new XYChart.Data<>("Moyenne", moyenne));
            series.getData().add(new XYChart.Data<>("Basse", basse));
        }
        barChart.getData().add(series);

        contenu.getChildren().addAll(
                statsLabel,
                cartes,
                graphLabel,
                pieChart,
                barLabel,
                barChart
        );

        // ===== ROOT =====
        VBox root = new VBox();
        root.getChildren().addAll(header, contenu);
        VBox.setVgrow(contenu, Priority.ALWAYS);

        this.scene = new Scene(root, 900, 600);
    }

    private VBox creerCarte(String titre, String valeur, String couleur) {
        VBox carte = new VBox(8);
        carte.setPadding(new Insets(20));
        carte.setPrefWidth(150);
        carte.setAlignment(Pos.CENTER);
        carte.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;"
        );

        Label valeurLbl = new Label(valeur);
        valeurLbl.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        valeurLbl.setTextFill(Color.web(couleur));

        Label titreLbl = new Label(titre);
        titreLbl.setFont(Font.font("Arial", 13));
        titreLbl.setTextFill(Color.GRAY);

        carte.getChildren().addAll(valeurLbl, titreLbl);
        return carte;
    }

    public Scene getScene() {
        return scene;
    }
}