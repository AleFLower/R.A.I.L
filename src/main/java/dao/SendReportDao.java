package dao;

import exception.PasswordReadException;
import exception.ReportAlreadyExistsException;
import model.RailwayAsset;
import java.io.IOException;
import java.sql.SQLException;

public interface SendReportDao {
    void sendRailwayAssetReport(RailwayAsset instance) throws SQLException, ReportAlreadyExistsException, PasswordReadException, IOException;
}
