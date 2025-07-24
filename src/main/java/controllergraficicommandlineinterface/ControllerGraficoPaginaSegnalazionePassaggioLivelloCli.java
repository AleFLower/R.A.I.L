package controllergraficicommandlineinterface;

import bean.BeanSegnalaEntita;
import cli.PaginaHome;
import cli.PaginaSegnalazionePassaggioALivelloCli;
import controllerapplicativi.ControllerApplicativoSegnalazioneEntita;
import eccezioni.*;

import factory.TypeEntita;
import factory.TypeOfPersistence;
import utility.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class ControllerGraficoPaginaSegnalazionePassaggioLivelloCli {
    private final PaginaSegnalazionePassaggioALivelloCli view;
    private final TypeOfPersistence typeOfPersistence;
    private final TypeEntita typeEntita = TypeEntita.LEVELCROSSING;

    public ControllerGraficoPaginaSegnalazionePassaggioLivelloCli(TypeOfPersistence tipoPersistenza) {
        this.typeOfPersistence = tipoPersistenza;
        this.view = new PaginaSegnalazionePassaggioALivelloCli();
    }

    public void mostraPaginaSegnalazione() throws IOException {
        String codicePL = view.chiediCodicePL();
        if (vuoleUscire(codicePL)) return;

        String localizzazione = view.chiediLocalizzazione();
        if (vuoleUscire(localizzazione)) return;

        String problematica = view.chiediProblematica();
        if (vuoleUscire(problematica)) return;

        if (codicePL.isEmpty() || localizzazione.isEmpty() || problematica.isEmpty()) {
            view.mostraErrore("La prossima volta inserisci qualcosa.");
            return;
        }

        try {
            if (view.confermaSalvataggio()) {
                inviaDatiAlBean(codicePL, localizzazione, problematica);
            }
        } catch (SceltaNonValidaException e) {
            view.mostraErrore(e.getMessage());
            mostraPaginaSegnalazione(); // retry
        }
    }

    private void inviaDatiAlBean(String codicePL, String localizzazione, String problematica) throws IOException {
        BeanSegnalaEntita bean = new BeanSegnalaEntita(
                codicePL, localizzazione, problematica, typeEntita, typeOfPersistence);
        try {
            bean.controllaInputLevelCrossing();
            new ControllerApplicativoSegnalazioneEntita(bean);

            view.mostraMessaggioSuccesso();
        } catch (SegnalazioneGiaAvvenutaException e) {
            view.mostraErrore("Segnalazione già avvenuta per quel passaggio a livello.");
        } catch (LunghezzaInputException | TipoEntitaException | NessunAccessoEffettuatoException |
                 SQLException | ErroreLetturaPasswordException e) {
            view.mostraErrore(e.getMessage());
        }
    }

    private boolean vuoleUscire(String input) {
        return input != null && input.equalsIgnoreCase("esc");
    }

}

