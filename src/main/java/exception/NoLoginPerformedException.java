package exception;

public class NoLoginPerformedException extends Exception{
    //eccezione che viene chiamata quando voglio fare un operazione che puo essere fatta solo se sono online
    public NoLoginPerformedException(String message){
        super(message);
    }
}
