package view.game;

import constants.Constants;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import shared.config.Config;
import shared.event.ClickOnBoardEvent;
import shared.event.RearrangeEvent;
import shared.event.StartGameEvent;
import shared.listener.EventListener;
import shared.model.Board;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePaneFXMLController implements Initializable {

    @FXML
    private Pane buttonsPane;
    @FXML
    private Button rearrangeButton;
    @FXML
    private Button startButton;
    @FXML
    private Label timer;
    @FXML
    private Label moves;
    @FXML
    private Label username;

    private final Button[][] buttons = new Button[11][11];

    private EventListener listener;
    private int side;
    private int rearrange = 0;

    private final Config config = new Config(Constants.CONFIG_ADDRESS);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        int height = config.getProperty(Integer.class, "buttonHeight").orElse(0);
        int weight = config.getProperty(Integer.class, "buttonWeight").orElse(0);

        for(int i=0 ; i<10 ; i++)
            for(int j=0 ; j<10 ; j++) {
                buttons[i][j] = new Button();
                buttons[i][j].setLayoutX(i*weight);
                buttons[i][j].setLayoutY(j*height);
                buttons[i][j].setPrefSize(weight, height);
                buttons[i][j].setStyle(config.getProperty(String.class, "darkBlue").orElse(""));

                int finalI = i;
                int finalJ = j;

                buttons[i][j].setOnAction(e -> clickOnBoard(finalI, finalJ));
                buttons[i][j].setDisable(true);
                buttonsPane.getChildren().add(buttons[i][j]);
            }

        rearrangeButton.setDisable(true);
        startButton.setDisable(true);
    }

    public void setListener(EventListener listener) {
        this.listener = listener;
    }

    public void startGame()
    {
        listener.listen(new StartGameEvent());
        rearrangeButton.setDisable(true);
        startButton.setDisable(true);
    }

    public void rearrange()
    {
        rearrange ++;
        listener.listen(new RearrangeEvent());

        if(rearrange == 2)
            rearrangeButton.setDisable(true);
    }

    public void setBoard(Board board, boolean mySelf)
    {
        rearrangeButton.setDisable(rearrange == 2);
        startButton.setDisable(false);

        rearrangeButton.setVisible(mySelf && !board.isStarted());
        startButton.setVisible(mySelf && !board.isStarted());

        timer.setText(Integer.toString(board.getTimeRemain()));
        username.setText(board.getUser().getUsername());
        moves.setText(board.getMoves() + " moves");

        if(mySelf)
        {
            for(int i=0 ; i<10 ; i++)
            {
                for(int j=0 ; j<10; j++)
                {
                    if(board.getCells()[i][j].isOccupied())
                        buttons[i][j].setStyle(config.getProperty(String.class, "purple").orElse(""));

                    else
                        buttons[i][j].setStyle(config.getProperty(String.class, "darkBlue").orElse(""));
                }
            }
        }

        for(int i=0 ; i<10 ; i++)
            for(int j=0 ; j<10 ; j++)
            {
                if(board.getCells()[i][j].isCrashed())
                {
                    buttons[i][j].setStyle(config.getProperty(String.class, "black").orElse(""));

                    if(board.getCells()[i][j].isOccupied())
                        buttons[i][j].setStyle(config.getProperty(String.class, "lightBlue").orElse(""));
                }
            }

        for(int i=0 ; i<10 ; i++)
        {
            boolean died = true;
            int x = board.getShips()[i].getX();
            int y = board.getShips()[i].getY();

            for(int j=0 ; j<board.getShips()[i].getLength(); j++)
            {
                if(!board.getCells()[x][y].isCrashed())
                    died = false;

                if(board.getShips()[i].isHorizontal())
                    x++;

                else
                    y++;
            }

            if(died)
            {
                x = board.getShips()[i].getX();
                y = board.getShips()[i].getY();

                for(int j=0 ; j<board.getShips()[i].getLength(); j++)
                {
                    buttons[x][y].setStyle(config.getProperty(String.class, "green").orElse(""));

                    if(board.getShips()[i].isHorizontal())
                        x++;

                    else
                        y++;
                }
            }
        }
    }

    public void disableButtons(boolean condition)
    {
        for(int i=0 ; i<10 ; i++)
            for(int j=0 ; j<10 ; j++)
                buttons[i][j].setDisable(condition);
    }

    public void clickOnBoard(int x, int y)
    {
        listener.listen(new ClickOnBoardEvent(x, y));
    }
}
