package dao;

import java.util.HashMap;
import java.util.Map;

public class LoggedUsers {
    public static final Map<String, String> utentiFittizi = new HashMap<>(); // email -> password
    public static final Map<String, String> nomiUtenti = new HashMap<>();    // email -> username

    static {
        // utenti di default
        utentiFittizi.put("mario@esempio.com", "1234");
        nomiUtenti.put("mario@esempio.com", "MarioRossi");

        utentiFittizi.put("anna@esempio.com", "abcd");
        nomiUtenti.put("anna@esempio.com", "AnnaVerdi");
    }
}
