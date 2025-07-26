package dao;


import eccezioni.SegnalazioneGiaAvvenutaException;
import entita.Binario;
import entita.EntitaFerroviaria;
import utility.UtilityAccesso;

import java.io.*;
public class BinarioDaoImplFileSystem implements EntitaFerroviariaDao {
    private static final String FILE_NAME = "BinarioSegnalato.txt";
    private int esitoSalvataggio;

    @Override
    public void saveEntitaStradale(EntitaFerroviaria instance)
            throws SegnalazioneGiaAvvenutaException,IOException {
        Binario binario = new Binario(instance.getInfo(), instance.getlocalizzazione(), instance.getDescrizioneProblema());

        // Controllo duplicato
        if (controllaEsistenzaBinario(binario.getInfo(), binario.getlocalizzazione())) {
            throw new SegnalazioneGiaAvvenutaException("Questo binario è già stato segnalato da un altro utente.");
        }

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String binarioSegnalataDaSalvareInLocale = convertibinarioInTxt(binario);
            fileWriter.write(binarioSegnalataDaSalvareInLocale);
            fileWriter.newLine();
            esitoSalvataggio = 0;
        } catch (IOException e) {
            esitoSalvataggio = 1;
            throw new IOException("problema con il file writer");
        }
    }

    private String convertibinarioInTxt(Binario binario) {
        return "Numero binario:  " + binario.getInfo() +
                "\nlocalizzazione: " + binario.getlocalizzazione() +
                "\nproblematica riscontrata: " + binario.getDescrizioneProblema() +
                "\nstato: " + binario.getStato() +
                "\ncodiceUtente: " + UtilityAccesso.getCodiceUtente();
    }

    public int getEsito() {
        return this.esitoSalvataggio;
    }

    private boolean controllaEsistenzaBinario(String numero, String localizzazione) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String numeroLetto = null;
            String localizzazioneLetta = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Numero binario:")) {
                    numeroLetto = line.split(":")[1].trim();
                } else if (line.startsWith("localizzazione:")) {
                    localizzazioneLetta = line.split(":")[1].trim();
                    if (numeroLetto != null && localizzazioneLetta != null &&
                            numeroLetto.equalsIgnoreCase(numero) &&
                            localizzazioneLetta.equalsIgnoreCase(localizzazione)) {
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
