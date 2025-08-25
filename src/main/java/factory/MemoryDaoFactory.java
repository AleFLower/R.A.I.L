package factory;

import dao.*;

public class MemoryDaoFactory extends DaoFactory {

    private static final UserRepository userRepository = new UserRepository();
    private static final ReportRepository reportRepository = new ReportRepository();

    @Override
    public SendReportDao getSendReportDao(AssetType assetType) {
            return new SendReportDaoMemory(reportRepository);
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
    public ActivefixedReportsDao getActivefixedDao() {
        return new ActivefixedReportsDaoMemory(reportRepository);
    }
}
