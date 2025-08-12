package viewcli;

import bean.ReportListBean;
import bean.ReportTrackBean;
import bean.ReportLevelCrossingBean;
import utility.Printer;


public class ActiveReportsViewCLI {

    public void displayActiveReports(ReportListBean reportList) {
        int lcCounter = reportList.getLevelCrossingReports().size();
        int trackCounter = reportList.getTrackReports().size();

        Printer.print("====== ACTIVE REPORTS ======");

        if (lcCounter > 0) {
            Printer.print("\n>> REPORTED LEVEL CROSSINGS:");
            for (int i = 0; i < lcCounter; i++) {
                ReportLevelCrossingBean levelCrossing = reportList.getLevelCrossingReports().get(i);
                Printer.print("\n" + (i + 1) + ") Level Crossing");
                Printer.print("Level crossing number: " + levelCrossing.getLcCode());
                Printer.print("Location: " + levelCrossing.getLocation());
                Printer.print("Issue: " + levelCrossing.getIssue());
                Printer.print("Status: " + levelCrossing.getState());
            }
        }

        if (trackCounter > 0) {
            Printer.print("\n>> REPORTED TRACKS:");
            for (int i = 0; i < trackCounter; i++) {
                ReportTrackBean binario = reportList.getTrackReports().get(i);
                Printer.print("\n" + (i + 1) + ") Track");
                Printer.print("Track number: " + binario.getTrackNumber());
                Printer.print("Location: " + binario.getLocation());
                Printer.print("Issue: " + binario.getIssue());
                Printer.print("Status: " + binario.getState());
            }
        }

    }

}
