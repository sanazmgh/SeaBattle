package controller.gameLogic;

import controller.network.ClientHandler;
import shared.model.Board;
import shared.model.Game;

import java.util.LinkedList;

public class GameLobby {
    private ClientHandler waiting;
    private final LinkedList<Game> runningGames = new LinkedList<>();

    public GameLobby() {
    }

    public synchronized void startGameRequest(ClientHandler clientHandler) {
        if (waiting == null) {
            waiting = clientHandler;
            //clientHandler.setSide(0);
        }

        else {
            if (waiting != clientHandler) {
                Game game = new Game();
                runningGames.add(game);

                Board board1 = new Board(clientHandler.getUser());
                board1.setMap();
                board1.setTimeRemain(30);
                board1.startTimer();

                Board board0 = new Board(waiting.getUser());
                board0.setMap();
                board0.setTimeRemain(30);
                board0.startTimer();

                game.setBoard(1, board1);
                game.setBoard(0, board0);

                waiting.setGame(game);
                clientHandler.setGame(game);

                waiting.setSide(0);
                clientHandler.setSide(1);

                waiting = null;
            }
        }
    }

    public synchronized void removeGame(Game game)
    {
        runningGames.remove(game);
    }

    public LinkedList<Game> getRunningGames()
    {
        return runningGames;
    }
}
