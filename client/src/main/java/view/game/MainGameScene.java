package view.game;

import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import shared.config.Config;
import shared.listener.EventListener;

import java.io.IOException;
import java.util.Objects;

public class MainGameScene {

    private final Scene scene ;
    private final FXMLLoader loader;
    private static final String MAIN_GAME = new Config(Constants.CONFIG_ADDRESS)
            .getProperty(String.class,"mainGameScene").orElse("");

    public MainGameScene(){

        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(MAIN_GAME)));
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

    public void setListeners(EventListener listener)
    {
        getLoader().setListener(listener);
    }

    public Scene getScene() {
        return this.scene;
    }

    public MainGameFXMLController getLoader() {
        return loader.getController();
    }
}
