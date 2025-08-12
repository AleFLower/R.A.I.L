package dao;


import exception.ReportAlreadyExistsException;

import model.Track;
import model.RailwayAsset;
import model.LevelCrossing;

import java.util.*;

public class ReportRepository {

    private final List<LevelCrossing> reportedLevelCrossing = new ArrayList<>();
    private final List<Track> reportedTracks = new ArrayList<>();
    private final Map<String, List<RailwayAsset>> reportsForUser = new HashMap<>();

    public void sendLevelCrossingReport(LevelCrossing newLcCode, String userCode) throws ReportAlreadyExistsException {
        for (LevelCrossing pl : reportedLevelCrossing) {
            if (pl.getAssetInfo().equals(newLcCode.getAssetInfo())) {
                throw new ReportAlreadyExistsException("Level Crossing already reported ");
            }
        }
        reportedLevelCrossing.add(newLcCode);
        registerReport(userCode, newLcCode);
    }

    public void sendTrackReport(Track newTrack, String userCode) throws ReportAlreadyExistsException {
        for (Track b : reportedTracks) {
            if (b.getAssetInfo().equals(newTrack.getAssetInfo())
                    && b.getLocation().equals(newTrack.getLocation())) {
                throw new ReportAlreadyExistsException("Track with same number and location already reported.");
            }
        }
        reportedTracks.add(newTrack);
        registerReport(userCode, newTrack);
    }

    private void registerReport(String userCode, RailwayAsset railwayAsset) {
        reportsForUser.computeIfAbsent(userCode, k -> new ArrayList<>()).add(railwayAsset);
    }

    public List<RailwayAsset> getReportsForUser(String userCode) {
        return reportsForUser.getOrDefault(userCode, Collections.emptyList());
    }
}
