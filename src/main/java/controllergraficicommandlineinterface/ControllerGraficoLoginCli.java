package controllergraficicommandlineinterface;

import cli.PaginaAccessoAlSistema;

import java.io.IOException;

public class ControllerGraficoLoginCli {
    public void accediAlSistema() throws IOException {
        PaginaAccessoAlSistema paginaAccessoAlSistema=new PaginaAccessoAlSistema();
        paginaAccessoAlSistema.mostraPaginaAccesso();
    }
}
