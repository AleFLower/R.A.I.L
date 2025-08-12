package dao;

import bean.ReportTrackBean;
import bean.ReportLevelCrossingBean;
import com.example.progettoispw.graphiccontroller.ReportType;
import factory.AssetType;
import model.Track;
import model.RailwayAsset;
import model.LevelCrossing;
import utility.AccessUtility;

import java.util.ArrayList;
import java.util.List;

public class ActiveResolvedReportsDaoMemory implements ActiveResolvedReportsDao {
    @Override
    public List<RailwayAsset> getReports(ReportType type) {
        List<RailwayAsset> list = new ArrayList<>();
        String userCode = AccessUtility.getUserCode();
        List<RailwayAsset> userReports = InMemoryReportArchive.getReportsForUser(userCode);

        for (RailwayAsset asset : userReports) {
            String state = asset.getState().toLowerCase();
            boolean isResolved = state.equals("resolved");

            if ((type == ReportType.RESOLVED && isResolved) || (type == ReportType.ACTIVE && !isResolved)) {
                list.add(asset);  // Aggiungi direttamente asset (LevelCrossing o Track)
            }
        }

        return list;
    }

}
