package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;

public class TaskView {

    private Scene scene;
    private TaskService taskService;
    private ObservableList<Task> tasksList;

    public TaskView() {

        taskService = new TaskService();
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

        Label titre = new Label("📋 Mes Tâches");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titre.setTextFill(Color.WHITE);

        Button btnAjouter = new Button("+ Nouvelle Tâche");
        btnAjouter.setStyle(
                "-fx-background-color: #6C63FF;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13;" +
                        "-fx-padding: 8 16;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 6;"
        );
        btnAjouter.setOnAction(e -> SceneManager.switchTo("taskform"));

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(btnRetour, titre, spacer, btnAjouter);

        // ===== FILTRES =====
        HBox filtres = new HBox(10);
        filtres.setPadding(new Insets(15, 30, 15, 30));
        filtres.setAlignment(Pos.CENTER_LEFT);
        filtres.setStyle("-fx-background-color: #EDEDF5;");

        Label filtreLabel = new Label("Filtrer :");
        filtreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        ComboBox<String> filtreStatut = new ComboBox<>();
        filtreStatut.getItems().addAll("Tous", "À faire", "En cours", "Terminé");
        filtreStatut.setValue("Tous");

        ComboBox<String> filtrePriorite = new ComboBox<>();
        filtrePriorite.getItems().addAll("Toutes", "Basse", "Moyenne", "Haute");
        filtrePriorite.setValue("Toutes");

        filtres.getChildren().addAll(filtreLabel, filtreStatut, filtrePriorite);

        // ===== TABLEAU DES TÂCHES =====
        TableView<Task> tableView = new TableView<>();
        tableView.setStyle("-fx-font-size: 13;");
        VBox.setVgrow(tableView, Priority.ALWAYS);

        TableColumn<Task, String> colTitre = new TableColumn<>("Titre");
        colTitre.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getTitre()));
        colTitre.setPrefWidth(250);

        TableColumn<Task, String> colPriorite = new TableColumn<>("Priorité");
        colPriorite.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getPriorite().name()));
        colPriorite.setPrefWidth(100);

        TableColumn<Task, String> colStatut = new TableColumn<>("Statut");
        colStatut.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getStatut().name()));
        colStatut.setPrefWidth(100);

        TableColumn<Task, String> colDate = new TableColumn<>("Date limite");
        colDate.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getDateLimite() != null ?
                                data.getValue().getDateLimite().toString() : ""));
        colDate.setPrefWidth(120);

        TableColumn<Task, Void> colActions = new TableColumn<>("Actions");
        colActions.setPrefWidth(150);
        colActions.setCellFactory(col -> new TableCell<>() {
            Button btnSuppr = new Button("🗑 Supprimer");
            {
                btnSuppr.setStyle(
                        "-fx-background-color: #E74C3C;" +
                                "-fx-text-fill: white;" +
                                "-fx-cursor: hand;" +
                                "-fx-background-radius: 4;"
                );
                btnSuppr.setOnAction(e -> {
                    Task task = getTableView().getItems().get(getIndex());
                    taskService.supprimer(task.getId());
                    getTableView().getItems().remove(task);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnSuppr);
            }
        });

        tableView.getColumns().addAll(
                colTitre, colPriorite, colStatut, colDate, colActions);

        // Charger les tâches
        if (user != null) {
            List<Task> tasks = taskService.getTachesParUser(user.getId());
            tasksList = FXCollections.observableArrayList(tasks);
            tableView.setItems(tasksList);
        }

        // Filtres actions
        filtreStatut.setOnAction(e -> {
            if (user != null) {
                List<Task> filtered;
                switch (filtreStatut.getValue()) {
                    case "À faire":
                        filtered = taskService.filtrerParStatut(
                                user.getId(), Task.Statut.A_FAIRE);
                        break;
                    case "En cours":
                        filtered = taskService.filtrerParStatut(
                                user.getId(), Task.Statut.EN_COURS);
                        break;
                    case "Terminé":
                        filtered = taskService.filtrerParStatut(
                                user.getId(), Task.Statut.TERMINE);
                        break;
                    default:
                        filtered = taskService.getTachesParUser(user.getId());
                }
                tableView.setItems(FXCollections.observableArrayList(filtered));
            }
        });

        // ===== CONTENU =====
        VBox contenu = new VBox();
        contenu.setStyle("-fx-background-color: #F9F9F9;");
        VBox.setVgrow(contenu, Priority.ALWAYS);
        contenu.getChildren().addAll(filtres, tableView);

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