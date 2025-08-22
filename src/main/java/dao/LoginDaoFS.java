package dao;

import model.Account;
import model.Role;
import utility.Printer;
import utility.AccessUtility;

import java.io.IOException;

import java.io.*;
import java.util.*;

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
        if (!file.exists()) return false;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<String[]> users = (List<String[]>) ois.readObject();

            for (String[] fields : users) {
                if (fields.length != 5) continue;

                String emailFile = fields[0].trim();
                String passwordFile = fields[1].trim();
                String username = fields[2].trim();
                String userCode = fields[3].trim();
                String role = fields[4].trim();
                Role userRole = Role.valueOf(role.toUpperCase());

                if (emailFile.equals(email) && passwordFile.equals(password)) {
                    account.registerAccount(username, userCode, userRole);
                    account.goOnline();
                    AccessUtility.setUsername(username);
                    AccessUtility.setUserCode(userCode);
                    AccessUtility.setRole(userRole);
                    return true;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            Printer.error("Error: " + e.getMessage());
        }
        return false;
    }
}

