package graphiccontrollercli;

import viewcli.ReportProblemViewCLI;
import utility.AccessUtility;

import java.io.IOException;

public class ReportProblemGraphicControllerCLI {
    private final ReportProblemViewCLI reportProblemPage = new ReportProblemViewCLI();

    public void displayReportChoice() throws IOException {
        int choice = reportProblemPage.displayReportProblems();

        switch (choice) {
            case 1 -> {
                ReportLevelCrossingGraphicControllerCLI lcReportGraphicController = new ReportLevelCrossingGraphicControllerCLI(AccessUtility.getPersistence());
                lcReportGraphicController.displayReportPage();
            }
            case 2 -> {
                ReportTrackGraphicControllerCLI trackReportGraphicController =
                        new ReportTrackGraphicControllerCLI();
                trackReportGraphicController.displayReportPage();
            }
            default -> {
                HomeGraphicControllerCLI homeGraphicController = new HomeGraphicControllerCLI();
                homeGraphicController.displayHomePage();
            }
        }
    }
}
