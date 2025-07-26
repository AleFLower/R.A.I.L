package dao;

import bean.BeanSegnalazioneBinario;
import bean.BeanSegnalazioneLevelCrossing;
import com.example.progettoispw.controllergrafici.TypeOfSegnalazione;
import utility.UtilityAccesso;

import java.io.*;
import java.util.*;

public class SegnalazioniAttiveRisolteDaoImplFileSystem implements SegnalazioniRisolteAttiveDao {

    private static final String FILE_BINARI = "BinarioSegnalato.txt";
    private static final String FILE_PL = "LevelCrossingSegnalato.txt";
    @Override
    public List<BeanSegnalazioneLevelCrossing> getSegnalazioniLevelCrossing(TypeOfSegnalazione tipo)
            throws IOException {

        List<BeanSegnalazioneLevelCrossing> lista = new ArrayList<>();
        File file = new File(FILE_PL);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String codice = null;
            String localizzazione = null;
            String problema = null;
            String stato = null;
            String codiceUtenteSegnalatore = null;

            String codiceUtenteAttuale = UtilityAccesso.getCodiceUtente();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Numero del passaggio a livello: ")) {
                    codice = line.split(": ", 2)[1].trim();
                } else if (line.startsWith("localizzazione:")) {
                    localizzazione = line.split(": ", 2)[1].trim();
                } else if (line.startsWith("Problematica segnalata:")) {
                    problema = line.split(": ", 2)[1].trim();
                } else if (line.startsWith("stato:")) {
                    stato = line.split(": ", 2)[1].trim().toLowerCase();
                } else if (line.startsWith("codiceUtente:")) {
                    codiceUtenteSegnalatore = line.split(": ", 2)[1].trim();
                    if (codiceUtenteSegnalatore.equals(codiceUtenteAttuale) && matchTipo(tipo, stato)) {
                        lista.add(new BeanSegnalazioneLevelCrossing(codice, localizzazione, problema, stato));
                    }
                }
            }
        }

        return lista;
    }

    @Override
    public List<BeanSegnalazioneBinario> getSegnalazioniBinari(TypeOfSegnalazione tipo)
            throws IOException {

        List<BeanSegnalazioneBinario> lista = new ArrayList<>();
        File file = new File(FILE_BINARI);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String numero = null;
            String localizzazione = null;
            String problema = null;
            String stato = null;
            String codiceUtenteSegnalatore = null;

            String codiceUtenteAttuale = UtilityAccesso.getCodiceUtente();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Numero binario:")) {
                    numero = line.split(": ", 2)[1].trim();
                } else if (line.startsWith("localizzazione:")) {
                    localizzazione = line.split(": ", 2)[1].trim();
                } else if (line.startsWith("problematica riscontrata:")) {
                    problema = line.split(": ", 2)[1].trim();
                } else if (line.startsWith("stato:")) {
                    stato = line.split(": ", 2)[1].trim().toLowerCase();
                } else if (line.startsWith("codiceUtente:")) {
                    codiceUtenteSegnalatore = line.split(": ", 2)[1].trim();
                    if (codiceUtenteSegnalatore.equals(codiceUtenteAttuale) && matchTipo(tipo, stato)) {
                        lista.add(new BeanSegnalazioneBinario(numero, localizzazione, problema, stato));
                    }
                }
            }
        }

        return lista;
    }

    private boolean matchTipo(TypeOfSegnalazione tipo, String statoLetto) {
        if (tipo == TypeOfSegnalazione.RISOLTE)
            return statoLetto.equalsIgnoreCase("risolto");
        else
            return statoLetto.equalsIgnoreCase("segnalato") || statoLetto.contains("ancora");
    }
}
