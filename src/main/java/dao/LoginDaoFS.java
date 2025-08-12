package dao;

import model.Account;
import model.Role;
import utility.Printer;
import utility.AccessUtility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginDaoFS implements LoginDao {

    private final Account account;

    private static final String PATH_FILE_UTENTI = "users.txt"; // percorso reale nel tuo progetto

    public LoginDaoFS() {
        account = Account.getInitialAccount();
    }

    @Override
    public boolean verifyAccount(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_FILE_UTENTI))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length != 5) continue; // ignora righe malformate

                String emailFile = fields[0].trim();
                String passwordFile = fields[1].trim();
                String username = fields[2].trim();
                String userCode = fields[3].trim();
                String role = fields[4].trim();  //il ruolo lo leggo da file system
                Role userRole = Role.valueOf(role.toUpperCase());

                if (emailFile.equals(email) && passwordFile.equals(password)) {
                    account.setCredentials(username, userCode,userRole);
                    account.goOnline();

                    AccessUtility.setUsername(username);
                    AccessUtility.setUserCode(userCode);
                    AccessUtility.setRole(userRole);  //setto il ruolo per quell utente nella utility

                    return true;
                }
            }
        } catch (IOException e) {
            Printer.error("Error: " + e.getMessage());
        }
        return false;
    }
}
