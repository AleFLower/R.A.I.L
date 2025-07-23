package bean;

import com.example.progettoispw.controllergrafici.TypeOfSegnalazione;

import java.util.ArrayList;
import java.util.List;

public class BeanListeElementi {
    public final List<BeanSegnalazioneLevelCrossing> listaSegnalazioniLevelCrossing;
    public final List<BeanSegnalazioneBinario> listaSegnalazioniBinari;

    private TypeOfSegnalazione typeOfSegnalazione;

    public BeanListeElementi(TypeOfSegnalazione typeOfSegnalazione) {
        listaSegnalazioniLevelCrossing = new ArrayList<>();
        listaSegnalazioniBinari = new ArrayList<>();
        this.typeOfSegnalazione = typeOfSegnalazione;
    }

    // Gestisci segnalazioni levelCrossing
    public void aggiungiSegnalazioneLevelCrossing(BeanSegnalazioneLevelCrossing bean) {
        listaSegnalazioniLevelCrossing.add(bean);
    }

    // Gestisci segnalazioni binario stradale
    public void aggiungiSegnalazioneBinario(BeanSegnalazioneBinario bean) {
        listaSegnalazioniBinari.add(bean);
    }

    // Getter per le segnalazioni
    public List<BeanSegnalazioneLevelCrossing> getSegnalazioniLevelCrossing() {
        return listaSegnalazioniLevelCrossing;
    }

    public List<BeanSegnalazioneBinario> getSegnalazioniBinari() {
        return listaSegnalazioniBinari;
    }

    public TypeOfSegnalazione getTipoSegnalazione() {
        return typeOfSegnalazione;
    }


}
