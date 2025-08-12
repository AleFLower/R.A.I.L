package graphiccontrollercli;

import bean.ReportBean;

import cli.ReportTrackViewCLI;
import applicationcontroller.ReportController;
import exception.*;
import factory.AssetType;

import utility.AccessUtility;

import java.io.IOException;

public class ReportTrackGraphicControllerCLI {
    private final ReportTrackViewCLI view;

    public ReportTrackGraphicControllerCLI() {
        this.view = new ReportTrackViewCLI();
    }

    public void displayReportPage() {
        try {
            String location = view.askLocation();
            if (location == null || location.equalsIgnoreCase("esc")) return;

            String trackNumber = view.askTrackNumber();
            if (trackNumber.equalsIgnoreCase("esc")) return;

            String issue = view.askIssue();
            if (issue.equalsIgnoreCase("esc")) return;

            if (view.confirmSaving()) {
                sendReport(location, trackNumber, issue);
            }

        } catch (IOException e) {
            view.showError("Input/output error: " + e.getMessage());
        }
        catch (InvalidChoiceException e) {
            view.showError("Invalid choice: " + e.getMessage());
        }
    }


    private void sendReport(String location, String trackNumber, String issue)  {
        try {
            ReportBean bean = new ReportBean(
                    trackNumber, location, issue, AssetType.TRACK, AccessUtility.getPersistence());

            new ReportController(bean);
            view.showSuccessMessage();
        } catch (ReportAlreadyExistsException e) {
            view.showError("Report already exists.");
        } catch (Exception e) {
            view.showError("Error: " + e.getMessage());
        }
    }
}
