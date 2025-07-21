package factory;

import dao.*;
import eccezioni.ErroreLetturaPasswordException;

import java.sql.SQLException;

public class FactoryDaoMemory extends FactoryDao {

    @Override
    public EntitaFerroviariaDao useDao(TypeOfPersistence typeOfPersistence, TypeEntita typeEntita) throws SQLException, ErroreLetturaPasswordException {
        // Restituisci il DAO corretto in base al tipo di entità e tipo di persistenza
            switch (typeEntita) {
                case BINARIO:
                    return new BinarioDaoImplMemory();
                case SEMAFORO:
                    return new SemaforoDaoImplMemory();
                default:
                    throw new IllegalArgumentException("Tipo di entità non supportato: " + typeEntita);
            }
    }


    @Override
    public LoginDao getLoginDao() {
        return new LoginDaoMemory();
    }

    @Override
    public RegistrazioneDao getRegistrazioneDao() {
        return new RegistrazioneDaoImplMemory();
    }

    @Override
    public SegnalazioniRisolteAttiveDao getSegnalazioniRisolteAttiveDao() {
        return new SegnalazioniAttiveRisolteDaoImplMemory();
    }
}
