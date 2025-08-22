package dao;

import model.Account;
import model.AccountData;
import utility.Printer;
import utility.AccessUtility;

import java.io.IOException;

import java.io.*;

import java.util.List;

public class LoginDaoFS implements LoginDao {

    private final Account account;
    private static final String PATH_FILE_UTENTI = "users.ser";

    public LoginDaoFS() {
        account = Account.getInstance();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean verifyAccount(String email, String password) {
        File file = new File(PATH_FILE_UTENTI);
        if (!file.exists() || file.length() == 0) return false;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<AccountData> users = (List<AccountData>) ois.readObject();

            for (AccountData u : users) {
                if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                    account.registerAccount(u.getUsername(), u.getUserCode(), u.getRole());
                    account.goOnline();
                    AccessUtility.setUsername(u.getUsername());
                    AccessUtility.setUserCode(u.getUserCode());
                    AccessUtility.setRole(u.getRole());
                    return true;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            Printer.error("Error: " + e.getMessage());
        }
        return false;
    }
}
