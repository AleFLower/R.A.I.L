package dao;

import eccezioni.ErroreLetturaPasswordException;
import eccezioni.SegnalazioneGiaAvvenutaException;
import entita.Binario;
import entita.EntitaFerroviaria;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
public class BinarioDaoImplFileSystem implements EntitaFerroviariaDao {
    //dao che si occupa di salvare l'entità stradale nel file system
    private static final String FILE_NAME = "BinarioSegnalato.txt";
    //variabile che viene cambiata in base all'esito del salvataggio della binario stradale nel file system
    //molto utile nel momento di testare il metodo SaveEntitaStradale
    private int esitoSalvataggio;

    @Override
    public void saveEntitaStradale(EntitaFerroviaria instance) throws SQLException, SegnalazioneGiaAvvenutaException, ErroreLetturaPasswordException, IOException {
        //se sono qui voglio salvare su file system la binario
        Binario binario= new Binario(instance.getlocalizzazione(),instance.getInfo(),instance.getDescrizioneProblema());
        //adesso devo salvarla in locale
        try {
            //imposto a true il secondo parametro del costruttore del file writer, in questo modo non c'e' sovrascrittura
            BufferedWriter fileWriter=new BufferedWriter(new FileWriter(FILE_NAME,true));
            String binarioSegnalataDaSalvareInLocale=convertibinarioInTxt(binario);
            fileWriter.write(binarioSegnalataDaSalvareInLocale);
            //vado a capo cosi la prossima volta che si scrive su quel file e' tutto piu ordinato e la segnalazione
            //nuova non si attaccherà alla vecchia
            fileWriter.newLine();
            fileWriter.close();
            //tutto e' andato a buon fine, esito assumerà un valore che indica il successo
            esitoSalvataggio=0;
        } catch (IOException e) {
            esitoSalvataggio=1;
            throw new IOException("problema con il file writer");
        }
    }
    private String convertibinarioInTxt(Binario binario){
        return "Visibilità binario:  "+binario.getInfo()+"\nlocalizzazione: "+binario.getlocalizzazione()+ "\nproblematica riscontrata: " + binario.getDescrizioneProblema() +"\nstato: ancora non segnalato al database";
    }
    public int getEsito(){
        return this.esitoSalvataggio;
    }
}
