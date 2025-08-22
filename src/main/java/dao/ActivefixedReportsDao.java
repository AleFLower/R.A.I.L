package dao;

import com.example.progettoispw.graphiccontroller.ReportType;
import exception.PasswordReadException;
import exception.NoReportsFoundException;
import model.RailwayAsset;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ActivefixedReportsDao {
    List<RailwayAsset> getReports(ReportType type) throws SQLException, IOException, PasswordReadException, NoReportsFoundException;
}

