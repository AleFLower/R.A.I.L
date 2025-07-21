package queries;

public class QueriesSemaforo {

    //questa classe svolgerà il ruolo di utility class, avrà solo metodi statici che restituiscono stringhe
    //che altro non sono che le query che posso fare sul mio oggetto Semaforo Illuminazione
    //nota nelle query il termine 'Semaforo' e' il nome della relazione presente nel database "database-app-ispw"
    //quetse sono dei prepare statment, i valori nei ? verranno settati nella classe che vuole svolgete la query
    static final String QUERIES_PRENDI_Semaforo="SELECT * FROM Semaforo where numeroSeriale=?";
    static final String QUERIES_SALVA_Semaforo="INSERT INTO Semaforo(numeroSeriale,stazione,problematica,codiceUtente) VALUES(?,?,?,?);";
    static final String QUERIES_RIMUOVI_Semaforo="DELETE  FROM Semaforo where (numeroSeriale=? AND stazione=?);";
    static final String QUERIES_RESTITUISCI_Semaforo="SELECT * FROM Semaforo";
    static final String QUERIES_CAMBIA_STATO_Semaforo="UPDATE Semaforo SET stato = ? WHERE (numeroSeriale=? AND stazione=?);";
    static final String QUERIES_CERCA_Semaforo="SELECT * FROM Semaforo WHERE (numeroSeriale=? AND stazione=?);";
    static final String QUERIES_MOSTRA_SEGNALAZIONI_EFFETTUATE="SELECT DISTINCT numeroSeriale,stazione,problematica, stato FROM Semaforo,account WHERE (Semaforo.codiceUtente=? AND problemaRisolto=false);";
    static final String QUERIES_MOSTRA_TUTTE_SEGNALAZIONI_semafori = "SELECT * FROM Semaforo;";
    //provala questa delle completate prima
    static final String QUERIES_MOSTRA_SEGNALAZIONI_COMPLETATE="SELECT DISTINCT numeroSeriale,stazione,problematica,stato FROM Semaforo,account WHERE (Semaforo.codiceUtente=? AND problemaRisolto!=false);";
    private QueriesSemaforo(){
        //NON FA NULLA PERCHE LA CLASSE DEVE FORNIRE SOLO METODI STATICI E PUBBLICI
    }
    public static String queriesPrendiSemaforo(){
        return QUERIES_PRENDI_Semaforo;
    }
    public static String queriesSalvaSemaforo(){return QUERIES_SALVA_Semaforo;}
    public static String queriesRimuoviSemaforo(){return QUERIES_RIMUOVI_Semaforo;}
    public static String  restituisciSemaforo(){return QUERIES_RESTITUISCI_Semaforo;}
    public static String cambiaStatoSemaforo(){return QUERIES_CAMBIA_STATO_Semaforo;}
    public static String cercaSemaforo(){return QUERIES_CERCA_Semaforo;}

    //queries che salva un Semaforo se l'utente si e' loggato
    public static String queriesSalvaSemaforoAdUnUtenteDelSistema(){return QUERIES_SALVA_Semaforo;}

    //queries per vedere i semafori che gli utenti loggati hanno segnalato e vogliono controllare lo stato della segnalazione
    public static String queriesMostraSegnalazioniEffettuate(){
        return QUERIES_MOSTRA_SEGNALAZIONI_EFFETTUATE;
    }
    public static String queriesMostraSegnalazioniCompletate(){return QUERIES_MOSTRA_SEGNALAZIONI_COMPLETATE;}

    public static String getQueriesMostraTutteSegnalazionisemafori(){return QUERIES_MOSTRA_TUTTE_SEGNALAZIONI_semafori;}

}
