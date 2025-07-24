package factory;

import dao.*;
import eccezioni.ErroreLetturaPasswordException;

import java.sql.SQLException;

public abstract class FactoryDao {

    public abstract LoginDao getLoginDao() throws SQLException, ErroreLetturaPasswordException;
    public abstract RegistrazioneDao getRegistrazioneDao() throws SQLException, ErroreLetturaPasswordException;
    public abstract SegnalazioniRisolteAttiveDao getSegnalazioniRisolteAttiveDao() throws SQLException, ErroreLetturaPasswordException;
    public abstract EntitaFerroviariaDao getSaveAssetDao(TypeOfPersistence typeOfPersistence, TypeEntita typeEntita) throws SQLException, ErroreLetturaPasswordException;
    // Factory method per ottenere la giusta implementazione di DAO in base al tipo di persistenza
    public static FactoryDao getFactory(TypeOfPersistence typeOfPersistence) {
        switch (typeOfPersistence) {
            case JDBC:
                return new FactoryDaoJDBC();
            case FILESYSTEM:
                return new FactoryDaoFS();
            case MEMORY:
                return new FactoryDaoMemory();
            default:
                throw new IllegalArgumentException("Tipo di persistenza non supportato: " + typeOfPersistence);
        }
    }
}

