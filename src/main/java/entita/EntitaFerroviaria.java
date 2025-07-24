package entita;

import factory.TypeEntita;

public abstract class EntitaFerroviaria {
    protected final String localizzazione;          // Es. "Binario 5", "localizzazione Centrale"
    protected final String descrizioneProblema; // Es. "display spento", "ascensore bloccato"
    protected String stato = "segnalato";        // Stato della segnalazione
    protected TypeEntita tipoEntita;  // Enum specifico per entità ferroviarie

    protected EntitaFerroviaria(String localizzazione, String descrizioneProblema) {
        this.localizzazione = localizzazione;
        this.descrizioneProblema = descrizioneProblema;
    }

    public String getlocalizzazione() {
        return localizzazione;
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

    public TypeEntita getTipoEntita() {
        return tipoEntita;
    }

    public void setTipoEntita(TypeEntita tipoEntita) {
        this.tipoEntita = tipoEntita;
    }

    public abstract String getInfo(); // info specifica della sottoclasse
}
