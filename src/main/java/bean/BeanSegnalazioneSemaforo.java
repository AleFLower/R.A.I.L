package bean;

public class BeanSegnalazioneSemaforo {
    private String numeroSeriale;
    private String stazione;
    private String problematica;
    private String stato;

    public BeanSegnalazioneSemaforo(String numeroSeriale, String stazione, String problematica, String stato) {
        this.numeroSeriale = numeroSeriale;
        this.stazione = stazione;
        this.problematica = problematica;
        this.stato = stato;
    }

    // Getter e Setter
    public String getNumeroSeriale() { return numeroSeriale; }
    public String getstazione() { return stazione; }
    public String getDescrizioneProblema() { return problematica; }
    public String getStato() { return stato; }
}
