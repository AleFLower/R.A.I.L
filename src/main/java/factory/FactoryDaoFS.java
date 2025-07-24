package factory;

import dao.*;
import eccezioni.ErroreLetturaPasswordException;

import java.sql.SQLException;

public class FactoryDaoFS extends FactoryDao  {

    //for future implementations
    @Override
    public LoginDao getLoginDao() throws SQLException, ErroreLetturaPasswordException {
        return new LoginDaoFileSystem();
    }

    @Override
    public RegistrazioneDao getRegistrazioneDao() throws SQLException, ErroreLetturaPasswordException {
        return new RegistrazioneDaoFileSystem();
    }

    @Override
    public SegnalazioniRisolteAttiveDao getSegnalazioniRisolteAttiveDao() throws SQLException, ErroreLetturaPasswordException {
        return new SegnalazioniAttiveRisolteDaoImplFileSystem();
    }


    public EntitaFerroviariaDao getSaveAssetDao(TypeOfPersistence typeOfPersistence, TypeEntita typeEntita) throws SQLException, ErroreLetturaPasswordException {
        // Restituisci il DAO corretto in base al tipo di entità e tipo di persistenza
        if (typeOfPersistence == TypeOfPersistence.FILESYSTEM) {
            switch (typeEntita) {
                case BINARIO:
                    return new BinarioDaoImplFileSystem();
                case LEVELCROSSING:
                    return new PassaggioLivelloDaoImplFileSystem();
                default:
                    throw new IllegalArgumentException("Tipo di entità non supportato: " + typeEntita);
            }
        }
        throw new IllegalArgumentException("Tipo di persistenza non supportato: " + typeOfPersistence);
    }
}
