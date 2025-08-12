package dao;

import bean.ReportTrackBean;
import bean.ReportLevelCrossingBean;
import com.example.progettoispw.controllergrafici.ReportType;
import utility.AccessUtility;

import java.io.*;
import java.util.*;

public class ActiveResolvedReportsDaoFS implements ActiveResolvedReportsDao {

    private static final String TRACK_FILE = "ReportedTracks.txt";
    private static final String LC_FILE = "ReportedLevelCrossing.txt";
    @Override
    public List<ReportLevelCrossingBean> getLevelCrossinReports(ReportType type)
            throws IOException {

        List<ReportLevelCrossingBean> list = new ArrayList<>();
        File file = new File(LC_FILE);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String lcCode = null;
            String location = null;
            String issue = null;
            String state = null;
            String userCode = null;

            String actualUserCode = AccessUtility.getUserCode();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Level crossing code: ")) {
                    lcCode = line.split(": ", 2)[1].trim();
                } else if (line.startsWith("location:")) {
                    location = line.split(": ", 2)[1].trim();
                } else if (line.startsWith("Issue:")) {
                    issue = line.split(": ", 2)[1].trim();
                } else if (line.startsWith("state:")) {
                    state = line.split(": ", 2)[1].trim().toLowerCase();
                } else if (line.startsWith("user code:")) {
                    userCode = line.split(": ", 2)[1].trim();
                    if (userCode.equals(actualUserCode) && typeMatch(type, state)) {
                        list.add(new ReportLevelCrossingBean(lcCode, location, issue, state));
                    }
                }
            }
        }

        return list;
    }

    @Override
    public List<ReportTrackBean> getTrackReports(ReportType type)
            throws IOException {

        List<ReportTrackBean> list = new ArrayList<>();
        File file = new File(TRACK_FILE);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String trackNumber = null;
            String location = null;
            String issue = null;
            String state = null;
            String userCode = null;

            String actualUserCode = AccessUtility.getUserCode();

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
                    userCode = line.split(": ", 2)[1].trim();
                    if (userCode.equals(actualUserCode) && typeMatch(type, state)) {
                        list.add(new ReportTrackBean(trackNumber, location, issue, state));
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
