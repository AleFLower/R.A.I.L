package com.example.progettoispw.controllergrafici;

import bean.BeanListeElementi;
import bean.BeanSegnalazioneBinario;
import bean.BeanSegnalazioneLevelCrossing;
import com.jfoenix.controls.JFXButton;
import controllerapplicativi.ControllerApplicativoTipoSegnalazione;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.NonEsistonoSegnalazioniException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import utility.UtilityAccesso;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerGraficoSegnalazioniAttive implements Initializable {
   @FXML
   private ListView<Label> listViewName;
   @FXML
   private JFXButton ritornaHomeButton;
    @FXML
    private Label labelErrore ;
    private Label label1;
    //se sono in questo controller grafico vuol dire che sono interessato a ricevere le segnalazioni attive
    private static final TypeOfSegnalazione typeOfSegnalazione= TypeOfSegnalazione.ATTIVE;
   ControllerVisualizzatoreScene controllerVisualizzatoreScene=ControllerVisualizzatoreScene.getInstance(null);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //devo chiedere al database tutte le segnalazioni che quell'utente ha effettuato, quindi le chiedo ad un
        //controller applicativo
        try {
            //questa variabile contatore riporta il numero di segnalazioni ancora attive ell'utente
            BeanListeElementi beanListeElementi=new BeanListeElementi(typeOfSegnalazione);
            //passo il bean al controller applicativo che riempira le liste in base alle segnalazioni attive dell'utente
            new ControllerApplicativoTipoSegnalazione(beanListeElementi, UtilityAccesso.getPersistence());
            //se non e' stata ricevuta nessuna eccezione vuol dire che non ci sono stati errori e che qualcosa nelle liste
            //dei bean e' stato inserito, creo 2 contatori uno che tiene conto dei levelCrossing e uno delle binari cosi so' il numero di
            //segnalazioni che l'utente ha per quelle 2 entita
            //contatore che tiene il numero di indirizzi dei levelCrossing ( per come ho costrutito il tutto a n indirizzi corrispondono
            //n numeri di levelCrossing, quindi contare gli indirizzi equivale a contare il numero di levelCrossing segnalati dall'utente)
            int contatoreLevelCrossing=beanListeElementi.getSegnalazioniLevelCrossing().size();
            //discorso duale per il contatore che conta gli indirizzi delle binari
            int contatorebinari=beanListeElementi.getSegnalazioniBinari().size();
            //per ogni segnalazione devo creare una label, settare il testo dentro la label stessa..
            listViewName.setFixedCellSize(90);
            //se ci sono dei levelCrossing li mostro
            if(contatoreLevelCrossing!=0) {
                label1 = new Label();
                label1.setText("PASSAGGI A LIVELLO SEGNALATI\n");
                listViewName.getItems().add(label1);
                for (int i = 0; i < contatoreLevelCrossing; i++) {
                    BeanSegnalazioneLevelCrossing levelCrossing = beanListeElementi.getSegnalazioniLevelCrossing().get(i);

                    label1 = new Label();
                    label1.setText(
                            (i + 1) + " Passaggio a livello segnalato\n" +
                                    "numero del passaggio a livello: " + levelCrossing.getcodicePL() + "\n" +
                                    "posizione: " + levelCrossing.getlocalizzazione() + "\n" +
                                    "problematica: " + levelCrossing.getDescrizioneProblema() + "\n" +
                                    "stato: " + levelCrossing.getStato()
                    );

                    listViewName.getItems().add(label1);
                }
            }
            if(contatorebinari!=0) {
                label1 = new Label();
                label1.setText("BINARI SEGNALATI\n");
                listViewName.getItems().add(label1);
                for (int i = 0; i < contatorebinari; i++) {
                    BeanSegnalazioneBinario binario = beanListeElementi.getSegnalazioniBinari().get(i);

                    label1 = new Label();
                    label1.setText(
                            (i + 1) + " binario segnalato\n" +
                                    "numero binario: " + binario.getNumeroBinario() + "\n" +
                                    "localizzazione: " + binario.getlocalizzazione() + "\n" +
                                    "problematica: " + binario.getDescrizioneProblema() + "\n" +
                                    "stato: " + binario.getStato()
                    );

                    listViewName.getItems().add(label1);
                }

            }
        }catch(SQLException | NonEsistonoSegnalazioniException | ErroreLetturaPasswordException | IOException e){
           if(e instanceof NonEsistonoSegnalazioniException) labelErrore.setText("Non hai segnalazioni attive");
            else labelErrore.setText(e.getMessage());
            labelErrore.setVisible(true);
        }
        ritornaHomeButton.setOnMouseClicked(event->{
            try {
                controllerVisualizzatoreScene.visualizzaScena("prova-home.fxml");
            }catch(Exception e){
                System.exit(-1);
            }
        });
    }
}
