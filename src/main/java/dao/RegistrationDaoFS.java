package dao;

import model.Role;
import model.AccountData;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class RegistrationDaoFS implements RegistrationDao {

    private static final String PATH_FILE_UTENTI = "users.ser";


    @Override
    @SuppressWarnings("unchecked")
    public boolean registrateUser(String username, String email, String password) throws IOException {
        if (verifyUserExistance(username, email)) {
            List<AccountData> users = new ArrayList<>();
            File file = new File(PATH_FILE_UTENTI);

            if (!file.exists() && file.length() > 0) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    users = (List<AccountData>) ois.readObject();
                } catch (ClassNotFoundException e) {
                    throw new IOException("Error while reading file", e);
                }
            }

            String userCode = String.valueOf(generateUserCode(users));

            users.add(new AccountData(email, username, password, userCode, Role.USER));

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH_FILE_UTENTI))) {
                oos.writeObject(users);
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean verifyUserExistance(String username, String email) throws IOException {
        File file = new File(PATH_FILE_UTENTI);

        if (!file.exists() || file.length() == 0) {
            return true; // se il file non esiste o è vuoto, nessun utente
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<AccountData> users = (List<AccountData>) ois.readObject();

            for (AccountData u : users) {
                if (u.getEmail().equalsIgnoreCase(email) && u.getUsername().equalsIgnoreCase(username)) {
                    return false;
                }
            }
        } catch (ClassNotFoundException e) {
            throw new IOException("Error while reading file", e);
        }

        return true;
    }

    private int generateUserCode(List<AccountData> users) {
        int maxCode = 0;
        for (AccountData u : users) {
            try {
                int code = Integer.parseInt(u.getUserCode());
                if (code > maxCode) maxCode = code;
            } catch (NumberFormatException ignored) {
                // Ignore non-numeric userCode values because some entries may have invalid codes
            }
        }
        return maxCode + 1;
    }
}
