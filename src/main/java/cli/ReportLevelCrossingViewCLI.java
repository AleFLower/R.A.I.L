package cli;

import exception.InvalidChoiceException;
import utility.Printer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReportLevelCrossingViewCLI {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String askLcCode() throws IOException {
        Clear.clear();
        Printer.print("""
                ------------------Level Crossing Report Page------------------
                Enter the level crossing number to report (type 'esc' to exit):""");
        return reader.readLine();
    }

    public String askLocation() throws IOException {
        Printer.print("Enter the location (type 'esc' to exit): ");
        return reader.readLine();
    }

    public String askIssue() throws IOException {
        Printer.print("Enter the issue of the level crossing:");
        return reader.readLine();
    }

    public boolean confirmSaving() throws IOException, InvalidChoiceException {
        Printer.print("Save the report? (y/n)");

        String choice = reader.readLine();

        if (choice == null) {
            throw new InvalidChoiceException("Input interrupted or null.");
        }

        choice = choice.trim().toLowerCase();

        if (!choice.equals("y") && !choice.equals("n")) {
            throw new InvalidChoiceException("Invalid choice. You must type only 'y' or 'n'.");
        }

        return choice.equals("y");
    }

    public void showSuccessMessage()  {
        Printer.print("Report submitted successfully! Back to home page...");
    }

    public void showError(String message) {
        Printer.error(message);
    }
}
