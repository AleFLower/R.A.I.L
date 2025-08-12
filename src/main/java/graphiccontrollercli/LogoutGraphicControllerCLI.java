package graphiccontrollercli;

import utility.Printer;
import utility.AccessUtility;

public class LogoutGraphicControllerCLI {

    public void logout() {
        // Pulisce i dati di sessione
        AccessUtility.setUserCode(null);
        AccessUtility.setUsername(null);
        AccessUtility.getAccount().goOffline();

        Printer.print("Logout successful.\nYou will be returned to the home screen.");
    }
}
