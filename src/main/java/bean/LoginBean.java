package bean;

import exception.DoubleAtSymbolException;
import exception.DuplicateCommaException;
import exception.EmailTerminatorException;


public class LoginBean {


    private String email;
    private String password;
    public LoginBean(String email, String password) {
        this.email=email.toLowerCase();
        this.password=password;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public String validate(){
        try {
            ValidationBean.verifyEmailSyntax(email);
        }catch(DoubleAtSymbolException | DuplicateCommaException | EmailTerminatorException e){
            return e.getMessage();
        }
        return null;
    }
}
