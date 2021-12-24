package controller.network;

import controller.gameLogic.AuthenticationController;
import controller.gameLogic.GameLobby;
import controller.gameLogic.GameLogic;
import db.UsersDB;
import shared.event.Event;
import shared.event.EventVisitor;
import shared.model.Game;
import shared.model.User;
import shared.response.*;

import java.security.SecureRandom;

public class ClientHandler extends Thread implements EventVisitor {
    private final SocketResponder sender;
    private final GameLobby gameLobby;
    private final GameLogic gameLogic = new GameLogic();
    private User user;
    private int side;
    private int authToken;

    public ClientHandler(SocketResponder sender, GameLobby gameLobby){
        this.sender = sender;
        this.gameLobby = gameLobby;
        gameLogic.setLobby(gameLobby);
    }

    public void setSide(int side) {
        gameLogic.setSide(side);
    }

    public void setGame(Game game) {
        gameLogic.setGame(game);
    }

    public User getUser() {
        return user;
    }

    public void run() {
        while (true) {
            Event event = sender.getEvent();

            if(event != null) {
                sender.sendResponse(event.visit(this));
            }
        }
    }

    @Override
    public Response Authentication(String username, String pass1, String pass2, boolean signingUp) {
        int isValid = AuthenticationController.validAuthentication(username, pass1, pass2, signingUp);

        if(isValid== 0 && signingUp)
        {
            UsersDB.add(new User(username, pass1));
        }

        return new AuthenticationResponse(signingUp, isValid);
    }

    @Override
    public Response Enter(String username) {
        this.user = UsersDB.get(username);
        user.makeOnline();
        UsersDB.update(user);
        SecureRandom random = new SecureRandom();
        this.authToken = random.nextInt();
        return new EnterResponse(authToken, user);
    }

    @Override
    public Response StartGame(User user, long autToken) {
        if(autToken != this.authToken)
            return null;

        gameLobby.startGameRequest(this);
        return getGame(autToken);
    }

    @Override
    public Response getGame(long autToken) {
        if(autToken != this.authToken)
            return null;

        gameLogic.checkForUpdate();
        return new GetGameResponse(gameLogic.getGame());
    }

    @Override
    public Response rearrange(long autToken) {
        if(autToken != this.authToken)
            return null;

        gameLogic.rearrange();
        return getGame(autToken);
    }

    @Override
    public Response startGame(long autToken) {
        if(autToken != this.authToken)
            return null;

        gameLogic.startGame();
        return getGame(autToken);
    }

    @Override
    public Response clickOnBoard(int x, int y, long autToken) {
        if(autToken != this.authToken)
            return null;

        gameLogic.bombed(x, y);
        return getGame(autToken);
    }

    @Override
    public Response getGamesList(long autToken) {
        if(autToken != this.authToken)
            return null;

        return new GamesListResponse(gameLobby.getRunningGames());
    }

    @Override
    public Response watchGame(int i, long autToken) {
        if(autToken != this.authToken)
            return null;

        setGame(gameLobby.getRunningGames().get(i));
        setSide(2);
        return getGame(autToken);
    }

    @Override
    public Response getRanking(long autToken) {
        if(autToken != this.authToken)
            return null;

        return new GetRankingResponse(UsersDB.getAll());
    }

    @Override
    public Response exitTheApp(long autToken) {
        if(autToken != this.authToken)
            return null;

        gameLobby.removeGame(gameLogic.getGame());
        this.user.makeOffline();
        UsersDB.update(user);
        return null;
    }
}
