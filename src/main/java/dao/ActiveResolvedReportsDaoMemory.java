package dao;


import com.example.progettoispw.graphiccontroller.ReportType;

import model.RailwayAsset;
import utility.AccessUtility;

import java.util.ArrayList;
import java.util.List;

public class ActiveResolvedReportsDaoMemory implements ActiveResolvedReportsDao {

    private final ReportRepository reportRepository;

    public ActiveResolvedReportsDaoMemory(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<RailwayAsset> getReports(ReportType type) {
        List<RailwayAsset> list = new ArrayList<>();
        String userCode = AccessUtility.getUserCode();
        List<RailwayAsset> userReports = reportRepository.getReportsForUser(userCode);

        for (RailwayAsset asset : userReports) {
            String state = asset.getState().toLowerCase();
            boolean isResolved = state.equals("resolved");

            if ((type == ReportType.RESOLVED && isResolved) || (type == ReportType.ACTIVE && !isResolved)) {
                list.add(asset);
            }
        }

        return list;
    }
}

