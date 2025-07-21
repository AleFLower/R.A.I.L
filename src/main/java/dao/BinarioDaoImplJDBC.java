package dao;

import eccezioni.ErroreLetturaPasswordException;
import eccezioni.SegnalazioneGiaAvvenutaException;
import entita.Binario;
import entita.EntitaFerroviaria;
import queries.QueriesSegnalazioneBinario;
import utility.UtilityAccesso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BinarioDaoImplJDBC implements EntitaFerroviariaDao {
    //dao che si preoccupa di salvare l'entita stradale, che in questo caso e' una  binario nel database
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    //variabile che viene impostata a 0 se il salvataggio di una binario nel db e' andato a buon fine,
    //altrimenti viene impostata a -1
    private int esito;
    public BinarioDaoImplJDBC() throws SQLException, ErroreLetturaPasswordException {
        connection=SingletonConnessione.getInstance();
    }
    private void verificaConnessione() throws SQLException, ErroreLetturaPasswordException {
        if(connection==null){
            new BinarioDaoImplJDBC();
        }
    }
    @Override
    public void saveEntitaStradale(EntitaFerroviaria instance) throws SQLException, SegnalazioneGiaAvvenutaException, ErroreLetturaPasswordException {
        //sono qui sto segnalando una binario quindi faccio il cast da entità stradale a binario
        Binario BinarioDaSegnalare = new Binario(instance.getInfo(), instance.getstazione(), instance.getDescrizioneProblema());
        //verifico se la binario che sto segnalando e' gia presente nel db( cioe' se c'e' già un stazione che ha
        //ricevuto quella segnalazione
        //cerca binario torna true se la binario è gia presente, false se non lo è
        if (!cercabinario(BinarioDaSegnalare)) {
            if (UtilityAccesso.getCodiceUtente() != null) {
                //la binario non è presente nel db, posso quindi  inviarla
                preparedStatement = connection.prepareStatement(QueriesSegnalazioneBinario.queriesSalvaBinarioUtente());
                preparedStatement.setString(1, BinarioDaSegnalare.getInfo());
                preparedStatement.setString(2, BinarioDaSegnalare.getstazione());
                preparedStatement.setString(3, BinarioDaSegnalare.getDescrizioneProblema());
                preparedStatement.setString(4, UtilityAccesso.getCodiceUtente());
                preparedStatement.executeUpdate();
                esito = 0;
            } else {
                preparedStatement = connection.prepareStatement(QueriesSegnalazioneBinario.queriesSalvabinario());
                preparedStatement.setString(1, BinarioDaSegnalare.getInfo());
                preparedStatement.setString(2, BinarioDaSegnalare.getstazione());
                preparedStatement.setString(3, BinarioDaSegnalare.getDescrizioneProblema());
            }
        } else {
            //la binario è presente nel db devo quindi comunicarlo all'utente
            esito = -1;
            throw new SegnalazioneGiaAvvenutaException("binario e' stata già segnalato da un altro utente");
        }
    }
    private boolean cercabinario(Binario binario) throws SQLException, ErroreLetturaPasswordException {
        //ritorna true se c'e 'una segnalazione già effettuata a quell'stazione, false altrimenti
        verificaConnessione();
        preparedStatement = connection.prepareStatement(QueriesSegnalazioneBinario.queriesVediSeLeSegnalAQuellstazioneSonoStateGiaSegnalate());
        preparedStatement.setString(1, binario.getstazione());
        resultSet = preparedStatement.executeQuery();
        //isBeforeFirst ritorna true se il result set contiene qualcosa, false altrimenti
        return resultSet.isBeforeFirst();
    }
    public int getEsito(){
        return this.esito;
    }

}
