package com.example.progettoispw.graphiccontroller;

import applicationcontroller.AccountController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utility.AccessUtility;

import java.net.URL;
import java.util.ResourceBundle;

public class LogoutGraphicController extends GeneralGraphicController {

    @FXML
    private Label userLabel;
    @FXML
    private JFXButton logoutButton;
    private AccountController accountController = new AccountController();

    private final SceneNavigatorGraphicController sceneController = SceneNavigatorGraphicController.getInstance(null);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    userLabel.setText("THANK YOU FOR BEING OUR USER : " + AccessUtility.getUsername());
    logoutButton.setOnMouseClicked(event->{

        AccessUtility.setUserCode(null);
        AccessUtility.setUsername(null);
        accountController.goOffline();

        try {
            sceneController.displayScene("/com/example/progettoispw/viewsfxml/LoginRegistrationView.fxml");
        } catch (Exception e) {
            System.exit(-1);
        }
    });
        super.initialize(url,resourceBundle);
    }

}
