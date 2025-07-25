package controllerapplicativi;

import bean.BeanListeElementi;
import bean.BeanSegnalazioneBinario;
import bean.BeanSegnalazioneLevelCrossing;
import com.example.progettoispw.controllergrafici.TypeOfSegnalazione;
import dao.*;

import eccezioni.ErroreLetturaPasswordException;
import eccezioni.NonEsistonoSegnalazioniException;
import factory.FactoryDao;
import factory.TypeOfPersistence;
import utility.UtilityAccesso;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ControllerApplicativoTipoSegnalazione {
    private int codiceUtente;
    private TypeOfSegnalazione typeOfSegnalazione;
    private SegnalazioniRisolteAttiveDao segnalazioniRisolteAttiveDao;

    public ControllerApplicativoTipoSegnalazione(BeanListeElementi bean, TypeOfPersistence persistence) throws NonEsistonoSegnalazioniException, SQLException, ErroreLetturaPasswordException, IOException {
        codiceUtente= Integer.parseInt(UtilityAccesso.getCodiceUtente());
        typeOfSegnalazione=bean.getTipoSegnalazione();
        //devo aggiungere elementi alla lista che si trova nel bean ce che verrà usata da controller grafico per prendere le info
        aggiungiElementi(bean,persistence);
    }
    private void aggiungiElementi(BeanListeElementi bean, TypeOfPersistence persistence)
            throws SQLException, NonEsistonoSegnalazioniException, ErroreLetturaPasswordException, IOException {

        FactoryDao dao = FactoryDao.getFactory(persistence);
        segnalazioniRisolteAttiveDao = dao.getSegnalazioniRisolteAttiveDao();

        List<BeanSegnalazioneLevelCrossing> levelCrossing = segnalazioniRisolteAttiveDao.getSegnalazioniLevelCrossing(typeOfSegnalazione);
        List<BeanSegnalazioneBinario> binari = segnalazioniRisolteAttiveDao.getSegnalazioniBinari(typeOfSegnalazione);

        if(levelCrossing.isEmpty() && binari.isEmpty()) throw new NonEsistonoSegnalazioniException("Non hai effettuato alcuna segnalazione");

        for (BeanSegnalazioneLevelCrossing s : levelCrossing) {
            bean.aggiungiSegnalazioneLevelCrossing(s);
        }

        for (BeanSegnalazioneBinario ss : binari) {
            bean.aggiungiSegnalazioneBinario(ss);
        }
    }
}