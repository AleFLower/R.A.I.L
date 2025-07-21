package com.example.progettoispw.controllergrafici;

import bean.BeanListeElementi;
import bean.BeanSegnalazioneBinario;
import bean.BeanSegnalazioneSemaforo;
import com.jfoenix.controls.JFXButton;
import controllerapplicativi.ControllerApplicativoTipoSegnalazione;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.NonEsistonoSegnalazioniException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import utility.UtilityAccesso;

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
            //dei bean e' stato inserito, creo 2 contatori uno che tiene conto dei semafori e uno delle buche cosi so' il numero di
            //segnalazioni che l'utente ha per quelle 2 entita
            //contatore che tiene il numero di indirizzi dei semafori ( per come ho costrutito il tutto a n indirizzi corrispondono
            //n numeri di semafori, quindi contare gli indirizzi equivale a contare il numero di semafori segnalati dall'utente)
            int contatoresemafori=beanListeElementi.getSegnalazioniSemafori().size();
            //discorso duale per il contatore che conta gli indirizzi delle buche
            int contatorebinari=beanListeElementi.getSegnalazioniBinari().size();
            //per ogni segnalazione devo creare una label, settare il testo dentro la label stessa..
            listViewName.setFixedCellSize(90);
            //se ci sono dei semafori li mostro
            if(contatoresemafori!=0) {
                label1 = new Label();
                label1.setText("semafori SEGNALATI\n");
                listViewName.getItems().add(label1);
                for (int i = 0; i < contatoresemafori; i++) {
                    BeanSegnalazioneSemaforo semaforo = beanListeElementi.getSegnalazioniSemafori().get(i);

                    label1 = new Label();
                    label1.setText(
                            (i + 1) + " Semaforo segnalato\n" +
                                    "numero seriale: " + semaforo.getNumeroSeriale() + "\n" +
                                    "stazione: " + semaforo.getstazione() + "\n" +
                                    "problematica: " + semaforo.getDescrizioneProblema() + "\n" +
                                    "stato: " + semaforo.getStato()
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
                                    "stazione: " + binario.getstazione() + "\n" +
                                    "problematica: " + binario.getDescrizioneProblema() + "\n" +
                                    "stato: " + binario.getStato()
                    );

                    listViewName.getItems().add(label1);
                }

            }
        }catch(SQLException | NonEsistonoSegnalazioniException | ErroreLetturaPasswordException e){
            labelErrore.setText(e.getMessage());
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
