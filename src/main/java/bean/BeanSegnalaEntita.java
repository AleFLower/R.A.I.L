package bean;

import factory.TypeOfPersistence;
import eccezioni.LunghezzaInputException;
import factory.TypeEntita;

public class BeanSegnalaEntita {
    //questo e' il bean che passerà i dati inseriti dall'utente al controller applicativo che invia la registrazioni

    private String localizzazione;
    private String problematica;
    private String infoEntita;
    private TypeEntita tipo;
    private static final int LUNGHEZZACODICEPL=5;
    private TypeOfPersistence typeOfPersistence;

    public BeanSegnalaEntita(String infoEntita, String localizzazione, String problematica,TypeEntita tipoEntitaSegnalata,TypeOfPersistence typeOfPersistence){
        this.localizzazione=localizzazione;
        this.infoEntita=infoEntita;
        this.problematica = problematica;
        this.tipo=tipoEntitaSegnalata;
        this.typeOfPersistence=typeOfPersistence;
    }
    public void controllaInputLevelCrossing()throws LunghezzaInputException {
        if (infoEntita.length() != LUNGHEZZACODICEPL) {
            throw new LunghezzaInputException("\nLa lunghezza del numero seriale non e' corretta");
        }
    }

    public String getlocalizzazione() {
        return localizzazione;
    }

    public String getInfoEntita() {
        return infoEntita;
    }


    public String getDescrizioneProblema(){return problematica;}

    public TypeEntita getTipoEntitaSegnalata(){
        return tipo;
    }

    public TypeOfPersistence getTypeOfPersistence() {
        return typeOfPersistence;
    }

    public void setTypeOfPersistence(TypeOfPersistence typeOfPersistence) {
        this.typeOfPersistence = typeOfPersistence;
    }
}
