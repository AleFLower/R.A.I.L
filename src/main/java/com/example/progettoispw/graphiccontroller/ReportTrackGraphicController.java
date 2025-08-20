package com.example.progettoispw.graphiccontroller;

import bean.ReportBean;
import com.jfoenix.controls.JFXButton;
import applicationcontroller.ReportController;
import factory.TypeOfPersistence;
import exception.*;
import factory.AssetType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utility.AccessUtility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReportTrackGraphicController extends GeneralGraphicController {
    private ReportBean reportBean;
    @FXML
    private TextField issueTextfield;
    @FXML
    private TextField locationTextfield;
    @FXML
    private ComboBox<Integer> trackNumberComboBox;
    @FXML
    private JFXButton sendReportBtn1;
    @FXML
    private Label errorLabel;
    private final SceneNavigatorGraphicController sceneController= SceneNavigatorGraphicController.getInstance(null);


    private AssetType assetType = AssetType.TRACK;
    private TypeOfPersistence typeOfPersistence;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sendReportBtn1.setOnMouseClicked(event-> {
            if(checkInput()){
                try {
                    typeOfPersistence= AccessUtility.getPersistence();
                    reportBean = verifyBean(
                            String.valueOf(trackNumberComboBox.getValue()),
                            locationTextfield.getText(),
                            issueTextfield.getText(),
                            assetType,
                            typeOfPersistence
                    );

                   new ReportController(reportBean);
                    showSuccessAlert("Report sent successfully.\nBack to home =)");
                    disableBtn();

                } catch(SQLException | PasswordReadException | ReportAlreadyExistsException |
                        NoLoginPerformedException | ReportTypeException |
                        IOException e){
                    setExceptionText(e);
                }
            }
        });


        for (int i = 1; i <= 20; i++) {
            trackNumberComboBox.getItems().add(i);
        }
        super.initialize(url,resourceBundle);
    }
    public ReportBean verifyBean(String trackNumber, String location, String issue, AssetType te, TypeOfPersistence top){
        return new ReportBean(trackNumber,location,issue,te,top);  //dummy, da aggiustare
    }
    public void disableBtn(){
        locationTextfield.setDisable(true);
        trackNumberComboBox.setDisable(true);
        sendReportBtn1.setDisable(true);
        issueTextfield.setDisable(true);
    }
    public void setExceptionText(Exception e){
        if (e instanceof NoLoginPerformedException) {
            showDialogAccess();
        } else {
            showErrorAlert(e.getMessage());
            disableBtn();
        }
    }

    private void showDialogAccess() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Access required");
        alert.setHeaderText("You are not logged in");
        alert.setContentText("Please log in or sign up to continue. What would you like to do?");

        ButtonType buttonLogin = new ButtonType("Sign in");
        ButtonType buttonRegister = new ButtonType("Sign up");
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonLogin, buttonRegister, buttonCancel);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == buttonLogin) {
                sceneController.displayScene("/com/example/progettoispw/viewsfxml/LoginRegistrationView.fxml");
            } else if (result.get() == buttonRegister) {
                sceneController.displayScene("/com/example/progettoispw/viewsfxml/RegistrationView.fxml");
            }
        }
    }

    private void showErrorAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showSuccessAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Done");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public boolean checkInput() {
        if (locationTextfield.getText().isEmpty() || trackNumberComboBox.getValue() == null) {
            errorLabel.setText("Please enter location and truck number");
            return false;
        }
        return true;
    }
}
