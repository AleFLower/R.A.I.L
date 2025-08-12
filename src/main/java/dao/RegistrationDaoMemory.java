package dao;

import model.Role;


public class RegistrationDaoMemory implements RegistrationDao {

    @Override
    public boolean registrateUser(String username, String email, String password) {
        if (!verifyUserExistance(username, email)) {
            LoggedUsers.addUser(username, email, password, Role.USER);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean verifyUserExistance(String username, String email) {
        // ritorna true se username o email ESISTONO già -> non posso registrare
        return LoggedUsers.userExistsByUsername(username) || LoggedUsers.userExistsByEmail(email);
    }
}

