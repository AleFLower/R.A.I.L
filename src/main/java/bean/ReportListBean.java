package bean;

import com.example.progettoispw.graphiccontroller.ReportType;

import java.util.ArrayList;
import java.util.List;

public class ReportListBean {
    public final List<ReportLevelCrossingBean> levelcrossingReportsList;
    public final List<ReportTrackBean> trackReportsList;

    private ReportType reportType;

    public ReportListBean(ReportType typeOfSegnalazione) {
        levelcrossingReportsList = new ArrayList<>();
        trackReportsList = new ArrayList<>();
        this.reportType = typeOfSegnalazione;
    }

    // Gestisci segnalazioni levelCrossing
    public void addLevelCrossingReports(ReportLevelCrossingBean bean) {
        levelcrossingReportsList.add(bean);
    }

    // Gestisci segnalazioni binario stradale
    public void addTrackReports(ReportTrackBean bean) {
        trackReportsList.add(bean);
    }

    // Getter per le segnalazioni
    public List<ReportLevelCrossingBean> getLevelCrossingReports() {
        return levelcrossingReportsList;
    }

    public List<ReportTrackBean> getTrackReports() {
        return trackReportsList;
    }

    public ReportType getReportType() {
        return reportType;
    }

}
