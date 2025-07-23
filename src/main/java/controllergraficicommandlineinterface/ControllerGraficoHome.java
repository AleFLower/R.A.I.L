package controllergraficicommandlineinterface;

import bean.BeanListeElementi;
import cli.PaginaHome;
import cli.PaginaSegnalazioneProblemaCli;
import cli.PaginaVisualizzazioneSegnalazioniAttiveCli;
import com.example.progettoispw.controllergrafici.TypeOfSegnalazione;
import controllerapplicativi.ControllerApplicativoTipoSegnalazione;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.NonEsistonoSegnalazioniException;
import utility.Printer;
import utility.UtilityAccesso;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerGraficoHome {

    public void segnalaProblema() throws IOException {
        PaginaSegnalazioneProblemaCli paginaSegnalazioneProblema = new PaginaSegnalazioneProblemaCli();
        paginaSegnalazioneProblema.mostraProblemiChePossonoEssereSegnalati();
    }

    public void visualizzaSegnalazioni() {
        try {

            if (UtilityAccesso.getAccount().getCodiceUtente() == null) {
                Printer.error("Devi effettuare il login per visualizzare le segnalazioni attive.");
                tornaAllaHome();
            }
            // Creo il bean specificando il tipo di segnalazioni da visualizzare
            BeanListeElementi bean = new BeanListeElementi(TypeOfSegnalazione.ATTIVE);

            // Passo il bean al controller applicativo per riempirlo
            new ControllerApplicativoTipoSegnalazione(bean, UtilityAccesso.getPersistence());

            // Passo il bean popolato alla view per la sola visualizzazione
            PaginaVisualizzazioneSegnalazioniAttiveCli view = new PaginaVisualizzazioneSegnalazioniAttiveCli();
            view.mostraSegnalazioniAttive(bean);

        } catch (SQLException | IOException | NonEsistonoSegnalazioniException | ErroreLetturaPasswordException e) {
            Printer.error("Errore durante il recupero delle segnalazioni attive: " + e.getMessage());

        }


    }

    private void tornaAllaHome() throws IOException {
        PaginaHome paginaHome=new PaginaHome();
        paginaHome.displayHomepage();
    }

}
