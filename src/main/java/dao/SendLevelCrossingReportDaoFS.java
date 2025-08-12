package dao;

import exception.ReportAlreadyExistsException;
import model.RailwayAsset;
import model.LevelCrossing;
import utility.AccessUtility;

import java.io.*;

public class SendLevelCrossingReportDaoFS implements SendReportDao {
    private static final String CSV_FILE_NAME = "ReportedLevelCrossing.txt";

    @Override
    public void sendRailwayAssetReport(RailwayAsset instance)
            throws ReportAlreadyExistsException, IOException {
        LevelCrossing levelCrossing = new LevelCrossing(instance.getAssetInfo(), instance.getLocation(), instance.getIssue());

        // Controllo duplicato
        if (verifyExistance(levelCrossing.getAssetInfo())) {
            throw new ReportAlreadyExistsException("The level crossing has been already reported by another user.");
        }

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(CSV_FILE_NAME, true))) {
            String levelCrossingToReport = convertLevelCrossingInTxt(levelCrossing);
            fileWriter.write(levelCrossingToReport);
            fileWriter.newLine();
        } catch (IOException e) {
            //sql exception? It is not right!
            throw new IOException("There are problems with file writer");
        }
    }

    private String convertLevelCrossingInTxt(LevelCrossing levelCrossing) {
        return "Code of the level crossing: " + levelCrossing.getAssetInfo() +
                "\nlocation: " + levelCrossing.getLocation() +
                "\nissue: " + levelCrossing.getIssue() +
                "\nstate: " + levelCrossing.getState() +
                "\nuserCode: " + AccessUtility.getUserCode();
    }

    private boolean verifyExistance(String lcCode) {
        File file = new File(CSV_FILE_NAME);
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Code of the level crossing:")) {
                    String readCode = line.split(": ", 2)[1].trim();
                    if (readCode.equalsIgnoreCase(lcCode)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            // Silenzio in lettura, non blocchiamo il flusso
        }

        return false;
    }
}
