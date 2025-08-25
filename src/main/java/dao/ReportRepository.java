package dao;


import exception.ReportAlreadyExistsException;

import factory.AssetType;
import model.RailwayAsset;

import java.util.*;

public class ReportRepository {

    private final Map<String, List<RailwayAsset>> reportsForUser = new HashMap<>();
    private final List<RailwayAsset> reports = new ArrayList<>();


    public void sendReport(RailwayAsset asset, String usercode) throws ReportAlreadyExistsException {
        for (RailwayAsset reportAsset : reports) {
            if (asset.getAssetType().equals(AssetType.TRACK)
                    && reportAsset.getAssetInfo().equals(asset.getAssetInfo())
                    && reportAsset.getLocation().equals(asset.getLocation())) {
                throw new ReportAlreadyExistsException("Track with same number and location already reported.");
            }
            else if (asset.getAssetType().equals(AssetType.LEVELCROSSING)
                    && reportAsset.getAssetInfo().equals(asset.getAssetInfo())) {
                throw new ReportAlreadyExistsException("Level crossing with same code already reported.");
            }
        }
        registerReport(usercode, asset);
    }



    private void registerReport(String userCode, RailwayAsset railwayAsset) {
        reportsForUser.computeIfAbsent(userCode, k -> new ArrayList<>()).add(railwayAsset);
        reports.add(railwayAsset);
    }

    public List<RailwayAsset> getReportsForUser(String userCode) {
        return reportsForUser.getOrDefault(userCode, Collections.emptyList());
    }
}
