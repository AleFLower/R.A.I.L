package controllerapplicativi;

import bean.BeanRegistrazione;
import dao.RegistrazioneDao;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.UtenteEsistenteException;
import factory.FactoryDao;
import factory.TypeOfPersistence;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerApplicativoRegistrazioneAlSistema {
    private String email;
    private String password;
    private String username;


    public ControllerApplicativoRegistrazioneAlSistema(BeanRegistrazione bean, TypeOfPersistence typeOfPersistence) throws SQLException, UtenteEsistenteException, ErroreLetturaPasswordException, IOException {
        email= bean.getEmail();
        username=bean.getUsername();
        password= bean.getPassword();
        registraUtente(typeOfPersistence);
    }
    private void registraUtente(TypeOfPersistence typeOfPersistence) throws SQLException, UtenteEsistenteException, ErroreLetturaPasswordException, IOException {
        //devo usare un dao per prendere la connessione e far registrare l'utente nel sistema

        FactoryDao factory = FactoryDao.getFactory(typeOfPersistence);
        RegistrazioneDao registrazioneDao = factory.getRegistrazioneDao();

        if(!registrazioneDao.registraUtente(username, email, password)){
            //la registrazione non è avvenuta, esiste un utente che usa già quelle credenziali, lancio un eccezioni che
            //comunica all'utente che non puo' registrarsi con quelle credenziali
            throw new UtenteEsistenteException("la username o l'email inserite sono associate\nad un altro account");
        }
    }
}
