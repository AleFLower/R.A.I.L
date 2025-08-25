package model;

import factory.AssetType;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class RailwayAsset implements Serializable {

    protected final String location;
    protected final String issue;
    protected AssetType assetType;
    protected AssetState state;
    private final LocalDateTime reportTime;

    protected RailwayAsset(String location, String issue) {
        this.location = location;
        this.issue = issue;
        markAsReported(); // default
        this.reportTime = LocalDateTime.now();
    }

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

    public void markAsReported() {
        this.state = AssetState.REPORTED;
    }

    public abstract String getAssetInfo();

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

    public int calculatePriority() {
        int priority = 5;
        if (issue != null) {
            String lower = issue.toLowerCase();
            if (lower.contains("derailment") || lower.contains("collision")) {
                priority += 10;
            }
        }

        long hours = elapsedReportTime();
        priority += hours / 2;
        return priority;
    }

    public boolean needsImmediateAction() {
        return calculatePriority() >= 10;
    }

    public long elapsedReportTime() {
        return java.time.Duration.between(reportTime, LocalDateTime.now()).toHours();
    }

    public String generateReportSummary() {
        return String.format("[%s] Asset at %s | State: %s | Issue: %s | Priority: %d",
                assetType, location, state, issue, calculatePriority());
    }


}
