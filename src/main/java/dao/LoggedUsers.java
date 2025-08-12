package dao;

import model.Role;

import java.util.HashMap;
import java.util.Map;

public class LoggedUsers {
    protected static final Map<String, String> users = new HashMap<>(); // email -> password
    protected static final Map<String, String> usernames = new HashMap<>();    // email -> username
    protected static final Map<String, Role> userRoles = new HashMap<>();     // email -> ruolo

    private LoggedUsers() {}
    static {
        final String EMAIL_MARIO = "mario@esempio.com";
        final String EMAIL_ANNA = "anna@esempio.com";

        // utenti di default
        users.put(EMAIL_MARIO, "1234");
        usernames.put(EMAIL_MARIO, "MarioRossi");
        userRoles.put(EMAIL_MARIO, Role.ADMIN);

        users.put(EMAIL_ANNA, "abcd");
        usernames.put(EMAIL_ANNA, "AnnaVerdi");
        userRoles.put(EMAIL_ANNA, Role.USER);
    }

}
