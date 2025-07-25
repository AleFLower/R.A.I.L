package dao;

import eccezioni.UtenteEsistenteException;
import entita.Role;

import java.io.*;

public class RegistrazioneDaoFileSystem implements RegistrazioneDao {

    private static final String PATH_FILE_UTENTI = "utenti.txt"; // percorso del file utenti

    @Override
    public boolean registraUtente(String username, String email, String password)
            throws IOException, UtenteEsistenteException {

        if (verificaEsistenzaUtente(username, email)) {
            String codiceUtente = String.valueOf(generaCodiceUtenteIncrementale());

            // Scrive su file aggiungendo una nuova riga
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_FILE_UTENTI, true))) {
                writer.write(email + "," + password + "," + username + "," + codiceUtente + "," + Role.USER);
                writer.newLine();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean verificaEsistenzaUtente(String username, String email)
            throws UtenteEsistenteException, IOException {

        File file = new File(PATH_FILE_UTENTI);
        if (!file.exists()) {
            return true; // Se il file non esiste, non c'è nessun utente
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] campi = line.split(",");
                if (campi.length < 4) continue;

                String emailFile = campi[0].trim();
                String usernameFile = campi[2].trim();

                if (emailFile.equalsIgnoreCase(email)) {
                    throw new UtenteEsistenteException("Email già esistente");
                }
                if (usernameFile.equalsIgnoreCase(username)) {
                    throw new UtenteEsistenteException("Username già esistente");
                }
            }
        }
        return true;
    }

    private int generaCodiceUtenteIncrementale() throws IOException {
        File file = new File(PATH_FILE_UTENTI);
        int maxCodice = 0;

        if (!file.exists()) {
            return 1; // primo utente
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] campi = line.split(",");
                if (campi.length < 4) continue;

                try {
                    int codice = Integer.parseInt(campi[3].trim());
                    if (codice > maxCodice) {
                        maxCodice = codice;
                    }
                } catch (NumberFormatException ignored) {
                    // ignora righe con codice non numerico
                }
            }
        }

        return maxCodice + 1;
    }

}
