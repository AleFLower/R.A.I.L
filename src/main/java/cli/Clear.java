package cli;

import utility.Printer;

public class Clear {
    //classe temp

    private Clear(){}
    public static void clear(){ 
        Printer.print("\033[H\033[2J");
    }
}
