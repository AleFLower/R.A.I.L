package entita;

import factory.TypeEntita;

public abstract class EntitaFerroviaria {
    protected final String stazione;          // Es. "Binario 5", "Stazione Centrale"
    protected final String descrizioneProblema; // Es. "display spento", "ascensore bloccato"
    protected String stato = "in attesa";        // Stato della segnalazione
    protected TypeEntita tipoEntita;  // Enum specifico per entità ferroviarie

    public EntitaFerroviaria(String stazione, String descrizioneProblema) {
        this.stazione = stazione;
        this.descrizioneProblema = descrizioneProblema;
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

    public TypeEntita getTipoEntita() {
        return tipoEntita;
    }

    public void setTipoEntita(TypeEntita tipoEntita) {
        this.tipoEntita = tipoEntita;
    }

    public abstract String getInfo(); // info specifica della sottoclasse
}
