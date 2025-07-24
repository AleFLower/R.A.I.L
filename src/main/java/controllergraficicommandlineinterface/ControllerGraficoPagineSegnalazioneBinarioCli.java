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

public class ControllerGraficoPagineSegnalazioneBinarioCli {

    private PaginaHome paginaHome=new PaginaHome();
    private String localizzazione;
    private String problematica;
    private String numeroBinario;

    private BeanSegnalaEntita beanVerificaDati;
    private TypeEntita typeEntita=TypeEntita.BINARIO;
    private TypeOfPersistence typeOfPersistence;


    public ControllerGraficoPagineSegnalazioneBinarioCli(String localizzazione, String problematica, String numeroBinario, TypeOfPersistence typeOfPersistence){
        this.localizzazione=localizzazione;
        this.problematica = problematica;
        this.numeroBinario = numeroBinario;
        this.typeOfPersistence = typeOfPersistence;
    }
    public void inviaDatiAlBean() throws IOException {
        try {
            beanVerificaDati=new BeanSegnalaEntita(numeroBinario,localizzazione, problematica,typeEntita,typeOfPersistence);
            //questi dati devono essere mandati al controller applicativo
            new ControllerApplicativoSegnalazioneEntita(beanVerificaDati);
            Printer.print("segnalazione avvenuta con successo\ntorna alla home =)\npremere qualsiasi tasto per tornare alla home: ");
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
            if(!bufferedReader.readLine().isEmpty()){
                tornaAllaHome();
            }
        }catch(SQLException| ErroreLetturaPasswordException | SegnalazioneGiaAvvenutaException | NessunAccessoEffettuatoException | TipoEntitaException e) {
            if(e instanceof SegnalazioneGiaAvvenutaException){
                Printer.error("Segnalazione gia avvenuta per quel binario");
            }
            else Printer.error(e.getMessage());
            tornaAllaHome();
        }

    }
    private void tornaAllaHome() throws IOException {
        paginaHome.displayHomepage();
    }
}
