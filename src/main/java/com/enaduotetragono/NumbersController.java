package com.enaduotetragono;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class NumbersController {

    @FXML
    private Label question;

    @FXML
    private Label alert;

    @FXML
    private Button home;

    @FXML
    private Button retry;

    @FXML
    private Button guess1;

    @FXML
    private Button guess2;

    @FXML
    private Button guess3;

    private int operand1;
    private int operand2;
    private char operator;
    private int answer;

    public void initialize() {
        generateEquation();
        alert.setOpacity(0);
    }

    private void generateEquation() {
        Random random = new Random();

        // Generate operands
        operand1 = random.nextInt(10) + 1; // Random number between 1 and 10
        operand2 = random.nextInt(10) + 1;

        // Generate operator
        int operatorIndex = random.nextInt(4);
        switch (operatorIndex) {
            case 0:
                operator = '+';
                answer = operand1 + operand2;
                break;
            case 1:
                operator = '-';
                answer = operand1 - operand2;
                break;
            case 2:
                operator = '*';
                answer = operand1 * operand2;
                break;
            case 3:
                operator = '/';
                answer = operand1 / operand2;
                break;
        }

        // Display equation
        question.setText(operand1 + " " + operator + " " + operand2 + " = ?");

        // Randomly place the correct answer in one of the buttons
        int correctButtonIndex = random.nextInt(3);
        switch (correctButtonIndex) {
            case 0:
                guess1.setText(String.valueOf(answer));
                guess2.setText(String.valueOf(random.nextInt(20) + 1)); // Random wrong answer
                guess3.setText(String.valueOf(random.nextInt(20) + 1)); // Random wrong answer
                break;
            case 1:
                guess1.setText(String.valueOf(random.nextInt(20) + 1)); // Random wrong answer
                guess2.setText(String.valueOf(answer));
                guess3.setText(String.valueOf(random.nextInt(20) + 1)); // Random wrong answer
                break;
            case 2:
                guess1.setText(String.valueOf(random.nextInt(20) + 1)); // Random wrong answer
                guess2.setText(String.valueOf(random.nextInt(20) + 1)); // Random wrong answer
                guess3.setText(String.valueOf(answer));
                break;
        }
    }

    @FXML
    private void handleGuessButton(Button button) {
        String buttonText = button.getText();
        int guessedAnswer = Integer.parseInt(buttonText);
        if (guessedAnswer == answer) {
            alert.setText("Μπραβοο!!");
            alert.setStyle("-fx-background-color: #178d19;");
            generateEquation();
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Result");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleRetry() {
        // Reload the FXML file and recreate the scene
        try {
            Parent root = FXMLLoader.load(getClass().getResource("numbers-game.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) retry.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleHomeButton() {
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
    private void handleGuess1() {
        handleGuessButton(guess1);
    }

    @FXML
    private void handleGuess2() {
        handleGuessButton(guess2);
    }

    @FXML
    private void handleGuess3() {
        handleGuessButton(guess3);
    }
}
