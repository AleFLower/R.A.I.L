package controllergraficicommandlineinterface;

import bean.BeanLogin;
import cli.PaginaAccessoAlSistema;
import cli.PaginaHome;
import controllerapplicativi.ControllerApplicativoLoginAlSistema;
import eccezioni.ErroreLetturaPasswordException;
import eccezioni.NonEsisteUtenteNelSistemaException;

import utility.UtilityAccesso;


import java.io.IOException;

import java.sql.SQLException;

public class ControllerGraficoInviaDatiAccessoAlSistemaCli {
    private final PaginaAccessoAlSistema view = new PaginaAccessoAlSistema();
    private final PaginaHome paginaHome = new PaginaHome();

    public void mostraPaginaAccesso() throws IOException {
        String email = view.chiediEmail();
        if (vuoleUscire(email)) {
            return;
        }

        String password = view.chiediPassword();
        if (vuoleUscire(password)) {
            return;
        }

        if (email.isBlank() || password.isBlank()) {
            view.mostraMessaggioErrore("La prossima volta inserisci una email e una password.");
            return;
        }

        inviaDatiAlBean(email, password);
    }

    private void inviaDatiAlBean(String email, String password) throws IOException {
        BeanLogin bean = new BeanLogin(email, password);
        String erroreValidazione = bean.svolgiControlli();

        if (erroreValidazione != null) {
            view.mostraMessaggioErrore(erroreValidazione);
            return;
        }

        try {
            new ControllerApplicativoLoginAlSistema(bean, UtilityAccesso.getPersistence());
            view.attendiTastoPerContinuare("Accesso effettuato! Premi INVIO per tornare alla home.");
        } catch (NonEsisteUtenteNelSistemaException e) {
            view.mostraMessaggioErrore("Credenziali non valide. L'utente non esiste.");
            if (view.confermaRegistrazione()) {
                String username = view.chiediUsername();
                avviaRegistrazione(email, password, username);
            }
        } catch (SQLException | ErroreLetturaPasswordException e) {
            view.mostraMessaggioErrore("Errore durante l'accesso: " + e.getMessage());
        }


    }

    private void avviaRegistrazione(String email, String password, String username) {
        ControllerGraficoRegistrazioneCli controllerRegistrazione =
                new ControllerGraficoRegistrazioneCli(email, password, username);
        controllerRegistrazione.registraUtente();
    }

    private boolean vuoleUscire(String input) {
        return input != null && input.equalsIgnoreCase("esc");
    }

}
