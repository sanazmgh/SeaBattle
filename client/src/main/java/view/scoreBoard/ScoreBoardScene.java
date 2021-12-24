package view.scoreBoard;

import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import listener.StringListener;
import shared.config.Config;
import shared.listener.EventListener;
import view.mainMenu.MainMenuFXMLController;

import java.io.IOException;
import java.util.Objects;

public class ScoreBoardScene {

    private final Scene scene ;
    private final FXMLLoader loader;
    private static final String SCORE_BOARD = new Config(Constants.CONFIG_ADDRESS)
            .getProperty(String.class,"scoreBoardScene").orElse("");

    public ScoreBoardScene()
    {
        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(SCORE_BOARD)));
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
        this.getLoader().setListener(listener);
    }

    public Scene getScene() {
        return this.scene;
    }

    public ScoreBoardFXMLController getLoader() {
        return loader.getController();
    }
}
