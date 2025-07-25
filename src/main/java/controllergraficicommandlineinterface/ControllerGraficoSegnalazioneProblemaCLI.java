package controllergraficicommandlineinterface;

import cli.PaginaSegnalazioneProblemaCli;
import utility.UtilityAccesso;

import java.io.IOException;

public class ControllerGraficoSegnalazioneProblemaCLI {
    private final PaginaSegnalazioneProblemaCli paginaSegnalazioneProblemaCli = new PaginaSegnalazioneProblemaCli();

    public void mostraSceltaSegnalazione() throws IOException {
        int scelta = paginaSegnalazioneProblemaCli.mostraProblemiChePossonoEssereSegnalati();

        switch (scelta) {
            case 1 -> {
                ControllerGraficoPaginaSegnalazionePassaggioLivelloCli controllerPL =
                        new ControllerGraficoPaginaSegnalazionePassaggioLivelloCli(UtilityAccesso.getPersistence());
                controllerPL.mostraPaginaSegnalazione();
            }
            case 2 -> {
                ControllerGraficoPagineSegnalazioneBinarioCli controllerBinario =
                        new ControllerGraficoPagineSegnalazioneBinarioCli();
                controllerBinario.mostraPaginaSegnalazione();
            }
            default -> {
                ControllerGraficoHome controllerHome = new ControllerGraficoHome();
                controllerHome.mostraHome();
            }
        }
    }
}
