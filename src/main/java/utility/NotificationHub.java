package utility;

import java.util.ArrayList;
import java.util.List;

public class NotificationHub {

    private static NotificationHub instance;

    private final List<NotificationItem> notifications;

    protected NotificationHub() {
        this.notifications = new ArrayList<>();
    }

    public static synchronized NotificationHub getInstance() {
        if (instance == null) {
            instance = new NotificationHub();
        }
        return instance;
    }

    public synchronized void addNotification(NotificationItem notification) {
        notifications.add(notification);
    }

    public synchronized List<NotificationItem> getNotifications() {
        return notifications;
    }

    public synchronized void clearNotifications() {
        notifications.clear();
    }
}
