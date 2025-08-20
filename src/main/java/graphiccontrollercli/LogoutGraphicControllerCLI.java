package graphiccontrollercli;

import applicationcontroller.AccountController;
import utility.Printer;
import utility.AccessUtility;

public class LogoutGraphicControllerCLI {

    private AccountController accountController = new AccountController();

    public void logout() {

        AccessUtility.setUserCode(null);
        AccessUtility.setUsername(null);
        accountController.goOffline();

        Printer.print("Logout successful.\nYou will be returned to the home screen.");
    }
}
