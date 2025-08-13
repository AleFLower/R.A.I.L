package com.example.progettoispw.graphiccontroller;
import graphiccontrollercli.HomeGraphicControllerCLI;
import model.Account;
import factory.TypeOfPersistence;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import utility.Printer;
import utility.AccessUtility;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        final String mainPage = "/com/example/progettoispw/viewsfxml/HomeView.fxml";
        //controller che fa da "navigation"
        SceneNavigatorGraphicController sceneController = SceneNavigatorGraphicController.getInstance(stage);
        sceneController.showMainScene(mainPage);
        stage.setOnCloseRequest(windowEvent->{  //se schiaccio la x nello stage, faccio il logout
            windowEvent.consume();
            logout(stage);
        });
    }

    public static void main(String[] args) throws IOException {
        //l'app viene lanciata, creiamo quindi un utente di default che possiede come stato di default offline

        AccessUtility.setAccount(Account.getInitialAccount()); //al primo accesso, creo una istanza di account
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        Printer.print("---------------------------------------------------------------------");

        TypeOfPersistence typeOfPersistence = null;


        while(true){

            // Chiedo il tipo di persistenza
            while (typeOfPersistence == null) {
                Printer.print("Choose app start mode: \n1 -> Full version DBMS\n2 -> Full version file system\n3 -> Demo version");
                String type = bufferedReader.readLine();
                if ("1".equals(type)) {
                    typeOfPersistence = TypeOfPersistence.JDBC;
                } else if ("2".equals(type)) {
                    typeOfPersistence = TypeOfPersistence.FILESYSTEM;
                } else if("3".equals(type)){
                    typeOfPersistence = TypeOfPersistence.MEMORY;
                }
                else Printer.print("Invalid choice, please try again");
            }

            // Salvo la scelta in una classe statica d’appoggio
            AccessUtility.setPersistence(typeOfPersistence);

            Printer.print("Type:\n1 to launch the app with GUI\n2 to launch the app in command line");
            String choice=bufferedReader.readLine();
            try {
                Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                //di default lancio l'interfaccia grafica
                launch();
                break;
            }
            //l'utente ha inserito effettivamente dei numeri
            int choiceNumber = Integer.parseInt(choice);
            if(choiceNumber==1) {
                //è stata scelta l'interfaccia grafica
                launch();
                System.exit(0);

            }
            //CLI
            else if(choiceNumber==2) {
                //è stata scelta la linea di comando
                HomeGraphicControllerCLI homeGraphicControllerCLI = new HomeGraphicControllerCLI();
                homeGraphicControllerCLI.displayHomePage();
                System.exit(0);
            }
            Printer.print("Sorry, please type 1 or 2");
            Printer.print("---------------------------------------------------------------------");
        }

    }
    public void logout(Stage stage){
        //metodo che si attiva se con l'interfaccia grafica clicco sulla "x" di uscita, avverte l'utente (grazie ad
        //una finestra) che sta uscendo dal sistema e gli fa decidere se uscire oppure no
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Do you really want to exit?");
        alert.setHeaderText("Exiting application");
        if(alert.showAndWait().get()== ButtonType.OK){
            //usciamo dall'applicazione
            stage.close();
        }
    }
}
