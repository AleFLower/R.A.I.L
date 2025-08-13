package graphiccontrollercli;

import bean.RegistrationBean;
import applicationcontroller.RegistrationController;
import exception.PasswordReadException;
import exception.UserAlreadyExistsException;
import factory.TypeOfPersistence;
import utility.Printer;
import utility.AccessUtility;

import java.io.IOException;
import java.sql.SQLException;

public class RegistrationGraphicControllerCLI {
    private final String username;
    private final String email;
    private final String password;

    public RegistrationGraphicControllerCLI(String email, String password, String username) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void registrateUser() {
        try {
            // Creo il bean con i dati forniti
            RegistrationBean bean = new RegistrationBean(email,password,username);


            // Chiamo il controller applicativo
            RegistrationController registrationController = new RegistrationController(bean);
            registrationController.registrateUser(AccessUtility.getPersistence());

            Printer.print("Registration successful!");
        } catch (UserAlreadyExistsException e) {
            Printer.error("Registration failed: " + e.getMessage());
        } catch (PasswordReadException | SQLException | IOException e) {
            Printer.error("Technical error during registration: " + e.getMessage());
        }
    }
}
