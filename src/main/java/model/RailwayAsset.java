package model;

import factory.AssetType;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class RailwayAsset implements Serializable {

    // --- ATTRIBUTI ---
    protected final String location;
    protected final String issue;
    protected AssetType assetType;
    private AssetState state;
    private final LocalDateTime reportTime;

    // --- COSTRUTTORE ---
    protected RailwayAsset(String location, String issue) {
        this.location = location;
        this.issue = issue;
        markAsReported(); // default
        this.reportTime = LocalDateTime.now();
    }

    // --- GETTER / SETTER CLASSICI ---
    public String getLocation() {
        return location;
    }

    public String getIssue() {
        return issue;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public AssetState getState() {
        return state;
    }

    public void setState(AssetState state) {
        this.state = state;
    }


    // --- OPERAZIONI DI STATO ---
    public void markAsReported() {
        this.state = AssetState.REPORTED;
    }


    //called by admin, not implemented
    public void markAsFixed() {
        if (this.state == AssetState.FIXED) {
            throw new IllegalStateException("Asset is already fixed");
        }
        this.state = AssetState.FIXED;
    }

    public boolean isReported() {
        return state == AssetState.REPORTED;
    }

    public boolean isFixed() {
        return state == AssetState.FIXED;
    }

    // --- PRIORITÀ DI INTERVENTO ---
    /**
     * Calcola la priorità del guasto in base all'issue e al tempo trascorso
     * Valori più alti indicano maggiore urgenza.
     */
    public int calculatePriority() {
        int priority = 5; // base
        if (issue != null) {
            String lower = issue.toLowerCase();
            if (lower.contains("derailment") || lower.contains("collision")) {
                priority += 10; // eventi critici
            }
        }

        long hours = elapsedReportTime();
        priority += hours / 2; // aumenta con il tempo
        return priority;
    }

    public boolean requiresImmediateAttention() {
        return calculatePriority() >= 10;
    }

    public long elapsedReportTime() {
        return java.time.Duration.between(reportTime, LocalDateTime.now()).toHours();
    }

    // --- RIASSUNTO PER REPORT ---
    public String generateReportSummary() {
        return String.format("[%s] Asset at %s | State: %s | Issue: %s | Priority: %d",
                assetType, location, state, issue, calculatePriority());
    }

    // --- INFO SPECIFICHE DELLA SOTTOCLASSE ---
    public abstract String getAssetInfo();
}
