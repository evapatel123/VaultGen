package com.securepassfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/main.fxml"));

        Scene scene = new Scene(loader.load());

        // Load CSS
        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm());

        // Window Settings
        stage.setTitle("🔐 SecurePassFX");

        stage.setScene(scene);

        stage.setMinWidth(900);
        stage.setMinHeight(650);

        // Uncomment after adding icon.png to resources
        // stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
