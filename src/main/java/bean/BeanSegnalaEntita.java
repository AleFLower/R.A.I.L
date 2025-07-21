package bean;

import factory.TypeOfPersistence;
import eccezioni.LunghezzaInputException;
import factory.TypeEntita;

public class BeanSegnalaEntita {
    //questo e' il bean che passerà i dati inseriti dall'utente al controller applicativo che invia la registrazioni

    private String stazione;
    private String problematica;
   // private String tipologia;
    private String infoEntita;
    private TypeEntita tipo;
    private static final int LUNGHEZZANUMEROSERIALE=12;
    private TypeOfPersistence typeOfPersistence;

    public BeanSegnalaEntita(String infoEntita, String stazione, String problematica,TypeEntita tipoEntitaSegnalata,TypeOfPersistence typeOfPersistence){
        this.stazione=stazione;
        this.infoEntita=infoEntita;
        this.problematica = problematica;
        this.tipo=tipoEntitaSegnalata;
      //  this.tipologia = tipologia;
        this.typeOfPersistence=typeOfPersistence;
    }
    public void controllaInputSemaforo()throws LunghezzaInputException {
        if (infoEntita.length() != LUNGHEZZANUMEROSERIALE) {
            throw new LunghezzaInputException("\nLa lunghezza del numero seriale non e' corretta");
        }
    }

    public String getstazione() {
        return stazione;
    }

    public void setstazione(String stazione) {
        this.stazione = stazione;
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
