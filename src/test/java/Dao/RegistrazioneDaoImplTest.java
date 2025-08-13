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
    void registerUser() throws SQLException, PasswordReadException, UserAlreadyExistsException {
        RegistrationDaoJDBC registrationDao=new RegistrationDaoJDBC();
        // se l'utente gia esiste nel sistema mi aspetto false come risultato, altrimenti true
        assertEquals(true,registrationDao.registrateUser("nuovoUtente12d","nuovoUtente1d2@gmail.com","nuovoUtente"));
    }

    @Test
    void verifyUserExistance() throws SQLException, PasswordReadException, UserAlreadyExistsException {
        RegistrationDaoJDBC registrationDao=new RegistrationDaoJDBC();
        //verificaEsistenzaUtente torna false se esiste già un utente con quelle credenziali, true altrimenti
        assertEquals(true,registrationDao.verifyUserExistance("nuovoUtente1","nuovoUtente1Test@gmail.com"));
    }
}