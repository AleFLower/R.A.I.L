package graphiccontrollercli;

import bean.ReportListBean;
import bean.AccountBeanObserver;
import viewcli.HomePageViewCLI;
import viewcli.ActiveReportsViewCLI;
import com.example.progettoispw.graphiccontroller.ReportType;
import applicationcontroller.HandleReportsController;
import exception.PasswordReadException;
import exception.NoReportsFoundException;
import model.Role;
import utility.Printer;
import utility.AccessUtility;

import java.io.IOException;
import java.sql.SQLException;

public class HomeGraphicControllerCLI {
    private final HomePageViewCLI homePage;

    private AccountBeanObserver beanObserver= AccountBeanObserver.getObserver();
    private static final String STATE = "OFFLINE";

    public HomeGraphicControllerCLI() {
        this.homePage = new HomePageViewCLI();
    }

    public void displayHomePage() throws IOException {
        while (true) {
            int choice = homePage.displayHomeMenu();

            switch (choice) {
                case 1 -> {
                    if(AccessUtility.getRole() == Role.ADMIN) homePage.showMessage("You must log in as a user to report a problem");
                    else reportProblem();
                }
                case 2 -> homePage.showFunctionNotAvailable("review");
                case 3 -> homePage.showFunctionNotAvailable("feature suggestion");
                case 4 -> handleAccess();
                case 5 -> displayActiveReports();
                case 6 -> displayfixedReports();
                case 7 -> exit();
                default -> homePage.showInputError();
            }
        }
    }

    private void reportProblem() throws IOException {
        ReportProblemGraphicControllerCLI reportGraphicController = new ReportProblemGraphicControllerCLI();
        reportGraphicController.displayReportChoice();
    }

    private void handleAccess() throws IOException {
        if(beanObserver.getActualState().equals(STATE))
            new LoginGraphicControllerCLI().login();
        else {
            LogoutGraphicControllerCLI logoutCli = new LogoutGraphicControllerCLI();
            logoutCli.logout();
        }
    }

    private void displayActiveReports() {
        try {
            if (beanObserver.getActualState().equals(STATE)) {
                Printer.error("You must log in to view active reports.");
                return;
            }

            ReportListBean bean = new ReportListBean(ReportType.ACTIVE);


            new HandleReportsController(bean, AccessUtility.getPersistence());


            ActiveReportsViewCLI view = new ActiveReportsViewCLI();
            view.displayActiveReports(bean);

        }catch (NoReportsFoundException e){
            Printer.error("No active reports found");
        }
        catch (SQLException | IOException | PasswordReadException e) {
            Printer.error("Failed to retrieve active reports: " + e.getMessage());

        }
    }

    private void displayfixedReports(){
        try {

            if (beanObserver.getActualState().equals(STATE)) {
                Printer.error("You must log in to view fixed reports.");
                return;
            }

            ReportListBean bean = new ReportListBean(ReportType.FIXED);

            new HandleReportsController(bean, AccessUtility.getPersistence());

            ActiveReportsViewCLI view = new ActiveReportsViewCLI();
            view.displayActiveReports(bean);

        } catch (NoReportsFoundException e) {
            Printer.error("No fixed reports found");
        } catch (SQLException | IOException | PasswordReadException e) {
            Printer.error("Failed to retrieve fixed reports: " + e.getMessage());
        }
    }

    private void exit() {
        homePage.showExitMessage();
        System.exit(0);
    }
}

