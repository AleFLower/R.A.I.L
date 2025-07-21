package cli;

import controllergraficicommandlineinterface.ControllerGraficoPaginaSegnalazioneSemaforoCli;
import factory.TypeOfPersistence;
import utility.Printer;
import utility.UtilityAccesso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PaginaSegnalazioneSemaforoCli {
    private String numeroSerialeSemaforo;
    private String stazione;


    private ControllerGraficoPaginaSegnalazioneSemaforoCli controllerGraficoPaginaSegnalazioneSemaforoCli;
    public void inserisciInput() throws IOException{
        Clear.clear();
        Printer.print("------------------Pagina Segnalazione Semaforo ----------------\n" +
                "inserisci numero seriale del semaforo ferroviario da segnalare( digitare esc per uscire): ");
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        numeroSerialeSemaforo=bufferedReader.readLine();
        Printer.print("inserisci stazione (digitare esc per uscire): ");
        stazione=bufferedReader.readLine();
        Printer.print("inserisci la problematica del semaforo(luce lampeggiante, spento,..): ");
        String problematica = bufferedReader.readLine();

        //problematica e stazione sono comuni a tutte e due le entità

        //prima verifichiamo se l'utente vuole uscire dalla schermata
        if(verificaInputUscita(numeroSerialeSemaforo, stazione,problematica)){
            tornaAllaHomePage();
        }else if(numeroSerialeSemaforo.equals("")|| stazione.equals("") || problematica.equals("")){
            Printer.print("la prossima volta inserisci qualcosa");
            tornaAllaHomePage();
        }
        else {
            //l'utente non vuole uscire, invio i dati al bean
            //invio i dati al controller grafico della pagina segnalazione problema il quale li invia al bean
            Printer.print("digita:\n1 se vuoi salvare la segnalazione\n2 se vuoi salvarla su file");
            try {
                String scelta = bufferedReader.readLine();
                int sceltaInt = Integer.parseInt(scelta);

                TypeOfPersistence tipoPersistenza;

                if (sceltaInt == 2) {
                    tipoPersistenza = TypeOfPersistence.FILESYSTEM;
                } else {
                    tipoPersistenza = UtilityAccesso.getPersistence(); // MEMORY o JDBC
                }
                controllerGraficoPaginaSegnalazioneSemaforoCli = new ControllerGraficoPaginaSegnalazioneSemaforoCli(numeroSerialeSemaforo, stazione,problematica, tipoPersistenza);
                controllerGraficoPaginaSegnalazioneSemaforoCli.inviaDatiAlBean();
            }catch(NumberFormatException e){
                //si verifica se l'utente non digita un numero
                Printer.error("digitare solo un numero tra 1,2");
                inserisciInput();  //retry
            }
        }
    }
    private boolean verificaInputUscita(String numeroSerialeSemaforo, String stazione,String problematica){
        return(numeroSerialeSemaforo.equalsIgnoreCase("esc") || stazione.equalsIgnoreCase("esc") || problematica.equalsIgnoreCase("esc"));

    }
    private void tornaAllaHomePage() throws IOException {
        PaginaHome paginaHome=new PaginaHome();
        paginaHome.displayHomepage();
    }
}
