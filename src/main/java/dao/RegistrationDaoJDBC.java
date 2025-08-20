package dao;

import exception.PasswordReadException;
import exception.UserAlreadyExistsException;
import model.Role;
import queries.AccessQueries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDaoJDBC implements RegistrationDao {

    private Connection connection=null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    public RegistrationDaoJDBC() throws SQLException, PasswordReadException {

        connection= DbConnection.getInstance();
    }

    @Override
    public boolean registrateUser(String username, String email, String password) throws SQLException, UserAlreadyExistsException {

        if(verifyUserExistance(username,email)){
            preparedStatement=connection.prepareStatement(AccessQueries.insertUserIntoSystem());
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,username);
            preparedStatement.setString(4, Role.USER.toString());
            preparedStatement.executeUpdate();
        }else {

            return false;
        }
        return true;
    }
    @Override
    public boolean verifyUserExistance(String username, String email) throws UserAlreadyExistsException, SQLException {
        preparedStatement = connection.prepareStatement(AccessQueries.checkIfUsernameExists());
        preparedStatement.setString(1, username);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.isBeforeFirst()) {

            return false;
        }else{
            preparedStatement=connection.prepareStatement(AccessQueries.checkIfEmailExists());
            preparedStatement.setString(1,email);
            resultSet=preparedStatement.executeQuery();

            return !resultSet.isBeforeFirst();
        }
    }
}
