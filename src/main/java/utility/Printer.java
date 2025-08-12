package utility;

public class Printer {
    private Printer(){}
    public static void print(String message){
        System.out.println(message);
    }
    public static void error(String error){
        System.err.println(error);
    }
}
