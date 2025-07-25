package Dao;

import dao.BinarioDaoImplJDBC;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.SegnalazioneGiaAvvenutaException;
import entita.Binario;
import entita.EntitaFerroviaria;
import org.junit.jupiter.api.Test;
import utility.UtilityAccesso;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class BinarioDaoImplJDBCTest {

    BinarioDaoImplJDBC binarioDaoImpl;

    @Test
    void saveBinarioNonPresenteNelDb() {
        // Test per salvare un binario che non è ancora nel DB
        try {
            UtilityAccesso.setCodiceUtente("2"); // imposta codice utente valido
            binarioDaoImpl = new BinarioDaoImplJDBC();
            EntitaFerroviaria nuovoBinario = new Binario("1", "Napoli", "rotaie usurate");
            binarioDaoImpl.saveEntitaStradale(nuovoBinario);
        } catch (SegnalazioneGiaAvvenutaException | SQLException | ErroreLetturaPasswordException e) {
            // nessuna eccezione prevista qui
        } finally {
            assertEquals(0, binarioDaoImpl.getEsito()); //mi aspetto che esito ritorni 0
        }
    }

    @Test
    void saveBinarioGiaPresenteNelDb() {
        // Test che verifica che il sistema rifiuti l'inserimento duplicato
        try {
            UtilityAccesso.setCodiceUtente("999");
            binarioDaoImpl = new BinarioDaoImplJDBC();
            EntitaFerroviaria binarioDuplicato = new Binario("2", "stazione termini", "corpo estraneo");
            binarioDaoImpl.saveEntitaStradale(binarioDuplicato);
        } catch (SegnalazioneGiaAvvenutaException | SQLException | ErroreLetturaPasswordException e) {
            // l'eccezione è prevista in questo caso
        } finally {
            assertEquals(-1, binarioDaoImpl.getEsito());  //mi aspetto che esito ritorni 1
        }
    }
}
