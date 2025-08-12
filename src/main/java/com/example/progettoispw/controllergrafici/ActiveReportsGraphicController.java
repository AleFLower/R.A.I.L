package com.example.progettoispw.controllergrafici;

import bean.ReportListBean;
import bean.ReportTrackBean;
import bean.ReportLevelCrossingBean;
import com.jfoenix.controls.JFXButton;
import applicationcontroller.ReportTypeController;
import exception.PasswordReadException;
import exception.NoReportsFoundException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import utility.AccessUtility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ActiveReportsGraphicController implements Initializable {
    @FXML
    private ListView<Label> listViewName;
    @FXML
    private JFXButton backhomeBtn;
    @FXML
    private Label errorLabel;
    private Label label1;
    //se sono in questo controller grafico vuol dire che sono interessato a ricevere le segnalazioni attive
    private static final ReportType reportType = ReportType.ACTIVE;
   SceneNavigatorGraphicController sceneController= SceneNavigatorGraphicController.getInstance(null);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //devo chiedere al database tutte le segnalazioni che quell'utente ha effettuato, quindi le chiedo ad un
        //controller applicativo
        try {
            //questa variabile contatore riporta il numero di segnalazioni ancora attive ell'utente
            ReportListBean reportList=new ReportListBean(reportType);
            //passo il bean al controller applicativo che riempira le liste in base alle segnalazioni attive dell'utente
            new ReportTypeController(reportList, AccessUtility.getPersistence());
            //se non e' stata ricevuta nessuna eccezione vuol dire che non ci sono stati errori e che qualcosa nelle liste
            //dei bean e' stato inserito, creo 2 contatori uno che tiene conto dei levelCrossing e uno delle binari cosi so' il numero di
            //segnalazioni che l'utente ha per quelle 2 entita
            //contatore che tiene il numero di indirizzi dei levelCrossing ( per come ho costrutito il tutto a n indirizzi corrispondono
            //n numeri di levelCrossing, quindi contare gli indirizzi equivale a contare il numero di levelCrossing segnalati dall'utente)
            int lcCounter=reportList.getLevelCrossingReports().size();
            //discorso duale per il contatore che conta gli indirizzi delle binari
            int trackCounter=reportList.getTrackReports().size();
            //per ogni segnalazione devo creare una label, settare il testo dentro la label stessa..
            listViewName.setFixedCellSize(90);
            //se ci sono dei levelCrossing li mostro
            if(lcCounter!=0) {
                label1 = new Label();
                label1.setText("REPORTED LEVEL CROSSING\n");
                listViewName.getItems().add(label1);
                for (int i = 0; i < lcCounter; i++) {
                    ReportLevelCrossingBean levelCrossing = reportList.getLevelCrossingReports().get(i);

                    label1 = new Label();
                    label1.setText(
                            (i + 1) + " Reported level crossing\n" +
                                    "level crossing code: " + levelCrossing.getLcCode() + "\n" +
                                    "location: " + levelCrossing.getLocation() + "\n" +
                                    "issue: " + levelCrossing.getIssue() + "\n" +
                                    "state: " + levelCrossing.getState()
                    );

                    listViewName.getItems().add(label1);
                }
            }
            if(trackCounter!=0) {
                label1 = new Label();
                label1.setText("REPORTED TRACKS\n");
                listViewName.getItems().add(label1);
                for (int i = 0; i < trackCounter; i++) {
                    ReportTrackBean track = reportList.getTrackReports().get(i);

                    label1 = new Label();
                    label1.setText(
                            (i + 1) + " Reported track\n" +
                                    "Track number: " + track.getTrackNumber() + "\n" +
                                    "location: " + track.getLocation() + "\n" +
                                    "issue: " + track.getIssue() + "\n" +
                                    "state: " + track.getState()
                    );

                    listViewName.getItems().add(label1);
                }

            }
        }catch(SQLException | NoReportsFoundException | PasswordReadException | IOException e){
           if(e instanceof NoReportsFoundException) errorLabel.setText("No active reports found");
           else errorLabel.setText(e.getMessage());
           errorLabel.setVisible(true);
        }
        backhomeBtn.setOnMouseClicked(event->{
            try {
                sceneController.displayScene("/com/example/progettoispw/viewsfxml/HomeView.fxml");
            }catch(Exception e){
                System.exit(-1);
            }
        });
    }
}
