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
    //che altro non sono che le query che posso fare sul mio oggetto levelCrossing
    //nota nelle query il termine 'levelCrossing' e' il nome della relazione presente nel database "database-app-ispw"
    //quetse sono dei prepare statment, i valori nei ? verranno settati nella classe che vuole svolgete la query
    static final String QUERIES_PRENDI_levelCrossing="SELECT * FROM levelCrossing where codicePL=?";
    static final String QUERIES_SALVA_levelCrossing="INSERT INTO levelCrossing(codicePL,localizzazione,problematica) VALUES(?,?,?);";
    static final String QUERIES_SALVA_levelCrossing_Ad_Utente="INSERT INTO levelCrossing(codicePL,localizzazione,problematica,codiceUtente) VALUES(?,?,?,?);";
    static final String QUERIES_RIMUOVI_levelCrossing="DELETE  FROM levelCrossing where (codicePL=? AND localizzazione=?);";
    static final String QUERIES_RESTITUISCI_levelCrossing="SELECT * FROM levelCrossing";
    static final String QUERIES_CAMBIA_STATO_levelCrossing="UPDATE levelCrossing SET stato = ? WHERE (codicePL=? AND localizzazione=?);";
    static final String QUERIES_CERCA_levelCrossing="SELECT * FROM levelCrossing WHERE (codicePL=?);";
    static final String QUERIES_MOSTRA_SEGNALAZIONI_EFFETTUATE="SELECT DISTINCT codicePL,localizzazione,problematica, stato FROM levelCrossing,account WHERE (levelCrossing.codiceUtente=? AND stato = 'segnalato');";
    static final String QUERIES_MOSTRA_TUTTE_SEGNALAZIONI_passaggioALivello = "SELECT * FROM levelCrossing;";
    //provala questa delle completate prima
    static final String QUERIES_MOSTRA_SEGNALAZIONI_COMPLETATE="SELECT DISTINCT codicePL,localizzazione,problematica,stato FROM levelCrossing,account WHERE (levelCrossing.codiceUtente=? AND stato = 'risolto');";
    private QueriesSegnalazionePassaggioLivello(){
        //NON FA NULLA PERCHE LA CLASSE DEVE FORNIRE SOLO METODI STATICI E PUBBLICI
    }
    public static String queriesPrendilevelCrossing(){
        return QUERIES_PRENDI_levelCrossing;
    }
    public static String queriesSalvalevelCrossing(){return QUERIES_SALVA_levelCrossing;}
    public static String queriesRimuovilevelCrossing(){return QUERIES_RIMUOVI_levelCrossing;}
    public static String  restituiscilevelCrossing(){return QUERIES_RESTITUISCI_levelCrossing;}
    public static String cambiaStatolevelCrossing(){return QUERIES_CAMBIA_STATO_levelCrossing;}
    public static String cercalevelCrossing(){return QUERIES_CERCA_levelCrossing;}

    //queries che salva un levelCrossing se l'utente si e' loggato
    public static String queriesSalvalevelCrossingAdUnUtenteDelSistema(){return QUERIES_SALVA_levelCrossing_Ad_Utente;}

    //queries per vedere i passaggi a livello che gli utenti loggati hanno segnalato e vogliono controllare lo stato della segnalazione
    public static String queriesMostraSegnalazioniEffettuate(){
        return QUERIES_MOSTRA_SEGNALAZIONI_EFFETTUATE;
    }
    public static String queriesMostraSegnalazioniCompletate(){return QUERIES_MOSTRA_SEGNALAZIONI_COMPLETATE;}
}
