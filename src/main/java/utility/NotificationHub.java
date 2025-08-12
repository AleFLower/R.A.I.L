package utility;

import java.util.ArrayList;
import java.util.List;

public class NotificationHub {
    private static final List<NotificationItem> notifications = new ArrayList<>();

    private NotificationHub() {}

    public static void addNotification(NotificationItem notification) {
        notifications.add(notification);
    }

    public static List<NotificationItem> getNotifications() {
        return notifications;
    }

    public static void clearNotifications(){
        notifications.clear();
    }

    public static void printNotifications() {
        Printer.print("🔔 Notifications for admin:");
        if (notifications.isEmpty()) {
            System.out.println("No notifications.");
        } else {
            for (NotificationItem n : notifications) {
                System.out.println(" - " + n);
            }
        }
    }
}
