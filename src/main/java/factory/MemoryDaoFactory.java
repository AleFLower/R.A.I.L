package factory;

import dao.*;

public class MemoryDaoFactory extends DaoFactory {

    private static final UserRepository userRepository = new UserRepository();
    private static final ReportRepository reportRepository = new ReportRepository();

    @Override
    public SendReportDao getSendAssetDao(TypeOfPersistence typeOfPersistence, AssetType assetType) {
            switch (assetType) {
                case TRACK:
                    return new SendTrackReportDaoMemory(reportRepository);
                case LEVELCROSSING:
                    return new SendLevelCrossingReportDaoMemory(reportRepository);
                default:
                    throw new IllegalArgumentException("Asset type not supported: " + assetType);
            }
    }


    @Override
    public LoginDao getLoginDao() {
        return new LoginDaoMemory(userRepository);
    }

    @Override
    public RegistrationDao getRegistrationDao() {
        return new RegistrationDaoMemory(userRepository);
    }

    @Override
    public ActiveResolvedReportsDao getActiveResolvedDao() {
        return new ActiveResolvedReportsDaoMemory(reportRepository);
    }
}
