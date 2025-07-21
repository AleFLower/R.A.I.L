package dao;

import bean.BeanSegnalazioneBinario;
import bean.BeanSegnalazioneSemaforo;
import com.example.progettoispw.controllergrafici.TypeOfSegnalazione;
import eccezioni.NonEsistonoSegnalazioniException;
import entita.Binario;
import entita.EntitaFerroviaria;
import entita.SemaforoFerroviario;
import utility.UtilityAccesso;

import java.util.ArrayList;
import java.util.List;

public class SegnalazioniAttiveRisolteDaoImplMemory implements SegnalazioniRisolteAttiveDao {
    @Override
    public List<BeanSegnalazioneSemaforo> getSegnalazioniSemafori(TypeOfSegnalazione tipo)
            throws NonEsistonoSegnalazioniException {
        List<BeanSegnalazioneSemaforo> lista = new ArrayList<>();

        String codiceUtente = UtilityAccesso.getCodiceUtente();
        List<EntitaFerroviaria> segnalazioniUtente = ArchivioSegnalazioniMemory.getSegnalazioniPerUtente(codiceUtente);

        if (segnalazioniUtente.isEmpty()) {
            throw new NonEsistonoSegnalazioniException("Non hai effettuato nessuna segnalazione.");
        }

        for (EntitaFerroviaria entita : segnalazioniUtente) {
            if (entita instanceof SemaforoFerroviario semaforo) {
                String stato = semaforo.getStato().toLowerCase();
                boolean isRisolta = stato.equals("risolta");

                if ((tipo == TypeOfSegnalazione.RISOLTE && isRisolta) ||
                        (tipo == TypeOfSegnalazione.ATTIVE && !isRisolta)) {

                    lista.add(new BeanSegnalazioneSemaforo(
                            semaforo.getInfo(),
                            semaforo.getstazione(),
                            semaforo.getDescrizioneProblema(),
                            stato
                    ));
                }
            }
        }

        return lista;
    }

    @Override
    public List<BeanSegnalazioneBinario> getSegnalazioniBinari(TypeOfSegnalazione tipo)
            throws NonEsistonoSegnalazioniException {
        List<BeanSegnalazioneBinario> lista = new ArrayList<>();

        String codiceUtente = UtilityAccesso.getCodiceUtente();
        List<EntitaFerroviaria> segnalazioniUtente = ArchivioSegnalazioniMemory.getSegnalazioniPerUtente(codiceUtente);

        if (segnalazioniUtente.isEmpty()) {
            throw new NonEsistonoSegnalazioniException("Non hai effettuato nessuna segnalazione.");
        }

        for (EntitaFerroviaria entita : segnalazioniUtente) {
            if (entita instanceof Binario binario) {
                String stato = binario.getStato().toLowerCase();
                boolean isRisolta = stato.equals("risolta");

                if ((tipo == TypeOfSegnalazione.RISOLTE && isRisolta) ||
                        (tipo == TypeOfSegnalazione.ATTIVE && !isRisolta)) {

                    lista.add(new BeanSegnalazioneBinario(
                            binario.getInfo(),
                            binario.getstazione(),
                            binario.getDescrizioneProblema(),
                            stato
                    ));
                }
            }
        }

        return lista;
    }

}
