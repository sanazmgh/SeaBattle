package view.authentication;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import listener.StringListener;
import shared.event.AuthenticationEvent;
import shared.event.EnterEvent;
import shared.listener.EventListener;

public class SignUpFXMLController {
    @FXML
    private TextField usernameTextField ;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField password2Field;

    @FXML
    private Text error1Text;

    @FXML
    private Text error2Text;

    private EventListener listener;
    private StringListener stringListener;

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getPassword2() {
        return password2Field.getText();
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
        stringListener.listen("Login");
    }

    public void signUp()
    {
        listener.listen(new AuthenticationEvent(this.getUsername(), this.getPassword(), this.getPassword2(), true));
    }

    public void setErrorVisibility(int type)
    {
        error1Text.setVisible(false);
        error2Text.setVisible(false);

        if(type == 1)
            error1Text.setVisible(true);

        else if(type == 2)
            error2Text.setVisible(true);

        else
        {
            listener.listen(new EnterEvent(this.getUsername()));
            //stringListener.listen("Enter");
            //listener.listen(new MainMenuDataEvent(this.getUsername()));
        }
    }
}
