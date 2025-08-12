package applicationcontroller;

import bean.RegistrationBean;
import dao.RegistrationDao;
import exception.PasswordReadException;
import exception.UserAlreadyExistsException;
import factory.DaoFactory;
import factory.TypeOfPersistence;

import java.io.IOException;
import java.sql.SQLException;

public class RegistrationController {
    private String email;
    private String password;
    private String username;


    public RegistrationController(RegistrationBean bean, TypeOfPersistence typeOfPersistence) throws SQLException, UserAlreadyExistsException, PasswordReadException, IOException {
        email= bean.getEmail();
        username=bean.getUsername();
        password= bean.getPassword();
        registrateUser(typeOfPersistence);
    }
    private void registrateUser(TypeOfPersistence typeOfPersistence) throws SQLException, UserAlreadyExistsException, PasswordReadException, IOException {
        //devo usare un dao per prendere la connessione e far registrare l'utente nel sistema

        DaoFactory factory = DaoFactory.getFactory(typeOfPersistence);
        RegistrationDao registrationDao = factory.getRegistrationDao();

        if(!registrationDao.registrateUser(username, email, password)){
            //la registrazione non è avvenuta, esiste un utente che usa già quelle credenziali, lancio un eccezioni che
            //comunica all'utente che non puo' registrarsi con quelle credenziali
            throw new UserAlreadyExistsException("The username or the email are linked\nto another account");
        }
    }
}
