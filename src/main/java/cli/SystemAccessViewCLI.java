package cli;

import utility.NotificationHub;
import utility.Printer;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

public class SystemAccessViewCLI {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public String askEmail() throws IOException {
        Printer.print("""
                --------------------------Login Page----------------------------
                Enter your email (type 'esc' to go back):""");
        return bufferedReader.readLine();
    }

    public String askPassword() throws IOException {
        Printer.print("Enter your password:");
        return bufferedReader.readLine();
    }

    public void displayErrorMessage(String messaggio) {
        Printer.error(messaggio);
    }

    public void showMessage(String messaggio) {
        Printer.print(messaggio);
    }

    public boolean confirmRegistration() throws IOException {
        Printer.print("Do you want to register with these credentials? (y/n): ");
        String risposta = bufferedReader.readLine();
        return risposta != null && risposta.equalsIgnoreCase("y");
    }

    public String askUsername() throws IOException {
        Printer.print("Enter username: ");
        return bufferedReader.readLine();
    }

    public void attendiTastoPerContinuare(String messaggio)  {
        Printer.print(messaggio);
    }

    public void displayAdminNotifications() {
        NotificationHub.printNotifications();
    }
}
