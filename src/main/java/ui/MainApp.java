package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        primaryStage.setTitle("Coach Intelligent de Productivité IA");
        primaryStage.setWidth(900);
        primaryStage.setHeight(600);

        // On démarre sur l'écran Login
        SceneManager.switchTo("login");



        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}