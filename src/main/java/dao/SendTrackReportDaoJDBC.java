package dao;

import exception.PasswordReadException;
import exception.ReportAlreadyExistsException;
import model.Track;
import model.RailwayAsset;
import queries.ReportTrackQueries;
import utility.AccessUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SendTrackReportDaoJDBC implements SendReportDao {
    //dao che si preoccupa di salvare l'entita stradale, che in questo caso e' una  binario nel database
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    //variabile che viene impostata a 0 se il salvataggio di una binario nel db e' andato a buon fine,
    //altrimenti viene impostata a -1
    private int outcome;
    public SendTrackReportDaoJDBC() throws SQLException, PasswordReadException {
        connection= DbConnection.getInstance();
    }
    private void verifyConnection() throws SQLException, PasswordReadException {
        if(connection==null){
            new SendTrackReportDaoJDBC();
        }
    }
    @Override
    public void sendRailwayAssetReport(RailwayAsset instance) throws SQLException, ReportAlreadyExistsException, PasswordReadException {
        //sono qui sto segnalando una binario quindi faccio il cast da entità stradale a binario
        Track track = new Track(instance.getAssetInfo(), instance.getLocation(), instance.getIssue());
        //verifico se la binario che sto segnalando e' gia presente nel db( cioe' se c'e' già un localizzazione che ha
        //ricevuto quella segnalazione
        //cerca binario torna true se la binario è gia presente, false se non lo è
        if (!searchTrack(track)) {
            if (AccessUtility.getUserCode() != null) {
                // binario non è presente nel db, posso quindi  inviarla
                preparedStatement = connection.prepareStatement(ReportTrackQueries.querySaveTrack());
                preparedStatement.setString(1, track.getAssetInfo());
                preparedStatement.setString(2, track.getLocation());
                preparedStatement.setString(3, track.getIssue());
                preparedStatement.setString(4, AccessUtility.getUserCode());
                preparedStatement.executeUpdate();
                outcome = 0;
            }
        } else {
            //la binario è presente nel db devo quindi comunicarlo all'utente
            outcome = -1;
            throw new ReportAlreadyExistsException("Track has been already reported by another user");
        }
    }
    private boolean searchTrack(Track track) throws SQLException, PasswordReadException {
        //ritorna true se c'e 'una segnalazione già effettuata a quell'localizzazione, false altrimenti
        verifyConnection();
        preparedStatement = connection.prepareStatement(ReportTrackQueries.queryCheckIfTrackAtLocationReported());
        preparedStatement.setString(1, track.getAssetInfo());
        preparedStatement.setString(2, track.getLocation());
        resultSet = preparedStatement.executeQuery();
        //isBeforeFirst ritorna true se il result set contiene qualcosa, false altrimenti
        return resultSet.isBeforeFirst();
    }
    public int getOutcome(){
        return this.outcome;
    }

}
