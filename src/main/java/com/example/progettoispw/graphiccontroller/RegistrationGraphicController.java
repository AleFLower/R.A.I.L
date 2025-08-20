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

            if(textFieldUsername.getText().equals("")||textFieldEmail.getText().equals("")||passwordFieldPassword.getText().equals("")){
                messageLabel.setText("Please enter all required fields");
            }else{

                RegistrationBean registrationBean=new RegistrationBean(textFieldEmail.getText(),passwordFieldPassword.getText(),textFieldUsername.getText());

                String emailValidation=registrationBean.validate();
                if (emailValidation == null) {

                    try{
                        RegistrationController registrationController = new RegistrationController(registrationBean);
                        registrationController.registrateUser(AccessUtility.getPersistence());
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
