package viewcli;

import utility.NotificationItem;
import utility.Printer;

import java.util.List;

//questa è la view corrispondente alla boundary del caso d'uso per l'admin. Qui è un file(view) a parte,
//in fxml invece non ho aggiunto una view in piu perché ho un alert che mostra le notifiche all admin,
//anche perché altrimenti se vuoi sempre un alert dovresti fa un fxml vuoto e metterci solo quell'alert

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
