package bean;

public class ReportTrackBean {
    private final String trackNumber;
    private final String location;
    private final String issue;
    private String state;

    public ReportTrackBean(String trackNumber, String location, String issue, String state) {
        this.trackNumber = trackNumber;
        this.location = location;
        this.issue = issue;
        this.state = state;
    }


    public String getTrackNumber() {
        return trackNumber;
    }

    public String getLocation() {
        return location;
    }

    public String getIssue() {
        return issue;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
