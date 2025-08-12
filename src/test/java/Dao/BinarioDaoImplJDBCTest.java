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

class BinarioDaoImplJDBCTest {

    SendTrackReportDaoJDBC binarioDaoImpl;

    @Test
    void saveBinarioNonPresenteNelDb() {
        // Test per salvare un binario che non è ancora nel DB
        try {
            AccessUtility.setUserCode("2"); // imposta codice utente valido
            binarioDaoImpl = new SendTrackReportDaoJDBC();
            RailwayAsset nuovoBinario = new Track("1", "Napoli", "rotaie usurate");
            binarioDaoImpl.sendRailwayAssetReport(nuovoBinario);
        } catch (ReportAlreadyExistsException | SQLException | PasswordReadException e) {
            // nessuna eccezione prevista qui
        } finally {
            assertEquals(0, binarioDaoImpl.getOutcome()); //mi aspetto che esito ritorni 0
        }
    }

    @Test
    void saveBinarioGiaPresenteNelDb() {
        // Test che verifica che il sistema rifiuti l'inserimento duplicato
        try {
            AccessUtility.setUserCode("999");
            binarioDaoImpl = new SendTrackReportDaoJDBC();
            RailwayAsset binarioDuplicato = new Track("2", "stazione termini", "corpo estraneo");
            binarioDaoImpl.sendRailwayAssetReport(binarioDuplicato);
        } catch (ReportAlreadyExistsException | SQLException | PasswordReadException e) {
            // l'eccezione è prevista in questo caso
        } finally {
            assertEquals(-1, binarioDaoImpl.getOutcome());  //mi aspetto che esito ritorni 1
        }
    }
}
