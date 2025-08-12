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
        if (LoggedUsers.users.containsKey(email) &&
                LoggedUsers.users.get(email).equals(password)) {

            String username = LoggedUsers.usernames.get(email);
            Role userRole = LoggedUsers.userRoles.get(email); // nuovo
            String userCode = Integer.toString(email.hashCode());  //genero a caso con un hash il codice utente

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

