package dao;

import exception.PasswordReadException;
import exception.ReportAlreadyExistsException;
import model.RailwayAsset;
import java.io.IOException;
import java.sql.SQLException;

public interface SendReportDao {
    //interfaccia che mette a disposizione ai dao cosa possono fare se un utente vuole slavare in locale
    //o nel db un entità stradale
    void sendRailwayAssetReport(RailwayAsset instance) throws SQLException, ReportAlreadyExistsException, PasswordReadException, IOException;
}
