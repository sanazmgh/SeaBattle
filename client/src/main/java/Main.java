import constants.Constants;
import controller.MainController;
import controller.network.SocketHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import shared.config.Config;

import java.net.Socket;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Config config = new Config(Constants.CONFIG_ADDRESS);
        String host = config.getProperty(String.class,"host").orElse(Constants.DEFAULT_HOST);
        int port = config.getProperty(Integer.class,"port").orElse(Constants.DEFAULT_PORT);

        Socket socket = new Socket(host, port);
        MainController controller = new MainController(new SocketHandler(socket), stage);
        controller.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
