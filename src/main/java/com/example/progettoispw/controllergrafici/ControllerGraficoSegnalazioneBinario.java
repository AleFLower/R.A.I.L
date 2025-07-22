package com.example.progettoispw.controllergrafici;

import bean.BeanSegnalaEntita;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controllerapplicativi.ControllerApplicativoSegnalazioneEntita;
import factory.TypeOfPersistence;
import eccezioni.*;
import factory.TypeEntita;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utility.UtilityAccesso;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerGraficoSegnalazioneBinario extends ControllerGraficoGenerale{
    private BeanSegnalaEntita beanVerificaDati;
    @FXML
    private TextField textFieldProblematica;
    @FXML
    private TextField textFieldstazione;

    @FXML
    private ComboBox<Integer> comboBoxNumeroBinario;
    @FXML
    private JFXButton inviaSegnalazioneButton1;
    @FXML
    private Label labelErrore;
    private final ControllerVisualizzatoreScene controllerVisualizzatoreScene=ControllerVisualizzatoreScene.getInstance(null);


    @FXML
    private JFXButton inviaSegnalazioneButtonInLocale;
    //se sono in questo controller grafico vuol dire che l'utente sta segnalando una binario , quindi la
    //mia entita stradale sara' di tipo type_binario_stradale
    private TypeEntita typeEntita=TypeEntita.BINARIO;
    private TypeOfPersistence typeOfPersistence;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //se l'utente vuole inviare la segnalazione al db
        inviaSegnalazioneButton1.setOnMouseClicked(event-> {
            if(controllaInput()){
                try {
                    typeOfPersistence= UtilityAccesso.getPersistence();
                    beanVerificaDati = beanVerifica(
                            String.valueOf(comboBoxNumeroBinario.getValue()),
                            textFieldstazione.getText(),
                            textFieldProblematica.getText(),
                            typeEntita,
                            typeOfPersistence
                    );
                    //questi dati devono essere mandati al controller applicativo
                    ControllerApplicativoSegnalazioneEntita controllerApplicativoSegnalazioneEntita=new ControllerApplicativoSegnalazioneEntita(beanVerificaDati);
                    labelErrore.setText("segnalazione avvenuta con successo\ntorna alla home =)");
                    disattivaButton();
                } catch(SQLException | ErroreLetturaPasswordException | SegnalazioneGiaAvvenutaException | NessunAccessoEffettuatoException | TipoEntitaException |
                        IOException e){
                    settaTestoEccezione(e);
                }
            }
        });
        //se l'utente vuole salvare la segnalazione in locale
        inviaSegnalazioneButtonInLocale.setOnMouseClicked(event->{

            if (UtilityAccesso.getPersistence() == TypeOfPersistence.MEMORY) {
                // Disabilita il bottone se persistence è memory
                inviaSegnalazioneButtonInLocale.setDisable(true);
                labelErrore.setText("In demo version this option is not available.");
                return; // Esci dal metodo, non procedere con l'invio
            }

            if(controllaInput()){
                try {

                    typeOfPersistence= UtilityAccesso.getPersistence();
                    beanVerificaDati = beanVerifica(
                            String.valueOf(comboBoxNumeroBinario.getValue()),
                            textFieldstazione.getText(),
                            textFieldProblematica.getText(),
                            typeEntita,
                            typeOfPersistence
                    );
                    //questi dati devono essere mandati al controller applicativo
                    ControllerApplicativoSegnalazioneEntita controllerApplicativoSegnalazioneEntita=new ControllerApplicativoSegnalazioneEntita(beanVerificaDati);
                    labelErrore.setText("segnalazione avvenuta con successo\ntorna alla home =)");
                    disattivaButton();
                } catch(SQLException | ErroreLetturaPasswordException | SegnalazioneGiaAvvenutaException | NessunAccessoEffettuatoException | TipoEntitaException | IOException e){
                    settaTestoEccezione(e);
                }
            }
        });
        // Popola la ComboBox con i numeri da 1 a 20
        for (int i = 1; i <= 20; i++) {
            comboBoxNumeroBinario.getItems().add(i);
        }
        super.initialize(url,resourceBundle);
    }
    public BeanSegnalaEntita beanVerifica(String numeroBinario,String stazione,String problematica,TypeEntita te,TypeOfPersistence top){
        return new BeanSegnalaEntita(numeroBinario,stazione,problematica,te,top);  //dummy, da aggiustare
    }
    public void disattivaButton(){
        textFieldstazione.setDisable(true);
        comboBoxNumeroBinario.setDisable(true);
        inviaSegnalazioneButton1.setDisable(true);
        inviaSegnalazioneButtonInLocale.setDisable(true);
    }
    public void settaTestoEccezione(Exception e){
        if (e instanceof NessunAccessoEffettuatoException) {
            mostraDialogoAccesso();
        } else {
            labelErrore.setText(e.getMessage());
        }
    }

    private void mostraDialogoAccesso() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Accesso richiesto");
        alert.setHeaderText("Non hai effettuato l'accesso");
        alert.setContentText("Per continuare devi accedere o registrarti. Cosa vuoi fare?");

        ButtonType buttonLogin = new ButtonType("Sign in");
        ButtonType buttonRegister = new ButtonType("Sign up");
        ButtonType buttonCancel = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonLogin, buttonRegister, buttonCancel);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == buttonLogin) {
                controllerVisualizzatoreScene.visualizzaScena("login-registrazione-page.fxml");
            } else if (result.get() == buttonRegister) {
                controllerVisualizzatoreScene.visualizzaScena("registrazione-page.fxml");
            }
        }
    }

    public boolean controllaInput() {
        if (textFieldstazione.getText().isEmpty() || comboBoxNumeroBinario.getValue() == null) {
            labelErrore.setText("Inserire stazione e selezionare la visibilità");
            return false;
        }
        return true;
    }
}
