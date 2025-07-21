package dao;

import eccezioni.ErroreLetturaPasswordException;
import eccezioni.SegnalazioneGiaAvvenutaException;
import entita.EntitaFerroviaria;
import java.io.IOException;
import java.sql.SQLException;

public interface EntitaFerroviariaDao {
    //interfaccia che mette a disposizione ai dao cosa possono fare se un utente vuole slavare in locale
    //o nel db un entità stradale
    void saveEntitaStradale(EntitaFerroviaria instance) throws SQLException, SegnalazioneGiaAvvenutaException, ErroreLetturaPasswordException, IOException;
}
