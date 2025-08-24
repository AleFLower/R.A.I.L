package queries;


public class ReportTrackQueries {

    private ReportTrackQueries() {}

    static final String QUERY_VIEW_REPORTED_TRACKS = "SELECT * FROM track";
    // operation that users can perform
    static final String QUERY_SAVE_TRACK = "INSERT INTO track(track_number, location, issue, user_id) VALUES(?,?,?,?);";
    // operation that only the admin can perform after the problem has been solved
    static final String QUERY_REMOVE_TRACK = "DELETE FROM track WHERE (location=? AND user_id=?);";
    static final String QUERY_CHANGE_TRACK_STATUS = "UPDATE track SET status = ? WHERE (user_id = ? AND location=?);";

    static final String QUERY_SHOW_REPORTED_SIGNALS = "SELECT DISTINCT location, status, issue, track_number FROM track, account WHERE (track.user_id=? AND status = 'REPORTED');";
    static final String QUERY_SHOW_COMPLETED_REPORTS = "SELECT DISTINCT track_number, location, issue, status FROM track, account WHERE (track.user_id=? AND status = 'FIXED');";
    static final String QUERY_SHOW_ALL_TRACK_REPORTS = "SELECT * FROM track;";

    static final String QUERY_CHECK_IF_TRACK_AT_LOCATION_REPORTED = "SELECT DISTINCT track_number, location FROM track WHERE (track.track_number = ? AND track.location=?);";

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
