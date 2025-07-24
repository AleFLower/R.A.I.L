package com.example.progettoispw.controllergrafici;

import bean.BeanSegnalaEntita;
import com.jfoenix.controls.JFXButton;
import controllerapplicativi.ControllerApplicativoSegnalazioneEntita;
import factory.TypeOfPersistence;
import eccezioni.*;
import factory.TypeEntita;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utility.UtilityAccesso;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ControllerGraficoSegnalazionePassaggioALivello extends ControllerGraficoGenerale {

    /*questo controller è associato alla PaginaSegnalaProblema la quale possiede:
     * i DUE textField in cui l'utente inserisce i campi
     * il pulsante invia che consente d' inviare la segnalazione che l'utente ha richiesto  */

    @FXML
    private JFXButton salvaInMemoryButton;
    private BeanSegnalaEntita beanVerificaDati;
    @FXML
    private TextField textFieldProblematica;
    @FXML
    private TextField textFieldcodicePL;
    @FXML
    private TextField textFieldlocalizzazione;
    @FXML
    private JFXButton inviaSegnalazioneButton;
    @FXML
    private Label labelErrore;
    //se sono in questo controller grafico vuol dire che l'utente sta segnalando un levelCrossing dell', quindi la
    //mia entita stradale sara' di tipo type_levelCrossing_
    private TypeEntita typeEntita = TypeEntita.LEVELCROSSING;
    private TypeOfPersistence typeOfPersistence;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //questo avrà i seton mouse click dei suoi pulsanti e alla fine chiama super.initialize() di quelli in comune
        inviaSegnalazioneButton.setOnMouseClicked(event -> {
            if (controllaInput()) {
                //sara' proprio qui che avverrà l'invio al bean dei dati che ha inserito l'utente in input
                try {
                    typeOfPersistence = UtilityAccesso.getPersistence();
                    beanVerificaDati=beanVerifica(textFieldcodicePL.getText(), textFieldlocalizzazione.getText(),textFieldProblematica.getText(), typeEntita, typeOfPersistence);
                    //la lunghezza seriale del levelCrossing inserita dall'utente è corretta, invio il bean al controller applicativo
                    ControllerApplicativoSegnalazioneEntita controllerApplicativoSegnalazioneEntita = new ControllerApplicativoSegnalazioneEntita(beanVerificaDati);
                    //se non c'e' stata nessuna eccezione vuol dire che la segnalazione e' avvenuta con successo
                    //lo comunico all'utente e blocco i pulsanti per non far inviare la stessa segnalazione
                    //in caso dovesse premere per sbaglio di nuovo il pulsante invia
                    mostraAlertSuccesso("Segnalazione avvenuta con successo.\nTorna alla home =)");
                    disattivaButton();
                } catch (LunghezzaInputException | SQLException | ErroreLetturaPasswordException |
                         SegnalazioneGiaAvvenutaException | NessunAccessoEffettuatoException |
                         TipoEntitaException | IOException e) {
                    verificaEccezione(e);
                }
            }
        });
        // codice che si attiva se l'utente clicca il button per salvare il problema segnalato in locale e non su un database


        //questo viene chiamato per implementare le azioni ai button che sono in comune tra le schermate, essendo questa
        //classe figlia di ControllerGraficoLoginPage, questo metodo super chiamerà initialize di ControllerGraficoLoginPage
        //che a sua volta chiamerà initialize di ControllerGraficoSenzaAccesso
        super.initialize(url, resourceBundle);
    }

    public BeanSegnalaEntita beanVerifica(String codicePL, String localizzazione,String problematica ,TypeEntita te, TypeOfPersistence top) throws LunghezzaInputException {
        BeanSegnalaEntita beanSegnalaEntita = new BeanSegnalaEntita(codicePL, localizzazione, problematica,te, top);
        beanSegnalaEntita.controllaInputLevelCrossing();
        return beanSegnalaEntita;
    }

    public void disattivaButton() {
        textFieldlocalizzazione.setDisable(true);
        textFieldcodicePL.setDisable(true);
        textFieldProblematica.setDisable(true);
        inviaSegnalazioneButton.setDisable(true);
    }

    private void mostraAlertSuccesso(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operazione completata");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    public boolean controllaInput() {
        if (textFieldlocalizzazione.getText().equals("") || textFieldcodicePL.getText().equals("")) {
            labelErrore.setText("inserire entrambi i campi");
            return false;
        }
        return true;
    }

    private void mostraAlertErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText("Si è verificato un errore");
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    public void verificaEccezione(Exception e){
        mostraAlertErrore(e.getMessage());
        if (e.getClass() == SegnalazioneGiaAvvenutaException.class) {
            /*se l'eccezione è di tipo segnalazione già avvenuta l'utente ha portato a termine quello che
             * voleva fare quindi posso disabilitare i pulsanti */
           // labelErrore.setText(e.getMessage());
            disattivaButton();
        }
    }
}
