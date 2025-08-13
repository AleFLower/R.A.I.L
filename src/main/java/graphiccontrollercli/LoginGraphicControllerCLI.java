package graphiccontrollercli;

import java.io.IOException;

public class LoginGraphicControllerCLI {
    public void login() throws IOException {
        SendDataGraphicControllerCLI controller = new SendDataGraphicControllerCLI();
        controller.displayAccessPage();
    }
}
