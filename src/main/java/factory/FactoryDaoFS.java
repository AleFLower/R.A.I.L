package factory;

import dao.*;
import eccezioni.ErroreLetturaPasswordException;

import java.sql.SQLException;

public class FactoryDaoFS extends FactoryDao  {

    //just for now , unuseful method, maybe i will implementate later
    @Override
    public LoginDao getLoginDao() throws SQLException, ErroreLetturaPasswordException {
        return null;
    }

    @Override
    public RegistrazioneDao getRegistrazioneDao() throws SQLException, ErroreLetturaPasswordException {
        return null;
    }

    @Override
    public SegnalazioniRisolteAttiveDao getSegnalazioniRisolteAttiveDao() throws SQLException, ErroreLetturaPasswordException {
        return null;
    }


    public EntitaFerroviariaDao useDao(TypeOfPersistence typeOfPersistence, TypeEntita typeEntita) throws SQLException, ErroreLetturaPasswordException {
        // Restituisci il DAO corretto in base al tipo di entità e tipo di persistenza
        if (typeOfPersistence == TypeOfPersistence.FILESYSTEM) {
            switch (typeEntita) {
                case BINARIO:
                    return new BinarioDaoImplFileSystem();
                case SEMAFORO:
                    return new SemaforoDaoImplFileSystem();
                default:
                    throw new IllegalArgumentException("Tipo di entità non supportato: " + typeEntita);
            }
        }
        throw new IllegalArgumentException("Tipo di persistenza non supportato: " + typeOfPersistence);
    }
}
