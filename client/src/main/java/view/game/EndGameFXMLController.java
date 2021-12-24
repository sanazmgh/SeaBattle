package view.game;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import listener.StringListener;

public class EndGameFXMLController {

    @FXML
    private Label winnerLabel;

    private StringListener listener;

    public void setListener(StringListener listener) {
        this.listener = listener;
    }

    public void setWinner(String winner)
    {
        winnerLabel.setText(winner);
    }

    public void returnToMainMenu()
    {
        listener.listen("Main menu");
    }
}
