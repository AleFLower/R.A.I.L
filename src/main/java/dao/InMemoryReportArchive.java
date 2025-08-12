package dao;


import exception.ReportAlreadyExistsException;

import model.Track;
import model.RailwayAsset;
import model.LevelCrossing;

import java.util.*;

public class InMemoryReportArchive {

    private static final List<LevelCrossing> reportedLevelCrossing = new ArrayList<>();
    private static final List<Track> reportedTracks = new ArrayList<>();

    //per memorizzare codice utente alle segnalazioni in memory
    private static final Map<String, List<RailwayAsset>> reportsForUser = new HashMap<>();

    public static void sendLevelCrossingReport(LevelCrossing newLcCode, String userCode) throws ReportAlreadyExistsException {
        for (LevelCrossing pl : reportedLevelCrossing) {
            if (pl.getAssetInfo().equals(newLcCode.getAssetInfo())) {
                throw new ReportAlreadyExistsException("Level Crossing already reported ");
            }
        }
        reportedLevelCrossing.add(newLcCode);
        registrateReport(userCode, newLcCode);
    }


    public static void sendTrackReport(Track newTrack, String userCode) throws ReportAlreadyExistsException {
        for (Track b : reportedTracks) {
            if (b.getAssetInfo().equals(newTrack.getAssetInfo())
                    && b.getLocation().equals(newTrack.getLocation())) {
                throw new ReportAlreadyExistsException("Track with same number and location already reported.");
            }
        }
        reportedTracks.add(newTrack);
        registrateReport(userCode, newTrack);
    }


    private static void registrateReport(String userCode, RailwayAsset railwayAsset) {
        reportsForUser.computeIfAbsent(userCode, k -> new ArrayList<>()).add(railwayAsset);
    }

    public static List<RailwayAsset> getReportsForUser(String userCode) {
        return reportsForUser.getOrDefault(userCode, Collections.emptyList());
    }
}


