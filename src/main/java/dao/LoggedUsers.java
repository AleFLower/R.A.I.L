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
        final String EMAIL_MARIO = "mario@esempio.com";
        final String EMAIL_ANNA = "anna@esempio.com";

        // utenti di default
        utentiFittizi.put(EMAIL_MARIO, "1234");
        nomiUtenti.put(EMAIL_MARIO, "MarioRossi");
        ruoliUtenti.put(EMAIL_MARIO, Role.ADMIN);

        utentiFittizi.put(EMAIL_ANNA, "abcd");
        nomiUtenti.put(EMAIL_ANNA, "AnnaVerdi");
        ruoliUtenti.put(EMAIL_ANNA, Role.USER);
    }

}
