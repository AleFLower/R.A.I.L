package com.example.progettoispw.controllergrafici;

import javafx.fxml.FXML;

public class ReportProblemGraphicController extends GeneralGraphicController {

    /*questo controller è associato alla pagina dei segnala problemi, anche questa ha i pulsanti in comune
    * nella barra del menu e quindi anche questa riuserà il codice di quei pulsanti che sono stati gia' definiti
    * inoltre implementerà la logica dei suoi button che non ha in comune con le altre view */
    //inizio implementando la logica dei buttonComuni
    private final SceneNavigatorGraphicController sceneController = SceneNavigatorGraphicController.getInstance(null);


    //a questa classe non serve il l'override di initialize, perche' effetivamente quello che dovrebbe fare in quel caso
    //in cui fosse presente e' chiamare super.intialize e basta poiche non ha sui bottoni a parte che condivide con altre
    //classi e che mi fanno duplicare il codice, tanto vale allora non mettere proprio intialize e far gestire il click c
    //come ho fatto per quello sotto
    @FXML
    public void reportLevelCrossing() throws Exception{
        sceneController.displayScene("/com/example/progettoispw/viewsfxml/ReportLevelCrossingProblemView.fxml");
    }
    @FXML
    public void reportTrack() throws Exception{
        sceneController.displayScene("/com/example/progettoispw/viewsfxml/ReportTrackProblemView.fxml");
    }
}