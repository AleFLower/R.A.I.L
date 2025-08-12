package factory;

import dao.*;
import exception.PasswordReadException;

import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    @Override
    public SendReportDao getSendAssetDao(TypeOfPersistence typeOfPersistence, AssetType assetType) throws SQLException, PasswordReadException {
        // Restituisci il DAO corretto in base al tipo di entità e tipo di persistenza
        if (typeOfPersistence == TypeOfPersistence.JDBC) {
            switch (assetType) {
                case TRACK:
                    return new SendTrackReportDaoJDBC();
                case LEVELCROSSING:
                    return new SendLevelCrossingReportDaoJDBC();
                default:
                    throw new IllegalArgumentException("Asset type not supported: " + assetType);
            }
        }
        throw new IllegalArgumentException("Persistence type not supported: " + typeOfPersistence);
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
    public ActiveResolvedReportsDao getActiveResolvedDao() throws SQLException, PasswordReadException {
        return new ActiveResolvedReportsDaoJDBC();
    }

}
