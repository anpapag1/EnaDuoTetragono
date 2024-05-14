package com.enaduotetragono;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.util.Objects;
import java.util.Random;
import javafx.util.Duration;
import java.util.Map;
import java.io.IOException;

public class ShapesController {

    @FXML
    private ImageView randShape;

    @FXML
    private BorderPane drop;

    @FXML
    private ImageView droppedShape;

    @FXML
    private ImageView shape1;

    @FXML
    private ImageView shape2;

    @FXML
    private ImageView shape3;

    @FXML
    private ImageView shape4;

    @FXML
    private ImageView shape5;

    @FXML
    private Button retry;

    @FXML
    private Button home;

    @FXML
    private Button check;

    @FXML
    private Label alert;

    int prevrandNum = 0;

    public void initialize() {
        alert.setOpacity(0);
        displayRand();

        drop.setOnDragOver(this::onDragOver);
        drop.setOnDragDropped(this::onDragDropped);
    }

    private void displayRand(){
        // Add a random shape image when the window is created
        Random random = new Random();
        int randNum = random.nextInt(5) + 1; // Generate a random number between 1 and 5
        while (randNum == prevrandNum) randNum = random.nextInt(5) + 1; //makes sure that the shape is not the same
        prevrandNum = randNum;
        String imageUrl = "/images/shape_" + randNum + ".png";
        Image image = new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toExternalForm());
        randShape.setImage(image);
        droppedShape.setImage(null);
        showShape();
    }

    @FXML
    private void onDragDetected(MouseEvent event) {
        ImageView source = (ImageView) event.getSource();
        source.setVisible(false);
        Dragboard db = source.startDragAndDrop(javafx.scene.input.TransferMode.MOVE);

        // Create a custom node for the drag view
        ImageView dragView = new ImageView(source.getImage()); // Use the same image as the source
        dragView.setFitWidth(100); // Set custom width
        dragView.setFitHeight(100); // Set custom height
        dragView.setOpacity(1); // Set custom opacity

        // Make the scene background transparent
        Scene scene = source.getScene();
        scene.setFill(null);

        // Set the drag view with a transparent background
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT); // Set transparent fill
        db.setDragView(dragView.snapshot(params, null)); // Use the custom node as the drag view

        db.setContent(Map.of(javafx.scene.input.DataFormat.PLAIN_TEXT, source.getImage().getUrl()));
        event.consume();
    }

    @FXML
    private void onDragDone(){
        showShape();
    }
    private void onDragOver(DragEvent event) {
        if (event.getDragboard().hasString()) {
            event.acceptTransferModes(javafx.scene.input.TransferMode.MOVE);
        }
        event.consume();
    }

    private void onDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            Image image = new Image(db.getString());
            droppedShape.setImage(image);
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void handleRetry() {
        // Reload the FXML file and recreate the scene
        try {
            Parent root = FXMLLoader.load(getClass().getResource("shapes-game.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) retry.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleHomeButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home-page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) home.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCheckButton(ActionEvent event) {
        if (randShape.getImage() == null || droppedShape.getImage() == null) {
            alert.setText("Βαλε ενα σχημα στο πλεσιο");
            alert.setStyle("-fx-background-color: #696363;");
        }else if (randShape.getImage().getUrl().equals(droppedShape.getImage().getUrl())) {
            alert.setText("Μπραβοο!!");
            alert.setStyle("-fx-background-color: #178d19;");
            displayRand();
        }else {
            alert.setText("Λαθος, ξαναπροσπαθησε");
            alert.setStyle("-fx-background-color: #8d3333;");
        }
        FadeTransition fadeUp = new FadeTransition(Duration.millis(1000), alert);
        fadeUp.setFromValue(0.0f);
        fadeUp.setToValue(1.0f);
        fadeUp.setCycleCount(1);
        fadeUp.setAutoReverse(false);
        fadeUp.play();
        FadeTransition fadeDown = new FadeTransition(Duration.millis(2000), alert);
        fadeDown.setFromValue(1.0f);
        fadeDown.setToValue(0.0f);
        fadeDown.setCycleCount(1);
        fadeDown.setAutoReverse(false);
        fadeDown.play();
    }

    private void showShape() {
        Image visible = droppedShape.getImage();
        if (visible == null) {
            shape1.setVisible(true);
            shape2.setVisible(true);
            shape3.setVisible(true);
            shape4.setVisible(true);
            shape5.setVisible(true);
        } else {
            shape1.setVisible(!Objects.equals(visible.getUrl(), shape1.getImage().getUrl()));
            shape2.setVisible(!Objects.equals(visible.getUrl(), shape2.getImage().getUrl()));
            shape3.setVisible(!Objects.equals(visible.getUrl(), shape3.getImage().getUrl()));
            shape4.setVisible(!Objects.equals(visible.getUrl(), shape4.getImage().getUrl()));
            shape5.setVisible(!Objects.equals(visible.getUrl(), shape5.getImage().getUrl()));
        }
    }
}
