package dao;

import eccezioni.ErroreLetturaPasswordException;
import eccezioni.SegnalazioneGiaAvvenutaException;
import entita.EntitaFerroviaria;
import entita.LevelCrossing;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;


public class PassaggioLivelloDaoImplFileSystem implements EntitaFerroviariaDao {
    //dao che salva l'entità stradale, in questo caso un passaggio a livello nel file system
    private static final String CSV_FILE_NAME = "LevelCrossingSegnalato.txt";
    @Override
    public void saveEntitaStradale(EntitaFerroviaria instance) throws SQLException, SegnalazioneGiaAvvenutaException, ErroreLetturaPasswordException {
        //se sono qui voglio salvare su file system il levelCrossing
        LevelCrossing levelCrossing = new LevelCrossing(instance.getInfo(), instance.getlocalizzazione(),instance.getDescrizioneProblema());
        //adesso devo salvarlo in locale
        try {
            //secondo argomento messo a true , spiegazione in BinarioDaoImplFileSystem
            BufferedWriter fileWriter=new BufferedWriter(new FileWriter(CSV_FILE_NAME,true));
            String levelCrossingDaMandareNelFileTxt= convertiLevelCrossingInTxt(levelCrossing);
            fileWriter.write(levelCrossingDaMandareNelFileTxt);
            //spiegazione in BinarioDaoImplFileSystem per questo uso di newLine()
            fileWriter.newLine();
            fileWriter.close();
        } catch (IOException e) {
            throw new SQLException("problema con il file writer");
        }
    }
    private String convertiLevelCrossingInTxt(LevelCrossing levelCrossing){
        return "Numero del passaggio a livello: "+levelCrossing.getInfo()+"\nlocalizzazione: "+levelCrossing.getlocalizzazione()+"\nProblematica segnalata: " + levelCrossing.getDescrizioneProblema()+"\nstato: ancora non segnalato al database";
    }

}
