package viewcli;


import utility.Printer;
import utility.AccessUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class HomePageViewCLI {

    public int displayHomeMenu() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String accessState = (AccessUtility.getUserCode() == null) ? "Login" : "Logout";

        Printer.print("""
                ------------------------------------------HOME------------------------------------------
                Type:
                1 to report a problem
                2 to leave a review
                3 to suggest new features
                4 to %s
                5 to view active reports
                6 to view resolved reports
                7 to exit the application
                """.formatted(accessState));

        try {
            return Integer.parseInt(bufferedReader.readLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void showFunctionNotAvailable(String function) {
        Printer.print("The function for %s is not yet available.".formatted(function));
    }

    public void showInputError() {
        Printer.error("Invalid input. Please try again.");
    }

    public void showExitMessage() {
        Printer.print("Thank you for using the application. Goodbye!");
    }

    public void showMessage(String s) {
        Printer.print(s);
    }
}
