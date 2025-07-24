package cli;

import bean.BeanListeElementi;
import bean.BeanSegnalazioneBinario;
import bean.BeanSegnalazioneLevelCrossing;
import utility.Printer;

import java.io.IOException;

public class PaginaVisualizzazioneSegnalazioniAttiveCli {

    public void mostraSegnalazioniAttive(BeanListeElementi beanListeElementi) {
        int contatorelevelCrossing = beanListeElementi.getSegnalazioniLevelCrossing().size();
        int contatoreBinari = beanListeElementi.getSegnalazioniBinari().size();

        Printer.print("====== SEGNALAZIONI ATTIVE ======");

        if (contatorelevelCrossing > 0) {
            Printer.print("\n>> PASSAGGI A LIVELLI SEGNALATI:");
            for (int i = 0; i < contatorelevelCrossing; i++) {
                BeanSegnalazioneLevelCrossing levelCrossing = beanListeElementi.getSegnalazioniLevelCrossing().get(i);
                Printer.print("\n" + (i + 1) + ") Passaggio a livello");
                Printer.print("Numero passaggio: " + levelCrossing.getcodicePL());
                Printer.print("posizione: " + levelCrossing.getlocalizzazione());
                Printer.print("Problematica: " + levelCrossing.getDescrizioneProblema());
                Printer.print("Stato: " + levelCrossing.getStato());
            }
        }

        if (contatoreBinari > 0) {
            Printer.print("\n>> BINARI SEGNALATI:");
            for (int i = 0; i < contatoreBinari; i++) {
                BeanSegnalazioneBinario binario = beanListeElementi.getSegnalazioniBinari().get(i);
                Printer.print("\n" + (i + 1) + ") Binario");
                Printer.print("Numero binario: " + binario.getNumeroBinario());
                Printer.print("posizione: " + binario.getlocalizzazione());
                Printer.print("Problematica: " + binario.getDescrizioneProblema());
                Printer.print("Stato: " + binario.getStato());
            }
        }

        if (contatorelevelCrossing == 0 && contatoreBinari == 0) {
            Printer.print("Non sono presenti segnalazioni attive.");
        }

    }

}
