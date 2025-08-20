package dao;

import exception.PasswordReadException;
import exception.ReportAlreadyExistsException;
import model.RailwayAsset;
import model.LevelCrossing;
import queries.ReportLevelCrossingQueries;
import utility.AccessUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SendLevelCrossingReportDaoJDBC implements SendReportDao {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;


    private int outcome;
    public SendLevelCrossingReportDaoJDBC() throws SQLException, PasswordReadException {
        connection= DbConnection.getInstance();
    }
    @Override
    public void sendRailwayAssetReport(RailwayAsset instance) throws SQLException, ReportAlreadyExistsException, PasswordReadException {

        LevelCrossing levelCrossing=new LevelCrossing(instance.getAssetInfo(),instance.getLocation(),instance.getIssue());

        if(!searchLevelCrossing(levelCrossing)) {
            if (AccessUtility.getUserCode() != null) {

                preparedStatement = connection.prepareStatement(ReportLevelCrossingQueries.querySaveLevelCrossingForSystemUser());
                preparedStatement.setString(1, levelCrossing.getAssetInfo());
                preparedStatement.setString(2, levelCrossing.getLocation());
                preparedStatement.setString(3, levelCrossing.getIssue());
                preparedStatement.setString(4, AccessUtility.getUserCode());
                preparedStatement.executeUpdate();
                this.outcome = 0;
            }
            else{
                preparedStatement = connection.prepareStatement(ReportLevelCrossingQueries.getQuerySaveLevelCrossing());
                preparedStatement.setString(1, levelCrossing.getAssetInfo());
                preparedStatement.setString(2, levelCrossing.getLocation());
                preparedStatement.setString(3, levelCrossing.getIssue());
                preparedStatement.executeUpdate();
            }
        }else {
            this.outcome= -1;
            throw new ReportAlreadyExistsException("the level crossing has been already reported by another user");
        }
    }
    private boolean searchLevelCrossing(LevelCrossing levelCrossing) throws SQLException, PasswordReadException {

        verifyConnection();
        preparedStatement=connection.prepareStatement(ReportLevelCrossingQueries.getQuerySearchLevelCrossing());
        preparedStatement.setString(1,levelCrossing.getAssetInfo());
        resultSet=preparedStatement.executeQuery();
        return resultSet.isBeforeFirst();
    }
    private void verifyConnection() throws SQLException, PasswordReadException {
        if(connection==null){
            new SendLevelCrossingReportDaoJDBC();
        }
    }
    public int getOutcome(){
        return this.outcome;
    }
}
