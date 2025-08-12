package viewcli;


import utility.Printer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReportTrackViewCLI {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String askLocation() throws IOException {
        Printer.print("Enter location (type 'esc' to exit):");
        return reader.readLine();
    }

    public String askTrackNumber() throws IOException {
        Printer.print("Enter track number:");
        return reader.readLine();
    }

    public String askIssue() throws IOException {
        Printer.print("Enter issue:");
        return reader.readLine();
    }

    public boolean confirmSaving() throws IOException {
        Printer.print("Save the report? (y/n)");
        String answer = reader.readLine();
        if (answer == null) {
            return false; // or you can choose a different behavior
        }

        answer = answer.trim().toLowerCase();
        return answer.equals("y");
    }

    public void showError(String msg) {
        Printer.error(msg);
    }

    public void showSuccessMessage()  {
        Printer.print("Report submitted successfully. Back to home page...");
    }
}
