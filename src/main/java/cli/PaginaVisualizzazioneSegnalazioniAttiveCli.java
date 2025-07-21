package cli;

import bean.BeanListeElementi;
import bean.BeanSegnalazioneBinario;
import bean.BeanSegnalazioneSemaforo;
import utility.Printer;

import java.io.IOException;

public class PaginaVisualizzazioneSegnalazioniAttiveCli {

    public void mostraSegnalazioniAttive(BeanListeElementi beanListeElementi) throws IOException {
        int contatoreSemafori = beanListeElementi.getSegnalazioniSemafori().size();
        int contatoreBinari = beanListeElementi.getSegnalazioniBinari().size();

        Printer.print("====== SEGNALAZIONI ATTIVE ======");

        if (contatoreSemafori > 0) {
            Printer.print("\n>> SEMAFORI SEGNALATI:");
            for (int i = 0; i < contatoreSemafori; i++) {
                BeanSegnalazioneSemaforo semaforo = beanListeElementi.getSegnalazioniSemafori().get(i);
                Printer.print("\n" + (i + 1) + ") Semaforo");
                Printer.print("Numero seriale: " + semaforo.getNumeroSeriale());
                Printer.print("stazione: " + semaforo.getstazione());
                Printer.print("Problematica: " + semaforo.getDescrizioneProblema());
                Printer.print("Stato: " + semaforo.getStato());
            }
        }

        if (contatoreBinari > 0) {
            Printer.print("\n>> BINARI SEGNALATI:");
            for (int i = 0; i < contatoreBinari; i++) {
                BeanSegnalazioneBinario binario = beanListeElementi.getSegnalazioniBinari().get(i);
                Printer.print("\n" + (i + 1) + ") Binario");
                Printer.print("Numero binario: " + binario.getNumeroBinario());
                Printer.print("stazione: " + binario.getstazione());
                Printer.print("Problematica: " + binario.getDescrizioneProblema());
                Printer.print("Stato: " + binario.getStato());
            }
        }

        if (contatoreSemafori == 0 && contatoreBinari == 0) {
            Printer.print("Non sono presenti segnalazioni attive.");
        }

        tornaAllaHomePage();
    }

    private void tornaAllaHomePage() throws IOException {
        PaginaHome paginaHome = new PaginaHome();
        paginaHome.displayHomepage();
    }
}
