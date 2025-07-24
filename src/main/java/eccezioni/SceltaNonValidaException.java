package eccezioni;

public class SceltaNonValidaException extends RuntimeException {
    public SceltaNonValidaException(String message) {
        super(message);
    }
}
