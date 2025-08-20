package model;

import factory.AssetType;

import java.io.Serializable;

public abstract class RailwayAsset implements Serializable {
    protected final String location;          // Es. "Binario 5", "localizzazione Centrale"
    protected final String issue; // Es. "display spento", "ascensore bloccato"
    protected String state = "reported";        // Stato della segnalazione
    protected AssetType assetType;  // Enum specifico per entità ferroviarie

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

    public void setState(String stato) {
        this.state = stato;
    }

    public AssetType getAssetType() {
        return assetType;
    }


    public abstract String getAssetInfo(); // info specifica della sottoclasse
}
