package com.example.progettoispw.graphiccontroller;

import javafx.fxml.FXML;

public class ReportProblemGraphicController extends GeneralGraphicController {


    private final SceneNavigatorGraphicController sceneController = SceneNavigatorGraphicController.getInstance(null);

    @FXML
    public void reportLevelCrossing() throws Exception{
        sceneController.displayScene("/com/example/progettoispw/viewsfxml/ReportLevelCrossingProblemView.fxml");
    }
    @FXML
    public void reportTrack() throws Exception{
        sceneController.displayScene("/com/example/progettoispw/viewsfxml/ReportTrackProblemView.fxml");
    }
}