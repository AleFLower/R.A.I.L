package dao;

import entita.Account;
import entita.Role;
import utility.Printer;
import utility.UtilityAccesso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginDaoFileSystem implements LoginDao {

    private final Account account;

    private static final String PATH_FILE_UTENTI = "utenti.txt"; // percorso reale nel tuo progetto

    public LoginDaoFileSystem() {
        account = Account.getInitialAccount();
    }

    @Override
    public boolean verificaAccountNelSistema(String email, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_FILE_UTENTI))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] campi = line.split(",");

                if (campi.length != 5) continue; // ignora righe malformate

                String emailFile = campi[0].trim();
                String passwordFile = campi[1].trim();
                String username = campi[2].trim();
                String codiceUtente = campi[3].trim();
                String role = campi[4].trim();  //il ruolo lo leggo da file system
                Role ruolo = Role.valueOf(role.toUpperCase());

                if (emailFile.equals(email) && passwordFile.equals(password)) {
                    account.setCredenziali(username, codiceUtente,ruolo);
                    account.passaOnline();

                    UtilityAccesso.setNomeUtenteNelDatabase(username);
                    UtilityAccesso.setCodiceUtente(codiceUtente);
                    UtilityAccesso.setRole(ruolo);  //setto il ruolo per quell utente nella utility

                    return true;
                }
            }
        } catch (IOException e) {
            Printer.error("Error: " + e.getMessage());
        }
        return false;
    }
}
