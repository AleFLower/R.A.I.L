package com.example.progettoispw.controllergrafici;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class SceneNavigatorGraphicController {
    private static SceneNavigatorGraphicController sceneController=null;
    private static Stage stage;

    private SceneNavigatorGraphicController(){}
    public static SceneNavigatorGraphicController getInstance(Stage newStage){
        if(sceneController==null){
            sceneController=new SceneNavigatorGraphicController();
            stage=newStage;
        }
        return sceneController;
    }


    public void showMainScene(String sceneString) throws Exception {
        //this method load the main view
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(sceneString));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public  void displayScene(String sceneString){
        try {
            Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(sceneString)));
            Scene scene = new Scene(fxmlLoader);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.exit(-1);
        }
    }
}
