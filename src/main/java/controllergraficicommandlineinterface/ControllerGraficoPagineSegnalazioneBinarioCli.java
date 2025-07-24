package controllergraficicommandlineinterface;

import bean.BeanSegnalaEntita;
import cli.PaginaHome;
import cli.PaginaSegnalazioneBinarioCli;
import controllerapplicativi.ControllerApplicativoSegnalazioneEntita;
import eccezioni.*;
import factory.TypeEntita;
import factory.TypeOfPersistence;
import utility.Printer;
import utility.UtilityAccesso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class ControllerGraficoPagineSegnalazioneBinarioCli {
    private final PaginaSegnalazioneBinarioCli view;

    public ControllerGraficoPagineSegnalazioneBinarioCli() {
        this.view = new PaginaSegnalazioneBinarioCli();
    }

    public void mostraPaginaSegnalazione() throws IOException {
        String localizzazione = view.chiediLocalizzazione();
        if (localizzazione.equalsIgnoreCase("esc")) return;

        String numeroBinario = view.chiediNumeroBinario();
        if (numeroBinario.equalsIgnoreCase("esc")) return;

        String problematica = view.chiediProblematica();
        if (problematica.equalsIgnoreCase("esc")) return;

        if (view.confermaSalvataggio()) {
            salvaSegnalazione(localizzazione, numeroBinario, problematica);
        }
    }

    private void salvaSegnalazione(String localizzazione, String numeroBinario, String problematica) throws IOException {
        try {
            BeanSegnalaEntita bean = new BeanSegnalaEntita(
                    numeroBinario, localizzazione, problematica, TypeEntita.BINARIO, UtilityAccesso.getPersistence());

            new ControllerApplicativoSegnalazioneEntita(bean);
            view.mostraSuccesso();
        } catch (SegnalazioneGiaAvvenutaException e) {
            view.mostraErrore("Segnalazione già presente.");
        } catch (Exception e) {
            view.mostraErrore("Errore: " + e.getMessage());
        }
    }
}

