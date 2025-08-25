package dao;

import exception.ReportAlreadyExistsException;
import model.RailwayAsset;
import utility.AccessUtility;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendReportDaoFS implements SendReportDao {

    private static final String TRACK_FILE = "ReportedTracks.ser";
    private static final String LEVELCROSSING_FILE = "ReportedLevelCrossing.ser";

    private int outcome;

    @Override
    @SuppressWarnings("unchecked")
    public void sendRailwayAssetReport(RailwayAsset railwayAsset)
            throws ReportAlreadyExistsException, IOException {

        switch (railwayAsset.getAssetType()) {
            case TRACK -> handleTrack(railwayAsset);
            case LEVELCROSSING -> handleLevelCrossing(railwayAsset);
            default -> throw new IllegalArgumentException("Unsupported asset type: " + railwayAsset.getAssetType());
        }
    }

    private void handleTrack(RailwayAsset track) throws IOException, ReportAlreadyExistsException {
        Map<String, List<RailwayAsset>> allReports = readReports(TRACK_FILE);
        String userCode = AccessUtility.getUserCode();

        for (List<RailwayAsset> reports : allReports.values()) {
            for (RailwayAsset r : reports) {
                if (r.getAssetInfo().equalsIgnoreCase(track.getAssetInfo())
                        && r.getLocation().equalsIgnoreCase(track.getLocation())) {
                    outcome = -1;
                    throw new ReportAlreadyExistsException("The track has already been reported.");
                }
            }
        }

        allReports.computeIfAbsent(userCode, k -> new ArrayList<>()).add(track);
        writeReports(TRACK_FILE, allReports);
        outcome = 0;
    }

    private void handleLevelCrossing(RailwayAsset lc) throws IOException, ReportAlreadyExistsException {
        Map<String, List<RailwayAsset>> allReports = readReports(LEVELCROSSING_FILE);
        String userCode = AccessUtility.getUserCode();

        for (List<RailwayAsset> reports : allReports.values()) {
            for (RailwayAsset r : reports) {
                if (r.getAssetInfo().equalsIgnoreCase(lc.getAssetInfo())) {
                    outcome = -1;
                    throw new ReportAlreadyExistsException("The level crossing has already been reported.");
                }
            }
        }

        allReports.computeIfAbsent(userCode, k -> new ArrayList<>()).add(lc);
        writeReports(LEVELCROSSING_FILE, allReports);
        outcome = 0;
    }

    @SuppressWarnings("unchecked")
    private Map<String, List<RailwayAsset>> readReports(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists() || file.length() == 0) return new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<String, List<RailwayAsset>>) ois.readObject();
        } catch (ClassNotFoundException | EOFException e) {
            return new HashMap<>();
        }
    }

    private void writeReports(String fileName, Map<String, List<RailwayAsset>> reports) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(reports);
        }
    }

    public int getOutcome() {
        return this.outcome;
    }
}

