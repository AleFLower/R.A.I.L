package bean;

import factory.TypeOfPersistence;
import eccezioni.LunghezzaInputException;
import factory.TypeEntita;

public class BeanSegnalaEntita {
    //questo e' il bean che passerà i dati inseriti dall'utente al controller applicativo che invia la registrazioni

    private String localizzazione;
    private String problematica;
   // private String tipologia;
    private String infoEntita;
    private TypeEntita tipo;
    private static final int LUNGHEZZAcodicePL=5;
    private TypeOfPersistence typeOfPersistence;

    public BeanSegnalaEntita(String infoEntita, String localizzazione, String problematica,TypeEntita tipoEntitaSegnalata,TypeOfPersistence typeOfPersistence){
        this.localizzazione=localizzazione;
        this.infoEntita=infoEntita;
        this.problematica = problematica;
        this.tipo=tipoEntitaSegnalata;
      //  this.tipologia = tipologia;
        this.typeOfPersistence=typeOfPersistence;
    }
    public void controllaInputLevelCrossing()throws LunghezzaInputException {
        if (infoEntita.length() != LUNGHEZZAcodicePL) {
            throw new LunghezzaInputException("\nLa lunghezza del numero seriale non e' corretta");
        }
    }

    public String getlocalizzazione() {
        return localizzazione;
    }

    public void setlocalizzazione(String localizzazione) {
        this.localizzazione = localizzazione;
    }

    public String getInfoEntita() {
        return infoEntita;
    }

    public void setInfoEntita(String infoEntita) {
        this.infoEntita = infoEntita;
    }

    public String getDescrizioneProblema(){return problematica;}
    public void setproblematica(){this.problematica = problematica;}
   // public String getTipologia(){return tipologia;}
   // public void setTipologia(){this.tipologia = tipologia;}

    public TypeEntita getTipoEntitaSegnalata(){
        return tipo;
    }
    public void setTipoEntitaSegnalata(TypeEntita tipoEntitaSegnalata) {
        this.tipo = tipoEntitaSegnalata;
    }

    public TypeOfPersistence getTypeOfPersistence() {
        return typeOfPersistence;
    }

    public void setTypeOfPersistence(TypeOfPersistence typeOfPersistence) {
        this.typeOfPersistence = typeOfPersistence;
    }
}
