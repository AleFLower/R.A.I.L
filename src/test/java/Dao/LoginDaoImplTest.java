package Dao;

import dao.LoginDaoJDBC;
import exception.PasswordReadException;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class LoginDaoImplTest {
    //test sul login,

    @Test
    void verificaEsistenzaNelSistema() throws SQLException, PasswordReadException {
        //test che verifica se una email esiste nel sistema, qui vi passerò una email che e' quindi effettivamente
        //presente nel db
            LoginDaoJDBC loginDao = new LoginDaoJDBC();
            //true =email presente
            assertEquals(true, loginDao.verifyAccount("andrea@esempio.com", "andrea"));
    }
    @Test
    void verificaNonEsistenzaNelSistema() throws  SQLException, PasswordReadException {
        //test che verifica se una mail non e'presente nel db, qui vi passerò una email non presente nel db
        LoginDaoJDBC loginDao= new LoginDaoJDBC();
        //false= email non presente
        assertEquals(false,loginDao.verifyAccount("pippo@esempio.com","pippo"));
    }
}