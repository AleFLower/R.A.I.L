package dao;


import com.example.progettoispw.graphiccontroller.ReportType;
import model.RailwayAsset;

import utility.AccessUtility;

import java.io.*;
import java.util.*;


public class ActivefixedReportsDaoFS implements ActivefixedReportsDao {

    private static final String TRACK_FILE = "ReportedTracks.ser";
    private static final String LC_FILE = "ReportedLevelCrossing.ser";

    @Override
    public List<RailwayAsset> getReports(ReportType type) throws IOException {
        String actualUserCode = AccessUtility.getUserCode();
        List<RailwayAsset> list = new ArrayList<>();

        list.addAll(readReportsFromFile(LC_FILE, type, actualUserCode));
        list.addAll(readReportsFromFile(TRACK_FILE, type, actualUserCode));

        return list;
    }

    @SuppressWarnings("unchecked")
    private List<RailwayAsset> readReportsFromFile(String fileName, ReportType type, String userCode) throws IOException {
        File file = new File(fileName);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            Map<String, List<RailwayAsset>> allReports = (Map<String, List<RailwayAsset>>) ois.readObject();

            List<RailwayAsset> userReports = allReports.getOrDefault(userCode, new ArrayList<>());
            List<RailwayAsset> filtered = new ArrayList<>();

            for (RailwayAsset asset : userReports) {
                if (typeMatch(type, asset.getState().toString())) {
                    filtered.add(asset);
                }
            }
            return filtered;

        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            throw new IOException("Error while reading file", e);
        }
    }

    private boolean typeMatch(ReportType type, String readState) {
        if (type == ReportType.FIXED)
            return "fixed".equalsIgnoreCase(readState);
        else
            return "reported".equalsIgnoreCase(readState) || readState.toLowerCase().contains("yet");
    }
}


