package com.example.progettoispw.graphiccontroller;
import bean.AccountBeanObserver;
import com.jfoenix.controls.JFXButton;
import model.Role;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import utility.AccessUtility;

import java.net.URL;
import java.util.ResourceBundle;
public class NoAccessGraphicController implements Initializable {
    @FXML
    private JFXButton settingsBtn;
    @FXML
    private JFXButton activeReportsBtn;
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXButton contactsBtn;
    @FXML
    private JFXButton helpBtn;
    @FXML
    private JFXButton aboutUsBtn;
    @FXML
    private Label menu;
    @FXML
    private Label menuBack;
    @FXML
    private AnchorPane slider;
    @FXML
    private JFXButton fixedReportsBtn;
    private final SceneNavigatorGraphicController sceneController = SceneNavigatorGraphicController.getInstance(null);
    private static final  String STATE ="ONLINE";

    private AccountBeanObserver beanObserver = AccountBeanObserver.getObserver();

    /* This class is used to implement the logic of the buttons common to all screens.
     * In particular, it acts as the graphic controller for HomeView.fxml,
     * which is the first screen displayed when the application starts. */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(beanObserver.getUsername()!=null){
            loginButton.setText(beanObserver.getUsername());
        }
        slider.setTranslateX(0);
        menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);
            slide.setOnFinished((ActionEvent e) -> {
                menu.setVisible(false);
                menuBack.setVisible(true);
            });
        });


        menuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);
            slide.setOnFinished((ActionEvent e) -> {
                menu.setVisible(true);
                menuBack.setVisible(false);
            });
        });
        try {

            fixedReportsBtn.setOnMouseClicked(event -> {

                if(beanObserver.getActualState()== STATE) {
                    sceneController.displayScene("/com/example/progettoispw/viewsfxml/FixedReportsView.fxml");
                }else {
                    sceneController.displayScene("/com/example/progettoispw/viewsfxml/RegistrationWarning.fxml");
                }
            });

            activeReportsBtn.setOnMouseClicked(event -> {

                if(beanObserver.getActualState()== STATE){

                    sceneController.displayScene("/com/example/progettoispw/viewsfxml/ActiveReportsView.fxml");
                }else{
                    sceneController.displayScene("/com/example/progettoispw/viewsfxml/RegistrationWarning.fxml");
                }
            });
            loginButton.setOnMouseClicked(event -> {

                if(beanObserver.getActualState()== STATE){

                        sceneController.displayScene("/com/example/progettoispw/viewsfxml/LogoutView.fxml");
                    } else {

                        sceneController.displayScene("/com/example/progettoispw/viewsfxml/LoginRegistrationView.fxml");
                    }
            });

        }catch (Exception e){
            System.exit(-1);
        }
    }

    @FXML
    void reportAccessProblem() throws Exception {
        if(AccessUtility.getRole()==Role.ADMIN){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Permission denied");
            alert.setHeaderText(null);
            alert.setContentText("Only users can report asset problems.");
            alert.showAndWait();
        }
       else  sceneController.displayScene("/com/example/progettoispw/viewsfxml/ReportProblemView.fxml");
    }


}