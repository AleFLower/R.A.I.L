package graphiccontrollercli;

import java.io.IOException;

public class LoginGraphicControllerCLI {
    public void accediAlSistema() throws IOException {
        SendDataGraphicControllerCLI controller = new SendDataGraphicControllerCLI();
        controller.displayAccessPage();
    }
}
