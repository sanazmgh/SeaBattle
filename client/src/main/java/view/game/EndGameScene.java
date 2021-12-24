package view.game;

import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import listener.StringListener;
import shared.config.Config;
import shared.listener.EventListener;

import java.io.IOException;
import java.util.Objects;

public class EndGameScene {

    private final Scene scene ;
    private final FXMLLoader loader;
    private static final String ENDGAME = new Config(Constants.CONFIG_ADDRESS)
            .getProperty(String.class,"endGameScene").orElse("");

    public EndGameScene(){

        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(ENDGAME)));
        Parent root = null;
        try
        {
            root = loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        this.scene = new Scene(root);

    }

    public void setListeners(StringListener listener)
    {
        getLoader().setListener(listener);
    }

    public Scene getScene() {
        return this.scene;
    }

    public EndGameFXMLController getLoader() {
        return loader.getController();
    }
}
