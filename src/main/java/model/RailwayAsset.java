package model;

import factory.AssetType;

import java.io.Serializable;

public abstract class RailwayAsset implements Serializable {
    protected final String location;
    protected final String issue;
    protected String state = "reported";
    protected AssetType assetType;

    protected RailwayAsset(String location, String issue) {
        this.location = location;
        this.issue = issue;
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

    public AssetType getAssetType() {
        return assetType;
    }


    public abstract String getAssetInfo();
}
