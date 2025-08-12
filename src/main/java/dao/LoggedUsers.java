package dao;

import model.Role;

import java.util.HashMap;
import java.util.Map;

public class LoggedUsers {
    // dati private static (incapsulati)
    private static final Map<String, String> users = new HashMap<>();
    private static final Map<String, String> usernames = new HashMap<>();
    private static final Map<String, Role> userRoles = new HashMap<>();

    private LoggedUsers(){}

    private static final String MARIO_EMAIL = "mario@esempio.com";
    private static final String ANNA_EMAIL = "anna@esempio.com";

    static {
        // di default crea uno user e un admin
        users.put(MARIO_EMAIL, "1234");
        usernames.put(MARIO_EMAIL, "MarioRossi");
        userRoles.put(MARIO_EMAIL, Role.ADMIN);

        users.put(ANNA_EMAIL, "abcd");
        usernames.put(ANNA_EMAIL, "AnnaVerdi");
        userRoles.put(ANNA_EMAIL, Role.USER);
    }
    // metodi statici per leggere dati (non espongo le mappe)
    public static boolean isValidUser(String email, String password) {
        return users.containsKey(email) && users.get(email).equals(password);
    }

    public static String getUsername(String email) {
        return usernames.get(email);
    }

    public static Role getUserRole(String email) {
        return userRoles.get(email);
    }
    public static  boolean userExistsByUsername(String username) {
        return usernames.containsValue(username);
    }

    public static boolean userExistsByEmail(String email) {
        return users.containsKey(email);
    }

    public static void addUser(String username, String email, String password, Role role) {
        usernames.put(email, username);
        users.put(email, password);
        userRoles.put(email, role);
    }
}

