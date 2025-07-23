package entita;

import factory.TypeEntita;

public class LevelCrossing extends EntitaFerroviaria {
    private final String codicePL;

    public LevelCrossing(String codicePL, String localizzazione, String problematica) {
        super(localizzazione, problematica);
        this.codicePL = codicePL;
        this.tipoEntita = TypeEntita.LEVELCROSSING;
    }

    @Override
    public String getInfo() {
        return codicePL;
    }
}
