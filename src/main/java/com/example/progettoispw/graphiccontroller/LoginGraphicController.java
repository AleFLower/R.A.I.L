package com.example.progettoispw.graphiccontroller;
import bean.LoginBean;
import com.jfoenix.controls.JFXButton;
import applicationcontroller.LoginController;
import exception.PasswordReadException;
import exception.UserNotFoundException;
import model.Role;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utility.NotificationHub;
import utility.NotificationItem;
import utility.AccessUtility;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class LoginGraphicController extends GeneralGraphicController {

    //aggiunta ora con il singleton
    private final SceneNavigatorGraphicController sceneController = SceneNavigatorGraphicController.getInstance(null);
    @FXML
    private JFXButton signupBtn;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private Label messageLabel;

    private LoginBean loginBean;

    /*questa classe gestisce la login page, avendo piu' button e non avendoli in comune con le altre page, ho creato
    * questo controller grafico apposta per essa, questa classe è il controller grafico della page LoginRegistrationView.fxml,
    * usa anche la classe ControllerGraficoSenzaAccesso nel caso in cui si dovessero premere altri pulsanti, lo faccio
    * per non dover duplicare codice inutilmente*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Registration Button Click Event
        signupBtn.setOnMouseClicked(event -> handleRegistration());

        // Login Button Click Event
        loginBtn.setOnMouseClicked(event -> handleLogin());

        // Call the superclass initialization for common logic
        super.initialize(url, resourceBundle);
    }

    private void handleRegistration() {
        try {
            sceneController.displayScene("/com/example/progettoispw/viewsfxml/RegistrationView.fxml");
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    private void handleLogin() {
        String email = emailTextField.getText();
        String password = passwordPasswordField.getText();

        // Check if email or password are empty
        if (isFieldEmpty(email, password)) {
            messageLabel.setText("Please enter all required fields");
        } else {
            processLogin(email, password);
        }
    }

    private boolean isFieldEmpty(String email, String password) {
        return email.equals("") || password.equals("");
    }

    private void processLogin(String email, String password) {
        loginBean = new LoginBean(email, password);

        // Validate email syntax
        String emailValidation = loginBean.validate();

        if (emailValidation == null) {
            attemptLogin();
        } else {
            messageLabel.setText(emailValidation);
        }
    }

    private void attemptLogin() {
        try {
            // Directly invoke the controller's constructor without storing the instance
            new LoginController(loginBean, AccessUtility.getPersistence());
            // If no exceptions, login was successful
            handleSuccessfulLogin();
        } catch (SQLException | UserNotFoundException | PasswordReadException e) {
            messageLabel.setText(e.getMessage());
        }
    }

    private void handleSuccessfulLogin() {
        emailTextField.setDisable(true);
        passwordPasswordField.setDisable(true);
        loginBtn.setDisable(true);
        signupBtn.setDisable(true);
        messageLabel.setText("Login successful");

        if (AccessUtility.getRole() == Role.ADMIN) {
            showAdminNotifications();
        }
    }

    private void showAdminNotifications() {
        List<NotificationItem> notifications = NotificationHub.getNotifications();
        if (notifications.isEmpty()) {
            showAlert("Admin notifications", "No notifications", "There are no notifications right now.");
        } else {
            displayNotifications(notifications);
        }
        NotificationHub.clearNotifications();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void displayNotifications(List<NotificationItem> notifications) {
        TextArea area = new TextArea();
        area.setEditable(false);
        area.setWrapText(true);
        area.setPrefHeight(300);
        area.setPrefWidth(400);

        StringBuilder sb = new StringBuilder();
        for (NotificationItem n : notifications) {
            sb.append("🔔 ").append(n.getMessage()).append("\n\n");
        }

        area.setText(sb.toString());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Admin notifications");
        alert.setHeaderText("New reports");
        alert.getDialogPane().setContent(area);
        alert.showAndWait();
    }

}
