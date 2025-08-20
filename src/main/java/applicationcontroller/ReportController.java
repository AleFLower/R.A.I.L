package applicationcontroller;

import bean.ReportBean;
import dao.*;
import exception.PasswordReadException;
import exception.NoLoginPerformedException;
import exception.ReportAlreadyExistsException;
import exception.ReportTypeException;
import model.RailwayAsset;
import factory.DaoFactory;
import factory.RailwayAssetFactory;
import factory.AssetType;
import factory.TypeOfPersistence;
import utility.AccessUtility;

import java.io.IOException;
import java.sql.SQLException;

public class ReportController {

    private AssetType assetType;
    private RailwayAsset railwayAsset;
    private AccountController accountController = new AccountController();


    public ReportController(ReportBean reportBean) throws SQLException, PasswordReadException, ReportAlreadyExistsException, NoLoginPerformedException, ReportTypeException, IOException {

        this.assetType = reportBean.getType();

        verifyAccountState();

        RailwayAssetFactory railwayAssetFactory=new RailwayAssetFactory();
        railwayAsset = railwayAssetFactory.createAsset(reportBean);

        sendReport(railwayAsset, reportBean.getTypeOfPersistence());

        notifyAdmin();
    }

    private void verifyAccountState() throws NoLoginPerformedException {

        if (assetType == AssetType.TRACK && accountController.getAccountState().equals("OFFLINE") ) {
            throw new NoLoginPerformedException("To report a track you must be logged in");
        }
    }

    private void notifyAdmin() {
        String username = AccessUtility.getUsername();
        username = (username != null) ? username : "Unknown user";
        NotificationController notificationController = new NotificationController();
        notificationController.addNotification(username + " has reported a " + assetType);
    }

    private void sendReport(RailwayAsset railwayAsset, TypeOfPersistence typeOfPersistence) throws SQLException, PasswordReadException, ReportAlreadyExistsException, IOException {

        DaoFactory factoryDao = DaoFactory.getFactory(typeOfPersistence);

        SendReportDao sendreportDao = factoryDao.getSendAssetDao(railwayAsset.getAssetType());
        sendreportDao.sendRailwayAssetReport(railwayAsset);
    }
}
