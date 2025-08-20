package bean;

import exception.DoubleAtSymbolException;
import exception.DuplicateCommaException;
import exception.EmailTerminatorException;

public class ValidationBean {

    private ValidationBean(){}
    public static void verifyEmailSyntax(String email) throws DoubleAtSymbolException, DuplicateCommaException, EmailTerminatorException {
        int counter = 0;
        int counter2 = 0;
        counter = email.indexOf('@');
        counter2 = email.indexOf('@', counter + 1);
        if (counter2 != -1) {

            throw new DoubleAtSymbolException("The email must\n must contain only one '@' ");
        }
        if (email.indexOf(',') != -1) {
            throw new DuplicateCommaException("The email should not \ncontain a ','");
        }
        if(!(email.endsWith(".com") || email.endsWith(".it") || email.endsWith(".live"))){
            throw new EmailTerminatorException("The email must terminate with\n.com o .it o .live");
        }
    }
}
