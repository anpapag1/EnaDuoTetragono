package com.enaduotetragono;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            // Load new FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home-page.fxml"));
            Parent root = loader.load();

            // Create new stage
            Stage newStage = new Stage();
            newStage.setTitle("Ενα, Δυο, Τετράγωνο...");
            newStage.getIcons().add(new Image(String.valueOf(Application.class.getResource("/images/logo.png"))));
            newStage.setScene(new Scene(root));

            newStage.setWidth(700);
            newStage.setHeight(500);
            // Show the new stage
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFxml(String load,Node button){
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource(load));
            Parent root = loader.load();
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
