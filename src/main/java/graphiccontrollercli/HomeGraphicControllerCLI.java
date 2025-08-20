package graphiccontrollercli;

import bean.ReportListBean;
import bean.AccountBeanObserver;
import viewcli.HomePageViewCLI;
import viewcli.ActiveReportsViewCLI;
import com.example.progettoispw.graphiccontroller.ReportType;
import applicationcontroller.ReportTypeController;
import exception.PasswordReadException;
import exception.NoReportsFoundException;
import model.Role;
import utility.Printer;
import utility.AccessUtility;

import java.io.IOException;
import java.sql.SQLException;

public class HomeGraphicControllerCLI {
    private final HomePageViewCLI homePage;
    //uso la bean per recuperare informazioni dall account, visto che le view non devono essere accoppiate con model
    //la bean si registra come observer: non appena avviene cambia stato l'account, notifica la bean
    private AccountBeanObserver beanObserver= new AccountBeanObserver();
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
                case 6 -> displayResolvedReports();
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
            // Creo il bean specificando il tipo di segnalazioni da visualizzare
            ReportListBean bean = new ReportListBean(ReportType.ACTIVE);

            // Passo il bean al controller applicativo per riempirlo
            new ReportTypeController(bean, AccessUtility.getPersistence());

            // Passo il bean popolato alla view per la sola visualizzazione
            ActiveReportsViewCLI view = new ActiveReportsViewCLI();
            view.displayActiveReports(bean);

        }catch (NoReportsFoundException e){
            Printer.error("No active reports found");
        }
        catch (SQLException | IOException | PasswordReadException e) {
            Printer.error("Failed to retrieve active reports: " + e.getMessage());

        }
    }

    private void displayResolvedReports(){
        try {

            if (beanObserver.getActualState().equals(STATE)) {
                Printer.error("You must log in to view resolved reports.");
                return;
            }
            // Creo il bean specificando il tipo di segnalazioni da visualizzare
            ReportListBean bean = new ReportListBean(ReportType.RESOLVED);

            // Passo il bean al controller applicativo per riempirlo
            new ReportTypeController(bean, AccessUtility.getPersistence());

            // Passo il bean popolato alla view per la sola visualizzazione
            ActiveReportsViewCLI view = new ActiveReportsViewCLI();
            view.displayActiveReports(bean);

        } catch (NoReportsFoundException e) {
            Printer.error("No resolved reports found");
        } catch (SQLException | IOException | PasswordReadException e) {
            Printer.error("Failed to retrieve resolved reports: " + e.getMessage());
        }
    }

    private void exit() {
        homePage.showExitMessage();
        System.exit(0);
    }
}

