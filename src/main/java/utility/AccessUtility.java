package utility;

import model.Account;
import model.Role;
import factory.TypeOfPersistence;

public class AccessUtility {
    /*questa classe contiene 2 attributi statici che sono settati inizialmente a null
    * se l'utente effettua l' accesso con successo gli attributi di questa classe vengono settati
    * con i giusti valori presenti nel database per quell'utente, e tutte le altre classi potranno vedere queste
    * informazioni contenute nella seguente classe, e le useranno per capire se l'utete ha i permessi per entrare
    * in alcune schermate  */
    private static String username =null;
    private static String userCode =null;
    private static Role role = null;
    private static TypeOfPersistence persistence;
    private AccessUtility(){
        //NON FA NULLA PERCHE E' UNA CLASSE UTILITY E FORNISCE METODI STATICI PER SETTARE IL VALORE DELLE
        //VARIABILI PRIVATE
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        AccessUtility.username = username;
    }


    public static Role getRole(){return role;}
    public static void setRole(Role ruolo){
        role = ruolo;
    }

    public static String getUserCode() {
        return userCode;
    }

    public static void setUserCode(String userCode) {
        AccessUtility.userCode = userCode;
    }

 //nota: mi serve che sia chiamata questa classe perché con essa vi interagiscono anche la parte di view
    //quindi la view non è che puo chiamare direttamente Account.goOffline() anche se raggiungibile, perché è non
    //deve venire a conoscenza della parte model. L'unico modo è dare questa resp a questa classe
    private static Account account;

    public static void goOffline(){
        account.goOffline();
    }

    public static void setAccount(Account account) {
        AccessUtility.account = account;
    }

    public static void setPersistence(TypeOfPersistence type) {
        persistence = type;
    }

    public static TypeOfPersistence getPersistence() {
        return persistence;
    }

}
