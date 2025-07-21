package entita;

import factory.TypeEntita;

public class SemaforoFerroviario extends EntitaFerroviaria {
    private final String numeroSeriale;

    public SemaforoFerroviario(String numeroSeriale, String stazione, String problematica) {
        super(stazione, problematica);
        this.numeroSeriale = numeroSeriale;
        this.tipoEntita = TypeEntita.SEMAFORO;
    }

    @Override
    public String getInfo() {
        return numeroSeriale;
    }
}
