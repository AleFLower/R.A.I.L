package dao;

import exception.ReportAlreadyExistsException;
import model.Track;
import model.RailwayAsset;
import utility.AccessUtility;

public class SendTrackReportDaoMemory implements SendReportDao {
    private int outcome;

    //questo metodo non viene chiamato
    @Override
    public void sendRailwayAssetReport(RailwayAsset instance) throws ReportAlreadyExistsException {

        Track track = new Track(instance.getAssetInfo(),instance.getLocation(), instance.getIssue());
        InMemoryReportArchive.sendTrackReport(track, AccessUtility.getUserCode());
        outcome = 0;
    }

    public int getOutcome() {
        return this.outcome;
    }

}


