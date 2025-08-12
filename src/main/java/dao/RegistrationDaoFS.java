package dao;

import model.Role;

import java.io.*;

public class RegistrationDaoFS implements RegistrationDao {

    private static final String PATH_FILE_UTENTI = "users.txt"; // percorso del file utenti

    @Override
    public boolean registrateUser(String username, String email, String password)
            throws IOException {

        if (verifyUserExistance(username, email)) {
            String userCode = String.valueOf(generateUserCode());

            // Scrive su file aggiungendo una nuova riga
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_FILE_UTENTI, true))) {
                writer.write(email + "," + password + "," + username + "," + userCode + "," + Role.USER);
                writer.newLine();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean verifyUserExistance(String username, String email)
            throws IOException {

        File file = new File(PATH_FILE_UTENTI);
        if (!file.exists()) {
            return true; // Se il file non esiste, non c'è nessun utente
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 4) continue;

                String emailFile = fields[0].trim();
                String usernameFile = fields[2].trim();

                //non so se lanciarle, al massimo ritorna false piu che lanciare queste eccezioni
                if (emailFile.equalsIgnoreCase(email) && usernameFile.equalsIgnoreCase(username)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int generateUserCode() throws IOException {
        File file = new File(PATH_FILE_UTENTI);
        int maxCode = 0;

        if (!file.exists()) {
            return 1; // primo utente
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
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
        }

        return maxCode + 1;
    }

}
