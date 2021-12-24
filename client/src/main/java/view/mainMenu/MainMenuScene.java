package view.mainMenu;

import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import listener.StringListener;
import shared.config.Config;
import shared.listener.EventListener;
import view.game.MainGameFXMLController;

import java.io.IOException;
import java.util.Objects;

public class MainMenuScene {

    private final Scene scene ;
    private final FXMLLoader loader;
    private static final String MAIN_MENU = new Config(Constants.CONFIG_ADDRESS)
            .getProperty(String.class,"mainMenuScene").orElse("");

    public MainMenuScene()
    {
        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(MAIN_MENU)));

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
        this.getLoader().setListener(listener);
    }

    public Scene getScene() {
        return this.scene;
    }

    public MainMenuFXMLController getLoader() {
        return loader.getController();
    }
}
