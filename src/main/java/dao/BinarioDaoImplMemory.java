package dao;

import eccezioni.SegnalazioneGiaAvvenutaException;
import entita.Binario;
import entita.EntitaFerroviaria;
import utility.UtilityAccesso;

public class BinarioDaoImplMemory implements EntitaFerroviariaDao {
    private int esito;

    //questo metodo non viene chiamato
    @Override
    public void saveEntitaStradale(EntitaFerroviaria instance) throws SegnalazioneGiaAvvenutaException {

        Binario binario = new Binario(instance.getInfo(),instance.getstazione(), instance.getDescrizioneProblema());
        ArchivioSegnalazioniMemory.salvabinario(binario, UtilityAccesso.getCodiceUtente());
        esito = 0;
    }

    public int getEsito() {
        return this.esito;
    }

}


