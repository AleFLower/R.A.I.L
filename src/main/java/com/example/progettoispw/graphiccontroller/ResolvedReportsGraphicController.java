package com.example.progettoispw.graphiccontroller;

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
public class ResolvedReportsGraphicController implements Initializable{
    @FXML
    private JFXButton backHomeBtn;
    @FXML
    private ListView<Label> resolvedReportsList;
    @FXML
    private Label errorLabel;
    SceneNavigatorGraphicController sceneController= SceneNavigatorGraphicController.getInstance(null);
    //se sono in questo controller grafico vuol dire che sono interessato a ricevere le segnalazioni risolte

    private static final ReportType reportType = ReportType.RESOLVED;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            //se l'utente e' entrato nella schermata vuol dire che possiede un account, gli mostro le sue
            //segnalazioni che sono state risolte
            //chiamo il controller applicativo che si preoccupa di restituire tutto cio che l'utente ha segnalato e che e' stato risolto
            ReportListBean reportListBean=new ReportListBean(reportType);
            new ReportTypeController(reportListBean, AccessUtility.getPersistence());
            //in questo punto tutte le segnalazioni risolte sono state aggiunte nella lista dentro il bean, le riprendo allora e le mostro in output
            int lcCounter=reportListBean.getLevelCrossingReports().size();
            int trackCounter=reportListBean.getTrackReports().size();
            resolvedReportsList.setFixedCellSize(90);
            //se ci sono dei levelCrossing li mostro
            if(lcCounter!=0) {
                Label label1 = new Label();
                label1.setText("REPORTED LEVEL CROSSINGS\n");
                resolvedReportsList.getItems().add(label1);
                for (int i = 0; i < lcCounter; i++) {
                    ReportLevelCrossingBean levelCrossing = reportListBean.getLevelCrossingReports().get(i);
                    label1 = new Label();
                    label1.setText(
                            (i + 1) + " Reported level crossing\n" +
                                    "level crossing code: " + levelCrossing.getLcCode() + "\n" +
                                    "location: " + levelCrossing.getLocation() + "\n" +
                                    "state: " + levelCrossing.getState()
                    );
                    resolvedReportsList.getItems().add(label1);
                }
            }
            if(trackCounter!=0) {
                Label label1 = new Label();
                label1.setText("REPORTED TRACKS\n");
                resolvedReportsList.getItems().add(label1);
                for (int i = 0; i < trackCounter; i++) {
                    ReportTrackBean reportTrackBean = reportListBean.getTrackReports().get(i);
                    label1 = new Label();
                    label1.setText(
                            (i + 1) + " Reported track\n" +
                                    "Track number: " + reportTrackBean.getTrackNumber() + "\n" +
                                    "location: " + reportTrackBean.getLocation() + "\n" +
                                    "state: " + reportTrackBean.getState()
                    );
                    resolvedReportsList.getItems().add(label1);
                }
            }
        } catch (SQLException | NoReportsFoundException | PasswordReadException | IOException e) {
            if(e instanceof NoReportsFoundException) errorLabel.setText("No resolved reports found");
            else errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
        //se l'utente clicca sul pulsante che ritorna alla home viene attivato questo metodo
        backHomeBtn.setOnMouseClicked(event->{
            try {
                sceneController.displayScene("/com/example/progettoispw/viewsfxml/HomeView.fxml");
            }catch(Exception e){
                System.exit(-1);
            }
        });
    }
}
