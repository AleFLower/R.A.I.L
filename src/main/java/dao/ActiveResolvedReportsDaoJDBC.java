package dao;

import bean.ReportTrackBean;
import bean.ReportLevelCrossingBean;
import com.example.progettoispw.controllergrafici.ReportType;
import exception.PasswordReadException;
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
    private PreparedStatement preparedStatement;
    private ResultSet rs;
    private ResultSet rs2;
    private void verifyConnection() throws SQLException, PasswordReadException {
        if(connection==null){
            connection= DbConnection.getInstance();
        }
    }
    public ActiveResolvedReportsDaoJDBC() throws SQLException, PasswordReadException {
        //il costruttore non fa altro che aprire o prendere una connessione
        connection= DbConnection.getInstance();
    }
    @Override
    public List<ReportLevelCrossingBean> getLevelCrossinReports(ReportType type)
            throws SQLException, PasswordReadException {

        verifyConnection();
        String query = type == ReportType.ACTIVE ?
                ReportLevelCrossingQueries.queryShowReportedSignals() :
                ReportLevelCrossingQueries.queryShowCompletedReports();

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, AccessUtility.getUserCode());
        rs = preparedStatement.executeQuery();

        List<ReportLevelCrossingBean> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new ReportLevelCrossingBean(
                    rs.getString("codicePL"),   //DA CAMBIARE, NEL NUOVO DB IN INGLESE
                    rs.getString("localizzazione"),
                    rs.getString("problematica"),
                    rs.getString("stato")
            ));
        }
        return list;
    }

    @Override
    public List<ReportTrackBean> getTrackReports(ReportType type)
            throws SQLException, PasswordReadException {

        verifyConnection();
        String query = type == ReportType.ACTIVE ?
                ReportTrackQueries.queryShowReportedSignals() :
                ReportTrackQueries.queryShowCompletedReports();

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, AccessUtility.getUserCode());
        rs2 = preparedStatement.executeQuery();


        List<ReportTrackBean> list = new ArrayList<>();
        while (rs2.next()) {
            list.add(new ReportTrackBean(
                    rs2.getString("numeroBinario"),
                    rs2.getString("localizzazione"),
                    rs2.getString("problematica"),
                    rs2.getString("stato")
            ));
        }
        return list;
    }

}
