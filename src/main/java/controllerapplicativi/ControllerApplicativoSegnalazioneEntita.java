package controllerapplicativi;

import bean.BeanSegnalaEntita;
import dao.*;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.NessunAccessoEffettuatoException;
import eccezioni.SegnalazioneGiaAvvenutaException;
import eccezioni.TipoEntitaException;
import entita.EntitaFerroviaria;
import factory.FactoryDao;
import factory.FactoryEntitaFerroviaria;
import factory.TypeEntita;
import factory.TypeOfPersistence;
import utility.UtilityAccesso;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerApplicativoSegnalazioneEntita {
    /*questo controller applicativo viene chiamato quanod sto inviando una nuova segnalazione di un entita al db,
    * crea quindi un entita dai dati che riceve, chiama il dao che gestisce la dovuta segnalazione e in caso di errori
    * lancia delle eccezioni, al controller grafico non ritorna nulla */
    private  String stazione;
    private  String infoEntita;
    private String problematica;
    private TypeEntita tipoEntita;
    private EntitaFerroviaria entitaStradale;
    private FactoryDao dao;
    public ControllerApplicativoSegnalazioneEntita(BeanSegnalaEntita beanSegnalaEntita) throws SQLException, ErroreLetturaPasswordException, SegnalazioneGiaAvvenutaException, NessunAccessoEffettuatoException, TipoEntitaException, IOException {
        /*il controller applicativo riceve il bean che contiene le informazioni dell'entita segnalata, setta quindi tutti i
        * suoi parametri prendendoli dal bean*/
        //inizio prendendo il tipo dell'entità segnalata
        this.tipoEntita=beanSegnalaEntita.getTipoEntitaSegnalata();
        //vedo se è una binario e se l'utente è online, cosi in caso contrario lo blocco subito
        if(tipoEntita==TypeEntita.BINARIO && UtilityAccesso.getAccount().getStatoAttuale().equals("OFFLINE")){
            //l'utente cerca di segnalare una binario ma non e' registrato quindi viene lanciata un eccezione che gli
            //dice che la segnalazione della binario puo' essere fatta solo se registrato
            throw new NessunAccessoEffettuatoException("per segnalare un binario devi essere registrato");
        }
        this.stazione=beanSegnalaEntita.getstazione();
        this.infoEntita=beanSegnalaEntita.getInfoEntita();
        this.problematica = beanSegnalaEntita.getDescrizioneProblema();
        //creo la factory che deve creare a sua volta la mia entita in base al tipo
        FactoryEntitaFerroviaria factoryEntitaStradale=new FactoryEntitaFerroviaria();
        //chiamo il metodo createEntita che in base al tipo che gli passo crea un Semaforo  o una binario stradale
        entitaStradale=factoryEntitaStradale.createEntita(this.tipoEntita,this.stazione,this.infoEntita,this.problematica);
        //nessuna eccezione creata, l'entita stradale e' stata quindi creata, devo inviarla alla rispettiva tabella nel db
        //nessuna eccezione devo inviare l'entità stradale segnalata e per capire se deve essere salvata sul db o in locale
        //passo come argomento il tipo di persistenza che il controller grafico ha inserito nel mio bean nel momento della segnalazione

        inviaSegnalazione(entitaStradale,beanSegnalaEntita.getTypeOfPersistence());
    }

    private void inviaSegnalazione(EntitaFerroviaria entitaStradale, TypeOfPersistence typeOfPersistence) throws SQLException, ErroreLetturaPasswordException, SegnalazioneGiaAvvenutaException, IOException {
        //creo una factoryDao la quale ha solo il metodo useDao e mi restituisce un dao in base al tipo di entita stradale e al
        //tipo di persistenza che ho ricevuto come parametri
        FactoryDao factoryDao= FactoryDao.getFactory(typeOfPersistence);
        EntitaFerroviariaDao entitaStradaleDao=factoryDao.useDao(typeOfPersistence,entitaStradale.getTipoEntita());
        entitaStradaleDao.saveEntitaStradale(entitaStradale);

    }
}
