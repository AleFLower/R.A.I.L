package dao;

import eccezioni.ErroreLetturaPasswordException;
import eccezioni.SegnalazioneGiaAvvenutaException;
import entita.EntitaFerroviaria;
import entita.LevelCrossing;
import queries.QueriesSegnalazionePassaggioLivello;
import utility.UtilityAccesso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PassaggioLivelloDaoImplJDBC implements EntitaFerroviariaDao {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    //variabile che viene impostata a 0 o a 1 in base all'esito del salvataggio del passaggio a livello
    //nel database, utile in fase di test
    private int esito;
    public PassaggioLivelloDaoImplJDBC() throws SQLException, ErroreLetturaPasswordException {
        connection=SingletonConnessione.getInstance();
    }
    @Override
    public void saveEntitaStradale(EntitaFerroviaria instance) throws SQLException, SegnalazioneGiaAvvenutaException, ErroreLetturaPasswordException {
        //qui so che ho a che fare con un passaggio a livello alla fine e lo devo mandare al db , allora rendo l'entita stradale un passaggio a livello
        //e poi lo invio
        LevelCrossing levelCrossing=new LevelCrossing(instance.getInfo(),instance.getlocalizzazione(),instance.getDescrizioneProblema());
        //ora lo invio
        //questa operazione può essere fatta da tutti, sia loggati che non
        //vedo se il passaggio a livello e' già presente o no nel db, se lo e' comunico che gi esiste nel db, altrimenti lo salvo nel db
        if(!cercaLevelCrossing(levelCrossing)) {
            if (UtilityAccesso.getCodiceUtente() != null) {
                //l'utente è loggato nel sistema, la sua segnalazione deve essere salvata nel db
                preparedStatement = connection.prepareStatement(QueriesSegnalazionePassaggioLivello.queriesSalvalevelCrossingAdUnUtenteDelSistema());
                preparedStatement.setString(1, levelCrossing.getInfo());
                preparedStatement.setString(2, levelCrossing.getlocalizzazione());
                preparedStatement.setString(3, levelCrossing.getDescrizioneProblema());
                preparedStatement.setString(4, UtilityAccesso.getCodiceUtente());
                preparedStatement.executeUpdate();
                this.esito = 0;
            }
            else{
                preparedStatement = connection.prepareStatement(QueriesSegnalazionePassaggioLivello.queriesSalvalevelCrossing());
                preparedStatement.setString(1, levelCrossing.getInfo());
                preparedStatement.setString(2, levelCrossing.getlocalizzazione());
                preparedStatement.setString(3, levelCrossing.getDescrizioneProblema());
            }
        }else {
            esito=-1;
            throw new SegnalazioneGiaAvvenutaException("il passaggio a livello è stato già segnalato da un altro utente");
        }
    }
    private boolean cercaLevelCrossing(LevelCrossing levelCrossing) throws SQLException, ErroreLetturaPasswordException {
        //verifichiamo che la connessione sia aperta prima
        verificaConnessione();
        preparedStatement=connection.prepareStatement(QueriesSegnalazionePassaggioLivello.cercalevelCrossing());
        preparedStatement.setString(1,levelCrossing.getInfo());
        resultSet=preparedStatement.executeQuery();
        return resultSet.isBeforeFirst();
    }
    private void verificaConnessione() throws SQLException, ErroreLetturaPasswordException {
        if(connection==null){
            new PassaggioLivelloDaoImplJDBC();
        }
    }
    public int getEsito(){
        return this.esito;
    }
}
