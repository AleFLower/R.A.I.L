package dao;

import eccezioni.SegnalazioneGiaAvvenutaException;
import entita.EntitaFerroviaria;
import entita.SemaforoFerroviario;
import utility.UtilityAccesso;

import java.util.Set;

public class SemaforoDaoImplMemory implements EntitaFerroviariaDao {

    @Override
    public void saveEntitaStradale(EntitaFerroviaria instance) throws SegnalazioneGiaAvvenutaException {
        SemaforoFerroviario Semaforo = new SemaforoFerroviario(instance.getInfo(), instance.getstazione(),instance.getDescrizioneProblema());
        ArchivioSegnalazioniMemory.salvaSemaforo(Semaforo,UtilityAccesso.getCodiceUtente());
        System.out.println(ArchivioSegnalazioniMemory.getSegnalazioniPerUtente(UtilityAccesso.getCodiceUtente()));

    }

    public static Set<SemaforoFerroviario> getsemaforiIlluminazioneSegnalati() {
        return ArchivioSegnalazioniMemory.getsemaforiSegnalati();
    }
}


