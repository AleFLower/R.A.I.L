package Dao;

import dao.SendTrackReportDaoJDBC;
import exception.PasswordReadException;
import exception.ReportAlreadyExistsException;
import model.Track;
import model.RailwayAsset;
import org.junit.jupiter.api.Test;
import utility.AccessUtility;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TrackDaoImplJDBCTest {

    SendTrackReportDaoJDBC trackDaoImpl;

    @Test
    void saveTrackNotInDB() {
        // Test per salvare un binario che non è ancora nel DB
        try {
            AccessUtility.setUserCode("2"); // imposta codice utente valido
            trackDaoImpl = new SendTrackReportDaoJDBC();
            RailwayAsset newTrack = new Track("1", "Milano centrale", "rotaie usurate");
            trackDaoImpl.sendRailwayAssetReport(newTrack);
        } catch (ReportAlreadyExistsException | SQLException | PasswordReadException e) {
            // nessuna eccezione prevista qui
        } finally {
            assertEquals(0, trackDaoImpl.getOutcome()); //mi aspetto che esito ritorni 0
        }
    }

    @Test
    void saveTrackAlreadyInDB() {
        // Test che verifica che il sistema rifiuti l'inserimento duplicato
        try {
            AccessUtility.setUserCode("999");
            trackDaoImpl = new SendTrackReportDaoJDBC();
            RailwayAsset duplicateTrack = new Track("2", "stazione termini", "corpo estraneo");
            trackDaoImpl.sendRailwayAssetReport(duplicateTrack);
        } catch (ReportAlreadyExistsException | SQLException | PasswordReadException e) {
            // l'eccezione è prevista in questo caso
        } finally {
            assertEquals(-1, trackDaoImpl.getOutcome());  //mi aspetto che esito ritorni 1
        }
    }
}
