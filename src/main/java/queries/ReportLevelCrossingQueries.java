package queries;



public class ReportLevelCrossingQueries {

    // this class will act as a utility class, it will have only static methods that return strings
    // which are nothing more than the queries I can execute on my LEVELCROSSING object
    static final String QUERY_GET_LEVELCROSSING = "SELECT * FROM levelcrossing WHERE lc_code=?";
    static final String QUERY_SAVE_LEVELCROSSING = "INSERT INTO levelcrossing(lc_code, location, issue) VALUES(?,?,?);";
    static final String QUERY_SAVE_LEVELCROSSING_FOR_USER = "INSERT INTO levelcrossing(lc_code, location, issue, user_id) VALUES(?,?,?,?);";
    static final String QUERY_REMOVE_LEVELCROSSING = "DELETE FROM levelcrossing WHERE lc_code=?;";
    static final String QUERY_GET_ALL_LEVELCROSSING = "SELECT * FROM levelcrossing";
    static final String QUERY_CHANGE_LEVELCROSSING_STATUS = "UPDATE levelcrossing SET status = ? WHERE lc_code=?;";
    static final String QUERY_SEARCH_LEVELCROSSING = "SELECT * FROM levelcrossing WHERE lc_code=?;";
    static final String QUERY_SHOW_REPORTED_SIGNALS = "SELECT DISTINCT lc_code, location, issue, status FROM levelcrossing, account WHERE (levelcrossing.user_id=? AND status = 'REPORTED');";
    static final String QUERY_SHOW_ALL_LEVELCROSSING_REPORTS = "SELECT * FROM levelcrossing;";

    static final String QUERY_SHOW_COMPLETED_REPORTS = "SELECT DISTINCT lc_code, location, issue, status FROM levelcrossing, account WHERE (levelcrossing.user_id=? AND status = 'FIXED');";

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
