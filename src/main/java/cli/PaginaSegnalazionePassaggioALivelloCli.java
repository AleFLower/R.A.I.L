package cli;

import controllergraficicommandlineinterface.ControllerGraficoPaginaSegnalazionePassaggioLivelloCli;
import factory.TypeOfPersistence;
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
        }else if(codicePLLevelCrossing.equals("")|| localizzazione.equals("") || problematica.equals("")){
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
                controllerGraficoPaginaSegnalazioneLevelCrossingCli = new ControllerGraficoPaginaSegnalazionePassaggioLivelloCli(codicePLLevelCrossing, localizzazione,problematica, tipoPersistenza);
                controllerGraficoPaginaSegnalazioneLevelCrossingCli.inviaDatiAlBean();
            }catch(NumberFormatException e){
                //si verifica se l'utente non digita un numero
                Printer.error("digitare solo un numero tra 1,2");
                inserisciInput();  //retry
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
