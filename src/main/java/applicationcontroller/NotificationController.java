package applicationcontroller;

import utility.NotificationHub;
import utility.NotificationItem;

import java.util.List;

/*
Controller introdotto per motivi di coesione, in questo modo separo le responsabilità e il controller applicativo
reprotController collabora con esso piuttosto che occuparsi tutto lui di notificare di inviare notifiche, rispettiamo quello
che è il SRP. Inoltre, con l'introduzione di tale controller, tutta la parte di view non è a conoscenza dell utility
del notificationHub e del notificationItem, prima era sparso ovunque.
 */
public class NotificationController {

    private final NotificationHub notificationHub = NotificationHub.getInstance();

    /**
     * Aggiunge una nuova notifica
     */
    public void addNotification(String message) {
        NotificationItem notification = new NotificationItem(message);
        notificationHub.addNotification(notification);
    }

    /**
     * Restituisce tutte le notifiche
     */
    public List<NotificationItem> getNotifications() {
        return notificationHub.getNotifications();
    }

    /**
     * Svuota le notifiche
     */
    public void clearNotifications() {
        notificationHub.clearNotifications();
    }

    /**
     * Verifica se ci sono notifiche disponibili
     */
    public boolean hasNotifications() {
        return !notificationHub.getNotifications().isEmpty();
    }
}
