package dao;

import eccezioni.UtenteEsistenteException;
import java.sql.SQLException;


public class RegistrazioneDaoImplMemory implements RegistrazioneDao {

    @Override
    public boolean registraUtente(String username, String email, String password) throws SQLException, UtenteEsistenteException {
        if (verificaEsistenzaUtente(username, email)) {
            // Salva nel "database in memoria" condiviso
            LoggedUsers.nomiUtenti.put(email, username);
            LoggedUsers.utentiFittizi.put(email, password);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean verificaEsistenzaUtente(String username, String email) throws UtenteEsistenteException {
        if (LoggedUsers.nomiUtenti.containsValue(username)) {
            throw new UtenteEsistenteException("Username già esistente");
        }
        if (LoggedUsers.utentiFittizi.containsKey(email)) {
            throw new UtenteEsistenteException("Email già esistente");
        }
        return true;
    }
}
