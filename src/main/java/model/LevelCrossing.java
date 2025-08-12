package model;

import factory.AssetType;

public class LevelCrossing extends RailwayAsset {
    private final String lcCode;

    public LevelCrossing(String lcCode, String location, String issue) {
        super(location, issue);
        this.lcCode = lcCode;
        this.assetType = AssetType.LEVELCROSSING;
    }

    @Override
    public String getAssetInfo() {
        return lcCode;
    }
}
