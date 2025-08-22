package Dao;

import dao.SendReportDaoJDBC;
import exception.PasswordReadException;
import exception.ReportAlreadyExistsException;
import model.Track;
import model.RailwayAsset;
import org.junit.jupiter.api.Test;
import utility.AccessUtility;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

class TrackDaoImplJDBCTest {

    SendReportDaoJDBC trackDaoImpl;

    @Test
    void saveTrackNotInDB() {
        try {
            AccessUtility.setUserCode("2");
            RailwayAsset newTrack = new Track("1", "Milano centrale", "rotaie usurate");
            trackDaoImpl.sendRailwayAssetReport(newTrack);
        } catch (ReportAlreadyExistsException | SQLException | PasswordReadException e) {
            //do nothing in test
        } finally {
            assertEquals(0, trackDaoImpl.getOutcome());
        }
    }

    @Test
    void saveTrackAlreadyInDB() {

        try {
            AccessUtility.setUserCode("999");
            RailwayAsset duplicateTrack = new Track("2", "stazione termini", "corpo estraneo");
            trackDaoImpl.sendRailwayAssetReport(duplicateTrack);
        } catch (ReportAlreadyExistsException | SQLException | PasswordReadException e) {
            //do nothing in test
        } finally {
            assertEquals(-1, trackDaoImpl.getOutcome());  //mi aspetto che esito ritorni 1
        }
    }
}
