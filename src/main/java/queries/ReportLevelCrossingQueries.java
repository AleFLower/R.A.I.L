package queries;



public class ReportLevelCrossingQueries {

    // this class will act as a utility class, it will have only static methods that return strings
    // which are nothing more than the queries I can execute on my LEVELCROSSING object
    // note: in the queries, the term 'LEVELCROSSING' is the name of the relation present in the database "database-app-ispw"
    // these are prepared statements, the values in the ? will be set in the class that wants to execute the query
    static final String QUERY_GET_LEVELCROSSING = "SELECT * FROM LEVELCROSSING where codicePL=?";
    static final String QUERY_SAVE_LEVELCROSSING = "INSERT INTO LEVELCROSSING(codicePL,localizzazione,problematica) VALUES(?,?,?);";
    static final String QUERY_SAVE_LEVELCROSSING_FOR_USER = "INSERT INTO LEVELCROSSING(codicePL,localizzazione,problematica,codiceUtente) VALUES(?,?,?,?);";
    static final String QUERY_REMOVE_LEVELCROSSING = "DELETE FROM LEVELCROSSING where (codicePL=? AND localizzazione=?);";
    static final String QUERY_GET_ALL_LEVELCROSSING = "SELECT * FROM LEVELCROSSING";
    static final String QUERY_CHANGE_LEVELCROSSING_STATUS = "UPDATE LEVELCROSSING SET stato = ? WHERE (codicePL=? AND localizzazione=?);";
    static final String QUERY_SEARCH_LEVELCROSSING = "SELECT * FROM LEVELCROSSING WHERE (codicePL=?);";
    static final String QUERY_SHOW_REPORTED_SIGNALS = "SELECT DISTINCT codicePL,localizzazione,problematica, stato FROM LEVELCROSSING,account WHERE (LEVELCROSSING.codiceUtente=? AND stato = 'reported');";
    static final String QUERY_SHOW_ALL_LEVELCROSSING_REPORTS = "SELECT * FROM LEVELCROSSING;";

    static final String QUERY_SHOW_COMPLETED_REPORTS = "SELECT DISTINCT codicePL,localizzazione,problematica,stato FROM LEVELCROSSING,account WHERE (LEVELCROSSING.codiceUtente=? AND stato = 'fixed');";

    private ReportLevelCrossingQueries() {}

    public static String queryGetLevelCrossing() {
        return QUERY_GET_LEVELCROSSING;
    }
    public static String getQuerySaveLevelCrossing() { return QUERY_SAVE_LEVELCROSSING; }
    public static String getQueryRemoveLevelCrossing() { return QUERY_REMOVE_LEVELCROSSING; }
    public static String getQueryGetAllLevelCrossing() { return QUERY_GET_ALL_LEVELCROSSING; }
    public static String getQueryChangeLevelCrossingStatus() { return QUERY_CHANGE_LEVELCROSSING_STATUS; }
    public static String getQuerySearchLevelCrossing() { return QUERY_SEARCH_LEVELCROSSING; }

    // query that saves a LEVELCROSSING if the user is logged in
    public static String querySaveLevelCrossingForSystemUser() { return QUERY_SAVE_LEVELCROSSING_FOR_USER; }

    // queries to see the level crossings that logged-in users have reported and want to check the status of the report
    public static String queryShowReportedSignals() {
        return QUERY_SHOW_REPORTED_SIGNALS;
    }
    public static String queryShowCompletedReports() { return QUERY_SHOW_COMPLETED_REPORTS; }
}
