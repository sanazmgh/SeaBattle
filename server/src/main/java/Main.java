import controller.network.SocketHandler;
import db.UsersDB;
import shared.config.Config;

public class Main {
    public static void main(String[] args) {
        Config config = new Config("./src/main/resources/Configuration/Server.properties");
        UsersDB.setConfig(config);
        SocketHandler socketHandler = new SocketHandler(config);
        socketHandler.start();
    }
}
