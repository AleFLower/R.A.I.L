package dao;

import entita.Account;
import entita.Role;
import utility.UtilityAccesso;


public class LoginDaoMemory implements LoginDao {
    private final Account account;

    public LoginDaoMemory() {
        account = Account.getInitialAccount();
    }

    @Override
    public boolean verificaAccountNelSistema(String email, String password) {
        if (LoggedUsers.utentiFittizi.containsKey(email) &&
                LoggedUsers.utentiFittizi.get(email).equals(password)) {

            String nomeUtente = LoggedUsers.nomiUtenti.get(email);
            Role ruoloUtente = LoggedUsers.ruoliUtenti.get(email); // nuovo
            String codiceFittizio = Integer.toString(email.hashCode());  //genero a caso con un hash il codice utente

            account.setCredenziali(nomeUtente, codiceFittizio, ruoloUtente);
            account.passaOnline();

            UtilityAccesso.setNomeUtenteNelDatabase(nomeUtente);
            UtilityAccesso.setCodiceUtente(codiceFittizio);
            UtilityAccesso.setRole(ruoloUtente);

            return true;
        }
        return false;
    }

}

