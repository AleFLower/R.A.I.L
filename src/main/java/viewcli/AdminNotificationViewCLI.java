package viewcli;

import utility.NotificationItem;
import utility.Printer;

import java.util.List;


public class AdminNotificationViewCLI {
    public void getReportNotifications(List<NotificationItem> notifications) {
        Printer.print("🔔 Notifications for admin:");
        if (notifications == null || notifications.isEmpty()) {
            System.out.println("No notifications.");
        } else {
            for (NotificationItem n : notifications) {
                System.out.println(" - " + n);
            }
        }
    }
}
