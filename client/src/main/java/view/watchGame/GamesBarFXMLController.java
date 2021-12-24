package view.watchGame;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import listener.StringListener;
import shared.event.WatchGameEvent;
import shared.listener.EventListener;
import shared.model.Game;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class GamesBarFXMLController implements Initializable {
    @FXML
    Button gameButton1;
    @FXML
    Button gameButton2;
    @FXML
    Button gameButton3;
    @FXML
    Button gameButton4;
    @FXML
    Button gameButton5;

    private EventListener listener;
    private StringListener stringListener;
    private final Button[] button = new Button[5];
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        button[0] = gameButton1;
        button[1] = gameButton2;
        button[2] = gameButton3;
        button[3] = gameButton4;
        button[4] = gameButton5;

        for(int i=0 ; i<5 ; i++)
        {
            int finalI = i;
            button[i].setOnAction(event -> watchGame(finalI));
            button[i].setVisible(false);
        }
    }

    public void setListener(EventListener listener, StringListener stringListener) {
        this.listener = listener;
        this.stringListener = stringListener;
    }

    public void setData(LinkedList<Game> games)
    {
        int ind = 0;
        for(int i=0 ;i<5 ; i++)
        {
            button[i].setVisible(false);
        }

        for (int i=0 ; i<games.size() && i<5 ; i++)
        {
            button[i].setText(games.get(i).getBoard()[0].getUser().getUsername() + " vs " + games.get(i).getBoard()[1].getUser().getUsername());
            button[i].setVisible(true);
        }
    }

    public void watchGame(int ind)
    {
        listener.listen(new WatchGameEvent(ind));
    }

    public void back()
    {
        stringListener.listen("Main menu");
    }
}
