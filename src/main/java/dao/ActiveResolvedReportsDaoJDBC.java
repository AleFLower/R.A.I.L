package dao;

import com.example.progettoispw.graphiccontroller.ReportType;
import exception.PasswordReadException;
import model.LevelCrossing;
import model.RailwayAsset;
import model.Track;
import queries.ReportLevelCrossingQueries;
import queries.ReportTrackQueries;
import utility.AccessUtility;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActiveResolvedReportsDaoJDBC implements ActiveResolvedReportsDao {
    private Connection connection;

    public ActiveResolvedReportsDaoJDBC() throws SQLException, PasswordReadException {
        connection = DbConnection.getInstance();
    }

    private void verifyConnection() throws SQLException, PasswordReadException {
        if (connection == null) {
            connection = DbConnection.getInstance();
        }
    }

    @Override
    public List<RailwayAsset> getReports(ReportType type) throws SQLException, PasswordReadException {
        verifyConnection();

        List<RailwayAsset> list = new ArrayList<>();

        list.addAll(readLevelCrossingReports(type));

        list.addAll(readTrackReports(type));

        return list;
    }

    private List<RailwayAsset> readLevelCrossingReports(ReportType type) throws SQLException {
        List<RailwayAsset> list = new ArrayList<>();

        String query = type == ReportType.ACTIVE ?
                ReportLevelCrossingQueries.queryShowReportedSignals() :
                ReportLevelCrossingQueries.queryShowCompletedReports();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, AccessUtility.getUserCode());
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    RailwayAsset lc = new LevelCrossing(
                            rs.getString("codicePL"),
                            rs.getString("localizzazione"),
                            rs.getString("problematica")
                    );
                    lc.setState(rs.getString("stato"));
                    list.add(lc);
                }
            }
        }
        return list;
    }

    private List<RailwayAsset> readTrackReports(ReportType type) throws SQLException {
        List<RailwayAsset> list = new ArrayList<>();

        String query = type == ReportType.ACTIVE ?
                ReportTrackQueries.queryShowReportedSignals() :
                ReportTrackQueries.queryShowCompletedReports();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, AccessUtility.getUserCode());
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    RailwayAsset track = new Track(
                            rs.getString("numeroBinario"),
                            rs.getString("localizzazione"),
                            rs.getString("problematica")
                    );
                    track.setState(rs.getString("stato"));
                    list.add(track);
                }
            }
        }
        return list;
    }
}

