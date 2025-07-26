package controllergraficicommandlineinterface;

import bean.BeanSegnalaEntita;

import cli.PaginaSegnalazioneBinarioCli;
import controllerapplicativi.ControllerApplicativoSegnalazioneEntita;
import eccezioni.*;
import factory.TypeEntita;

import utility.UtilityAccesso;

import java.io.IOException;


public class ControllerGraficoPagineSegnalazioneBinarioCli {
    private final PaginaSegnalazioneBinarioCli view;

    public ControllerGraficoPagineSegnalazioneBinarioCli() {
        this.view = new PaginaSegnalazioneBinarioCli();
    }

    public void mostraPaginaSegnalazione() {
        try {
            String localizzazione = view.chiediLocalizzazione();
            if (localizzazione == null || localizzazione.equalsIgnoreCase("esc")) return;

            String numeroBinario = view.chiediNumeroBinario();
            if (numeroBinario.equalsIgnoreCase("esc")) return;

            String problematica = view.chiediProblematica();
            if (problematica.equalsIgnoreCase("esc")) return;

            try {
                if (view.confermaSalvataggio()) {
                    salvaSegnalazione(localizzazione, numeroBinario, problematica);
                }
            } catch (SceltaNonValidaException e) {
                view.mostraErrore("Scelta non valida: " + e.getMessage());
            }

        } catch (IOException e) {
            view.mostraErrore("Errore di input/output: " + e.getMessage());
        }
    }


    private void salvaSegnalazione(String localizzazione, String numeroBinario, String problematica)  {
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

