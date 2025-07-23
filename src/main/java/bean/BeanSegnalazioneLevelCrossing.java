package bean;

public class BeanSegnalazioneLevelCrossing {
    private String codicePL;
    private String localizzazione;
    private String problematica;
    private String stato;

    public BeanSegnalazioneLevelCrossing(String codicePL, String localizzazione, String problematica, String stato) {
        this.codicePL = codicePL;
        this.localizzazione = localizzazione;
        this.problematica = problematica;
        this.stato = stato;
    }

    // Getter e Setter
    public String getcodicePL() { return codicePL; }
    public String getlocalizzazione() { return localizzazione; }
    public String getDescrizioneProblema() { return problematica; }
    public String getStato() { return stato; }
}
