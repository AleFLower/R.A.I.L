package graphiccontrollercli;

import bean.LoginBean;
import cli.SystemAccessViewCLI;
import cli.HomePageViewCLI;
import applicationcontroller.LoginController;
import exception.PasswordReadException;
import exception.UserNotFoundException;

import model.Role;
import utility.NotificationHub;
import utility.AccessUtility;


import java.io.IOException;

import java.sql.SQLException;

public class SendDataGraphicControllerCLI {
    private final SystemAccessViewCLI view = new SystemAccessViewCLI();
    private final HomePageViewCLI homePage = new HomePageViewCLI();

    public void displayAccessPage() throws IOException {
        String email = view.askEmail();
        if (wantsToLeave(email)) {
            return;
        }

        String password = view.askPassword();
        if (wantsToLeave(password)) {
            return;
        }

        if (email.isBlank() || password.isBlank()) {
            view.displayErrorMessage("Next time please enter an email and a password.");
            return;
        }

        createLoginBean(email, password);
    }

    private void createLoginBean(String email, String password) throws IOException {
        LoginBean bean = new LoginBean(email, password);
        String validationError = bean.validate();

        if (validationError != null) {
            view.displayErrorMessage(validationError);
            return;
        }

        try {
            new LoginController(bean, AccessUtility.getPersistence());
            if(AccessUtility.getRole() == Role.ADMIN && !NotificationHub.getNotifications().isEmpty()){
                view.displayAdminNotifications();
                NotificationHub.clearNotifications();  // after showing notifications, clear old ones

            }
            view.showMessage("Login successful! Back to home...");
        } catch (UserNotFoundException e) {
            view.displayErrorMessage("Invalid credentials. User does not exist.");
            if (view.confirmRegistration()) {
                String username = view.askUsername();
                registrateUser(email, password, username);
            }
        } catch (SQLException | PasswordReadException e) {
            view.displayErrorMessage("Error during login: " + e.getMessage());
        }
    }

    private void registrateUser(String email, String password, String username) {
        RegistrationGraphicControllerCLI registrationGraphicController =
                new RegistrationGraphicControllerCLI(email, password, username);
        registrationGraphicController.registrateUser();
    }

    private boolean wantsToLeave(String input) {
        return input != null && input.equalsIgnoreCase("esc");
    }

}
