package factory;

import dao.*;
import exception.PasswordReadException;

import java.sql.SQLException;

public class FSDaoFactory extends DaoFactory {

    //for future implementations
    @Override
    public LoginDao getLoginDao() throws SQLException, PasswordReadException {
        return new LoginDaoFS();
    }

    @Override
    public RegistrationDao getRegistrationDao() throws SQLException, PasswordReadException {
        return new RegistrationDaoFS();
    }

    @Override
    public ActiveResolvedReportsDao getActiveResolvedDao() throws SQLException, PasswordReadException {
        return new ActiveResolvedReportsDaoFS();
    }


    public SendReportDao getSendAssetDao(TypeOfPersistence typeOfPersistence, AssetType assetType) throws SQLException, PasswordReadException {
        // Restituisci il DAO corretto in base al tipo di entità e tipo di persistenza
        if (typeOfPersistence == TypeOfPersistence.FILESYSTEM) {
            switch (assetType) {
                case TRACK:
                    return new SendTrackReportDaoFS();
                case LEVELCROSSING:
                    return new SendLevelCrossingReportDaoFS();
                default:
                    throw new IllegalArgumentException("Asset type not supported: " + assetType);
            }
        }
        throw new IllegalArgumentException("Persistence type not supported: " + typeOfPersistence);
    }
}
