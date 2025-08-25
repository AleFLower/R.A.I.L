package factory;

import dao.*;
import exception.PasswordReadException;

import java.sql.SQLException;

public abstract class DaoFactory {

    public abstract LoginDao getLoginDao() throws SQLException, PasswordReadException;
    public abstract RegistrationDao getRegistrationDao() throws SQLException, PasswordReadException;
    public abstract ActivefixedReportsDao getActivefixedDao() throws SQLException, PasswordReadException;
    public abstract SendReportDao getSendReportDao(AssetType assetType) throws SQLException, PasswordReadException;

    public static DaoFactory getFactory(TypeOfPersistence typeOfPersistence) {
        switch (typeOfPersistence) {
            case JDBC:
                return new JDBCDaoFactory();
            case FILESYSTEM:
                return new FSDaoFactory();
            case MEMORY:
                return new MemoryDaoFactory();
            default:
                throw new IllegalArgumentException("Persistence type not supported: " + typeOfPersistence);
        }
    }
}

