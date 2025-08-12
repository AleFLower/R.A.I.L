package dao;

import bean.ReportTrackBean;
import bean.ReportLevelCrossingBean;
import com.example.progettoispw.graphiccontroller.ReportType;
import model.Track;
import model.RailwayAsset;
import model.LevelCrossing;
import utility.AccessUtility;

import java.util.ArrayList;
import java.util.List;

public class ActiveResolvedReportsDaoMemory implements ActiveResolvedReportsDao {
    @Override
    public List<ReportLevelCrossingBean> getLevelCrossinReports(ReportType type)
            {
        List<ReportLevelCrossingBean> list = new ArrayList<>();

        String userCode = AccessUtility.getUserCode();
        List<RailwayAsset> userReports = InMemoryReportArchive.getReportsForUser(userCode);

        //WARNING: CERCA DI NON USARE instanceof

        for (RailwayAsset railwayAsset : userReports) {
            if (railwayAsset instanceof LevelCrossing levelCrossing) {
                String state = levelCrossing.getState().toLowerCase();
                boolean isResolved = state.equals("resolved");

                if ((type == ReportType.RESOLVED && isResolved) ||
                        (type == ReportType.ACTIVE && !isResolved)) {

                    list.add(new ReportLevelCrossingBean(
                            levelCrossing.getAssetInfo(),
                            levelCrossing.getLocation(),
                            levelCrossing.getIssue(),
                            state
                    ));
                }
            }
        }

        return list;
    }

    @Override
    public List<ReportTrackBean> getTrackReports(ReportType type)
    {
        List<ReportTrackBean> list = new ArrayList<>();

        String userCode = AccessUtility.getUserCode();
        List<RailwayAsset> userReports = InMemoryReportArchive.getReportsForUser(userCode);

        for (RailwayAsset railwayAsset : userReports) {
            if (railwayAsset instanceof Track track) {
                String state = track.getState().toLowerCase();
                boolean isResolved = state.equals("resolved");

                if ((type == ReportType.RESOLVED && isResolved) ||
                        (type == ReportType.ACTIVE && !isResolved)) {

                    list.add(new ReportTrackBean(
                            track.getAssetInfo(),
                            track.getLocation(),
                            track.getIssue(),
                            state
                    ));
                }
            }
        }

        return list;
    }

}
