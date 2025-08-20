package utility;


import model.Role;
import factory.TypeOfPersistence;

public class AccessUtility {
    /* Utility class that stores user access details and configuration info. */

    private static String username =null;
    private static String userCode =null;
    private static Role role = null;
    private static TypeOfPersistence persistence;
    private AccessUtility(){}

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

    public static void setPersistence(TypeOfPersistence type) {
        persistence = type;
    }

    public static TypeOfPersistence getPersistence() {
        return persistence;
    }

}
