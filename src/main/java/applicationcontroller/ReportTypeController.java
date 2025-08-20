package applicationcontroller;

import bean.ReportListBean;
import bean.ReportTrackBean;
import bean.ReportLevelCrossingBean;
import com.example.progettoispw.graphiccontroller.ReportType;
import dao.*;

import exception.PasswordReadException;
import exception.NoReportsFoundException;
import factory.AssetType;
import factory.DaoFactory;
import factory.TypeOfPersistence;
import model.RailwayAsset;
import utility.AccessUtility;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ReportTypeController {
    private int userCode;
    private ReportType reportType;
    private ActiveResolvedReportsDao activeResolvedReportsDao;


    public ReportTypeController(ReportListBean bean, TypeOfPersistence persistence) throws NoReportsFoundException, SQLException, PasswordReadException, IOException {
        userCode = Integer.parseInt(AccessUtility.getUserCode());
        reportType =bean.getReportType();

        addElements(bean,persistence);
    }
    private void addElements(ReportListBean bean, TypeOfPersistence persistence)
            throws SQLException, NoReportsFoundException, PasswordReadException, IOException {

        DaoFactory dao = DaoFactory.getFactory(persistence);
        activeResolvedReportsDao = dao.getActiveResolvedDao();

       List<RailwayAsset> reports = dao.getActiveResolvedDao().getReports(reportType);

        if(reports.isEmpty()) throw new NoReportsFoundException("You have not filed any reports");


        for(RailwayAsset asset: reports){
            if(asset.getAssetType().equals(AssetType.LEVELCROSSING)){
                bean.addLevelCrossingReports(new ReportLevelCrossingBean(asset.getAssetInfo(), asset.getLocation(), asset.getIssue(), asset.getState()));
            }
            else bean.addTrackReports(new ReportTrackBean(asset.getAssetInfo(), asset.getLocation(), asset.getIssue(), asset.getState()));
        }
    }
}