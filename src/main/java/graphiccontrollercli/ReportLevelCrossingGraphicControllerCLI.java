package graphiccontrollercli;

import bean.ReportBean;
import viewcli.ReportLevelCrossingViewCLI;
import applicationcontroller.ReportController;
import exception.*;

import factory.AssetType;
import factory.TypeOfPersistence;

import java.io.IOException;
import java.sql.SQLException;

public class ReportLevelCrossingGraphicControllerCLI {
    private final ReportLevelCrossingViewCLI view;
    private final TypeOfPersistence typeOfPersistence;

    public ReportLevelCrossingGraphicControllerCLI(TypeOfPersistence typeOfPersistence) {
        this.typeOfPersistence = typeOfPersistence;
        this.view = new ReportLevelCrossingViewCLI();
    }

    public void displayReportPage() throws IOException {
        String lcCode = view.askLcCode();
        if (wantsToLeave(lcCode)) return;

        String location = view.askLocation();
        if (wantsToLeave(location)) return;

        String issue = view.askIssue();
        if (wantsToLeave(issue)) return;

        if (lcCode.isEmpty() || location.isEmpty() || issue.isEmpty()) {
            view.showError("Next time, please enter some data.");
            return;
        }

        try {
            if (view.confirmSaving()) {
                createReport(lcCode, location, issue);
            }
        } catch (InvalidChoiceException e) {
            view.showError(e.getMessage());
            displayReportPage(); // retry
        }
    }

    private void createReport(String lcCode, String location, String issue) throws IOException {
        ReportBean bean = new ReportBean(
                lcCode, location, issue, AssetType.LEVELCROSSING, typeOfPersistence);
        try {
            bean.validateInput();
            new ReportController(bean);

            view.showSuccessMessage();
        } catch (ReportAlreadyExistsException e) {
            view.showError("Report already exists for that level crossing.");
        } catch (InvalidInputLengthException | ReportTypeException | NoLoginPerformedException |
                 SQLException | PasswordReadException e) {
            view.showError(e.getMessage());
        }
    }

    private boolean wantsToLeave(String input) {
        return input != null && input.equalsIgnoreCase("esc");
    }
}
