package applicationcontroller;

import bean.LoginBean;
import dao.LoginDao;
import exception.PasswordReadException;
import exception.UserNotFoundException;
import factory.DaoFactory;
import factory.TypeOfPersistence;

import java.sql.SQLException;

public class LoginController {
    //questo controller applicativo invia i dati inseriti al db e se l'utente con quelle credenziali esiste
    //allora l'utente si puo' definire loggato altrimenti ci sara' un ritorno di errore
    private final String email;
    private final String password;
    //costruttore a cui il controller grafico passa i bean
    public LoginController(LoginBean beanLogin, TypeOfPersistence typeOfPersistence) throws SQLException, UserNotFoundException, PasswordReadException {
        this.email=beanLogin.getEmail();
        this.password=beanLogin.getPassword();
        login(typeOfPersistence);
    }
    public void login(TypeOfPersistence typeOfPersistence) throws SQLException, UserNotFoundException, PasswordReadException {
        //devo aprire una connessione e far inviare al dao del login i dati
        //questo metodo ritorna true se l'utente e' registrato nel sistema, false altrimenti
        //l'utente è registrato nel sistema, questo viene comunicato al controller applicativo, il quale indirizza
        //l'utente alla home
        DaoFactory factory = DaoFactory.getFactory(typeOfPersistence);
        LoginDao loginDao = factory.getLoginDao();
        if (loginDao.verifyAccount(email, password)) {
            return;
        }

        //se l'account non esiste nel sistema( quindi l'if) non viene svolto, lancio una eccezione
        throw new UserNotFoundException("No user found with these credentials");

    }

}
