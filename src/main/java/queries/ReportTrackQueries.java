package queries;


public class ReportTrackQueries {


    private ReportTrackQueries() {}

    static final String QUERY_VIEW_REPORTED_TRACKS = "SELECT * FROM BINARIO";
    // operation that users can perform
    static final String QUERY_SAVE_TRACK = "INSERT INTO BINARIO(numeroBINARIO,localizzazione,problematica,codiceUtente) VALUES(?,?,?,?);";
    // operation that only the admin can perform after the problem has been solved
    static final String QUERY_REMOVE_TRACK = "DELETE FROM BINARIO where (localizzazione=? AND codice_utente=?);";
    static final String QUERY_CHANGE_TRACK_STATUS = "UPDATE BINARIO SET stato = ? WHERE (codiceUtente = ? AND localizzazione=?);";

    static final String QUERY_SHOW_REPORTED_SIGNALS = "SELECT DISTINCT localizzazione,stato,problematica,numeroBINARIO FROM BINARIO,account WHERE (BINARIO.codiceUtente=? AND stato = 'reported');";
    static final String QUERY_SHOW_COMPLETED_REPORTS = "SELECT DISTINCT numeroBINARIO,localizzazione,problematica,stato FROM BINARIO,account WHERE (BINARIO.codiceUtente=? AND stato = 'fixed');";
    static final String QUERY_SHOW_ALL_TRACK_REPORTS = "SELECT * FROM BINARIO;";

    static final String QUERY_CHECK_IF_TRACK_AT_LOCATION_REPORTED = "SELECT DISTINCT numeroBINARIO,localizzazione FROM BINARIO WHERE (BINARIO.numeroBINARIO = ? AND BINARIO.localizzazione=?);";

    public static String queryViewReportedTracks() {
        return QUERY_VIEW_REPORTED_TRACKS;
    }
    public static String querySaveTrack() { return QUERY_SAVE_TRACK; }
    public static String queryRemoveTrack() { return QUERY_REMOVE_TRACK; }
    public static String queryChangeTrackStatus() { return QUERY_CHANGE_TRACK_STATUS; }

    public static String queryShowReportedSignals() {
        return QUERY_SHOW_REPORTED_SIGNALS;
    }
    public static String queryShowCompletedReports() { return QUERY_SHOW_COMPLETED_REPORTS; }
    public static String queryCheckIfTrackAtLocationReported() {
        return QUERY_CHECK_IF_TRACK_AT_LOCATION_REPORTED;
    }
    public static String getQueryShowAllTrackReports() { return QUERY_SHOW_ALL_TRACK_REPORTS; }
}
