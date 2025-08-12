package Dao;

import dao.RegistrationDaoJDBC;
import exception.PasswordReadException;
import exception.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RegistrazioneDaoImplTest {
    //test sulla registrazione
    @Test
    void registraUtente() throws SQLException, PasswordReadException, UserAlreadyExistsException {
        RegistrationDaoJDBC registrazioneDao=new RegistrationDaoJDBC();
        // se l'utente gia esiste nel sistema mi aspetto false come risultato, altrimenti true
        assertEquals(true,registrazioneDao.registrateUser("nuovoUtente12d","nuovoUtente1d2@gmail.com","nuovoUtente"));
    }

    @Test
    void verificaEsistenzaUtente() throws SQLException, PasswordReadException, UserAlreadyExistsException {
        RegistrationDaoJDBC registrazioneDao=new RegistrationDaoJDBC();
        //verificaEsistenzaUtente torna false se esiste già un utente con quelle credenziali, true altrimenti
        assertEquals(true,registrazioneDao.verifyUserExistance("nuovoUtente1","nuovoUtente1Test@gmail.com"));
    }
}