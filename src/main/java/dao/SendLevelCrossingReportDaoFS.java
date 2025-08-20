package dao;

import exception.ReportAlreadyExistsException;
import model.RailwayAsset;
import utility.AccessUtility;

import java.io.*;
import java.util.*;

public class SendLevelCrossingReportDaoFS implements SendReportDao {

    private static final String FILE_NAME = "ReportedLevelCrossing.ser";

    @Override
    @SuppressWarnings("unchecked")
    public void sendRailwayAssetReport(RailwayAsset instance)
            throws ReportAlreadyExistsException, IOException {

        String userCode = AccessUtility.getUserCode();
        Map<String, List<RailwayAsset>> allReports = readReports();


        for (List<RailwayAsset> reports : allReports.values()) {
            for (RailwayAsset r : reports) {
                if (r.getAssetInfo().equalsIgnoreCase(instance.getAssetInfo())) {
                    throw new ReportAlreadyExistsException(
                            "The level crossing has been already reported by another user.");
                }
            }
        }


        allReports.computeIfAbsent(userCode, k -> new ArrayList<>()).add(instance);

        writeReports(allReports);
    }

    private Map<String, List<RailwayAsset>> readReports() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) return new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<String, List<RailwayAsset>>) ois.readObject();
        } catch (ClassNotFoundException | EOFException e) {
            return new HashMap<>();
        }
    }

    private void writeReports(Map<String, List<RailwayAsset>> reports) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(reports);
        }
    }
}