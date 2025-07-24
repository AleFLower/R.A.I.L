package cli;

import controllergraficicommandlineinterface.ControllerGraficoPaginaSegnalazionePassaggioLivelloCli;
import eccezioni.SceltaNonValidaException;
import utility.Printer;
import utility.UtilityAccesso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PaginaSegnalazionePassaggioALivelloCli {
    private String codicePLLevelCrossing;
    private String localizzazione;


    private ControllerGraficoPaginaSegnalazionePassaggioLivelloCli controllerGraficoPaginaSegnalazioneLevelCrossingCli;
    public void inserisciInput() throws IOException{
        Clear.clear();
        Printer.print("------------------Pagina Segnalazione LevelCrossing ----------------\n" +
                "inserisci numero del passaggio a livello da segnalare( digitare esc per uscire): ");
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        codicePLLevelCrossing=bufferedReader.readLine();
        Printer.print("inserisci la posizione (digitare esc per uscire): ");
        localizzazione=bufferedReader.readLine();
        Printer.print("inserisci la problematica del passaggio a livello: ");  //magari qui spiegare tra () quali problematiche possono esserci
        String problematica = bufferedReader.readLine();

        //problematica e localizzazione sono comuni a tutte e due le entità

        //prima verifichiamo se l'utente vuole uscire dalla schermata
        if(verificaInputUscita(codicePLLevelCrossing, localizzazione,problematica)){
            tornaAllaHomePage();
        }else if(codicePLLevelCrossing.isEmpty() || localizzazione.isEmpty() || problematica.isEmpty()){
            Printer.print("la prossima volta inserisci qualcosa");
            tornaAllaHomePage();
        }
        else {
            //l'utente non vuole uscire, invio i dati al bean
            //invio i dati al controller grafico della pagina segnalazione problema il quale li invia al bean
            Printer.print("salvare la segnalazione? (y/n)");
            try {
                String scelta = bufferedReader.readLine().trim().toLowerCase();

                if (!scelta.equals("y") && !scelta.equals("n")) {
                    throw new SceltaNonValidaException("Scelta non valida. Devi digitare solo 'y' oppure 'n'.");
                }
                if (scelta.equals("n")) return;

                controllerGraficoPaginaSegnalazioneLevelCrossingCli = new ControllerGraficoPaginaSegnalazionePassaggioLivelloCli(codicePLLevelCrossing, localizzazione, problematica, UtilityAccesso.getPersistence());
                controllerGraficoPaginaSegnalazioneLevelCrossingCli.inviaDatiAlBean();

            } catch (SceltaNonValidaException e) {
                Printer.error(e.getMessage());
                inserisciInput(); // Retry
            } catch (IOException e) {
                Printer.error("Errore durante la lettura dell'input.");
            }
        }
    }
    private boolean verificaInputUscita(String codicePLLevelCrossing, String localizzazione,String problematica){
        return(codicePLLevelCrossing.equalsIgnoreCase("esc") || localizzazione.equalsIgnoreCase("esc") || problematica.equalsIgnoreCase("esc"));

    }
    private void tornaAllaHomePage() throws IOException {
        PaginaHome paginaHome=new PaginaHome();
        paginaHome.displayHomepage();
    }
}
