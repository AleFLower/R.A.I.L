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

    /*questo controller è associato alla PaginaSegnalaProblema la quale possiede:
     * i DUE textField in cui l'utente inserisce i campi
     * il pulsante invia che consente d' inviare la segnalazione che l'utente ha richiesto  */
    
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
    //se sono in questo controller grafico vuol dire che l'utente sta segnalando un levelCrossing dell', quindi la
    //mia entita stradale sara' di tipo type_levelCrossing_
    private AssetType assetType = AssetType.LEVELCROSSING;
    private TypeOfPersistence typeOfPersistence;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //questo avrà i seton mouse click dei suoi pulsanti e alla fine chiama super.initialize() di quelli in comune
        sendReportBtn.setOnMouseClicked(event -> {
            if (checkInput()) {
                //sara' proprio qui che avverrà l'invio al bean dei dati che ha inserito l'utente in input
                try {
                    typeOfPersistence = AccessUtility.getPersistence();
                    reportBean = verifyBean(lcCodeTextfield.getText(), locationTextfield.getText(),issueTextfield.getText(), assetType, typeOfPersistence);
                    //la lunghezza seriale del levelCrossing inserita dall'utente è corretta, invio il bean al controller applicativo
                    new ReportController(reportBean);
                    //se non c'e' stata nessuna eccezione vuol dire che la segnalazione e' avvenuta con successo
                    //lo comunico all'utente e blocco i pulsanti per non far inviare la stessa segnalazione
                    //in caso dovesse premere per sbaglio di nuovo il pulsante invia
                    showSuccessAlert("Report sent successfully.\nBack to home =)");
                    disableButton();
                } catch (InvalidInputLengthException | SQLException | PasswordReadException |
                         ReportAlreadyExistsException | NoLoginPerformedException |
                         ReportTypeException | IOException e) {
                    verifyException(e);
                }
            }
        });
        // codice che si attiva se l'utente clicca il button per salvare il problema segnalato in locale e non su un database


        //questo viene chiamato per implementare le azioni ai button che sono in comune tra le schermate, essendo questa
        //classe figlia di ControllerGraficoLoginPage, questo metodo super chiamerà initialize di ControllerGraficoLoginPage
        //che a sua volta chiamerà initialize di ControllerGraficoSenzaAccesso
        super.initialize(url, resourceBundle);
    }

    public ReportBean verifyBean(String lcCode, String location, String issue , AssetType te, TypeOfPersistence top) throws InvalidInputLengthException {
        ReportBean reportBean = new ReportBean(lcCode, location, issue,te, top);
        reportBean.validateInput();
        return reportBean;
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
            /*se l'eccezione è di tipo segnalazione già avvenuta l'utente ha portato a termine quello che
             * voleva fare quindi posso disabilitare i pulsanti */
            disableButton();
        }
    }
}
