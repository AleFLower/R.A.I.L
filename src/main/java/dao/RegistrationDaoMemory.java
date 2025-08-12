package dao;

import model.Role;


public class RegistrationDaoMemory implements RegistrationDao {

    private final UserRepository userRepository;

    public RegistrationDaoMemory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean registrateUser(String username, String email, String password) {
        if (!verifyUserExistance(username, email)) {
            userRepository.addUser(username, email, password, Role.USER);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean verifyUserExistance(String username, String email) {
        // ritorna true se username o email ESISTONO già -> non posso registrare
        return userRepository.userExistsByUsername(username) || userRepository.userExistsByEmail(email);
    }
}

