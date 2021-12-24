package view.authentication;

import constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import listener.StringListener;
import shared.config.Config;
import shared.listener.EventListener;

import java.io.IOException;
import java.util.Objects;

public class LoginScene {
    private final Scene scene ;
    private final FXMLLoader loader;
    private static final String LOGIN = new Config(Constants.CONFIG_ADDRESS)
            .getProperty(String.class,"loginScene").orElse("");

    public LoginScene(){

        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(LOGIN)));
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

    public void setListeners(EventListener listener, StringListener stringListener)
    {
        LoginFXMLController loginFXML = this.getLoader();
        loginFXML.setListener(listener);
        loginFXML.setStringListener(stringListener);
    }

    public Scene getScene() {
        return this.scene;
    }

    public LoginFXMLController getLoader() {
        return loader.getController();
    }
}
