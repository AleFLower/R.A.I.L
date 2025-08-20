package bean;

import factory.TypeOfPersistence;
import exception.InvalidInputLengthException;
import factory.AssetType;

public class ReportBean {

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
