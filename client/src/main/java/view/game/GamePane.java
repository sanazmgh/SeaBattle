package view.game;

import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import shared.config.Config;
import shared.listener.EventListener;

import java.io.IOException;
import java.util.Objects;

public class GamePane {

    private Pane pane;
    private final FXMLLoader loader;
    private static final String GAME = new Config(Constants.CONFIG_ADDRESS)
            .getProperty(String.class,"gamePane").orElse("");

    public GamePane(){

        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(GAME)));
        try
        {
            pane = loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void setListeners(EventListener listener)
    {
        getLoader().setListener(listener);
    }

    public Pane getPane() {
        return this.pane;
    }

    public GamePaneFXMLController getLoader() {
        return loader.getController();
    }
}
