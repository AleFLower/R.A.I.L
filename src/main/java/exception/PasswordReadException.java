package exception;

import java.io.IOException;

public class PasswordReadException extends Exception{
    public PasswordReadException(String message){
        super(message);
    }
}
