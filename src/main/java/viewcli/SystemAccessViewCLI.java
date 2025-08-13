package viewcli;

import utility.NotificationItem;
import utility.Printer;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

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

    public void displayErrorMessage(String msg) {
        Printer.error(msg);
    }

    public void showMessage(String msg) {
        Printer.print(msg);
    }

    public boolean confirmRegistration() throws IOException {
        Printer.print("Do you want to register with these credentials? (y/n): ");
        String answer = bufferedReader.readLine();
        return answer != null && answer.equalsIgnoreCase("y");
    }

    public String askUsername() throws IOException {
        Printer.print("Enter username: ");
        return bufferedReader.readLine();
    }

    public void displayAdminNotifications(List<NotificationItem> notifications) {
        Printer.print("🔔 Notifications for admin:");
        if (notifications == null || notifications.isEmpty()) {
            System.out.println("No notifications.");
        } else {
            for (NotificationItem n : notifications) {
                System.out.println(" - " + n);
            }
        }
    }
}
