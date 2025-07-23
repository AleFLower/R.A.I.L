package cli;

import controllergraficicommandlineinterface.ControllerGraficoPagineSegnalazioneBinarioCli;
import factory.TypeOfPersistence;
import utility.Printer;
import utility.UtilityAccesso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PaginaSegnalazioneBinarioCli {
    private String localizzazione;
    private String numeroBinario;
    private String problematica;
    public void inserisciInput() throws IOException {
        try{
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        Printer.print("-----------------Pagina Segnalazione binario stradale-----------------\n" +
                "inserisci localizzazione(digitare esc per uscire): ");
        this.localizzazione =bufferedReader.readLine();
        Printer.print("inserisci il numero del binario: ");
        this.numeroBinario =bufferedReader.readLine();
        Printer.print("inserisci la problematica da segnalare: ");
        this.problematica=bufferedReader.readLine();
        
        if(verificaInputUscita(localizzazione, numeroBinario,problematica)){
            //l'utente vuole tornare alla home
            tornaAllaHomePage();
        }


        //l'utente ha inserito dati corretti, ora questi li passiamo al controller grafico che li passerà a sua volta al bean
        Printer.print("digita:\n1 per salvare il binario\n2 per salvare il binario su file");
        String scelta=bufferedReader.readLine();
            int sceltaInt = Integer.parseInt(scelta);

            //secondo le richieste del progetto, in memory non si deve usare nessun meccanismo di persistenza
            //se uso la full version, allora si , posso scegliere il tipo di persistenza
            if(UtilityAccesso.getPersistence() == TypeOfPersistence.MEMORY){
                if(sceltaInt == 2){
                    Printer.print("This option is not available in demo version");
                    tornaAllaHomePage();
                }
            }

            TypeOfPersistence tipoPersistenza;

            if (sceltaInt == 2) {
                tipoPersistenza = TypeOfPersistence.FILESYSTEM;
            } else {
                tipoPersistenza = UtilityAccesso.getPersistence(); // MEMORY o JDBC
            }
        ControllerGraficoPagineSegnalazioneBinarioCli controllerGraficoPagineSegnalazioneBinarioCli=new ControllerGraficoPagineSegnalazioneBinarioCli(localizzazione,problematica, numeroBinario,tipoPersistenza);
        controllerGraficoPagineSegnalazioneBinarioCli.inviaDatiAlBean();
    }catch (NumberFormatException e){
            Printer.error("inserire numeri dove e' richiesto");
            inserisciInput();
        }
    }
    private boolean verificaInputUscita(String localizzazione, String numeroBinario, String problematica){
        return (localizzazione.equalsIgnoreCase("esc") || numeroBinario.equalsIgnoreCase("esc") || problematica.equalsIgnoreCase("esc"));

    }
    private void tornaAllaHomePage() throws IOException {
        PaginaHome paginaHome=new PaginaHome();
        paginaHome.displayHomepage();
    }
}
