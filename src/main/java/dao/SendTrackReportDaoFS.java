package dao;


import exception.ReportAlreadyExistsException;
import model.Track;
import model.RailwayAsset;
import utility.AccessUtility;

import java.io.*;
public class SendTrackReportDaoFS implements SendReportDao {
    private static final String FILE_NAME = "ReportedTracks.txt";
    private int outcome;

    @Override
    public void sendRailwayAssetReport(RailwayAsset instance)
            throws ReportAlreadyExistsException,IOException {
        Track track = new Track(instance.getAssetInfo(), instance.getLocation(), instance.getIssue());

        // Controllo duplicato
        if (verifyExistance(track.getAssetInfo(), track.getLocation())) {
            throw new ReportAlreadyExistsException("The track has been already reported by another user.");
        }

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String trackToReport = convertTrackInTxt(track);
            fileWriter.write(trackToReport);
            fileWriter.newLine();
            outcome = 0;
        } catch (IOException e) {
            outcome = 1;
            throw new IOException("There are problems with file writer");
        }
    }

    private String convertTrackInTxt(Track track) {
        return "Track number:  " + track.getAssetInfo() +
                "\nlocation: " + track.getLocation() +
                "\nissue: " + track.getIssue() +
                "\nstate: " + track.getState() +
                "\nuserCode: " + AccessUtility.getUserCode();
    }

    public int getEsito() {
        return this.outcome;
    }

    private boolean verifyExistance(String trackNumber, String location) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String readNumber = null;
            String readLocation = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Track number:")) {
                    readNumber = line.split(":")[1].trim();
                } else if (line.startsWith("location:")) {
                    readLocation = line.split(":")[1].trim();
                    if (readNumber != null && readLocation != null &&
                            readNumber.equalsIgnoreCase(trackNumber) &&
                            readLocation.equalsIgnoreCase(location)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            // Silenzio in lettura
        }

        return false;
    }
}
