package factory;

import eccezioni.TipoEntitaException;
import entita.Binario;
import entita.EntitaFerroviaria;
import entita.LevelCrossing;

public class FactoryEntitaFerroviaria {
    //questa factory in base al tipo che riceve il suo unico metodo crea un entita stradale e la restituisce a chi la chiama
    public EntitaFerroviaria createEntita(TypeEntita tipoEntitaSegnalata, String localizzazione, String infoEntita, String problematica) throws TipoEntitaException {
        //faccio uno switch case in base al tipo di entità che ricevo
        switch (tipoEntitaSegnalata){
            case BINARIO:
                //ho ricevuto un Type_binario_Stradale, creo e ritorno una binario stradale
                return new Binario(infoEntita,localizzazione,problematica);
            case LEVELCROSSING:
                //ho ricevuto un Semaforo, creo e ritorno un Semaforo
                return new LevelCrossing(infoEntita,localizzazione,problematica);
            default:
                //nel caso in cui non ricevessi nessuna delle due di sopra, non posso fare nulla e lancio un eccezione
                throw new TipoEntitaException("segnalare o un passaggio a livello o una binario");
        }
    }
}
