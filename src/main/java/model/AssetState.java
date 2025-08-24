package model;

public enum AssetState {
    REPORTED,
    FIXED;

    public static AssetState fromValue(String value) {
        if (value == null) return REPORTED; // default
        switch (value.trim().toLowerCase()) {
            case "reported": return REPORTED;
            case "fixed": return FIXED;
            default: throw new IllegalArgumentException("Unknown asset state: " + value);
        }
    }
}
