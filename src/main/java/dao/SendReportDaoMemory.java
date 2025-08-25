package dao;

import exception.ReportAlreadyExistsException;
import model.RailwayAsset;
import utility.AccessUtility;


public class SendReportDaoMemory implements SendReportDao{

    private final ReportRepository reportRepository;
    private int outcome;

    public SendReportDaoMemory(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    @Override
    public void sendRailwayAssetReport(RailwayAsset railwayAsset) throws ReportAlreadyExistsException {
       reportRepository.sendReport(railwayAsset,AccessUtility.getUserCode());
    }

    public int getOutcome() {
        return this.outcome;
    }
}
