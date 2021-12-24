package view.game;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import shared.listener.EventListener;
import shared.model.Board;
import shared.model.Game;
import shared.model.User;

public class MainGameFXMLController {

    @FXML
    Label statusLabel;
    @FXML
    Pane rivalPane;
    @FXML
    Pane selfPane;

    private final GamePane selfBoard = new GamePane();
    private final GamePane rivalBoard = new GamePane();
    private EventListener listener;

    public void setListener(EventListener listener) {
        selfBoard.setListeners(listener);
        rivalBoard.setListeners(listener);
    }

    public void setData()
    {
        rivalPane.getChildren().clear();
        rivalPane.getChildren().add(rivalBoard.getPane());

        selfPane.getChildren().clear();
        selfPane.getChildren().add(selfBoard.getPane());
    }

    public void setStatus(String str)
    {
        statusLabel.setText("Playing with " + str);
    }

    public void setStatus(String player1, String player2)
    {
        statusLabel.setText("Watching the battle between  " + player1 + " and" + player2);
    }

    public void setBoards(Board selfBoard, Board rivalBoard, boolean isWatching)
    {
        this.selfBoard.getLoader().setBoard(selfBoard, !isWatching);
        this.rivalBoard.getLoader().setBoard(rivalBoard, false);
    }

    public void setGame(Game game, User user)
    {
        if(game != null)
        {
            int side = 2;

            if(game.getBoard()[0].getUser().getUsername().equals(user.getUsername()))
                side = 0;

            if(game.getBoard()[1].getUser().getUsername().equals(user.getUsername()))
                side = 1;

            if(side == 2)
            {
                setStatus(game.getBoard()[0].getUser().getUsername(), game.getBoard()[1].getUser().getUsername());
                setBoards(game.getBoard()[0], game.getBoard()[1], true);
            }

            else
            {
                setStatus(game.getBoard()[(side + 1) % 2].getUser().getUsername());
                setBoards(game.getBoard()[side], game.getBoard()[(side + 1) % 2], false);
            }

            this.rivalBoard.getLoader().disableButtons(!game.isStarted() || game.getSide() != side);

        }
    }
}
