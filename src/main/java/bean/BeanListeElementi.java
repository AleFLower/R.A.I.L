package bean;

import com.example.progettoispw.controllergrafici.TypeOfSegnalazione;

import java.util.ArrayList;
import java.util.List;

public class BeanListeElementi {
    public final List<BeanSegnalazioneSemaforo> listaSegnalazioniSemafori;
    public final List<BeanSegnalazioneBinario> listaSegnalazioniBinari;

    private TypeOfSegnalazione typeOfSegnalazione;

    public BeanListeElementi(TypeOfSegnalazione typeOfSegnalazione) {
        listaSegnalazioniSemafori = new ArrayList<>();
        listaSegnalazioniBinari = new ArrayList<>();
        this.typeOfSegnalazione = typeOfSegnalazione;
    }

    // Gestisci segnalazioni semaforo
    public void aggiungiSegnalazioneSemaforo(BeanSegnalazioneSemaforo bean) {
        listaSegnalazioniSemafori.add(bean);
    }

    // Gestisci segnalazioni binario stradale
    public void aggiungiSegnalazioneBinario(BeanSegnalazioneBinario bean) {
        listaSegnalazioniBinari.add(bean);
    }

    // Getter per le segnalazioni
    public List<BeanSegnalazioneSemaforo> getSegnalazioniSemafori() {
        return listaSegnalazioniSemafori;
    }

    public List<BeanSegnalazioneBinario> getSegnalazioniBinari() {
        return listaSegnalazioniBinari;
    }

    public TypeOfSegnalazione getTipoSegnalazione() {
        return typeOfSegnalazione;
    }


}
