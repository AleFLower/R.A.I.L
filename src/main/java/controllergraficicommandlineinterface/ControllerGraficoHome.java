package controllergraficicommandlineinterface;

import bean.BeanListeElementi;
import cli.PaginaHome;
import cli.PaginaVisualizzazioneSegnalazioniAttiveCli;
import com.example.progettoispw.controllergrafici.TypeOfSegnalazione;
import controllerapplicativi.ControllerApplicativoTipoSegnalazione;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.NonEsistonoSegnalazioniException;
import entita.Role;
import utility.Printer;
import utility.UtilityAccesso;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerGraficoHome {
    private final PaginaHome paginaHome;

    public ControllerGraficoHome() {
        this.paginaHome = new PaginaHome();
    }

    public void mostraHome() throws IOException {
        while (true) {
            int scelta = paginaHome.mostraMenuHome();

            switch (scelta) {
                case 1 -> {
                    if(UtilityAccesso.getRole() == Role.ADMIN) paginaHome.mostraMessaggio("Devi loggarti come user per segnalare un problema");
                    else segnalaProblema();
                }
                case 2 -> paginaHome.mostraFunzioneNonDisponibile("recensione");
                case 3 -> paginaHome.mostraFunzioneNonDisponibile("suggerimento funzionalità");
                case 4 -> gestisciAccesso();
                case 5 -> visualizzaSegnalazioniAttive();
                case 6 -> visualizzaSegnalazioniRisolte();
                case 7 -> esci();
                default -> paginaHome.mostraErroreInput();
            }
        }
    }

    private void segnalaProblema() throws IOException {
        ControllerGraficoSegnalazioneProblemaCLI controllerSegnalazione = new ControllerGraficoSegnalazioneProblemaCLI();
        controllerSegnalazione.mostraSceltaSegnalazione();
    }

    private void gestisciAccesso() throws IOException {
        if (UtilityAccesso.getCodiceUtente() == null)
            new ControllerGraficoLoginCli().accediAlSistema();
        else {
            ControllerGraficoLogoutCli logoutCli = new ControllerGraficoLogoutCli();
            logoutCli.logout();
        }
    }

    private void visualizzaSegnalazioniAttive() {
        try {

            if (UtilityAccesso.getAccount().getCodiceUtente() == null) {
                Printer.error("Devi effettuare il login per visualizzare le segnalazioni attive.");
                return;
            }
            // Creo il bean specificando il tipo di segnalazioni da visualizzare
            BeanListeElementi bean = new BeanListeElementi(TypeOfSegnalazione.ATTIVE);

            // Passo il bean al controller applicativo per riempirlo
            new ControllerApplicativoTipoSegnalazione(bean, UtilityAccesso.getPersistence());

            // Passo il bean popolato alla view per la sola visualizzazione
            PaginaVisualizzazioneSegnalazioniAttiveCli view = new PaginaVisualizzazioneSegnalazioniAttiveCli();
            view.mostraSegnalazioniAttive(bean);

        } catch (SQLException | IOException | NonEsistonoSegnalazioniException | ErroreLetturaPasswordException e) {
            Printer.error("Recupero segnalazioni attive non riuscito: " + e.getMessage());

        }
    }

    private void visualizzaSegnalazioniRisolte(){
        try {
            if (UtilityAccesso.getAccount().getCodiceUtente() == null) {
                Printer.error("Devi effettuare il login per visualizzare le segnalazioni attive.");
                return;
            }
            // Creo il bean specificando il tipo di segnalazioni da visualizzare
            BeanListeElementi bean = new BeanListeElementi(TypeOfSegnalazione.RISOLTE);

            // Passo il bean al controller applicativo per riempirlo
            new ControllerApplicativoTipoSegnalazione(bean, UtilityAccesso.getPersistence());

            // Passo il bean popolato alla view per la sola visualizzazione
            PaginaVisualizzazioneSegnalazioniAttiveCli view = new PaginaVisualizzazioneSegnalazioniAttiveCli();
            view.mostraSegnalazioniAttive(bean);

        } catch (SQLException | IOException | NonEsistonoSegnalazioniException | ErroreLetturaPasswordException e) {
            if(e instanceof NonEsistonoSegnalazioniException) Printer.error("Non esistono segnalazioni risolte");
            else Printer.error("Recupero segnalazioni risolte non riuscito: " + e.getMessage());

        }
    }

    private void esci() {
        paginaHome.mostraMessaggioUscita();
        System.exit(0);
    }
}
