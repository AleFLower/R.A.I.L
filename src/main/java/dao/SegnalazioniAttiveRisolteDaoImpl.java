package dao;

import bean.BeanSegnalazioneBinario;
import bean.BeanSegnalazioneSemaforo;
import com.example.progettoispw.controllergrafici.TypeOfSegnalazione;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.NonEsistonoSegnalazioniException;
import queries.QueriesSemaforo;
import queries.QueriesSegnalazioneBinario;
import utility.UtilityAccesso;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SegnalazioniAttiveRisolteDaoImpl implements SegnalazioniRisolteAttiveDao{
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet rs;
    private ResultSet rs2;
    private void verificaConnessione() throws SQLException, ErroreLetturaPasswordException {
        if(connection==null){
            connection=SingletonConnessione.getInstance();
        }
    }
    public SegnalazioniAttiveRisolteDaoImpl() throws SQLException, ErroreLetturaPasswordException {
        //il costruttore non fa altro che aprire o prendere una connessione
        connection=SingletonConnessione.getInstance();
    }
    @Override
    public List<BeanSegnalazioneSemaforo> getSegnalazioniSemafori(TypeOfSegnalazione tipo)
            throws SQLException, NonEsistonoSegnalazioniException, ErroreLetturaPasswordException {

        verificaConnessione();
        String query = tipo == TypeOfSegnalazione.ATTIVE ?
                QueriesSemaforo.queriesMostraSegnalazioniEffettuate() :
                QueriesSemaforo.queriesMostraSegnalazioniCompletate();

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, UtilityAccesso.getCodiceUtente());
        rs = preparedStatement.executeQuery();

        List<BeanSegnalazioneSemaforo> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(new BeanSegnalazioneSemaforo(
                    rs.getString("numeroSeriale"),
                    rs.getString("stazione"),
                    rs.getString("problematica"),
                    rs.getString("stato")
            ));
        }
        return lista;
    }

    @Override
    public List<BeanSegnalazioneBinario> getSegnalazioniBinari(TypeOfSegnalazione tipo)
            throws SQLException, NonEsistonoSegnalazioniException, ErroreLetturaPasswordException {

        verificaConnessione();
        String query = tipo == TypeOfSegnalazione.ATTIVE ?
                QueriesSegnalazioneBinario.queriesMostraSegnalazioniEffettuate() :
                QueriesSegnalazioneBinario.queriesMostraSegnalazioniCompletate();

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, UtilityAccesso.getCodiceUtente());
        rs2 = preparedStatement.executeQuery();


        List<BeanSegnalazioneBinario> lista = new ArrayList<>();
        while (rs2.next()) {
            lista.add(new BeanSegnalazioneBinario(
                    rs2.getString("numeroBinario"),
                    rs2.getString("stazione"),
                    rs2.getString("problematica"),
                    rs2.getString("stato")
            ));
        }
        return lista;
    }

}
