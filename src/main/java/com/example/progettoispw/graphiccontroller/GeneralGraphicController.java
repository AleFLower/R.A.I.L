package com.example.progettoispw.graphiccontroller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;

public class GeneralGraphicController extends NoAccessGraphicController {
    @FXML
    private JFXButton backHomeBtn;

    private final SceneNavigatorGraphicController sceneController = SceneNavigatorGraphicController.getInstance(null);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backHomeBtn.setOnMouseClicked(event->{
            try {
                sceneController.displayScene("/com/example/progettoispw/viewsfxml/HomeView.fxml");
            }catch(Exception e){
                System.exit(-1);
            }
        });
        //questo avrà i suoi pulsanti e alla fine chiamerà il super di quelli in comune, per non darli al figlio
        //quelli che non sono in comune dovrei farli private
        super.initialize(url,resourceBundle);
    }
}
