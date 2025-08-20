package dao;

import exception.PasswordReadException;
import exception.UserAlreadyExistsException;

import java.io.IOException;
import java.sql.SQLException;

public interface RegistrationDao {
    boolean registrateUser(String username, String email, String password) throws SQLException, UserAlreadyExistsException, IOException;
    boolean verifyUserExistance(String username, String email) throws UserAlreadyExistsException, SQLException, PasswordReadException, IOException;
}
