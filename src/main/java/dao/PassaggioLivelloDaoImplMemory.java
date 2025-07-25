package dao;

import eccezioni.SegnalazioneGiaAvvenutaException;
import entita.EntitaFerroviaria;
import entita.LevelCrossing;
import utility.UtilityAccesso;

public class PassaggioLivelloDaoImplMemory implements EntitaFerroviariaDao {

    @Override
    public void saveEntitaStradale(EntitaFerroviaria instance) throws SegnalazioneGiaAvvenutaException {
        LevelCrossing levelCrossing = new LevelCrossing(instance.getInfo(), instance.getlocalizzazione(),instance.getDescrizioneProblema());
        ArchivioSegnalazioniMemory.salvaLevelCrossing(levelCrossing,UtilityAccesso.getCodiceUtente());
    }

}


