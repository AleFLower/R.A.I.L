package Dao;

import dao.SegnaleStradaleDaoImplJDBC;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.SegnalazioneGiaAvvenutaException;
import entita.SegnaleStradale;
import entita.EntitaFerroviaria;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SegnaleStradaleDaoImplJDBCTest {
    SegnaleStradaleDaoImplJDBC SegnaleStradaleDaoImp;

    @Test
    void saveSegnaleStradaleNonPresenteNelDb() {
        //test per salvare una Segnale stradale che non e' presente nel db
        try{
            SegnaleStradaleDaoImp=new SegnaleStradaleDaoImplJDBC();
            EntitaFerroviaria entitaStradale = new SegnaleStradale("34", "vdaapoi");
            SegnaleStradaleDaoImp.saveEntitaStradale(entitaStradale);
        }catch(SegnalazioneGiaAvvenutaException | SQLException | ErroreLetturaPasswordException e){
            //niente da fare qui
        }finally {
            assertEquals(0,SegnaleStradaleDaoImp.getEsito());
        }
    }
    @Test
    void saveSegnaleStradaleGiaPresenteNelDb(){
        //test che salva nel db una Segnale stradale che già esiste nel db, quindi dovrà essere -1 perche
        //per come e' costruito il sistema non posso inserire 2 buche stradali identiche
        try{
            SegnaleStradaleDaoImp = new SegnaleStradaleDaoImplJDBC();
            EntitaFerroviaria entitaStradale = new SegnaleStradale("16", "j");
            SegnaleStradaleDaoImp.saveEntitaStradale(entitaStradale);
        }catch(SegnalazioneGiaAvvenutaException | SQLException | ErroreLetturaPasswordException e){
            //niente da fare qui
        }finally {
            assertEquals(-1, SegnaleStradaleDaoImp.getEsito());
        }
    }
}