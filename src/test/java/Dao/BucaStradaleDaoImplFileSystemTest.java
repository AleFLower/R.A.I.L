package Dao;

import dao.SegnaleStradaleDaoImplFileSystem;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.SegnalazioneGiaAvvenutaException;
import entita.SegnaleStradale;
import entita.EntitaFerroviaria;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SegnaleStradaleDaoImplFileSystemTest {
    private SegnaleStradaleDaoImplFileSystem segnaleStradaleDaoImplFileSystem;

    @Test
    void saveSegnaleStradaleNelFileSystem(){
        //verifico che venga effettivamente salvata una Segnale stradale che sto segnalando nel fileSystem
        //in un formato .txt
        try {
            SegnaleStradaleDaoImplFileSystem = new SegnaleStradaleDaoImplFileSystem();
            EntitaFerroviaria entitaStradale = new SegnaleStradale("32", "viaa");
            segnaleStradaleDaoImplFileSystem.saveEntitaStradale(entitaStradale);
        }catch(SegnalazioneGiaAvvenutaException |SQLException |ErroreLetturaPasswordException |IOException e){
            //niente da fare qui
        }finally{
            //se la segnalazione è andata a buon fine esito varrà 0, quindi ho scritto sul file SegnaleStradaleSegnalata.txt
            assertEquals(0, segnaleStradaleDaoImplFileSystem.getEsito());
        }
    }

}