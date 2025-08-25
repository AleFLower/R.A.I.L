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
        stage.setOnCloseRequest(windowEvent->{
            windowEvent.consume();
            logout(stage);
        });
    }

    public static void main(String[] args) throws IOException {


        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Printer.print("---------------------------------------------------------------------");

        TypeOfPersistence typeOfPersistence = null;

        while (typeOfPersistence == null) {
            Printer.print("Choose app start mode: \n1 -> Full version DBMS\n2 -> Full version file system\n3 -> Demo version");
            String type = bufferedReader.readLine();
            switch (type) {
                case "1" -> typeOfPersistence = TypeOfPersistence.JDBC;
                case "2" -> typeOfPersistence = TypeOfPersistence.FILESYSTEM;
                case "3" -> typeOfPersistence = TypeOfPersistence.MEMORY;
                default -> Printer.print("Invalid choice, please try again");
            }
        }

        AccessUtility.setPersistence(typeOfPersistence);

        Printer.print("Type:\n1 to launch the app with GUI\n2 to launch the app in command line");
        String choice = bufferedReader.readLine();
        int choiceNumber;
        try {
            choiceNumber = Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            choiceNumber = 1; // default GUI
        }

        switch (choiceNumber) {
            case 1 -> { // GUI
                launch();
                System.exit(0);
            }
            case 2 -> { // CLI
                HomeGraphicControllerCLI homeCLI = new HomeGraphicControllerCLI();
                homeCLI.displayHomePage();
                System.exit(0);
            }
            default -> Printer.print("Sorry, please type 1 or 2");
        }

        Printer.print("---------------------------------------------------------------------");
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
