package dao;

import eccezioni.ErroreLetturaPasswordException;
import eccezioni.SegnalazioneGiaAvvenutaException;
import entita.EntitaFerroviaria;
import entita.LevelCrossing;
import utility.UtilityAccesso;

import java.io.*;
import java.sql.SQLException;


public class PassaggioLivelloDaoImplFileSystem implements EntitaFerroviariaDao {
    private static final String CSV_FILE_NAME = "LevelCrossingSegnalato.txt";

    @Override
    public void saveEntitaStradale(EntitaFerroviaria instance)
            throws SQLException, SegnalazioneGiaAvvenutaException, ErroreLetturaPasswordException {
        LevelCrossing levelCrossing = new LevelCrossing(instance.getInfo(), instance.getlocalizzazione(), instance.getDescrizioneProblema());

        // Controllo duplicato
        if (controllaEsistenzaLevelCrossing(levelCrossing.getInfo())) {
            throw new SegnalazioneGiaAvvenutaException("Questo passaggio a livello è già stato segnalato da un altro utente.");
        }

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(CSV_FILE_NAME, true))) {
            String levelCrossingDaMandareNelFileTxt = convertiLevelCrossingInTxt(levelCrossing);
            fileWriter.write(levelCrossingDaMandareNelFileTxt);
            fileWriter.newLine();
        } catch (IOException e) {
            throw new SQLException("problema con il file writer");
        }
    }

    private String convertiLevelCrossingInTxt(LevelCrossing levelCrossing) {
        return "Numero del passaggio a livello: " + levelCrossing.getInfo() +
                "\nlocalizzazione: " + levelCrossing.getlocalizzazione() +
                "\nProblematica segnalata: " + levelCrossing.getDescrizioneProblema() +
                "\nstato: " + levelCrossing.getStato() +
                "\ncodiceUtente: " + UtilityAccesso.getCodiceUtente();
    }

    private boolean controllaEsistenzaLevelCrossing(String codicePL) {
        File file = new File(CSV_FILE_NAME);
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Numero del passaggio a livello:")) {
                    String codiceLetto = line.split(": ", 2)[1].trim();
                    if (codiceLetto.equalsIgnoreCase(codicePL)) {
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
