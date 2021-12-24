package view.mainMenu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import listener.StringListener;
import shared.event.GetGamesListEvent;
import shared.event.GetRankingEvent;
import shared.event.NewGameEvent;
import shared.event.WatchGameEvent;
import shared.listener.EventListener;
import shared.model.User;

public class MainMenuFXMLController {
    @FXML
    private Label usernameLabel;

    @FXML
    private Label wonLabel;

    @FXML
    private Label lostLabel;

    @FXML
    private Label totalLabel;

    private EventListener listener;
    private User user;

    public void setListener(EventListener listener) {
        this.listener = listener;
    }

    public void newGame()
    {
        listener.listen(new NewGameEvent(user));
    }

    public void watchGame()
    {
        listener.listen(new GetGamesListEvent());
    }

    public void chart()
    {
        listener.listen(new GetRankingEvent());
    }

    public void setData(String username, int lost, int won)
    {
        usernameLabel.setText(username);
        lostLabel.setText("Lost : " + lost);
        wonLabel.setText("Won : " + won);
        totalLabel.setText("Score : " + (won-lost));
    }
}
