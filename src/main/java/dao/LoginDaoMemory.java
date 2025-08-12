package dao;

import model.Account;
import model.Role;
import utility.AccessUtility;


public class LoginDaoMemory implements LoginDao {
    private final Account account;

    public LoginDaoMemory() {
        account = Account.getInitialAccount();
    }

    @Override
    public boolean verifyAccount(String email, String password) {
        if (LoggedUsers.isValidUser(email, password)) {
            String username = LoggedUsers.getUsername(email);
            Role userRole = LoggedUsers.getUserRole(email);
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

