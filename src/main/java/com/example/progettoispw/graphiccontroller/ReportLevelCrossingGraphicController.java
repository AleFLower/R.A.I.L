package com.example.progettoispw.graphiccontroller;

import bean.ReportBean;
import com.jfoenix.controls.JFXButton;
import applicationcontroller.ReportController;
import factory.TypeOfPersistence;
import exception.*;
import factory.AssetType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utility.AccessUtility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ReportLevelCrossingGraphicController extends GeneralGraphicController {


    
    private ReportBean reportBean;
    @FXML
    private TextField issueTextfield;
    @FXML
    private TextField lcCodeTextfield;
    @FXML
    private TextField locationTextfield;
    @FXML
    private JFXButton sendReportBtn;
    @FXML
    private Label errorLabel;

    private AssetType assetType = AssetType.LEVELCROSSING;
    private TypeOfPersistence typeOfPersistence;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sendReportBtn.setOnMouseClicked(event -> {
            if (checkInput()) {

                try {
                    typeOfPersistence = AccessUtility.getPersistence();
                    reportBean = verifyBean(lcCodeTextfield.getText(), locationTextfield.getText(),issueTextfield.getText(), assetType, typeOfPersistence);

                    new ReportController(reportBean);

                    showSuccessAlert("Report sent successfully.\nBack to home =)");
                    disableButton();
                } catch (InvalidInputLengthException | SQLException | PasswordReadException |
                         ReportAlreadyExistsException | NoLoginPerformedException |
                         ReportTypeException | IOException e) {
                    verifyException(e);
                }
            }
        });


        super.initialize(url, resourceBundle);
    }

    public ReportBean verifyBean(String lcCode, String location, String issue , AssetType te, TypeOfPersistence top) throws InvalidInputLengthException {
        ReportBean bean = new ReportBean(lcCode, location, issue,te, top);
        bean.validateInput();
        return bean;
    }

    public void disableButton() {
        locationTextfield.setDisable(true);
        lcCodeTextfield.setDisable(true);
        issueTextfield.setDisable(true);
        sendReportBtn.setDisable(true);
    }

    private void showSuccessAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Done");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public boolean checkInput() {
        if (locationTextfield.getText().equals("") || lcCodeTextfield.getText().equals("")) {
            errorLabel.setText("Please enter all required fields");
            return false;
        }
        return true;
    }

    private void showErrorAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void verifyException(Exception e){
        showErrorAlert(e.getMessage());

        if (e.getClass() == ReportAlreadyExistsException.class) {
            disableButton();
        }
    }
}
