package cli;

import controllergraficicommandlineinterface.ControllerGraficoHome;
import controllergraficicommandlineinterface.ControllerGraficoLoginCli;
import controllergraficicommandlineinterface.ControllerGraficoLogoutCli;
import dao.SingletonConnessione;
import utility.Printer;
import utility.UtilityAccesso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PaginaHome {
    //controller grafici per CLI

    private ControllerGraficoLoginCli controllerGraficoLoginCli=new ControllerGraficoLoginCli();
    private ControllerGraficoHome controller = new ControllerGraficoHome();

    public void displayHomepage() throws IOException {

        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        Clear.clear();
        String statoAccesso = (UtilityAccesso.getCodiceUtente() == null) ? "Login" : "Logout";

        Printer.print("------------------------------------------HOME------------------------------------------\n" +
                "digita:\n" +
                "1 per segnalare un problema\n" +
                "2 per lasciare una recensione\n" +
                "3 per suggerire nuove funzioni\n" +
                "4 per " + statoAccesso + "\n" +
                "5 per visualizzare le segnalazioni attive\n" +
                "6 per uscire dall'applicazione");
        while(true){
            String scelta=bufferedReader.readLine();
            try {
                Integer.parseInt(scelta);
            } catch (NumberFormatException e) {
                Printer.error("la prossima volta accertati di digitare un numero ");
                System.exit(-1);
            }
            //l'utente ha inserito effettivamente dei numeri
            int numeroScelta = Integer.parseInt(scelta);
            if(numeroScelta == 1){
                this.controller.segnalaProblema();
                break;
            }
            if(numeroScelta == 2){
                Printer.print("la funzione per lasciare una recensione ancora non e' stata implementata, presto sarà disponibile");
                Printer.print("seleziona un'altra funzione");
            }
            if(numeroScelta ==3){
                Printer.print("la funzione per suggerire nuove funzioni ancora non e' stata implementata, presto sarà disponibile");
                Printer.print("seleziona un'altra funzione");
            }
            if (numeroScelta == 4) {
                if (UtilityAccesso.getCodiceUtente() == null) {
                    // Utente non loggato → login
                    this.controllerGraficoLoginCli.accediAlSistema();
                } else {
                    // Utente loggato → logout
                    ControllerGraficoLogoutCli logoutCli = new ControllerGraficoLogoutCli();
                    logoutCli.logout();
                }
            }

            if(numeroScelta==5){
                this.controller.visualizzaSegnalazioni();
            }
            if(numeroScelta==6){
                SingletonConnessione.closeConnection();
                //per come è strutturata l'applicazione non si entra mai in questa eccezione, ho provato in tutti i
                //modi ma non viene mai generata un eccezione sql exception
                Printer.print("grazie per aver usato l'applicazione, arrivederci =)");
                System.exit(0);
            }
            Printer.print("riprova con uno tra i seguenti numeri : 1, 4 ,5, 6 ");

        }
    }
}
