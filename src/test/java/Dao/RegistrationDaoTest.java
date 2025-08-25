package Dao;

import dao.RegistrationDaoJDBC;
import exception.PasswordReadException;
import exception.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationDaoTest {
    //test sulla registrazione
    @Test
    void registerUser() throws SQLException, PasswordReadException, UserAlreadyExistsException {
        RegistrationDaoJDBC registrationDao=new RegistrationDaoJDBC();
        assertEquals(true,registrationDao.registrateUser("nuovoUtente12d","nuovoUtente1d2@gmail.com","nuovoUtente"));
    }

    @Test
    void verifyUserExistance() throws SQLException, PasswordReadException, UserAlreadyExistsException {
        RegistrationDaoJDBC registrationDao=new RegistrationDaoJDBC();
        assertEquals(true,registrationDao.verifyUserExistance("nuovoUtente1","nuovoUtente1Test@gmail.com"));
    }
}