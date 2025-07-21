package controllergraficicommandlineinterface;

import utility.Printer;
import utility.UtilityAccesso;

public class ControllerGraficoLogoutCli {

    public void logout() {
        // Pulisce i dati di sessione
        UtilityAccesso.setCodiceUtente(null);
        UtilityAccesso.setNomeUtenteNelDatabase(null);
        UtilityAccesso.getAccount().passaOffline();

        Printer.print("Logout effettuato con successo.\nTornerai alla schermata iniziale.");
    }
}
