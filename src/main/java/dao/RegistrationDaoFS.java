package dao;

import model.Role;

import java.io.*;
import java.util.*;

public class RegistrationDaoFS implements RegistrationDao {

    private static final String PATH_FILE_UTENTI = "users.ser"; // file serializzato

    @Override
    @SuppressWarnings("unchecked")
    public boolean registrateUser(String username, String email, String password)
            throws IOException {

        if (verifyUserExistance(username, email)) {
            List<String[]> users = new ArrayList<>();
            File file = new File(PATH_FILE_UTENTI);


            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    users = (List<String[]>) ois.readObject();
                } catch (ClassNotFoundException e) {
                    throw new IOException("Error while reading file", e);
                }
            }

            String userCode = String.valueOf(generateUserCode(users));

            users.add(new String[]{
                    email,
                    password,
                    username,
                    userCode,
                    Role.USER.name()
            });

            // Riscrive la lista su file
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
        if (!file.exists()) {
            return true; // Se il file non esiste, non c'è nessun utente
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<String[]> users = (List<String[]>) ois.readObject();

            for (String[] fields : users) {
                if (fields.length < 3) continue;
                String emailFile = fields[0].trim();
                String usernameFile = fields[2].trim();

                if (emailFile.equalsIgnoreCase(email) && usernameFile.equalsIgnoreCase(username)) {
                    return false;
                }
            }
        } catch (ClassNotFoundException e) {
            throw new IOException("Error while reading file", e);
        }

        return true;
    }

    private int generateUserCode(List<String[]> users) {
        int maxCode = 0;
        for (String[] fields : users) {
            if (fields.length < 4) continue;
            try {
                int code = Integer.parseInt(fields[3].trim());
                if (code > maxCode) {
                    maxCode = code;
                }
            } catch (NumberFormatException ignored) {
                // ignora righe con codice non numerico
            }
        }
        return maxCode + 1;
    }
}
