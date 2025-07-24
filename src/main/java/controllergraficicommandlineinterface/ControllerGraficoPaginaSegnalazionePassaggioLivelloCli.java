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

public class ControllerGraficoPaginaSegnalazionePassaggioLivelloCli {
    private PaginaHome paginaHome=new PaginaHome();
    private String codicePL;
    private String problematica;
    private String localizzazione;

    private BeanSegnalaEntita beanVerificaDati;

    private TypeEntita typeEntita = TypeEntita.LEVELCROSSING;

    private TypeOfPersistence typeOfPersistence;

    public ControllerGraficoPaginaSegnalazionePassaggioLivelloCli(String codicePL, String localizzazione, String problematica, TypeOfPersistence tipoPersistenza){
        this.codicePL=codicePL;
        this.localizzazione=localizzazione;
        this.problematica = problematica;

        this.typeOfPersistence = tipoPersistenza;
    }
    public void inviaDatiAlBean() throws IOException {
        //qui invio i dati al bean
        beanVerificaDati =  new BeanSegnalaEntita(codicePL,localizzazione, problematica,typeEntita, typeOfPersistence);
        try {
            beanVerificaDati.controllaInputLevelCrossing();
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
            if(e instanceof SegnalazioneGiaAvvenutaException){
                Printer.error("Segnalazione gia avvenuta per quel passaggio a livello");
            }
            else Printer.error(e.getMessage());
            tornaAllaHome();
        }
    }
    private void tornaAllaHome() throws IOException {
        paginaHome.displayHomepage();
    }
}
