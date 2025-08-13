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

        // Controllo duplicato tra tutti gli utenti
        for (List<RailwayAsset> reports : allReports.values()) {
            for (RailwayAsset r : reports) {
                if (r.getAssetInfo().equalsIgnoreCase(instance.getAssetInfo()) &&
                        r.getLocation().equalsIgnoreCase(instance.getLocation())) {
                    throw new ReportAlreadyExistsException(
                            "The level crossing has been already reported by another user.");
                }
            }
        }

        // Aggiungi report dell’utente
        allReports.computeIfAbsent(userCode, k -> new ArrayList<>()).add(instance);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(allReports);
        }
    }
}
