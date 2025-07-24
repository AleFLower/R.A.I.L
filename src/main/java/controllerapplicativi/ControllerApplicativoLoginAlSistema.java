package controllerapplicativi;

import bean.BeanLogin;
import dao.LoginDao;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.NonEsisteUtenteNelSistemaException;
import factory.FactoryDao;
import factory.TypeOfPersistence;

import java.sql.SQLException;

public class ControllerApplicativoLoginAlSistema {
    //questo controller applicativo invia i dati inseriti al db e se l'utente con quelle credenziali esiste
    //allora l'utente si puo' definire loggato altrimenti ci sara' un ritorno di errore
    private final String email;
    private final String password;
    //costruttore a cui il controller grafico passa i bean
    public ControllerApplicativoLoginAlSistema(BeanLogin beanLogin, TypeOfPersistence typeOfPersistence) throws SQLException, NonEsisteUtenteNelSistemaException, ErroreLetturaPasswordException {
        this.email=beanLogin.getEmail();
        this.password=beanLogin.getPassword();
        inviaSegnalazione(typeOfPersistence);
    }
    public void inviaSegnalazione(TypeOfPersistence typeOfPersistence) throws SQLException, NonEsisteUtenteNelSistemaException, ErroreLetturaPasswordException {
        //devo aprire una connessione e far inviare al dao del login i dati
        //questo metodo ritorna true se l'utente e' registrato nel sistema, false altrimenti
        //l'utente è registrato nel sistema, questo viene comunicato al controller applicativo, il quale indirizza
        //l'utente alla home
        FactoryDao factory = FactoryDao.getFactory(typeOfPersistence);
        LoginDao loginDao = factory.getLoginDao();
        if (loginDao.verificaAccountNelSistema(email, password)) {
            return;
        }

        //se l'account non esiste nel sistema( quindi l'if) non viene svolto, lancio una eccezione
        throw new NonEsisteUtenteNelSistemaException("non esiste nessun utente con queste credenziali");

    }

}
