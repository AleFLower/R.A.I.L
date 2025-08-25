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

class SendTrackDaoTest {

    SendReportDaoJDBC trackDaoImpl;

    @Test
    void saveTrackNotInDB() {
        try {
            trackDaoImpl = new SendReportDaoJDBC();
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
            trackDaoImpl = new SendReportDaoJDBC();
            AccessUtility.setUserCode("999");
            RailwayAsset duplicateTrack = new Track("2", "roma termini", "corpo estraneo sui binari");
            trackDaoImpl.sendRailwayAssetReport(duplicateTrack);
        } catch (ReportAlreadyExistsException | SQLException | PasswordReadException e) {
            //do nothing in test
        } finally {
            assertEquals(-1, trackDaoImpl.getOutcome());  //mi aspetto che esito ritorni 1
        }
    }
}
