package dao;

import eccezioni.ErroreLetturaPasswordException;
import eccezioni.SegnalazioneGiaAvvenutaException;
import entita.EntitaFerroviaria;
import entita.SemaforoFerroviario;
import queries.QueriesSemaforo;
import utility.UtilityAccesso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SemaforoDaoImplJDBC implements EntitaFerroviariaDao {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    //variabile che viene impostata a 0 o a 1 in base all'esito del salvataggio del Semaforo illuminazione
    //nel database, utile in fase di test
    private int esito;
    public SemaforoDaoImplJDBC() throws SQLException, ErroreLetturaPasswordException {
        connection=SingletonConnessione.getInstance();
    }
    @Override
    public void saveEntitaStradale(EntitaFerroviaria instance) throws SQLException, SegnalazioneGiaAvvenutaException, ErroreLetturaPasswordException {
        //qui so che ho a che fare con un Semaforo alla fine e lo devo mandare al db , allora rendo l'entita stradale un Semaforo
        //e poi lo invio
        SemaforoFerroviario SemaforoDaSegnalare=new SemaforoFerroviario(instance.getInfo(),instance.getstazione(),instance.getDescrizioneProblema());
        //ora lo invio
        //questa operazione può essere fatta da tutti, sia loggati che non
        //vedo se il Semaforo e' già presente o no nel db, se lo e' comunico che gi esiste nel db, altrimenti lo salvo nel db
        if(!cercaSemaforo(SemaforoDaSegnalare)){
                //l'utente è loggato nel sistema, la sua segnalazione deve essere salvata nel db
                preparedStatement=connection.prepareStatement(QueriesSemaforo.queriesSalvaSemaforoAdUnUtenteDelSistema());
                preparedStatement.setString(1,SemaforoDaSegnalare.getInfo());
                preparedStatement.setString(2,SemaforoDaSegnalare.getstazione());
                preparedStatement.setString(3,SemaforoDaSegnalare.getDescrizioneProblema());
                preparedStatement.setString(4,UtilityAccesso.getCodiceUtente());
                preparedStatement.executeUpdate();
                this.esito=0;
        }else {
            esito=-1;
            throw new SegnalazioneGiaAvvenutaException("il semaforo è stato già segnalato da un altro utente");
        }
    }
    private boolean cercaSemaforo(SemaforoFerroviario SemaforoDaCercare) throws SQLException, ErroreLetturaPasswordException {
        //verifichiamo che la connessione sia aperta prima
        verificaConnessione();
        preparedStatement=connection.prepareStatement(QueriesSemaforo.cercaSemaforo());
        preparedStatement.setString(1,SemaforoDaCercare.getInfo());
        preparedStatement.setString(2,SemaforoDaCercare.getstazione());
        resultSet=preparedStatement.executeQuery();
        return resultSet.isBeforeFirst();
    }
    private void verificaConnessione() throws SQLException, ErroreLetturaPasswordException {
        if(connection==null){
            new SemaforoDaoImplJDBC();
        }
    }
    public int getEsito(){
        return this.esito;
    }
}
