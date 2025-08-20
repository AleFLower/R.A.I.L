package model;

import factory.AssetType;

public class Track extends RailwayAsset {
    private final String trackNumber;

    public Track(String trackNumber, String location, String issue) {
        super(location, issue);
        this.trackNumber = trackNumber;
        this.assetType = AssetType.TRACK;
    }

    @Override
    public String getAssetInfo() {
        return trackNumber;
    }
}