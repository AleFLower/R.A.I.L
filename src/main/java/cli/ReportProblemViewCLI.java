package cli;

import utility.Printer;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

public class ReportProblemViewCLI {
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public int displayReportProblems() throws IOException {
        Clear.clear();
        Printer.print("""
        ------------------------------------------Problem Reporting Page------------------------------------------
        Type:
        1 to report a level crossing
        2 to report a track
        any other input to return to home
        """);

        String choice = bufferedReader.readLine();
        try {
            return Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            return -1;  // fallback value to return to home
        }
    }
}
