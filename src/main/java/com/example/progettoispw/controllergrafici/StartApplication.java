package com.example.progettoispw.controllergrafici;
import cli.PaginaHome;
import controllergraficicommandlineinterface.ControllerGraficoHome;
import dao.SingletonConnessione;
import entita.Account;
import factory.TypeOfPersistence;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import utility.Printer;
import utility.UtilityAccesso;
import java.io.*;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        final String schermataPrincipale = "prova-home.fxml";
        //controller che fa da "navigation"
        ControllerVisualizzatoreScene controllerVisualizzatoreScene = ControllerVisualizzatoreScene.getInstance(stage);
        controllerVisualizzatoreScene.visualizzaScenaPrincipale(schermataPrincipale);
        stage.setOnCloseRequest(windowEvent->{
            windowEvent.consume();
            logout(stage);
        });
    }

    public static void main(String[] args) throws IOException {
        //l'app viene lanciata, creiamo quindi un utente di default che possiede come stato di default offline
        UtilityAccesso.setAccount(Account.getInitialAccount());
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        Printer.print("---------------------------------------------------------------------");

        TypeOfPersistence typeOfPersistence = null;


        while(true){

            // Chiedo il tipo di persistenza
            while (typeOfPersistence == null) {
                Printer.print("Scegli modalità di avvio dell'app: \n1 -> Full version dbms\n2 -> Full version file system\n3 -> Demo version");
                String tipo = bufferedReader.readLine();
                if ("1".equals(tipo)) {
                    typeOfPersistence = TypeOfPersistence.JDBC;
                } else if ("2".equals(tipo)) {
                    typeOfPersistence = TypeOfPersistence.FILESYSTEM;
                } else if("3".equals(tipo)){
                    typeOfPersistence = TypeOfPersistence.MEMORY;
                }
                else Printer.print("Scelta non valida, riprova");
            }

            // Salvo la scelta in una classe statica d’appoggio
            UtilityAccesso.setPersistence(typeOfPersistence);

            Printer.print("digitare:\n1 per visualizzare l'app con l'interfaccia grafica\n2 per visualizzare l'app in linea di comando");
            String scelta=bufferedReader.readLine();
            try {
                Integer.parseInt(scelta);
            } catch (NumberFormatException e) {
                //di default lancio l'interfaccia grafica
                launch();
                break;
            }
            //l'utente ha inserito effettivamente dei numeri
            int numeroScelta = Integer.parseInt(scelta);
            if(numeroScelta==1) {
                //è stata scelta l'interfaccia grafica
                launch();
                System.exit(0);

            }
            //CLI
            else if(numeroScelta==2) {
                //è stata scelta la linea di comando
                ControllerGraficoHome controllerGraficoHome = new ControllerGraficoHome();
                controllerGraficoHome.mostraHome();
                System.exit(0);
            }
            Printer.print("mi spiace, prova a digitare 1 oppure 2");
            Printer.print("---------------------------------------------------------------------");
        }

    }
    public void logout(Stage stage){
        //metodo che si attiva se con l'interfaccia grafica clicco sulla "x" di uscita, avverte l'utente (grazie ad
        //una finestra) che sta uscendo dal sistema e gli fa decidere se uscire oppure no
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("uscita");
        alert.setContentText("vuoi davvero uscire ? ");
        alert.setHeaderText("stai uscendo ");
        if(alert.showAndWait().get()== ButtonType.OK){
            //verifichiamo prima di aver chiuso la connessione con il db se e' stata aperta
            SingletonConnessione.closeConnection();
            //usciamo dall'applicazione
            stage.close();
        }
    }
}