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

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

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

        Track track = new Track(instance.getAssetInfo(), instance.getLocation(), instance.getIssue());

        if (!searchTrack(track)) {
            if (AccessUtility.getUserCode() != null) {

                preparedStatement = connection.prepareStatement(ReportTrackQueries.querySaveTrack());
                preparedStatement.setString(1, track.getAssetInfo());
                preparedStatement.setString(2, track.getLocation());
                preparedStatement.setString(3, track.getIssue());
                preparedStatement.setString(4, AccessUtility.getUserCode());
                preparedStatement.executeUpdate();
                outcome = 0;
            }
        } else {

            outcome = -1;
            throw new ReportAlreadyExistsException("Track has been already reported by another user");
        }
    }
    private boolean searchTrack(Track track) throws SQLException, PasswordReadException {

        verifyConnection();
        preparedStatement = connection.prepareStatement(ReportTrackQueries.queryCheckIfTrackAtLocationReported());
        preparedStatement.setString(1, track.getAssetInfo());
        preparedStatement.setString(2, track.getLocation());
        resultSet = preparedStatement.executeQuery();

        return resultSet.isBeforeFirst();
    }
    public int getOutcome(){
        return this.outcome;
    }

}
