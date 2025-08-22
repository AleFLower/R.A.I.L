package factory;

import bean.ReportBean;
import exception.ReportTypeException;
import model.Track;
import model.RailwayAsset;
import model.LevelCrossing;

public class RailwayAssetFactory {

    public RailwayAsset createAsset(ReportBean bean) throws ReportTypeException {

        switch (bean.getType()){
            case TRACK:
                return new Track(bean.getAssetInfo(), bean.getLocation(), bean.getIssue());
            case LEVELCROSSING:
                return new LevelCrossing(bean.getAssetInfo(), bean.getLocation(), bean.getIssue());
            default:
                throw new ReportTypeException("You can report track and level crossing");
        }
    }
}
