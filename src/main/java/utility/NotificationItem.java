package utility;

public class NotificationItem {
    private final String message;

    public NotificationItem(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
