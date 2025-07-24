package queries;


/*
Ipotesi livello db(e non solo ) per passaggio a livello: molto semplicemente, suppongo che vi sia un univoco
codicePL in tutta la nazione, quindi non ci sono problemi di ambiguità. Inoltre, si suppone per la memory che nello
stesso istante di uso dell'app, è molto improbabile che venga segnalato un ulteriore problema per quel passaggio a livello
mentre per db, una volta risolto, si presuppone che l'admin cancelli dal db delle segnalate la entry per quella segnalazione
e dunque in futuro un altro utente possa segnalarla. Stessa cosa per i binari.

*/

public class QueriesSegnalazionePassaggioLivello {

    //questa classe svolgerà il ruolo di utility class, avrà solo metodi statici che restituiscono stringhe
    //che altro non sono che le query che posso fare sul mio oggetto LEVELCROSSING
    //nota nelle query il termine 'LEVELCROSSING' e' il nome della relazione presente nel database "database-app-ispw"
    //quetse sono dei prepare statment, i valori nei ? verranno settati nella classe che vuole svolgete la query
    static final String QUERIES_PRENDI_LEVELCROSSING="SELECT * FROM LEVELCROSSING where codicePL=?";
    static final String QUERIES_SALVA_LEVELCROSSING="INSERT INTO LEVELCROSSING(codicePL,localizzazione,problematica) VALUES(?,?,?);";
    static final String QUERIES_SALVA_LEVELCROSSING_AD_UTENTE="INSERT INTO LEVELCROSSING(codicePL,localizzazione,problematica,codiceUtente) VALUES(?,?,?,?);";
    static final String QUERIES_RIMUOVI_LEVELCROSSING="DELETE  FROM LEVELCROSSING where (codicePL=? AND localizzazione=?);";
    static final String QUERIES_RESTITUISCI_LEVELCROSSING="SELECT * FROM LEVELCROSSING";
    static final String QUERIES_CAMBIA_STATO_LEVELCROSSING="UPDATE LEVELCROSSING SET stato = ? WHERE (codicePL=? AND localizzazione=?);";
    static final String QUERIES_CERCA_LEVELCROSSING="SELECT * FROM LEVELCROSSING WHERE (codicePL=?);";
    static final String QUERIES_MOSTRA_SEGNALAZIONI_EFFETTUATE="SELECT DISTINCT codicePL,localizzazione,problematica, stato FROM LEVELCROSSING,account WHERE (LEVELCROSSING.codiceUtente=? AND stato = 'segnalato');";
    static final String QUERIES_MOSTRA_TUTTE_SEGNALAZIONI_PASSAGGIOALIVELLO = "SELECT * FROM LEVELCROSSING;";
    //provala questa delle completate prima
    static final String QUERIES_MOSTRA_SEGNALAZIONI_COMPLETATE="SELECT DISTINCT codicePL,localizzazione,problematica,stato FROM LEVELCROSSING,account WHERE (LEVELCROSSING.codiceUtente=? AND stato = 'risolto');";
    private QueriesSegnalazionePassaggioLivello(){
        //NON FA NULLA PERCHE LA CLASSE DEVE FORNIRE SOLO METODI STATICI E PUBBLICI
    }
    public static String queriesPrendiLEVELCROSSING(){
        return QUERIES_PRENDI_LEVELCROSSING;
    }
    public static String getQueriesSalvaLevelcrossing(){return QUERIES_SALVA_LEVELCROSSING;}
    public static String getQueriesRimuoviLevelcrossing(){return QUERIES_RIMUOVI_LEVELCROSSING;}
    public static String  getQueriesRestituisciLevelcrossing(){return QUERIES_RESTITUISCI_LEVELCROSSING;}
    public static String getQueriesCambiaStatoLevelcrossing(){return QUERIES_CAMBIA_STATO_LEVELCROSSING;}
    public static String getQueriesCercaLevelcrossing(){return QUERIES_CERCA_LEVELCROSSING;}

    //queries che salva un LEVELCROSSING se l'utente si e' loggato
    public static String queriesSalvaLevelCrossingAdUnUtenteDelSistema(){return QUERIES_SALVA_LEVELCROSSING_AD_UTENTE;}

    //queries per vedere i passaggi a livello che gli utenti loggati hanno segnalato e vogliono controllare lo stato della segnalazione
    public static String queriesMostraSegnalazioniEffettuate(){
        return QUERIES_MOSTRA_SEGNALAZIONI_EFFETTUATE;
    }
    public static String queriesMostraSegnalazioniCompletate(){return QUERIES_MOSTRA_SEGNALAZIONI_COMPLETATE;}
}
