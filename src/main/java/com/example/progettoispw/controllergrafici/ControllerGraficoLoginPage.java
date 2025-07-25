package com.example.progettoispw.controllergrafici;
import bean.BeanLogin;
import com.jfoenix.controls.JFXButton;
import controllerapplicativi.ControllerApplicativoLoginAlSistema;
import dao.LoginDao;
import dao.LoginDaoMemory;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.NonEsisteUtenteNelSistemaException;
import entita.Role;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utility.CentroNotifiche;
import utility.Notifica;
import utility.UtilityAccesso;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerGraficoLoginPage extends ControllerGraficoGenerale {

    //aggiunta ora con il singleton
    private final ControllerVisualizzatoreScene controllerVisualizzatoreScene=ControllerVisualizzatoreScene.getInstance(null);
    @FXML
    private JFXButton registratiButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private JFXButton accediButton;
    @FXML
    private Label labelComunicazione;

    private BeanLogin beanAccessoUtente;

    /*questa classe gestisce la login page, avendo piu' button e non avendoli in comune con le altre page, ho creato
    * questo controller grafico apposta per essa, questa classe è il controller grafico della page login-registrazione-page.fxml,
    * usa anche la classe ControllerGraficoSenzaAccesso nel caso in cui si dovessero premere altri pulsanti, lo faccio
    * per non dover duplicare codice inutilmente*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Registration Button Click Event
        registratiButton.setOnMouseClicked(event -> handleRegistrazioneButtonClick());

        // Login Button Click Event
        accediButton.setOnMouseClicked(event -> handleLoginButtonClick());

        // Call the superclass initialization for common logic
        super.initialize(url, resourceBundle);
    }

    private void handleRegistrazioneButtonClick() {
        try {
            controllerVisualizzatoreScene.visualizzaScena("registrazione-page.fxml");
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    private void handleLoginButtonClick() {
        String email = emailTextField.getText();
        String password = passwordPasswordField.getText();

        // Check if email or password are empty
        if (isFieldEmpty(email, password)) {
            labelComunicazione.setText("inserire entrambi i campi");
        } else {
            processLogin(email, password);
        }
    }

    private boolean isFieldEmpty(String email, String password) {
        return email.equals("") || password.equals("");
    }

    private void processLogin(String email, String password) {
        beanAccessoUtente = new BeanLogin(email, password);

        // Validate email syntax
        String emailValidation = beanAccessoUtente.svolgiControlli();

        if (emailValidation == null) {
            attemptLogin();
        } else {
            labelComunicazione.setText(emailValidation);
        }
    }

    private void attemptLogin() {
        try {
            // Directly invoke the controller's constructor without storing the instance
            new ControllerApplicativoLoginAlSistema(beanAccessoUtente, UtilityAccesso.getPersistence());
            // If no exceptions, login was successful
            handleSuccessfulLogin();
        } catch (SQLException | NonEsisteUtenteNelSistemaException | ErroreLetturaPasswordException e) {
            labelComunicazione.setText(e.getMessage());
        }
    }

    private void handleSuccessfulLogin() {
        emailTextField.setDisable(true);
        passwordPasswordField.setDisable(true);
        accediButton.setDisable(true);
        registratiButton.setDisable(true);
        labelComunicazione.setText("accesso effettuato con successo");

        if (UtilityAccesso.getRole() == Role.ADMIN) {
            mostraNotificheAdmin();
        }
    }

    private void mostraNotificheAdmin() {
        List<Notifica> notifiche = CentroNotifiche.getNotifiche();
        if (notifiche.isEmpty()) {
            showAlert("Notifiche Admin", "Nessuna nuova notifica", "Al momento non ci sono nuove segnalazioni.");
        } else {
            displayNotifications(notifiche);
        }
        CentroNotifiche.clearNotifications();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void displayNotifications(List<Notifica> notifiche) {
        TextArea area = new TextArea();
        area.setEditable(false);
        area.setWrapText(true);
        area.setPrefHeight(300);
        area.setPrefWidth(400);

        StringBuilder sb = new StringBuilder();
        for (Notifica n : notifiche) {
            sb.append("🔔 ").append(n.getMessaggio()).append("\n\n");
        }

        area.setText(sb.toString());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notifiche Admin");
        alert.setHeaderText("Nuove segnalazioni ricevute");
        alert.getDialogPane().setContent(area);
        alert.showAndWait();
    }

}
