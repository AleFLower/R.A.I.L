package entita;

import factory.TypeEntita;

public class Binario extends EntitaFerroviaria {
    private final String numeroBinario; // ad esempio "Binario 2"

    public Binario(String numeroBinario, String stazione, String descrizioneProblema) {
        super(stazione, descrizioneProblema);
        this.numeroBinario = numeroBinario;
        this.tipoEntita = TypeEntita.BINARIO;
    }

    @Override
    public String getInfo() {
        return numeroBinario;
    }
}