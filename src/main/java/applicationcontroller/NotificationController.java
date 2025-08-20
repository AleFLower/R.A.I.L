package applicationcontroller;

import utility.NotificationHub;
import utility.NotificationItem;

import java.util.List;

public class NotificationController {

    private final NotificationHub notificationHub = NotificationHub.getInstance();

    public void addNotification(String message) {
        NotificationItem notification = new NotificationItem(message);
        notificationHub.addNotification(notification);
    }

    public List<NotificationItem> getNotifications() {
        return notificationHub.getNotifications();
    }


    public void clearNotifications() {
        notificationHub.clearNotifications();
    }

    public boolean hasNotifications() {
        return !notificationHub.getNotifications().isEmpty();
    }
}
