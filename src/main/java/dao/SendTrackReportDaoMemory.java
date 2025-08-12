package dao;

import exception.ReportAlreadyExistsException;
import model.Track;
import model.RailwayAsset;
import utility.AccessUtility;

public class SendTrackReportDaoMemory implements SendReportDao {

    private final ReportRepository reportRepository;
    private int outcome;

    public SendTrackReportDaoMemory(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public void sendRailwayAssetReport(RailwayAsset instance) throws ReportAlreadyExistsException {
        Track track = new Track(instance.getAssetInfo(), instance.getLocation(), instance.getIssue());
        reportRepository.sendTrackReport(track, AccessUtility.getUserCode());
        outcome = 0;
    }

    public int getOutcome() {
        return this.outcome;
    }
}
