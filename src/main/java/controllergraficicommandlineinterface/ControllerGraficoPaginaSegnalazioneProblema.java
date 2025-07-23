package controllergraficicommandlineinterface;

import cli.PaginaSegnalazionePassaggioALivelloCli;
import java.io.IOException;


public class ControllerGraficoPaginaSegnalazioneProblema {
    public void segnalalevelCrossing() throws IOException {
        PaginaSegnalazionePassaggioALivelloCli paginaSegnalazionelevelCrossing=new PaginaSegnalazionePassaggioALivelloCli();
        paginaSegnalazionelevelCrossing.inserisciInput();
    }
}
