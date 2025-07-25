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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class ControllerGraficoSegnalazioniRisolte implements Initializable{
    @FXML
    private JFXButton ritornaHomeButton;
    @FXML
    private ListView<Label> listViewRisolteName;
    @FXML
    private Label labelErrore;
    ControllerVisualizzatoreScene controllerVisualizzatoreScene=ControllerVisualizzatoreScene.getInstance(null);
    //se sono in questo controller grafico vuol dire che sono interessato a ricevere le segnalazioni risolte

    private static final TypeOfSegnalazione type_of_segnalazione= TypeOfSegnalazione.RISOLTE;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            //se l'utente e' entrato nella schermata vuol dire che possiede un account, gli mostro le sue
            //segnalazioni che sono state risolte
            //chiamo il controller applicativo che si preoccupa di restituire tutto cio che l'utente ha segnalato e che e' stato risolto
            BeanListeElementi beanListeElementi=new BeanListeElementi(type_of_segnalazione);
            new ControllerApplicativoTipoSegnalazione(beanListeElementi, UtilityAccesso.getPersistence());
            //in questo punto tutte le segnalazioni risolte sono state aggiunte nella lista dentro il bean, le riprendo allora e le mostro in output
            int contatorelevelCrossing=beanListeElementi.getSegnalazioniLevelCrossing().size();
            int contatorebinari=beanListeElementi.getSegnalazioniBinari().size();
            listViewRisolteName.setFixedCellSize(90);
            //se ci sono dei levelCrossing li mostro
            if(contatorelevelCrossing!=0) {
                Label label1 = new Label();
                label1.setText("PASSAGGI A LIVELLO SEGNALATI\n");
                listViewRisolteName.getItems().add(label1);
                for (int i = 0; i < contatorelevelCrossing; i++) {
                    BeanSegnalazioneLevelCrossing levelCrossing = beanListeElementi.getSegnalazioniLevelCrossing().get(i);
                    label1 = new Label();
                    label1.setText(
                            (i + 1) + " Passaggio a livello segnalato\n" +
                                    "numero del passaggio a livello: " + levelCrossing.getcodicePL() + "\n" +
                                    "posizione: " + levelCrossing.getlocalizzazione() + "\n" +
                                    "stato: " + levelCrossing.getStato()
                    );
                    listViewRisolteName.getItems().add(label1);
                }
            }
            if(contatorebinari!=0) {
                Label label1 = new Label();
                label1.setText("BINARI SEGNALATI\n");
                listViewRisolteName.getItems().add(label1);
                for (int i = 0; i < contatorebinari; i++) {
                    BeanSegnalazioneBinario binario = beanListeElementi.getSegnalazioniBinari().get(i);
                    label1 = new Label();
                    label1.setText(
                            (i + 1) + " binario segnalato\n" +
                                    "Numero binario: " + binario.getNumeroBinario() + "\n" +
                                    "posizione: " + binario.getlocalizzazione() + "\n" +
                                    "stato: " + binario.getStato()
                    );
                    listViewRisolteName.getItems().add(label1);
                }
            }
        } catch (SQLException | NonEsistonoSegnalazioniException | ErroreLetturaPasswordException | IOException e) {
            if(e instanceof NonEsistonoSegnalazioniException) labelErrore.setText("Non hai segnalazioni risolte");
            else labelErrore.setText(e.getMessage());
            labelErrore.setVisible(true);
        }
        //se l'utente clicca sul pulsante che ritorna alla home viene attivato questo metodo
        ritornaHomeButton.setOnMouseClicked(event->{
            try {
                controllerVisualizzatoreScene.visualizzaScena("prova-home.fxml");
            }catch(Exception e){
                System.exit(-1);
            }
        });
    }
}
