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
    static final String QUERIES_VEDI_BINARI_SEGNALATI="SELECT * FROM BINARIO";
    //operazione che possono fare gli utenti
    static final String QUERIES_SALVA_BINARIO = "INSERT INTO BINARIO(numeroBINARIO,localizzazione,problematica,codiceUtente) VALUES(?,?,?,?);";
    //operazione che puo fare solo l'admin dopo che il problema e' stato risolto
    static final String QUERIES_RIMUOVI_BINARIO="DELETE  FROM BINARIO where (localizzazione=? AND codice_utente=?);";
    static final String QUERIES_CAMBIA_STATO_BINARIO="UPDATE BINARIO SET stato = ? WHERE (codiceUtente = ? AND localizzazione=?);";
    //queste 2 che mostrano le segnalazioni falle girare prima su mysql rivedila
    static final String QUERIES_MOSTRA_SEGNALAZIONI_EFFETTUATE="SELECT DISTINCT localizzazione,stato,problematica,numeroBINARIO FROM BINARIO,account WHERE (BINARIO.codiceUtente=? AND stato = 'segnalato');";
    //provala questa delle completate prima
    static final String QUERIES_MOSTRA_SEGNALAZIONI_COMPLETATE="SELECT DISTINCT numeroBINARIO,localizzazione,problematica,stato FROM BINARIO,account WHERE (BINARIO.codiceUtente=? AND stato = 'risolto');";
    static final String QUERIES_MOSTRA_TUTTE_SEGNALAZIONI_BINARIO = "SELECT * FROM BINARIO;";


    static final String QUERIES_COTROLLA_SE_LA_BINARIO_A_QUELL_LOCALIZZAZIONE_E_STATA_SEGNALATA="SELECT DISTINCT numeroBINARIO,localizzazione FROM BINARIO WHERE (BINARIO.numeroBINARIO = ? AND BINARIO.localizzazione=?);";
    public static String queriesVediBinariSegnalati(){
        return QUERIES_VEDI_BINARI_SEGNALATI;
    }
    public static String queriesSalvabinario(){return QUERIES_SALVA_BINARIO;}
    public static String queriesRimuovibinario(){return QUERIES_RIMUOVI_BINARIO;}
    public static String queriesCambiaStatobinario(){return QUERIES_CAMBIA_STATO_BINARIO;}
   //queries per vedere le binari che gli utenti loggati hanno segnalato
    public static String queriesMostraSegnalazioniEffettuate(){
        return QUERIES_MOSTRA_SEGNALAZIONI_EFFETTUATE;
    }
    //queries per mostrare le binari che sono state riparate
    public static String queriesMostraSegnalazioniCompletate(){return QUERIES_MOSTRA_SEGNALAZIONI_COMPLETATE;}
    public static String queriesVediSeLeSegnalAQuelllocalizzazioneSonoStateGiaSegnalate(){
        return QUERIES_COTROLLA_SE_LA_BINARIO_A_QUELL_LOCALIZZAZIONE_E_STATA_SEGNALATA;
    }
    public static String getQueriesMostraTutteSegnalazioniBinario(){return QUERIES_MOSTRA_TUTTE_SEGNALAZIONI_BINARIO;}
}
