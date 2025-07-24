package dao;

import java.util.HashMap;
import java.util.Map;

public class LoggedUsers {
    protected static final Map<String, String> utentiFittizi = new HashMap<>(); // email -> password
    protected static final Map<String, String> nomiUtenti = new HashMap<>();    // email -> username

    private LoggedUsers(){}

    static {
        // utenti di default
        utentiFittizi.put("mario@esempio.com", "1234");
        nomiUtenti.put("mario@esempio.com", "MarioRossi");

        utentiFittizi.put("anna@esempio.com", "abcd");
        nomiUtenti.put("anna@esempio.com", "AnnaVerdi");
    }
}
