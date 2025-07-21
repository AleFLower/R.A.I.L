package bean;

public class BeanSegnalazioneBinario {
    private final String numeroBinario;  // Es: "Binario 2"
    private final String stazione;    // Es: "Stazione Milano Centrale"
    private final String descrizioneProblema;  // Es: "Binario fuori servizio"
    private String stato;  // Stato della segnalazione, es. "In attesa", "Risolto"

    public BeanSegnalazioneBinario(String numeroBinario, String stazione, String descrizioneProblema, String stato) {
        this.numeroBinario = numeroBinario;
        this.stazione = stazione;
        this.descrizioneProblema = descrizioneProblema;
        this.stato = stato;
    }

    // Getter e Setter
    public String getNumeroBinario() {
        return numeroBinario;
    }

    public String getstazione() {
        return stazione;
    }

    public String getDescrizioneProblema() {
        return descrizioneProblema;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }


}
