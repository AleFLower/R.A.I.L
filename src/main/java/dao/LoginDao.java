package dao;

import java.sql.SQLException;

public interface LoginDao {
    boolean verifyAccount(String email, String password) throws SQLException;
}
