package utility;

public class Notifica {
    private final String messaggio;

    public Notifica(String messaggio) {
        this.messaggio = messaggio;
    }

    public String getMessaggio() {
        return messaggio;
    }

    @Override
    public String toString() {
        return messaggio;
    }
}
