package bean;

public class ReportLevelCrossingBean {
    private String lcCode;
    private String location;
    private String issue;
    private String state;

    public ReportLevelCrossingBean(String LcCode, String location, String issue, String state) {
        this.lcCode = LcCode;
        this.location = location;
        this.issue = issue;
        this.state = state;
    }

    // Getter e Setter
    public String getLcCode() { return lcCode; }
    public String getLocation() { return location; }
    public String getIssue() { return issue; }
    public String getState() { return state; }
}

