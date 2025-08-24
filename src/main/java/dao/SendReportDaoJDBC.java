package dao;

import exception.PasswordReadException;
import exception.ReportAlreadyExistsException;

import model.RailwayAsset;

import queries.ReportLevelCrossingQueries;
import queries.ReportTrackQueries;
import utility.AccessUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SendReportDaoJDBC implements SendReportDao {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private int outcome;

    public SendReportDaoJDBC() throws SQLException, PasswordReadException {
        connection = DbConnection.getInstance();
    }

    @Override
    public void sendRailwayAssetReport(RailwayAsset asset)
            throws SQLException, ReportAlreadyExistsException, PasswordReadException {

        verifyConnection();

        switch (asset.getAssetType()) {
            case TRACK -> handleTrack(asset);
            case LEVELCROSSING -> handleLevelCrossing(asset);
            default -> throw new IllegalArgumentException("Unsupported asset type: " + asset.getAssetType());
        }
    }


    private void handleTrack(RailwayAsset track) throws SQLException, ReportAlreadyExistsException {
        if (!searchTrack(track)) {
            preparedStatement = connection.prepareStatement(ReportTrackQueries.querySaveTrack());
            preparedStatement.setString(1, track.getAssetInfo());
            preparedStatement.setString(2, track.getLocation());
            preparedStatement.setString(3, track.getIssue());
            preparedStatement.setString(4, AccessUtility.getUserCode());
            preparedStatement.executeUpdate();
            outcome = 0;
        } else {
            outcome = -1;
            throw new ReportAlreadyExistsException("Track already reported.");
        }
    }

    private boolean searchTrack(RailwayAsset track) throws SQLException {
        preparedStatement = connection.prepareStatement(ReportTrackQueries.queryCheckIfTrackAtLocationReported());
        preparedStatement.setString(1, track.getAssetInfo());
        preparedStatement.setString(2, track.getLocation());
        resultSet = preparedStatement.executeQuery();
        return resultSet.isBeforeFirst();
    }


    private void handleLevelCrossing(RailwayAsset lc) throws SQLException, ReportAlreadyExistsException {
        if (!searchLevelCrossing(lc)) {
            if (AccessUtility.getUserCode() != null) {
                preparedStatement = connection.prepareStatement(ReportLevelCrossingQueries.querySaveLevelCrossingForSystemUser());
                preparedStatement.setString(1, lc.getAssetInfo());
                preparedStatement.setString(2, lc.getLocation());
                preparedStatement.setString(3, lc.getIssue());
                preparedStatement.setString(4, AccessUtility.getUserCode());
                preparedStatement.executeUpdate();
            } else {
                preparedStatement = connection.prepareStatement(ReportLevelCrossingQueries.getQuerySaveLevelCrossing());
                preparedStatement.setString(1, lc.getAssetInfo());
                preparedStatement.setString(2, lc.getLocation());
                preparedStatement.setString(3, lc.getIssue());
                preparedStatement.executeUpdate();
            }
            outcome = 0;
        } else {
            outcome = -1;
            throw new ReportAlreadyExistsException("Level crossing already reported.");
        }
    }

    private boolean searchLevelCrossing(RailwayAsset lc) throws SQLException {
        preparedStatement = connection.prepareStatement(ReportLevelCrossingQueries.getQuerySearchLevelCrossing());
        preparedStatement.setString(1, lc.getAssetInfo());
        resultSet = preparedStatement.executeQuery();
        return resultSet.isBeforeFirst();
    }


    private void verifyConnection() throws SQLException, PasswordReadException {
        if (connection == null) {
            connection = DbConnection.getInstance();
        }
    }

    public int getOutcome() {
        return this.outcome;
    }
}
