package controllergraficicommandlineinterface;

import bean.BeanRegistrazione;
import controllerapplicativi.ControllerApplicativoRegistrazioneAlSistema;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.UtenteEsistenteException;
import factory.TypeOfPersistence;
import utility.Printer;
import utility.UtilityAccesso;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerGraficoRegistrazioneCli {
    private final String username;
    private final String email;
    private final String password;

    public ControllerGraficoRegistrazioneCli(String email, String password, String username) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void registraUtente() {
        try {
            // Creo il bean con i dati forniti
            BeanRegistrazione bean = new BeanRegistrazione(email,password,username);

            // Recupero il tipo di persistenza selezionata (JDBC, FILESYSTEM, ecc.)
            TypeOfPersistence persistence = UtilityAccesso.getPersistence();

            // Chiamo il controller applicativo
            new ControllerApplicativoRegistrazioneAlSistema(bean, persistence);

            Printer.print("Registrazione avvenuta con successo!");
        } catch (UtenteEsistenteException e) {
            Printer.error("Registrazione fallita: " + e.getMessage());
        } catch (ErroreLetturaPasswordException | SQLException | IOException e) {
            Printer.error("Errore tecnico durante la registrazione: " + e.getMessage());
        }
    }
}
