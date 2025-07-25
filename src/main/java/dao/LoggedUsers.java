package dao;

import entita.Role;

import java.util.HashMap;
import java.util.Map;

public class LoggedUsers {
    protected static final Map<String, String> utentiFittizi = new HashMap<>(); // email -> password
    protected static final Map<String, String> nomiUtenti = new HashMap<>();    // email -> username
    protected static final Map<String, Role> ruoliUtenti = new HashMap<>();     // email -> ruolo

    private LoggedUsers() {}

    static {
        // utenti di default
        utentiFittizi.put("mario@esempio.com", "1234");
        nomiUtenti.put("mario@esempio.com", "MarioRossi");
        ruoliUtenti.put("mario@esempio.com", Role.ADMIN);

        utentiFittizi.put("anna@esempio.com", "abcd");
        nomiUtenti.put("anna@esempio.com", "AnnaVerdi");
        ruoliUtenti.put("anna@esempio.com", Role.USER);
    }
}
