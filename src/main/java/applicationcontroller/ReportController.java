package applicationcontroller;

import bean.ReportBean;
import dao.*;
import exception.PasswordReadException;
import exception.NoLoginPerformedException;
import exception.ReportAlreadyExistsException;
import exception.ReportTypeException;
import model.RailwayAsset;
import factory.DaoFactory;
import factory.RailwayAssetFactory;
import factory.AssetType;
import factory.TypeOfPersistence;
import utility.AccessUtility;

import java.io.IOException;
import java.sql.SQLException;

public class ReportController {
    /*questo controller applicativo viene chiamato quanod sto inviando una nuova segnalazione di un entita al db,
     * crea quindi un entita dai dati che riceve, chiama il dao che gestisce la dovuta segnalazione e in caso di errori
     * lancia delle eccezioni, al controller grafico non ritorna nulla */
    private AssetType assetType;
    private RailwayAsset railwayAsset;
    private DaoFactory dao;
    private AccountController accountController = new AccountController();

    //lui non cattura SegnalazioneGiaAvvenuta(e altre) ma le rilancia al metodo chiamante, e lancia una nuova eccezione nessunaccessoeffettuato
    public ReportController(ReportBean reportBean) throws SQLException, PasswordReadException, ReportAlreadyExistsException, NoLoginPerformedException, ReportTypeException, IOException {
        /*il controller applicativo riceve il bean che contiene le informazioni dell'entita segnalata, setta quindi tutti i
         * suoi parametri prendendoli dal bean*/
        //inizio prendendo il tipo dell'entità segnalata
        this.assetType = reportBean.getType();

        //verifica stato account prima di procedere
        verifyAccountState();

        //creo la factory che deve creare a sua volta la mia entita in base al tipo
        RailwayAssetFactory railwayAssetFactory=new RailwayAssetFactory();
        //chiamo il metodo createEntita che in base al tipo che gli passo crea un passaggio a livello  o un binario
        railwayAsset = railwayAssetFactory.createAsset(reportBean);
        //nessuna eccezione creata, l'entita ferroviaria e' stata quindi creata, devo inviarla alla rispettiva tabella nel db
        //nessuna eccezione devo inviare l'entità ferroviaria segnalata e per capire se deve essere salvata sul db o in locale
        //passo come argomento il tipo di persistenza che il controller grafico ha inserito nel mio bean nel momento della segnalazione

        sendReport(railwayAsset, reportBean.getTypeOfPersistence());
        //qui mando la notifica all admin, le salvo tutte dentro il centro notifiche
        notifyAdmin();
    }

    private void verifyAccountState() throws NoLoginPerformedException {
        //vedo se è una binario e se l'utente è online, cosi in caso contrario lo blocco subito
        if (assetType == AssetType.TRACK && accountController.getAccountState().equals("OFFLINE") ) {
            //l'utente cerca di segnalare una binario ma non e' registrato quindi viene lanciata un eccezione che gli
            //dice che la segnalazione della binario puo' essere fatta solo se registrato
            throw new NoLoginPerformedException("To report a track you must be logged in");
        }
    }

    private void notifyAdmin() {
        String username = AccessUtility.getUsername();
        username = (username != null) ? username : "Unknown user";
        NotificationController notificationController = new NotificationController();  //collabora con esso
        notificationController.addNotification(username + " has reported a " + assetType);
    }

    private void sendReport(RailwayAsset railwayAsset, TypeOfPersistence typeOfPersistence) throws SQLException, PasswordReadException, ReportAlreadyExistsException, IOException {
        //creo una factoryDao la quale ha solo il metodo getSaveAssetDao e mi restituisce un dao in base al tipo di entita stradale e al
        //tipo di persistenza che ho ricevuto come parametri
        DaoFactory factoryDao = DaoFactory.getFactory(typeOfPersistence);
        SendReportDao sendreportDao = factoryDao.getSendAssetDao(typeOfPersistence, railwayAsset.getAssetType());
        sendreportDao.sendRailwayAssetReport(railwayAsset);
    }
}
