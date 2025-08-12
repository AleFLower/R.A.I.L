package queries;

public class AccessQueries {
    /*questa e' una utility class che contiene solo metodi pubblici e statici i quali ritornano tutti una stringa
     * cioè la query che un metodo chiede di voler usare  */

    private AccessQueries(){
        //NON FA NULLA PERCHE LA CLASSE DEVE FORNIRE SOLO METODI STATICI E PUBBLICI.
    }
    static final String QUERY_CHECK_EMAIL_AND_PASSWORD = "SELECT * FROM account WHERE (email=? AND password=?);";
    static final String QUERY_CHECK_IF_USERNAME_EXISTS = "SELECT * FROM account WHERE username=?;";
    static final String QUERY_CHECK_IF_EMAIL_EXISTS = "SELECT * FROM account WHERE email=?;";
    static final String QUERY_INSERT_USER_INTO_SYSTEM = "INSERT INTO account(email,password,username,role) VALUES(?,?,?,?);";

    public static String checkEmailAndPassword() { return QUERY_CHECK_EMAIL_AND_PASSWORD; }
    public static String checkIfUsernameExists() { return QUERY_CHECK_IF_USERNAME_EXISTS; }
    public static String checkIfEmailExists() { return QUERY_CHECK_IF_EMAIL_EXISTS; }
    public static String insertUserIntoSystem() { return QUERY_INSERT_USER_INTO_SYSTEM; }

}
