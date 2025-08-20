package factory;

import dao.*;

public class FSDaoFactory extends DaoFactory {


    @Override
    public LoginDao getLoginDao()  {
        return new LoginDaoFS();
    }

    @Override
    public RegistrationDao getRegistrationDao()  {
        return new RegistrationDaoFS();
    }

    @Override
    public ActiveResolvedReportsDao getActiveResolvedDao() {
        return new ActiveResolvedReportsDaoFS();
    }


    public SendReportDao getSendAssetDao(AssetType assetType)  {

            switch (assetType) {
                case TRACK:
                    return new SendTrackReportDaoFS();
                case LEVELCROSSING:
                    return new SendLevelCrossingReportDaoFS();
                default:
                    throw new IllegalArgumentException("Asset type not supported: " + assetType);
            }

    }
}
