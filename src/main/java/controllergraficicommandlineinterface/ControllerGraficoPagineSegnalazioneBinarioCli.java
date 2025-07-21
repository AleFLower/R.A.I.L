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
    private String stazione;
    private String problematica;
    private String visibilità;

    private BeanSegnalaEntita beanVerificaDati;
    private TypeEntita typeEntita=TypeEntita.BINARIO;
    private TypeOfPersistence typeOfPersistence;


    public ControllerGraficoPagineSegnalazioneBinarioCli(String stazione, String problematica, String visibilità, TypeOfPersistence typeOfPersistence){
        this.stazione=stazione;
        this.problematica = problematica;
        this.visibilità = visibilità;
        this.typeOfPersistence = typeOfPersistence;
    }
    public void inviaDatiAlBean() throws IOException {
        try {
            beanVerificaDati=new BeanSegnalaEntita(visibilità,stazione, problematica,typeEntita,typeOfPersistence);
            //questi dati devono essere mandati al controller applicativo
            new ControllerApplicativoSegnalazioneEntita(beanVerificaDati);
            Printer.print("segnalazione avvenuta con successo\ntorna alla home =)\npremere qualsiasi tasto per tornare alla home: ");
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
            if(bufferedReader.readLine().length()>=1){
                tornaAllaHome();
            }
        }catch(SQLException| ErroreLetturaPasswordException | SegnalazioneGiaAvvenutaException | NessunAccessoEffettuatoException | TipoEntitaException e) {
            Printer.error(e.getMessage());
            tornaAllaHome();
        }

    }
    private void tornaAllaHome() throws IOException {
        paginaHome.displayHomepage();
    }
}
