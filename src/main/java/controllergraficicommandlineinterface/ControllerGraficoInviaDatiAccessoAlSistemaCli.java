package controllergraficicommandlineinterface;

import bean.BeanLogin;
import cli.PaginaHome;
import controllerapplicativi.ControllerApplicativoLoginAlSistema;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.NonEsisteUtenteNelSistemaException;
import utility.Printer;
import utility.UtilityAccesso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class ControllerGraficoInviaDatiAccessoAlSistemaCli {
    private PaginaHome paginaHome=new PaginaHome();
    private String email;
    private String password;
    private BeanLogin beanAccessoUtente;
    public ControllerGraficoInviaDatiAccessoAlSistemaCli(String email, String password){
        this.email=email;
        this.password=password;
    }
    public void inviaDatiAlBean() throws IOException {
        beanAccessoUtente = new BeanLogin(email, password);
        //svolgo prima i controlli sulla email inserita dall'utente, verifico cioè se è sintatticamente corretta
        String controlliSintatticiEmail = beanAccessoUtente.svolgiControlli();
        //se l'email è sintatticamente corretta vado avanti altrimenti counico l'errore all'utente
        if (controlliSintatticiEmail == null) {
            //mando il bean al controller applicativo
            try {
                new ControllerApplicativoLoginAlSistema(beanAccessoUtente, UtilityAccesso.getPersistence());
                // se non si e' verificata nessuna eccezione vuol dire che l'accesso e' stato effettuato con successo
                Printer.print("accesso effettuato, premi qualsiasi tasto per tornare alla home");

                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
                if(!bufferedReader.readLine().isEmpty()){
                    tornaAllaHome();
                }
            }catch(SQLException | NonEsisteUtenteNelSistemaException | ErroreLetturaPasswordException| IOException e){
                if (e instanceof NonEsisteUtenteNelSistemaException) {
                    Printer.error("Credenziali non valide. L'utente con queste credenziali non esiste.");
                    Printer.print("Vuoi registrarti con queste credenziali? (s/n): ");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    String risposta = bufferedReader.readLine();
                    if (risposta.equalsIgnoreCase("s")) {
                        // Avvia il processo di registrazione se l'utente risponde "s"
                        Printer.print("Inserisci username: ");
                        String username = bufferedReader.readLine();
                        avviaRegistrazione(email, password,username);
                    } else {
                        // Altrimenti torna alla home
                        tornaAllaHome();
                    }
                }
                tornaAllaHome();
            }
        }else{
            Printer.error(controlliSintatticiEmail);
            tornaAllaHome();  //comunque sia torna alla home
        }

    }
    private void tornaAllaHome() throws IOException {
        paginaHome.displayHomepage();
    }


    private void avviaRegistrazione(String email,String password, String username)  {
        // Qui non creo il bean di registrazione, lo faccio nel controller grafico
        ControllerGraficoRegistrazioneCli controllerRegistrazione = new ControllerGraficoRegistrazioneCli(email,password,username);
        controllerRegistrazione.registraUtente();
    }
}
