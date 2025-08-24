package com.example.progettoispw.graphiccontroller;

import bean.ReportListBean;
import bean.ReportTrackBean;
import bean.ReportLevelCrossingBean;
import com.jfoenix.controls.JFXButton;
import applicationcontroller.HandleReportsController;
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

    private static final ReportType reportType = ReportType.ACTIVE;
   SceneNavigatorGraphicController sceneController= SceneNavigatorGraphicController.getInstance(null);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            ReportListBean reportList=new ReportListBean(reportType);

            new HandleReportsController(reportList,AccessUtility.getPersistence());
            int lcCounter=reportList.getLevelCrossingReports().size();

            int trackCounter=reportList.getTrackReports().size();

            listViewName.setFixedCellSize(90);

            if(lcCounter!=0) {
                label1 = new Label();
                label1.setText("REPORTED LEVEL CROSSING\n");
                listViewName.getItems().add(label1);
                for (int i = 0; i < lcCounter; i++) {
                    ReportLevelCrossingBean levelCrossing = reportList.getLevelCrossingReports().get(i);

                    label1 = new Label();
                    label1.setText(
                            (i + 1) +"°"+ " Reported level crossing\n" +
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
                            (i + 1) +"°"+ " Reported track\n" +
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
