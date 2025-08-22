package dao;

import exception.PasswordReadException;
import model.Account;
import model.Role;
import queries.AccessQueries;
import utility.AccessUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDaoJDBC implements LoginDao{

    private Connection connection=null;
    private PreparedStatement preparedStatement=null;
    private ResultSet resultSet=null;
    private Account account;
    public LoginDaoJDBC() throws SQLException, PasswordReadException {
        connection= DbConnection.getInstance();
        account=Account.getInstance();
    }

    public boolean verifyAccount(String email, String password) throws SQLException {

        preparedStatement=connection.prepareStatement(AccessQueries.checkEmailAndPassword());
        preparedStatement.setString(1,email);
        preparedStatement.setString(2,password);
        resultSet=preparedStatement.executeQuery();

        if(resultSet.isBeforeFirst()){
            resultSet.next();

            account.setCredentials(resultSet.getString("username"),Integer.toString(resultSet.getInt("codiceUtente")), Role.valueOf(resultSet.getString("role")));
            account.goOnline();

            AccessUtility.setUsername(resultSet.getString("username"));

            AccessUtility.setUserCode(Integer.toString(resultSet.getInt("codiceUtente")));
            AccessUtility.setRole(Role.valueOf(resultSet.getString("role")));  //prendo il role dal db

            return true;
        }

        return false;
    }
}
