package dao;

import entita.Account;
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
            //momentaneo
            String codiceFittizio = Integer.toString(email.hashCode());

            account.setCredenziali(nomeUtente, codiceFittizio);
            account.passaOnline();

            UtilityAccesso.setNomeUtenteNelDatabase(nomeUtente);
            UtilityAccesso.setCodiceUtente(codiceFittizio);
            return true;
        }
        return false;
    }
}

