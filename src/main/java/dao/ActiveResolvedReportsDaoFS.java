package dao;


import com.example.progettoispw.graphiccontroller.ReportType;
import model.LevelCrossing;
import model.RailwayAsset;
import model.Track;
import utility.AccessUtility;

import java.io.*;
import java.util.*;

public class ActiveResolvedReportsDaoFS implements ActiveResolvedReportsDao {

    private static final String TRACK_FILE = "ReportedTracks.txt";
    private static final String LC_FILE = "ReportedLevelCrossing.txt";

    @Override
    public List<RailwayAsset> getReports(ReportType type) throws IOException {
        List<RailwayAsset> list = new ArrayList<>();
        String actualUserCode = AccessUtility.getUserCode();

        list.addAll(readLevelCrossingReports(type, actualUserCode));
        list.addAll(readTrackReports(type, actualUserCode));

        return list;
    }

    private List<RailwayAsset> readLevelCrossingReports(ReportType type, String userCode) throws IOException {
        List<RailwayAsset> list = new ArrayList<>();
        File lcFile = new File(LC_FILE);

        if (!lcFile.exists()) {
            // File non trovato, ritorno lista vuota
            return list;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(lcFile))) {
            String line;
            String lcCode = null;
            String location = null;
            String issue = null;
            String state = null;
            String fileUserCode = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Level crossing code: ")) {
                    lcCode = line.split(": ", 2)[1].trim();
                } else if (line.startsWith("location:")) {
                    location = line.split(": ", 2)[1].trim();
                } else if (line.startsWith("issue:")) {
                    issue = line.split(": ", 2)[1].trim();
                } else if (line.startsWith("state:")) {
                    state = line.split(": ", 2)[1].trim().toLowerCase();
                } else if (line.startsWith("userCode:")) {
                    fileUserCode = line.split(": ", 2)[1].trim();
                    if (fileUserCode.equals(userCode) && typeMatch(type, state)) {
                        RailwayAsset lc = new LevelCrossing(lcCode, location, issue);
                        lc.setState(state);
                        list.add(lc);
                    }
                }
            }
        }
        return list;
    }

    private List<RailwayAsset> readTrackReports(ReportType type, String userCode) throws IOException {
        List<RailwayAsset> list = new ArrayList<>();
        File trackFile = new File(TRACK_FILE);

        if (!trackFile.exists()) {
            // File non trovato, ritorno lista vuota
            return list;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(trackFile))) {
            String line;
            String trackNumber = null;
            String location = null;
            String issue = null;
            String state = null;
            String fileUserCode = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Track number:")) {
                    trackNumber = line.split(": ", 2)[1].trim();
                } else if (line.startsWith("location:")) {
                    location = line.split(": ", 2)[1].trim();
                } else if (line.startsWith("issue:")) {
                    issue = line.split(": ", 2)[1].trim();
                } else if (line.startsWith("state:")) {
                    state = line.split(": ", 2)[1].trim().toLowerCase();
                } else if (line.startsWith("userCode:")) {
                    fileUserCode = line.split(": ", 2)[1].trim();
                    if (fileUserCode.equals(userCode) && typeMatch(type, state)) {
                        RailwayAsset track = new Track(trackNumber, location, issue);
                        track.setState(state);
                        list.add(track);
                    }
                }
            }
        }
        return list;
    }

    private boolean typeMatch(ReportType type, String readState) {
        if (type == ReportType.RESOLVED)
            return readState.equalsIgnoreCase("resolved");
        else
            return readState.equalsIgnoreCase("reported") || readState.contains("yet");
    }
}
