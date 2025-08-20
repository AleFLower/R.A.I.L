package bean;

import exception.DoubleAtSymbolException;
import exception.DuplicateCommaException;
import exception.EmailTerminatorException;

public class RegistrationBean {

    private String email;
    private String password;
    private String username;
    public RegistrationBean(String email, String password, String  username){
        this.email=email;
        this.password=password;
        this.username=username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
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
