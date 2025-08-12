package dao;

import exception.ReportAlreadyExistsException;
import model.RailwayAsset;
import model.LevelCrossing;
import utility.AccessUtility;

public class SendLevelCrossingReportDaoMemory implements SendReportDao {

    private final ReportRepository reportRepository;

    public SendLevelCrossingReportDaoMemory(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public void sendRailwayAssetReport(RailwayAsset instance) throws ReportAlreadyExistsException {
        LevelCrossing levelCrossing = new LevelCrossing(instance.getAssetInfo(), instance.getLocation(), instance.getIssue());
        reportRepository.sendLevelCrossingReport(levelCrossing, AccessUtility.getUserCode());
    }
}
