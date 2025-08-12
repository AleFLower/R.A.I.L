package com.example.progettoispw.graphiccontroller;

import javafx.fxml.FXML;

public class RegistrationAlertGraphicController {
    private final SceneNavigatorGraphicController sceneController = SceneNavigatorGraphicController.getInstance(null);
    @FXML
    public void backHome(){
        sceneController.displayScene("/com/example/progettoispw/viewsfxml/HomeView.fxml");
    }
    @FXML
    public void login(){
        sceneController.displayScene("/com/example/progettoispw/viewsfxml/LoginRegistrationView.fxml");
    }
    @FXML
    public void signup(){
        sceneController.displayScene("/com/example/progettoispw/viewsfxml/RegistrationView.fxml");
    }
}
