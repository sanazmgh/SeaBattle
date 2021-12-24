package view.scoreBoard;

import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import shared.config.Config;
import shared.listener.EventListener;
import view.game.GamePaneFXMLController;

import java.io.IOException;
import java.util.Objects;

public class PlayerInfoPane {

    private Pane pane;
    private final FXMLLoader loader;
    private static final String PLAYER = new Config(Constants.CONFIG_ADDRESS)
            .getProperty(String.class,"playerInfoPane").orElse("");

    public PlayerInfoPane(){

        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(PLAYER)));

        try
        {
            pane = loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public Pane getPane() {
        return this.pane;
    }

    public PlayerInfoFXMLController getLoader() {
        return loader.getController();
    }
}
