package factory;

import bean.BeanSegnalaEntita;
import eccezioni.TipoEntitaException;
import entita.Binario;
import entita.EntitaFerroviaria;
import entita.LevelCrossing;

public class FactoryEntitaFerroviaria {
    //questa factory in base al tipo che riceve il suo unico metodo crea un entita stradale e la restituisce a chi la chiama
    public EntitaFerroviaria createEntita(BeanSegnalaEntita bean) throws TipoEntitaException {
        //faccio uno switch case in base al tipo di entità che ricevo
        switch (bean.getTipoEntitaSegnalata()){
            case BINARIO:
                //ho ricevuto un Type_binario_Stradale, creo e ritorno una binario stradale
                return new Binario(bean.getInfoEntita(), bean.getlocalizzazione(), bean.getDescrizioneProblema());
            case LEVELCROSSING:
                //ho ricevuto un passaggio a livello, creo e ritorno un passaggio a livello
                return new LevelCrossing(bean.getInfoEntita(), bean.getlocalizzazione(), bean.getDescrizioneProblema());
            default:
                //nel caso in cui non ricevessi nessuna delle due di sopra, non posso fare nulla e lancio un eccezione
                throw new TipoEntitaException("segnalare o un passaggio a livello o una binario");
        }
    }
}
