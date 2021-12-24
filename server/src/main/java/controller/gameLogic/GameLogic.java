package controller.gameLogic;

import controller.network.ClientHandler;
import db.UsersDB;
import shared.model.Cell;
import shared.model.Game;
import shared.model.Ship;

public class GameLogic {
    private Game game;
    private int side;
    private GameLobby lobby;

    public void setSide(int side) {
        this.side = side;
        //game.getBoard()[side].setTimeRemain(30);

        if(side != 2)
            game.getBoard()[side].startTimer();
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setLobby(GameLobby lobby) {
        this.lobby = lobby;
    }

    public void checkForUpdate()
    {
        if(side == 2)
            return;

        if(game != null) {
            checkForEndGame();

            synchronized (game) {
                if (game.isStarted())
                {
                    if (game.getSide() == this.side)
                        if (game.getBoard()[side].getTimeRemain() <= 0)
                            game.switchSide(false);
                }

                else
                {
                    if (game.getBoard()[side].getTimeRemain() <= 0)
                    {
                        game.getBoard()[side].stopTimer();
                        game.getBoard()[side].startGame();
                    }

                    if (game.getBoard()[side].isStarted() && game.getBoard()[(side + 1) % 2].isStarted())
                        game.startGame();
                }
            }
        }
    }

    public void checkForEndGame()
    {
        if(side == 2)
            return;

        if(game != null)
        {
            synchronized (game)
            {
                if (!game.isFinished())
                    if (game.getBoard()[side].getRemainedShips() == 0) {
                        game.setFinished(true, (side+1)%2);
                        game.getBoard()[side].getUser().addToLost();
                        game.getBoard()[(side + 1) % 2].getUser().addToWon();

                        lobby.removeGame(game);

                        UsersDB.update(game.getBoard()[0].getUser());
                        UsersDB.update(game.getBoard()[1].getUser());
                    }
            }
        }
    }

    public void rearrange() {
        if(side == 2)
            return;

        game.getBoard()[side].addOnTime(10);
        game.getBoard()[side].setMap();
    }

    public void startGame()
    {
        if(side == 2)
            return;

        game.getBoard()[side].startGame();
        game.getBoard()[side].stopTimer();
    }

    public void bombed(int x, int y)
    {
        if(side == 2)
            return;

        Cell cell = game.getBoard()[(side+1)%2].getCells()[x][y];
        cell.setCrashed(true);

        if(cell.isOccupied())
        {
            Ship ship = game.getBoard()[(side + 1) % 2].getShips()[cell.getOccupiedBy()];
            int shipX = ship.getX();
            int shipY = ship.getY();
            boolean isCrashed = true;

            for (int i = 0; i < ship.getLength(); i++) {
                if (!game.getBoard()[(side + 1) % 2].getCells()[shipX][shipY].isCrashed())
                    isCrashed = false;

                if (ship.isHorizontal())
                    shipX++;

                else
                    shipY++;
            }

            if(isCrashed)
            {
                game.getBoard()[(side+1)%2].loseShip();

                shipX = ship.getX();
                shipY = ship.getY();

                for (int i = 0; i < ship.getLength(); i++) {
                    Cell current = game.getBoard()[(side + 1) % 2].findCell(shipX + 1, shipY);
                    if (current != null)
                        current.setCrashed(true);

                    current = game.getBoard()[(side + 1) % 2].findCell(shipX + 1, shipY + 1);
                    if (current != null)
                        current.setCrashed(true);

                    current = game.getBoard()[(side + 1) % 2].findCell(shipX, shipY + 1);
                    if (current != null)
                        current.setCrashed(true);

                    current = game.getBoard()[(side + 1) % 2].findCell(shipX - 1, shipY + 1);
                    if (current != null)
                        current.setCrashed(true);

                    current = game.getBoard()[(side + 1) % 2].findCell(shipX-1, shipY );
                    if (current != null)
                        current.setCrashed(true);

                    current = game.getBoard()[(side + 1) % 2].findCell(shipX - 1, shipY - 1);
                    if (current != null)
                        current.setCrashed(true);

                    current = game.getBoard()[(side + 1) % 2].findCell(shipX, shipY-1);
                    if (current != null)
                        current.setCrashed(true);

                    current = game.getBoard()[(side + 1) % 2].findCell(shipX+1, shipY-1);
                    if (current != null)
                        current.setCrashed(true);

                    if (ship.isHorizontal())
                        shipX++;

                    else
                        shipY++;

                }
            }
        }

        game.getBoard()[side].addToMoves();
        game.switchSide(cell.isOccupied());
    }
}
