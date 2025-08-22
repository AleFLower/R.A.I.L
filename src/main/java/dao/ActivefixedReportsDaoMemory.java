package dao;


import com.example.progettoispw.graphiccontroller.ReportType;

import model.RailwayAsset;
import utility.AccessUtility;

import java.util.ArrayList;
import java.util.List;

public class ActivefixedReportsDaoMemory implements ActivefixedReportsDao {

    private final ReportRepository reportRepository;

    public ActivefixedReportsDaoMemory(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<RailwayAsset> getReports(ReportType type) {
        List<RailwayAsset> list = new ArrayList<>();
        String userCode = AccessUtility.getUserCode();
        List<RailwayAsset> userReports = reportRepository.getReportsForUser(userCode);

        for (RailwayAsset asset : userReports) {
            String state = asset.getState().toString().toLowerCase();
            boolean isfixed = state.equals("fixed");

            if ((type == ReportType.FIXED && isfixed) || (type == ReportType.ACTIVE && !isfixed)) {
                list.add(asset);
            }
        }

        return list;
    }
}

