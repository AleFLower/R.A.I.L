package queries;


/*
Ipotesi a livello database(e anche memory) per i binari: ho come chiave primaria numero del binario e la localizzazione.
La segnalazione avviene cosi: ovviamente ci sono sparsi nella nazione diversi binari con stesso numero di binario
quindi distinguiamo ovviamente anche per localizzazione(ovvero, una sorta di indirizzo, con il chilometraggio
di dove si trova il binario, tipo km 45+12, cosi un utente non si sbaglia, piuttosto che inserire stazione termini
o roma termini

Inoltre, per quanto riguarda il fatto che un utente vuole segnalare la stessa entità binario o passaggio a livello
si presuppone che in memory se l'ho gia segnalato, dopo che spengo l'app non l'ho piu e quindi posso risegnalarlo. Su persistenza
si presuppone che l'admin, dopo aver risolto il problema, faccia un refresh del db eliminando alcune entry e quindi
si puo risegnalare lo stesso problema per quello stesso binario o passaggio a livello.
*/
public class ReportTrackQueries {

    // we build the tracks so that only users registered in the system can report them
    // operation that only the admin can perform
    private ReportTrackQueries() {
        // not called by anyone, this class is ultimately a utility
    }

    static final String QUERY_VIEW_REPORTED_TRACKS = "SELECT * FROM BINARIO";
    // operation that users can perform
    static final String QUERY_SAVE_TRACK = "INSERT INTO BINARIO(numeroBINARIO,localizzazione,problematica,codiceUtente) VALUES(?,?,?,?);";
    // operation that only the admin can perform after the problem has been solved
    static final String QUERY_REMOVE_TRACK = "DELETE FROM BINARIO where (localizzazione=? AND codice_utente=?);";
    static final String QUERY_CHANGE_TRACK_STATUS = "UPDATE BINARIO SET stato = ? WHERE (codiceUtente = ? AND localizzazione=?);";
    // these 2 queries that show the reports, run them first on MySQL to review them
    static final String QUERY_SHOW_REPORTED_SIGNALS = "SELECT DISTINCT localizzazione,stato,problematica,numeroBINARIO FROM BINARIO,account WHERE (BINARIO.codiceUtente=? AND stato = 'segnalato');";
    // try this one for completed first
    static final String QUERY_SHOW_COMPLETED_REPORTS = "SELECT DISTINCT numeroBINARIO,localizzazione,problematica,stato FROM BINARIO,account WHERE (BINARIO.codiceUtente=? AND stato = 'risolto');";
    static final String QUERY_SHOW_ALL_TRACK_REPORTS = "SELECT * FROM BINARIO;";

    static final String QUERY_CHECK_IF_TRACK_AT_LOCATION_REPORTED = "SELECT DISTINCT numeroBINARIO,localizzazione FROM BINARIO WHERE (BINARIO.numeroBINARIO = ? AND BINARIO.localizzazione=?);";

    public static String queryViewReportedTracks() {
        return QUERY_VIEW_REPORTED_TRACKS;
    }
    public static String querySaveTrack() { return QUERY_SAVE_TRACK; }
    public static String queryRemoveTrack() { return QUERY_REMOVE_TRACK; }
    public static String queryChangeTrackStatus() { return QUERY_CHANGE_TRACK_STATUS; }
    // queries to see the tracks that logged-in users have reported
    public static String queryShowReportedSignals() {
        return QUERY_SHOW_REPORTED_SIGNALS;
    }
    // queries to show the tracks that have been repaired
    public static String queryShowCompletedReports() { return QUERY_SHOW_COMPLETED_REPORTS; }
    public static String queryCheckIfTrackAtLocationReported() {
        return QUERY_CHECK_IF_TRACK_AT_LOCATION_REPORTED;
    }
    public static String getQueryShowAllTrackReports() { return QUERY_SHOW_ALL_TRACK_REPORTS; }
}
