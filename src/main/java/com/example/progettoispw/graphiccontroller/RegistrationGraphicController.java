package com.example.progettoispw.graphiccontroller;

import bean.RegistrationBean;
import com.jfoenix.controls.JFXButton;
import applicationcontroller.RegistrationController;
import exception.PasswordReadException;
import exception.UserAlreadyExistsException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utility.AccessUtility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegistrationGraphicController extends GeneralGraphicController {

    private final SceneNavigatorGraphicController sceneController = SceneNavigatorGraphicController.getInstance(null);

    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private JFXButton signupBtn;
    @FXML
    private Label messageLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        signupBtn.setOnMouseClicked(event -> handleSignup());

        super.initialize(url, resourceBundle);
    }

    private void handleSignup() {

        StringBuilder missingFields = new StringBuilder();

        if (textFieldUsername.getText().isEmpty()) missingFields.append("- Username\n");
        if (textFieldEmail.getText().isEmpty()) missingFields.append("- Email\n");
        if (passwordFieldPassword.getText().isEmpty()) missingFields.append("- Password\n");

        if (!missingFields.isEmpty()) {
            showAlert("Incomplete Registration", "Required fields missing!", missingFields.toString());
            return;
        }

        RegistrationBean registrationBean = new RegistrationBean(
                textFieldEmail.getText(),
                passwordFieldPassword.getText(),
                textFieldUsername.getText()
        );

        String emailValidation = registrationBean.validate();
        if (emailValidation != null) {
            showAlert("Invalid Input", "Email validation failed", emailValidation);
            return;
        }

        attemptRegistration(registrationBean);
    }

    private void attemptRegistration(RegistrationBean registrationBean) {
        try {
            RegistrationController registrationController = new RegistrationController(registrationBean);
            registrationController.registrateUser(AccessUtility.getPersistence());

            // Disabilita i campi e bottone
            textFieldEmail.setDisable(true);
            textFieldUsername.setDisable(true);
            passwordFieldPassword.setDisable(true);
            signupBtn.setDisable(true);

            // Messaggio di conferma lungo e dettagliato
            StringBuilder successContent = new StringBuilder();
            successContent.append("Congratulations!\n\n");
            successContent.append("Your account has been successfully created.\n\n");
            successContent.append("Account details:\n");
            successContent.append("Username: ").append(textFieldUsername.getText()).append("\n");
            successContent.append("Email: ").append(textFieldEmail.getText()).append("\n");
            successContent.append("Role: User\n\n");

            showAlert("Registration Successful", "Welcome!", successContent.toString());

        } catch (SQLException | UserAlreadyExistsException | PasswordReadException | IOException e) {
            showAlert("Registration Error", "Failed to create account", e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);

            TextArea textArea = new TextArea(content);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);

            alert.getDialogPane().setContent(textArea);
            alert.show();
        });
    }
}
