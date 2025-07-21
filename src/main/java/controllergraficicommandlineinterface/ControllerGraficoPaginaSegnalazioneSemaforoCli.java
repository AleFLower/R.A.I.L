package controllergraficicommandlineinterface;

import bean.BeanSegnalaEntita;
import cli.PaginaHome;
import controllerapplicativi.ControllerApplicativoSegnalazioneEntita;
import eccezioni.*;

import factory.TypeEntita;
import factory.TypeOfPersistence;
import utility.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class ControllerGraficoPaginaSegnalazioneSemaforoCli {
    private PaginaHome paginaHome=new PaginaHome();
    private String numeroSeriale;
    private String problematica;
    private String stazione;

    private BeanSegnalaEntita beanVerificaDati;

    private TypeEntita typeEntita = TypeEntita.SEMAFORO;

    private TypeOfPersistence typeOfPersistence;

    public ControllerGraficoPaginaSegnalazioneSemaforoCli(String numeroSeriale,String stazione,String problematica,TypeOfPersistence tipoPersistenza){
        this.numeroSeriale=numeroSeriale;
        this.stazione=stazione;
        this.problematica = problematica;

        this.typeOfPersistence = tipoPersistenza;
    }
    public void inviaDatiAlBean() throws IOException {
        //qui invio i dati al bean
        beanVerificaDati =  new BeanSegnalaEntita(numeroSeriale,stazione, problematica,typeEntita, typeOfPersistence);
        try {
            beanVerificaDati.controllaInputSemaforo();
            new ControllerApplicativoSegnalazioneEntita(beanVerificaDati);
            //se non c'e' stata nessuna eccezione vuol dire che la segnalazione e' avvenuta con successo
            //lo comunico all'utente e blocco i pulsanti per non far inviare la stessa segnalazione
            //in caso dovesse premere per sbaglio di nuovo il pulsante invia
            Printer.print("segnalazione avvenuta con successo\ntorna alla home =)\npremere qualsiasi tasto per tornare alla home: ");
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
            if(bufferedReader.readLine().length()>=1){
                tornaAllaHome();
            }
        }catch(LunghezzaInputException | TipoEntitaException | SegnalazioneGiaAvvenutaException | NessunAccessoEffettuatoException | SQLException | ErroreLetturaPasswordException |IOException e){
            Printer.error(e.getMessage());
            tornaAllaHome();
        }
    }
    private void tornaAllaHome() throws IOException {
        paginaHome.displayHomepage();
    }
}
