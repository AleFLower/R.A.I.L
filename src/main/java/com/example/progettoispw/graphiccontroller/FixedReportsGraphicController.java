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
public class FixedReportsGraphicController implements Initializable{
    @FXML
    private JFXButton backHomeBtn;
    @FXML
    private ListView<Label> fixedReportsList;
    @FXML
    private Label errorLabel;
    SceneNavigatorGraphicController sceneController= SceneNavigatorGraphicController.getInstance(null);


    private static final ReportType reportType = ReportType.FIXED;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{

            ReportListBean reportListBean=new ReportListBean(reportType);
            new HandleReportsController(reportListBean, AccessUtility.getPersistence());

            int lcCounter=reportListBean.getLevelCrossingReports().size();
            int trackCounter=reportListBean.getTrackReports().size();
            fixedReportsList.setFixedCellSize(90);

            if(lcCounter!=0) {
                Label label1 = new Label();
                label1.setText("REPORTED LEVEL CROSSINGS\n");
                fixedReportsList.getItems().add(label1);
                for (int i = 0; i < lcCounter; i++) {
                    ReportLevelCrossingBean levelCrossing = reportListBean.getLevelCrossingReports().get(i);
                    label1 = new Label();
                    label1.setText(
                            (i + 1) + " Reported level crossing\n" +
                                    "level crossing code: " + levelCrossing.getLcCode() + "\n" +
                                    "location: " + levelCrossing.getLocation() + "\n" +
                                    "fixed issue: " + levelCrossing.getIssue() + "\n" +
                                    "state: " + levelCrossing.getState()
                    );
                    fixedReportsList.getItems().add(label1);
                }
            }
            if(trackCounter!=0) {
                Label label1 = new Label();
                label1.setText("REPORTED TRACKS\n");
                fixedReportsList.getItems().add(label1);
                for (int i = 0; i < trackCounter; i++) {
                    ReportTrackBean reportTrackBean = reportListBean.getTrackReports().get(i);
                    label1 = new Label();
                    label1.setText(
                            (i + 1) + " Reported track\n" +
                                    "Track number: " + reportTrackBean.getTrackNumber() + "\n" +
                                    "location: " + reportTrackBean.getLocation() + "\n" +
                                    "fixed issue: " + reportTrackBean.getIssue() + "\n" +
                                    "state: " + reportTrackBean.getState()
                    );
                    fixedReportsList.getItems().add(label1);
                }
            }
        } catch (SQLException | NoReportsFoundException | PasswordReadException | IOException e) {
            if(e instanceof NoReportsFoundException) errorLabel.setText("No fixed reports found");
            else errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }

        backHomeBtn.setOnMouseClicked(event->{
            try {
                sceneController.displayScene("/com/example/progettoispw/viewsfxml/HomeView.fxml");
            }catch(Exception e){
                System.exit(-1);
            }
        });
    }
}
