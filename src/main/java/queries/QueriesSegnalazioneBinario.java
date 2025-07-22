package queries;

public class QueriesSegnalazioneBinario {

    //costruiamo le buche in modo che solo gli utenti registrati al sistema possono segnalarle
    //operazione che puo fare solo l'admin
    private QueriesSegnalazioneBinario(){
        //non viene chiamato da nessuno, questa classe e' una utity alla fine
    }
    static final String QUERIES_VEDI_Segnal_SEGNALATE="SELECT * FROM binario";
    //operazione che possono fare gli utenti
    static final String QUERIES_SALVA_binario = "INSERT INTO binario(numeroBinario,stazione,problematica,codiceUtente) VALUES(?,?,?,?);";
    //operazione che puo fare solo l'admin dopo che il problema e' stato risolto
    static final String QUERIES_RIMUOVI_binario="DELETE  FROM binario where (stazione=? AND codice_utente=?);";
    static final String QUERIES_CAMBIA_STATO_binario="UPDATE binario SET stato = ? WHERE (codiceUtente = ? AND stazione=?);";
    //queste 2 che mostrano le segnalazioni falle girare prima su mysql rivedila
    static final String QUERIES_MOSTRA_SEGNALAZIONI_EFFETTUATE="SELECT DISTINCT stazione,stato,problematica,numeroBinario FROM binario,account WHERE (binario.codiceUtente=? AND problemaRisolto=false);";
    //provala questa delle completate prima
    static final String QUERIES_MOSTRA_SEGNALAZIONI_COMPLETATE="SELECT DISTINCT numeroBinario,stazione,problematica,stato FROM binario,account WHERE (binario.codiceUtente=? AND problemaRisolto!=false);";
    static final String QUERIES_MOSTRA_TUTTE_SEGNALAZIONI_binario = "SELECT * FROM binario;";


    static final String QUERIES_COTROLLA_SE_LA_binario_A_QUELL_stazione_E_STATA_SEGNALATA="SELECT DISTINCT stazione FROM binario WHERE (binario.stazione=?);";
    public static String queriesVediSegnalSegnalate(){
        return QUERIES_VEDI_Segnal_SEGNALATE;
    }
    public static String queriesSalvabinario(){return QUERIES_SALVA_binario;}
    public static String queriesRimuovibinario(){return QUERIES_RIMUOVI_binario;}
    public static String queriesCambiaStatobinario(){return QUERIES_CAMBIA_STATO_binario;}
   //queries per vedere le buche che gli utenti loggati hanno segnalato
    public static String queriesMostraSegnalazioniEffettuate(){
        return QUERIES_MOSTRA_SEGNALAZIONI_EFFETTUATE;
    }
    //queries per mostrare le buche che sono state riparate
    public static String queriesMostraSegnalazioniCompletate(){return QUERIES_MOSTRA_SEGNALAZIONI_COMPLETATE;}
    public static String queriesVediSeLeSegnalAQuellstazioneSonoStateGiaSegnalate(){
        return QUERIES_COTROLLA_SE_LA_binario_A_QUELL_stazione_E_STATA_SEGNALATA;
    }
    public static String getQueriesMostraTutteSegnalazioniSegnal(){return QUERIES_MOSTRA_TUTTE_SEGNALAZIONI_binario;}
}
