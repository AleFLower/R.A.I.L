package queries;


/*
Ipotesi a livello database(e anche memory) per i binari: ho come chiave primaria numero del binario e la localizzazione.
La segnalazione avviene cosi: ovviamente ci sono sparsi nella nazione diversi binari con stesso numero di binario
quindi distinguiamo ovviamente anche per localizzazione(ovvero, una sorta di indirizzo, con il chilometraggio
di dove si trova il binario, tipo km 45+12, cosi un utente non si sbaglia, piuttosto che inserire stazione termini
o roma termini

Inoltre, per quanto riguarda il fatto che un utente vuole segnalare la stessa entità binario o passaggio a livello
si presuppone che in memory se l'ho gia segnalato, dopo che spengo l'app non l'ho piu e quindi posso risegnalarlo. Su persistenza
si presuppone che l'admin, dopo aver risolto il problema, faccia un refresh del db eliminando alcune entry e quindi
si puo risegnalare lo stesso problema per quello stesso binario o passaggio a livello.
*/
public class QueriesSegnalazioneBinario {

    //costruiamo i binari in modo che solo gli utenti registrati al sistema possono segnalarli
    //operazione che puo fare solo l'admin
    private QueriesSegnalazioneBinario(){
        //non viene chiamato da nessuno, questa classe e' una utity alla fine
    }
    static final String QUERIES_VEDI_Segnal_SEGNALATE="SELECT * FROM binario";
    //operazione che possono fare gli utenti
    static final String QUERIES_SALVA_binario = "INSERT INTO binario(numeroBinario,localizzazione,problematica,codiceUtente) VALUES(?,?,?,?);";
    //operazione che puo fare solo l'admin dopo che il problema e' stato risolto
    static final String QUERIES_RIMUOVI_binario="DELETE  FROM binario where (localizzazione=? AND codice_utente=?);";
    static final String QUERIES_CAMBIA_STATO_binario="UPDATE binario SET stato = ? WHERE (codiceUtente = ? AND localizzazione=?);";
    //queste 2 che mostrano le segnalazioni falle girare prima su mysql rivedila
    static final String QUERIES_MOSTRA_SEGNALAZIONI_EFFETTUATE="SELECT DISTINCT localizzazione,stato,problematica,numeroBinario FROM binario,account WHERE (binario.codiceUtente=? AND stato = 'segnalato');";
    //provala questa delle completate prima
    static final String QUERIES_MOSTRA_SEGNALAZIONI_COMPLETATE="SELECT DISTINCT numeroBinario,localizzazione,problematica,stato FROM binario,account WHERE (binario.codiceUtente=? AND stato = 'risolto');";
    static final String QUERIES_MOSTRA_TUTTE_SEGNALAZIONI_binario = "SELECT * FROM binario;";


    static final String QUERIES_COTROLLA_SE_LA_binario_A_QUELL_localizzazione_E_STATA_SEGNALATA="SELECT DISTINCT numeroBinario,localizzazione FROM binario WHERE (binario.numeroBinario = ? AND binario.localizzazione=?);";
    public static String queriesVediSegnalSegnalate(){
        return QUERIES_VEDI_Segnal_SEGNALATE;
    }
    public static String queriesSalvabinario(){return QUERIES_SALVA_binario;}
    public static String queriesRimuovibinario(){return QUERIES_RIMUOVI_binario;}
    public static String queriesCambiaStatobinario(){return QUERIES_CAMBIA_STATO_binario;}
   //queries per vedere le binari che gli utenti loggati hanno segnalato
    public static String queriesMostraSegnalazioniEffettuate(){
        return QUERIES_MOSTRA_SEGNALAZIONI_EFFETTUATE;
    }
    //queries per mostrare le binari che sono state riparate
    public static String queriesMostraSegnalazioniCompletate(){return QUERIES_MOSTRA_SEGNALAZIONI_COMPLETATE;}
    public static String queriesVediSeLeSegnalAQuelllocalizzazioneSonoStateGiaSegnalate(){
        return QUERIES_COTROLLA_SE_LA_binario_A_QUELL_localizzazione_E_STATA_SEGNALATA;
    }
    public static String getQueriesMostraTutteSegnalazioniSegnal(){return QUERIES_MOSTRA_TUTTE_SEGNALAZIONI_binario;}
}
