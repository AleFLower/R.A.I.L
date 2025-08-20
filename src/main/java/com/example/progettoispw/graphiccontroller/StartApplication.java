package com.example.progettoispw.graphiccontroller;
import graphiccontrollercli.HomeGraphicControllerCLI;
import factory.TypeOfPersistence;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import utility.Printer;
import utility.AccessUtility;
import java.io.*;


public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        final String mainPage = "/com/example/progettoispw/viewsfxml/HomeView.fxml";

        SceneNavigatorGraphicController sceneController = SceneNavigatorGraphicController.getInstance(stage);
        sceneController.showMainScene(mainPage);
        stage.setOnCloseRequest(windowEvent->{  //se schiaccio la x nello stage, faccio il logout
            windowEvent.consume();
            logout(stage);
        });
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        Printer.print("---------------------------------------------------------------------");

        TypeOfPersistence typeOfPersistence = null;


        while(true){


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


            AccessUtility.setPersistence(typeOfPersistence);

            Printer.print("Type:\n1 to launch the app with GUI\n2 to launch the app in command line");
            String choice=bufferedReader.readLine();
            try {
                Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                //default=GUI
                launch();
                break;
            }
            int choiceNumber = Integer.parseInt(choice);
            if(choiceNumber==1) {
                launch();
                System.exit(0);

            }

            else if(choiceNumber==2) {

                HomeGraphicControllerCLI homeGraphicControllerCLI = new HomeGraphicControllerCLI();
                homeGraphicControllerCLI.displayHomePage();
                System.exit(0);
            }
            Printer.print("Sorry, please type 1 or 2");
            Printer.print("---------------------------------------------------------------------");
        }

    }
    public void logout(Stage stage){

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Do you really want to exit?");
        alert.setHeaderText("Exiting application");
        if(alert.showAndWait().get()== ButtonType.OK){
            stage.close();
        }
    }
}
