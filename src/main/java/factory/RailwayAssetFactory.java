package factory;

import bean.ReportBean;
import exception.ReportTypeException;
import model.Track;
import model.RailwayAsset;
import model.LevelCrossing;

public class RailwayAssetFactory {
    //questa factory in base al tipo che riceve il suo unico metodo crea un entita ferroviaria e la restituisce a chi la chiama
    public RailwayAsset createAsset(ReportBean bean) throws ReportTypeException {
        //faccio uno switch case in base al tipo di entità che ricevo
        switch (bean.getType()){
            case TRACK:
                //ho ricevuto un Type_binario, creo e ritorno una binario
                return new Track(bean.getAssetInfo(), bean.getLocation(), bean.getIssue());
            case LEVELCROSSING:
                //ho ricevuto un passaggio a livello, creo e ritorno un passaggio a livello
                return new LevelCrossing(bean.getAssetInfo(), bean.getLocation(), bean.getIssue());
            default:
                //nel caso in cui non ricevessi nessuna delle due di sopra, non posso fare nulla e lancio un eccezione
                throw new ReportTypeException("You can report track and level crossing");
        }
    }
}
