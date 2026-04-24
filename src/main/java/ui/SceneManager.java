package ui;

import javafx.scene.Scene;

public class SceneManager {

    public static void switchTo(String screenName) {
        Scene scene = null;

        switch (screenName) {
            case "login":
                scene = new LoginView().getScene();
                break;
            case "inscription":
                scene = new InscriptionView().getScene();
                break;
            case "main":
                scene = new MainView().getScene();
                break;
            case "tasks":
                scene = new TaskView().getScene();
                break;
            case "taskform":
                scene = new TaskFormView().getScene();
                break;
            case "profil":
                scene = new ProfilView().getScene();
                break;
            case "ai":
                scene = new AIView().getScene();
                break;
            case "stats":
                scene = new StatsView().getScene();
                break;
            default:
                System.out.println("Écran inconnu : " + screenName);
                return;
        }

        MainApp.primaryStage.setScene(scene);
    }
}