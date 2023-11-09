package com.example.csc311regexandjavadocweek9hw;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;


public class RegistrationFormController {
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField dobField;

    @FXML
    private TextField zipCodeField;

    @FXML
    private Label validationMessage;

    @FXML
    private Button addButton;

    private final String nameRegex = "[a-zA-Z]{2,25}";
    private final String emailRegex = "^[A-Za-z0-9+_.-]+@farmingdale\\.edu$";
    private final String zipCodeRegex = "\\d{5}";

    @FXML
    public void initialize() {
        addButton.setDisable(true);

        firstNameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateAndSetStyle(firstNameField, nameRegex);
                checkButtonState();
            }
        });

        lastNameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateAndSetStyle(lastNameField, nameRegex);
                checkButtonState();
            }
        });

        // Email field validation
        emailField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateAndSetStyle(emailField, emailRegex);
                checkButtonState();
            }
        });

        // Date of Birth field validation
        dobField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // Focus lost
                validateDateOfBirth();
                checkButtonState();
            }
        });

        zipCodeField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // Focus lost
                validateAndSetStyle(zipCodeField, zipCodeRegex);
                checkButtonState();
            }
        });
    }

    @FXML
    private void handleAddButton() {
        if (isValidInput()) {
            openNewScene();
        }
    }


    private void validateAndSetStyle(TextField field, String regex) {
        if (Pattern.matches(regex, field.getText())) {
            field.setStyle("");
            validationMessage.setText("");
        } else {
            field.setStyle("-fx-border-color: red;");
            validationMessage.setText("Invalid input. Please correct the field.");
        }
    }

    private void validateDateOfBirth() {
        if (isValidDate(dobField.getText())) {
            dobField.setStyle("");
            validationMessage.setText("");
        } else {
            dobField.setStyle("-fx-border-color: red;");
            validationMessage.setText("Invalid date format (MM/DD/YYYY).");
        }
    }

    private boolean isValidDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate date = LocalDate.parse(dateStr, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void checkButtonState() {
        addButton.setDisable(!isValidInput());
    }

    private boolean isValidInput() {
        return Pattern.matches(nameRegex, firstNameField.getText()) &&
                Pattern.matches(nameRegex, lastNameField.getText()) &&
                Pattern.matches(emailRegex, emailField.getText()) &&
                isValidDate(dobField.getText()) &&
                Pattern.matches(zipCodeRegex, zipCodeField.getText());
    }
    @FXML
    private void openNewScene() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddedUserUI.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Stage stage = new Stage();
        stage.setTitle("User Added!");
        stage.setScene(new Scene(root));

        stage.show();
        ((Stage) addButton.getScene().getWindow()).close();
    }


}

