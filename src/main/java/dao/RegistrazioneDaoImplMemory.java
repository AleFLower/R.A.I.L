package dao;

import entita.Role;


public class RegistrazioneDaoImplMemory implements RegistrazioneDao {

    @Override
    public boolean registraUtente(String username, String email, String password)  {
        if (verificaEsistenzaUtente(username, email)) {
            // Salva nel "database in memoria" condiviso
            LoggedUsers.nomiUtenti.put(email, username);
            LoggedUsers.utentiFittizi.put(email, password);
            LoggedUsers.ruoliUtenti.put(email, Role.USER); //di deafult, ho un admin gia nel sistema, quindi tutti gli altri sono user
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean verificaEsistenzaUtente(String username, String email)  {

        //non so se lanciare, forse deve ritornare solo false per come è stato progettato?
        return !LoggedUsers.nomiUtenti.containsValue(username) || !LoggedUsers.utentiFittizi.containsKey(email);
    }
}
