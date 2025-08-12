package dao;

import exception.ReportAlreadyExistsException;
import model.RailwayAsset;
import model.LevelCrossing;
import utility.AccessUtility;

public class SendLevelCrossingReportDaoMemory implements SendReportDao {

    @Override
    public void sendRailwayAssetReport(RailwayAsset instance) throws ReportAlreadyExistsException {
        LevelCrossing levelCrossing = new LevelCrossing(instance.getAssetInfo(), instance.getLocation(),instance.getIssue());
        InMemoryReportArchive.sendLevelCrossingReport(levelCrossing, AccessUtility.getUserCode());
    }

}


