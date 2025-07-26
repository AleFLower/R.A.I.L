package dao;

import bean.BeanSegnalazioneBinario;
import bean.BeanSegnalazioneLevelCrossing;
import com.example.progettoispw.controllergrafici.TypeOfSegnalazione;
import eccezioni.NonEsistonoSegnalazioniException;
import entita.Binario;
import entita.EntitaFerroviaria;
import entita.LevelCrossing;
import utility.UtilityAccesso;

import java.util.ArrayList;
import java.util.List;

public class SegnalazioniAttiveRisolteDaoImplMemory implements SegnalazioniRisolteAttiveDao {
    @Override
    public List<BeanSegnalazioneLevelCrossing> getSegnalazioniLevelCrossing(TypeOfSegnalazione tipo)
            {
        List<BeanSegnalazioneLevelCrossing> lista = new ArrayList<>();

        String codiceUtente = UtilityAccesso.getCodiceUtente();
        List<EntitaFerroviaria> segnalazioniUtente = ArchivioSegnalazioniMemory.getSegnalazioniPerUtente(codiceUtente);

        //WARNING: CERCA DI NON USARE instanceof

        for (EntitaFerroviaria entita : segnalazioniUtente) {
            if (entita instanceof LevelCrossing levelCrossing) {
                String stato = levelCrossing.getStato().toLowerCase();
                boolean isRisolta = stato.equals("risolta");

                if ((tipo == TypeOfSegnalazione.RISOLTE && isRisolta) ||
                        (tipo == TypeOfSegnalazione.ATTIVE && !isRisolta)) {

                    lista.add(new BeanSegnalazioneLevelCrossing(
                            levelCrossing.getInfo(),
                            levelCrossing.getlocalizzazione(),
                            levelCrossing.getDescrizioneProblema(),
                            stato
                    ));
                }
            }
        }

        return lista;
    }

    @Override
    public List<BeanSegnalazioneBinario> getSegnalazioniBinari(TypeOfSegnalazione tipo)
    {
        List<BeanSegnalazioneBinario> lista = new ArrayList<>();

        String codiceUtente = UtilityAccesso.getCodiceUtente();
        List<EntitaFerroviaria> segnalazioniUtente = ArchivioSegnalazioniMemory.getSegnalazioniPerUtente(codiceUtente);

        for (EntitaFerroviaria entita : segnalazioniUtente) {
            if (entita instanceof Binario binario) {
                String stato = binario.getStato().toLowerCase();
                boolean isRisolta = stato.equals("risolta");

                if ((tipo == TypeOfSegnalazione.RISOLTE && isRisolta) ||
                        (tipo == TypeOfSegnalazione.ATTIVE && !isRisolta)) {

                    lista.add(new BeanSegnalazioneBinario(
                            binario.getInfo(),
                            binario.getlocalizzazione(),
                            binario.getDescrizioneProblema(),
                            stato
                    ));
                }
            }
        }

        return lista;
    }

}
