package dao;

import eccezioni.ErroreLetturaPasswordException;
import eccezioni.SegnalazioneGiaAvvenutaException;
import entita.EntitaFerroviaria;
import entita.SemaforoFerroviario;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;


public class SemaforoDaoImplFileSystem implements EntitaFerroviariaDao {
    //dao che salva l'entità stradale, in questo caso un Semaforo illuminazione nel file system
    private static final String CSV_FILE_NAME = "SemaforoSegnalato.txt";
    @Override
    public void saveEntitaStradale(EntitaFerroviaria instance) throws SQLException, SegnalazioneGiaAvvenutaException, ErroreLetturaPasswordException {
        //se sono qui voglio salvare su file system il Semaforo
        SemaforoFerroviario Semaforo = new SemaforoFerroviario(instance.getInfo(), instance.getstazione(),instance.getDescrizioneProblema());
        //adesso devo salvarlo in locale
        try {
            //secondo argomento messo a true , spiegazione in BinarioDaoImplFileSystem
            BufferedWriter fileWriter=new BufferedWriter(new FileWriter(CSV_FILE_NAME,true));
            String SemaforoSegnalatoDaMandareNelFileTxt=convertiSemaforoInTxt(Semaforo);
            fileWriter.write(SemaforoSegnalatoDaMandareNelFileTxt);
            //spiegazione in BinarioDaoImplFileSystem per questo uso di newLine()
            fileWriter.newLine();
            fileWriter.close();
        } catch (IOException e) {
            throw new SQLException("problema con il file writer");
        }
    }
    private String convertiSemaforoInTxt(SemaforoFerroviario SemaforoSegnalato){
        return "Numero seriale semaforo segnalato: "+SemaforoSegnalato.getInfo()+"\nstazione: "+SemaforoSegnalato.getstazione()+"\nProblematica segnalata: " + SemaforoSegnalato.getDescrizioneProblema()+"\nstato: ancora non segnalato al database";
    }

}
