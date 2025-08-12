package bean;

import factory.TypeOfPersistence;
import exception.InvalidInputLengthException;
import factory.AssetType;

public class ReportBean {
    //questo e' il bean che passerà i dati inseriti dall'utente al controller applicativo che invia la registrazioni

    //se dovessi aggiungere attributi in modo asimmetrico tra le entity(1 in binario, 2 in PL ad esempio), posso aggiungere tali attributi specifici qui
    //tanto la bean è proprio un contenitore di dati, non ha funzionalità, e poi nella factory in base al tipo e costruttore, chiamerò
    //quel getter per ottenere quel valore. GetInfo l'ho messa in generale, perché ho 1:1, è l'unica informazione che ho
    //poi o prevedo un altro costruttore che accetta un parametro in piu, e quindi per l'altra uso un parametro in meno o metto null a quando gli passo
    //  per quella che non prevede il parametro aggiuntivo
    private String location;
    private String issue;
    private String assetInfo;
    private AssetType assetType;
    private static final int LCCODELENGHT =5;
    private TypeOfPersistence typeOfPersistence;

    public ReportBean(String assetInfo, String location, String issue, AssetType assetType, TypeOfPersistence typeOfPersistence){
        this.location = location;
        this.assetInfo = assetInfo;
        this.issue = issue;
        this.assetType =assetType;
        this.typeOfPersistence=typeOfPersistence;
    }

    public void validateInput()throws InvalidInputLengthException {
        if (assetInfo.length() != LCCODELENGHT) {
            throw new InvalidInputLengthException("\nThe lenght of the code is wrong");
        }
    }

    public String getLocation() {
        return location;
    }

    public String getAssetInfo() {
        return assetInfo;
    }


    public String getIssue(){return issue;}

    public AssetType getType(){
        return assetType;
    }

    public TypeOfPersistence getTypeOfPersistence() {
        return typeOfPersistence;
    }

    public void setTypeOfPersistence(TypeOfPersistence typeOfPersistence) {
        this.typeOfPersistence = typeOfPersistence;
    }
}
