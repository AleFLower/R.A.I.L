package dao;

import model.Account;
import model.Role;
import utility.AccessUtility;


public class LoginDaoMemory implements LoginDao {
    private final UserRepository userRepository;
    private final Account account;

    public LoginDaoMemory(UserRepository userRepository) {
        this.userRepository = userRepository;
        account = Account.getInitialAccount();
    }

    @Override
    public boolean verifyAccount(String email, String password) {
        if (userRepository.isValidUser(email, password)) {
            String username = userRepository.getUsername(email);
            Role userRole = userRepository.getUserRole(email);
            String userCode = Integer.toString(email.hashCode());

            account.setCredentials(username, userCode, userRole);
            account.goOnline();

            AccessUtility.setUsername(username);
            AccessUtility.setUserCode(userCode);
            AccessUtility.setRole(userRole);

            return true;
        }
        return false;
    }
}


