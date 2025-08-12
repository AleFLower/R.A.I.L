package factory;

import dao.*;
import exception.PasswordReadException;

import java.sql.SQLException;

public class MemoryDaoFactory extends DaoFactory {

    @Override
    public SendReportDao getSendAssetDao(TypeOfPersistence typeOfPersistence, AssetType assetType) throws SQLException, PasswordReadException {
        // Restituisci il DAO corretto in base al tipo di entità e tipo di persistenza
            switch (assetType) {
                case TRACK:
                    return new SendTrackReportDaoMemory();
                case LEVELCROSSING:
                    return new SendLevelCrossingReportDaoMemory();
                default:
                    throw new IllegalArgumentException("Asset type not supported: " + assetType);
            }
    }


    @Override
    public LoginDao getLoginDao() {
        return new LoginDaoMemory();
    }

    @Override
    public RegistrationDao getRegistrationDao() {
        return new RegistrationDaoMemory();
    }

    @Override
    public ActiveResolvedReportsDao getActiveResolvedDao() {
        return new ActiveResolvedReportsDaoMemory();
    }
}
