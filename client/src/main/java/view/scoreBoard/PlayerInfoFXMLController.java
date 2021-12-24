package view.scoreBoard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlayerInfoFXMLController {
    @FXML
    private Label usernameLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label scoreLabel;

    public void setData(String username, boolean isOnline, int score)
    {
        usernameLabel.setText(username);
        statusLabel.setText(isOnline? "Online" : "Offline");
        scoreLabel.setText(Integer.toString(score));
    }
}
