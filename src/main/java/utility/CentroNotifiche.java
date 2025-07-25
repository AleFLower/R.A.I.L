package utility;

import java.util.ArrayList;
import java.util.List;

public class CentroNotifiche {
    private static final List<Notifica> notifiche = new ArrayList<>();

    private CentroNotifiche() {}

    public static void aggiungiNotifica(Notifica notifica) {
        notifiche.add(notifica);
    }

    public static List<Notifica> getNotifiche() {
        return new ArrayList<>(notifiche); // restituisce una copia per sicurezza
    }

    public static void stampaNotifiche() {
        Printer.print("🔔 Notifiche per l'admin:");
        if (notifiche.isEmpty()) {
            System.out.println("Nessuna notifica.");
        } else {
            for (Notifica n : notifiche) {
                System.out.println(" - " + n);
            }
        }
    }
}
