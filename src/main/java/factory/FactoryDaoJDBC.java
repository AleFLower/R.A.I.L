package factory;

import dao.*;
import eccezioni.ErroreLetturaPasswordException;

import java.sql.SQLException;

public class FactoryDaoJDBC extends FactoryDao {

    @Override
    public EntitaFerroviariaDao getSaveAssetDao(TypeOfPersistence typeOfPersistence, TypeEntita typeEntita) throws SQLException, ErroreLetturaPasswordException {
        // Restituisci il DAO corretto in base al tipo di entità e tipo di persistenza
        if (typeOfPersistence == TypeOfPersistence.JDBC) {
            switch (typeEntita) {
                case BINARIO:
                    return new BinarioDaoImplJDBC();
                case LEVELCROSSING:
                    return new PassaggioLivelloDaoImplJDBC();
                default:
                    throw new IllegalArgumentException("Tipo di entità non supportato: " + typeEntita);
            }
        }
        throw new IllegalArgumentException("Tipo di persistenza non supportato: " + typeOfPersistence);
    }



    @Override
    public LoginDao getLoginDao() throws SQLException, ErroreLetturaPasswordException {
        return new LoginDaoImpl();
    }

    @Override
    public RegistrazioneDao getRegistrazioneDao() throws SQLException, ErroreLetturaPasswordException {
        return new RegistrazioneDaoImpl();
    }

    @Override
    public SegnalazioniRisolteAttiveDao getSegnalazioniRisolteAttiveDao() throws SQLException, ErroreLetturaPasswordException {
        return new SegnalazioniAttiveRisolteDaoImpl();
    }

}
