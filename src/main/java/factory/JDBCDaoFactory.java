package factory;

import dao.*;
import exception.PasswordReadException;

import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {


    @Override
    public SendReportDao getSendReportDao(AssetType assetType) throws SQLException, PasswordReadException {
        return new SendReportDaoJDBC();
    }

    @Override
    public LoginDao getLoginDao() throws SQLException, PasswordReadException {
        return new LoginDaoJDBC();
    }

    @Override
    public RegistrationDao getRegistrationDao() throws SQLException, PasswordReadException {
        return new RegistrationDaoJDBC();
    }

    @Override
    public ActivefixedReportsDao getActivefixedDao() throws SQLException, PasswordReadException {
        return new ActivefixedReportsDaoJDBC();
    }

}
