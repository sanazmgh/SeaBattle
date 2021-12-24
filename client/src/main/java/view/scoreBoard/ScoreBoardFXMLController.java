package view.scoreBoard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import listener.StringListener;
import shared.model.User;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ScoreBoardFXMLController implements Initializable {
    @FXML
    private Pane player1Pane;
    @FXML
    private Pane player2Pane;
    @FXML
    private Pane player3Pane;
    @FXML
    private Pane player4Pane;
    @FXML
    private Pane player5Pane;
    @FXML
    private Pane player6Pane;
    @FXML
    private Pane player7Pane;

    private final Pane[] panes = new Pane[7];
    private StringListener listener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        panes[0] = player1Pane;
        panes[1] = player2Pane;
        panes[2] = player3Pane;
        panes[3] = player4Pane;
        panes[4] = player5Pane;
        panes[5] = player6Pane;
        panes[6] = player7Pane;

    }

    public void updateRanking(LinkedList<User> users)
    {
        for (int i=0 ; i<7 ; i++)
            panes[i].setVisible(false);

        for(int i=0 ; i<users.size() && i<7 ; i++)
        {
            User user = users.get(i);
            panes[i].getChildren().clear();
            PlayerInfoPane playerInfoPane = new PlayerInfoPane();
            playerInfoPane.getLoader().setData(user.getUsername(), user.isOnline(), users.get(i).getScore());
            panes[i].getChildren().add(playerInfoPane.getPane());
            panes[i].setVisible(true);
        }
    }

    public void setListener(StringListener listener) {
        this.listener = listener;
    }

    public void back()
    {
        listener.listen("Main menu");
    }
}
