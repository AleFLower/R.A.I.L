package utility;

import java.util.ArrayList;
import java.util.List;

public class NotificationHub {

    private static NotificationHub instance;

    private final List<NotificationItem> notifications;

    protected NotificationHub() {
        this.notifications = new ArrayList<>();
    }

    // Singleton thread-safe semplice
    public static synchronized NotificationHub getInstance() {
        if (instance == null) {
            instance = new NotificationHub();
        }
        return instance;
    }

    // Aggiunge una notifica
    public synchronized void addNotification(NotificationItem notification) {
        notifications.add(notification);
    }

    // Restituisce una lista immutabile per evitare modifiche esterne
    public synchronized List<NotificationItem> getNotifications() {
        return notifications;
    }

    // Pulisce tutte le notifiche
    public synchronized void clearNotifications() {
        notifications.clear();
    }
}
