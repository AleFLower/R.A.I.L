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
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import utility.AccessUtility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class FixedReportsGraphicController implements Initializable {

    @FXML private ListView<Node> fixedReportsList;
    @FXML private JFXButton backHomeBtn;
    @FXML private Label errorLabel;

    private static final ReportType reportType = ReportType.FIXED;
    SceneNavigatorGraphicController sceneController = SceneNavigatorGraphicController.getInstance(null);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ReportListBean reportListBean = new ReportListBean(reportType);
            new HandleReportsController(reportListBean, AccessUtility.getPersistence());

            fixedReportsList.setFixedCellSize(140);

            fixedReportsList.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(Node item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty || item == null ? null : item);
                }
            });

            for (ReportLevelCrossingBean levelCrossing : reportListBean.getLevelCrossingReports()) {
                fixedReportsList.getItems().add(createCard(
                        "Fixed Level Crossing",
                        "Code: " + levelCrossing.getLcCode(),
                        "Location: " + levelCrossing.getLocation(),
                        "Fixed issue: " + levelCrossing.getIssue(),
                        "State: " + levelCrossing.getState()
                ));
            }

            for (ReportTrackBean track : reportListBean.getTrackReports()) {
                fixedReportsList.getItems().add(createCard(
                        "Fixed Track",
                        "Track number: " + track.getTrackNumber(),
                        "Location: " + track.getLocation(),
                        "Fixed issue: " + track.getIssue(),
                        "State: " + track.getState()

                ));
            }

        } catch (SQLException | NoReportsFoundException | PasswordReadException | IOException e) {
            errorLabel.setText(e instanceof NoReportsFoundException ? "No fixed reports found" : e.getMessage());
            errorLabel.setVisible(true);
        }

        backHomeBtn.setOnMouseClicked(event -> {
            try {
                sceneController.displayScene("/com/example/progettoispw/viewsfxml/HomeView.fxml");
            } catch (Exception e) {
                System.exit(-1);
            }
        });
    }

    private VBox createCard(String title, String code, String location, String issue, String state) {
        Text titleText = new Text(title + "\n");
        titleText.getStyleClass().add("report-title");

        Text detailsText = new Text(
                code + "\n" +
                        location + "\n" +
                        issue + "\n" +
                        state
        );
        detailsText.getStyleClass().add("report-item");


        TextFlow flow = new TextFlow(titleText, detailsText);
        flow.getStyleClass().add("report-card");
        flow.setLineSpacing(4);

        VBox card = new VBox(flow);
        card.setSpacing(6);
        return card;
    }

}