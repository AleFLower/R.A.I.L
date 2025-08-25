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
    public ActivefixedReportsDao getActivefixedDao() {
        return new ActivefixedReportsDaoFS();
    }


    public SendReportDao getSendReportDao(AssetType assetType)  {
           return new SendReportDaoFS();
    }
}
