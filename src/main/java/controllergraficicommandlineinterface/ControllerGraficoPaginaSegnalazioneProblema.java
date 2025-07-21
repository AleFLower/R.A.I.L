package controllergraficicommandlineinterface;

import cli.PaginaSegnalazioneSemaforoCli;
import java.io.IOException;


public class ControllerGraficoPaginaSegnalazioneProblema {
    public void segnalaSemaforo() throws IOException {
        PaginaSegnalazioneSemaforoCli paginaSegnalazioneSemaforo=new PaginaSegnalazioneSemaforoCli();
        paginaSegnalazioneSemaforo.inserisciInput();
    }
}
