package bean;

public class ReportTrackBean {
    private final String trackNumber;  // Es: "Binario 2"
    private final String location;    // Es: "localizzazione Milano Centrale"
    private final String issue;  // Es: "Binario fuori servizio"
    private String state;  // Stato della segnalazione, es. "In attesa", "Risolto"

    public ReportTrackBean(String trackNumber, String location, String issue, String state) {
        this.trackNumber = trackNumber;
        this.location = location;
        this.issue = issue;
        this.state = state;
    }

    // Getter e Setter
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
