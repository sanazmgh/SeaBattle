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

public class SignUpScene {
    private final Scene scene ;
    private final FXMLLoader loader;
    private static final String SIGNUP = new Config(Constants.CONFIG_ADDRESS)
            .getProperty(String.class,"signUpScene").orElse("");

    public SignUpScene(){

        this.loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(SIGNUP)));
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
        SignUpFXMLController signUpFXML = this.getLoader();
        signUpFXML.setListener(listener);
        signUpFXML.setStringListener(stringListener);
    }

    public Scene getScene() {
        return this.scene;
    }

    public SignUpFXMLController getLoader() {
        return loader.getController();
    }
}
