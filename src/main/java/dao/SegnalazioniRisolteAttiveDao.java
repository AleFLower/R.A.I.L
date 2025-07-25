package dao;

import bean.BeanSegnalazioneBinario;
import bean.BeanSegnalazioneLevelCrossing;
import com.example.progettoispw.controllergrafici.TypeOfSegnalazione;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.NonEsistonoSegnalazioniException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface SegnalazioniRisolteAttiveDao {

    List<BeanSegnalazioneLevelCrossing> getSegnalazioniLevelCrossing(TypeOfSegnalazione tipo)
            throws SQLException, NonEsistonoSegnalazioniException, ErroreLetturaPasswordException, IOException;

    List<BeanSegnalazioneBinario> getSegnalazioniBinari(TypeOfSegnalazione tipo)
            throws SQLException, NonEsistonoSegnalazioniException, ErroreLetturaPasswordException, IOException;
}
