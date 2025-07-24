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
    private final ControllerGraficoLoginCli controllerGraficoLoginCli = new ControllerGraficoLoginCli();
    private final ControllerGraficoHome controller = new ControllerGraficoHome();

    public void displayHomepage() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Clear.clear();

        String statoAccesso = (UtilityAccesso.getCodiceUtente() == null) ? "Login" : "Logout";
        Printer.print("""
                ------------------------------------------HOME------------------------------------------
                digita:
                1 per segnalare un problema
                2 per lasciare una recensione
                3 per suggerire nuove funzioni
                4 per %s
                5 per visualizzare le segnalazioni attive
                6 per uscire dall'applicazione
                """.formatted(statoAccesso));

        while (true) {
            String scelta = bufferedReader.readLine();
            int numeroScelta = parseInputOrExit(scelta);

            switch (numeroScelta) {
                case 1 -> controller.segnalaProblema();
                case 2 -> mostraMessaggioNonDisponibile("recensione");
                case 3 -> mostraMessaggioNonDisponibile("suggerimento funzionalità");
                case 4 -> gestisciAccesso();
                case 5 -> controller.visualizzaSegnalazioni();
                case 6 -> chiudiApplicazione();
                default -> Printer.error("Selezione non valida. Riprova.");
            }

            if (numeroScelta == 1 || numeroScelta == 4 || numeroScelta == 5) break;
        }
    }

    private int parseInputOrExit(String scelta) {
        try {
            return Integer.parseInt(scelta);
        } catch (NumberFormatException e) {
            Printer.error("La prossima volta accertati di digitare un numero.");
            System.exit(-1);
            return -1; // inutile, ma richiesto dal compilatore
        }
    }

    private void mostraMessaggioNonDisponibile(String funzione) {
        Printer.print("La funzione per %s ancora non è stata implementata, presto sarà disponibile.".formatted(funzione));
        Printer.print("Seleziona un'altra funzione.");
    }

    private void gestisciAccesso() throws IOException {
        if (UtilityAccesso.getCodiceUtente() == null) {
            controllerGraficoLoginCli.accediAlSistema();
        } else {
            ControllerGraficoLogoutCli logoutCli = new ControllerGraficoLogoutCli();
            logoutCli.logout();
        }
    }

    private void chiudiApplicazione() {
        SingletonConnessione.closeConnection();
        Printer.print("Grazie per aver usato l'applicazione, arrivederci =)");
        System.exit(0);
    }
}
