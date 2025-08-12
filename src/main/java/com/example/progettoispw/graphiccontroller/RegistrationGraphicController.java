package com.example.progettoispw.graphiccontroller;

import bean.RegistrationBean;
import com.jfoenix.controls.JFXButton;
import applicationcontroller.RegistrationController;
import exception.PasswordReadException;
import exception.UserAlreadyExistsException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utility.AccessUtility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegistrationGraphicController extends GeneralGraphicController {
    //controller grafico che gestisce la pagina di registrazione al sistema

    private final SceneNavigatorGraphicController sceneController= SceneNavigatorGraphicController.getInstance(null);

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

        signupBtn.setOnMouseClicked(event->{
            //prima controllo che i campi siano stati tutti compilati
            if(textFieldUsername.getText().equals("")||textFieldEmail.getText().equals("")||passwordFieldPassword.getText().equals("")){
                messageLabel.setText("Please enter all required fields");
            }else{
                //tutti i campi sono stati passati, creo un bean che prendi questi parametri in input
                RegistrationBean registrationBean=new RegistrationBean(textFieldEmail.getText(),passwordFieldPassword.getText(),textFieldUsername.getText());
                // faccio fare al bean i controlli sintattici
                String emailValidation=registrationBean.validate();
                if (emailValidation == null) {
                    //la sintassi dell'email e' corretta, invio il bean al controller applicativo che gestisce la registrazione
                    try{
                        new RegistrationController(registrationBean, AccessUtility.getPersistence());
                        textFieldEmail.setDisable(true);
                        textFieldUsername.setDisable(true);
                        passwordFieldPassword.setDisable(true);
                        signupBtn.setDisable(true);
                        messageLabel.setText("Registration successful");
                    }catch (SQLException | UserAlreadyExistsException | PasswordReadException | IOException e){
                        messageLabel.setText(e.getMessage());
                    }
                }else{
                    messageLabel.setText(emailValidation);
                }
            }
        });

        super.initialize(url,resourceBundle);
    }

}
