package dao;


import exception.ReportAlreadyExistsException;
import model.Track;
import model.RailwayAsset;
import utility.AccessUtility;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendTrackReportDaoFS implements SendReportDao {

    private static final String FILE_NAME = "ReportedTracks.ser";
    private int outcome;

    @Override
    @SuppressWarnings("unchecked")
    public void sendRailwayAssetReport(RailwayAsset instance)
            throws ReportAlreadyExistsException, IOException {

        Track track = new Track(instance.getAssetInfo(), instance.getLocation(), instance.getIssue());
        String userCode = AccessUtility.getUserCode();

        Map<String, List<RailwayAsset>> allReports = new HashMap<>();
        File file = new File(FILE_NAME);


        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                allReports = (Map<String, List<RailwayAsset>>) ois.readObject();
            } catch (ClassNotFoundException e) {
                throw new IOException("Error while reading file", e);
            } catch (EOFException e) {
                allReports = new HashMap<>();
            }
        }


        for (List<RailwayAsset> reports : allReports.values()) {
            for (RailwayAsset r : reports) {
                if (r.getAssetInfo().equalsIgnoreCase(track.getAssetInfo()) &&
                        r.getLocation().equalsIgnoreCase(track.getLocation())) {
                    throw new ReportAlreadyExistsException(
                            "The track has been already reported by another user.");
                }
            }
        }


        allReports.computeIfAbsent(userCode, k -> new ArrayList<>()).add(track);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(allReports);
            outcome = 0;
        } catch (IOException e) {
            outcome = 1;
            throw new IOException("Error while writing file", e);
        }
    }

    public int getEsito() {
        return outcome;
    }
}
