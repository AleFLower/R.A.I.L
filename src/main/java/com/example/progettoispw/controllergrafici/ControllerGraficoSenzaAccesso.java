package com.example.progettoispw.controllergrafici;
import bean.BeanObserverAccount;
import com.jfoenix.controls.JFXButton;
import entita.Role;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import utility.UtilityAccesso;

import java.net.URL;
import java.util.ResourceBundle;
public class ControllerGraficoSenzaAccesso implements Initializable {
    @FXML
    private JFXButton impostazioniButton;
    @FXML
    private JFXButton attiveButtonName;
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXButton contattiButton;
    @FXML
    private JFXButton aiutoButton;
    @FXML
    private JFXButton chiSiamoButton;
    @FXML
    private Label menu;
    @FXML
    private Label menuBack;
    @FXML
    private AnchorPane slider;
    @FXML
    private JFXButton risolteButtonName;
    private final ControllerVisualizzatoreScene controllerVisualizzatoreScene = ControllerVisualizzatoreScene.getInstance(null);
    private static final  String STATO="ONLINE";

    private BeanObserverAccount beanObserver= BeanObserverAccount.getObserver();

    /*questa classe la uso per implementare la logica dei button comuni a tutte le schermate, in particolare questa
     * classe svolge il ruolo di controller grafico per la prova-home.fxml, la quale è la prima schermata che viene
     * quando start l'app*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //vedo ogni volta se lo stato del mio concrete observer( rappresentato da beanObserver) è cambiato, perche e' vero che devo
        //notificare il concrete observer, ma in realtà a me interessa che in cambiamenti nel concrete observer vengano riportati al
        //controller grafico
        if(beanObserver.getNomeUtente()!=null){
            loginButton.setText(beanObserver.getNomeUtente());
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
            //risolteButtonName se l'utente e' loggato deve reindirizzarlo alla pagina che mostra le sue segnalazioni
            //risolte
            risolteButtonName.setOnMouseClicked(event -> {
                //devo vedere se l'utente e' loggato prima di accedere alla pagina
                //getStatoAttuale ritorna ONLINE se l'utente ha effettuato l'accesso, OFFLINE altrimenti
                //verifico quindi che l'utente abbbia effettuato l'accesso per fargli vedere le segnalazioni
                //associate al suo account
                if(beanObserver.getStatoAttuale()==STATO) {
                    controllerVisualizzatoreScene.visualizzaScena("Segnalazioni-risolte-page.fxml");
                }else {
                    controllerVisualizzatoreScene.visualizzaScena("AvvertenzaRegistrazione.fxml");
                }
            });
            //attiveButtonName se l'utent e' registrato deve reindirizzare l'utente alla pagina che mostra le sue
            //segnalazioni che ancora non sono state risolte
            attiveButtonName.setOnMouseClicked(event -> {
                //GetStatoAttuale ritorna ONLINE se l'utente ha effettuato l'accesso, OFFLINE altrimenti
                //verifico quindi che l'utente abbbia effettuato l'accesso per fargli vedere le segnalazioni
                //associate al suo account
                if(beanObserver.getStatoAttuale()==STATO){
                    //posso accedere
                    controllerVisualizzatoreScene.visualizzaScena("Segnalazioni-attive-page.fxml");
                }else{
                    controllerVisualizzatoreScene.visualizzaScena("AvvertenzaRegistrazione.fxml");
                }
            });
            loginButton.setOnMouseClicked(event -> {
                //se l'utente e' online allora non posso fagli rivedere la pagina per riinserire le credenziali
                //gli mostro la pagina per fare il logout
                if(beanObserver.getStatoAttuale()==STATO){
                        //l'utente si e' loggato, voglio quindi caricare la schermata che mi permette di fare il logout
                        controllerVisualizzatoreScene.visualizzaScena("logout-page.fxml");
                    } else {
                        //l'utente non è loggato, carico la schermata normale di login
                        //ricorda se l'utente fa il logout le variabili di utility accesso vengono settate a null
                        controllerVisualizzatoreScene.visualizzaScena("login-registrazione-page.fxml");
                    }
            });

        }catch (Exception e){
            System.exit(-1);
        }
    }
    //verifico che solamente lo user puo segnalare problemi, l'admin l'ho messo solo per far vedere la conclusione
    //del caso d'uso con la notifica all'admin.
    @FXML
    void SegnalaProblemaAccess() throws Exception {
        if(UtilityAccesso.getRole()==Role.ADMIN){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Permesso negato");
            alert.setHeaderText(null);
            alert.setContentText("Solo un UTENTE può segnalare un problema.");
            alert.showAndWait();
        }
       else  controllerVisualizzatoreScene.visualizzaScena("PaginaSegnalaProblema.fxml");
    }

}