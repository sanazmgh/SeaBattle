package view.authentication;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import listener.StringListener;
import shared.event.AuthenticationEvent;
import shared.event.EnterEvent;
import shared.listener.EventListener;

public class LoginFXMLController {
    @FXML
    private TextField usernameTextField ;

    @FXML
    private TextField passwordField;

    @FXML
    private Text errorText;

    private EventListener listener;
    private StringListener stringListener;

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public void setListener(EventListener listener) {
        this.listener = listener;
    }

    public void setStringListener(StringListener stringListener)
    {
        this.stringListener = stringListener;
    }

    public void login()
    {
        listener.listen(new AuthenticationEvent(this.getUsername(), this.getPassword(), "", false));
    }

    public void signUp()
    {
        stringListener.listen("Sign up");
    }

    public void setErrorVisibility(int type)
    {
        if(type == 1)
            errorText.setVisible(true);

        else
        {
            listener.listen(new EnterEvent(this.getUsername()));
            //listener.listen(new MainMenuDataEvent(this.getUsername()));
        }

    }
}
