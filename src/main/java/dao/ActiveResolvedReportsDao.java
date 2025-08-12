package dao;

import bean.ReportTrackBean;
import bean.ReportLevelCrossingBean;
import com.example.progettoispw.controllergrafici.ReportType;
import exception.PasswordReadException;
import exception.NoReportsFoundException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ActiveResolvedReportsDao {

    List<ReportLevelCrossingBean> getLevelCrossinReports(ReportType type)
            throws SQLException, NoReportsFoundException, PasswordReadException, IOException;

    List<ReportTrackBean> getTrackReports(ReportType type)
            throws SQLException, NoReportsFoundException, PasswordReadException, IOException;
}
