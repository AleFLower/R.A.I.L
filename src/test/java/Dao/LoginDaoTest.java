package Dao;

import dao.LoginDaoJDBC;
import exception.PasswordReadException;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class LoginDaoTest {

    @Test
    void verifyExistance() throws SQLException, PasswordReadException {
            LoginDaoJDBC loginDao = new LoginDaoJDBC();
            assertEquals(true, loginDao.verifyAccount("admin@example.com", "adminpassword"));
    }
    @Test
    void verifyNotExistInSystem() throws  SQLException, PasswordReadException {
        LoginDaoJDBC loginDao= new LoginDaoJDBC();
        assertEquals(false,loginDao.verifyAccount("pippo@esempio.com","pippo"));
    }
}