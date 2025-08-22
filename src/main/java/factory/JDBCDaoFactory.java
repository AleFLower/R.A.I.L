package factory;

import dao.*;
import exception.PasswordReadException;

import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    //forse un po ridondante? Mi devo fermare prima a controllare typeOfPersistence? Qui è ovvio che livello di persistenza
    //è jdbc
    @Override
    public SendReportDao getSendAssetDao(AssetType assetType) throws SQLException, PasswordReadException {

            switch (assetType) {
                case TRACK:
                    return new SendTrackReportDaoJDBC();
                case LEVELCROSSING:
                    return new SendLevelCrossingReportDaoJDBC();
                default:
                    throw new IllegalArgumentException("Asset type not supported: " + assetType);
            }
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
