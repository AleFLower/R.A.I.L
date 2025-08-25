package dao;

import model.Role;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private final Map<String, String> users = new HashMap<>();
    private final Map<String, String> usernames = new HashMap<>();
    private final Map<String, Role> userRoles = new HashMap<>();

    public UserRepository() {
       //default users
        addUser("admin", "admin@example.com", "admin123", Role.ADMIN);
        addUser("user", "user@example.com", "user123", Role.USER);
    }

    public boolean isValidUser(String email, String password) {
        return users.containsKey(email) && users.get(email).equals(password);
    }

    public String getUsername(String email) {
        return usernames.get(email);
    }

    public Role getUserRole(String email) {
        return userRoles.get(email);
    }

    public boolean userExistsByUsername(String username) {
        return usernames.containsValue(username);
    }

    public boolean userExistsByEmail(String email) {
        return users.containsKey(email);
    }

    public void addUser(String username, String email, String password, Role role) {
        usernames.put(email, username);
        users.put(email, password);
        userRoles.put(email, role);
    }
}


