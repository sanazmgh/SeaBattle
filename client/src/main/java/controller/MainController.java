package controller;

import controller.network.SocketHandler;
import javafx.stage.Stage;
import shared.event.Event;
import shared.model.Game;
import shared.model.User;
import shared.response.Response;
import shared.response.ResponseVisitor;
import shared.util.Loop;
import view.MainStage;

import java.util.LinkedList;
import java.util.List;

public class MainController implements ResponseVisitor {
    private final SocketHandler socketHandler;
    private final Stage stage;
    private MainStage mainStage;
    private long authToken;
    private final List<Event> events;
    private final Loop loop;

    public MainController(SocketHandler socketHandler, Stage stage) {
        this.socketHandler = socketHandler;
        this.stage = stage;
        this.events = new LinkedList<>();
        this.loop = new Loop(10, this::sendEvents);
    }

    public void start() {
        loop.start();
        mainStage = new MainStage(stage, this::addEvent);
    }

    private void addEvent(Event event) {
        synchronized (events) {
            events.add(event);
        }
    }

    private void sendEvents() {
        List<Event> temp;
        synchronized (events) {
            temp = new LinkedList<>(events);
            events.clear();
        }
        for (Event event : temp) {
            event.setAuthToken(authToken);
            Response response = socketHandler.send(event);

            if(response != null)
                response.visit(this);
        }
    }

    public void close()
    {
        socketHandler.close();
    }

    @Override
    public void visitGame(Game game) {
        mainStage.gameScene(game);
    }

    @Override
    public void visitGamesList(LinkedList<Game> games) {
        mainStage.getRunningGames(games);
    }

    @Override
    public void visitGetRanking(LinkedList<User> users) {
        mainStage.getRanking(users);
    }

    @Override
    public void visitAuthentication(boolean signingUp, int type) {
        mainStage.setVisible(signingUp, type);
    }

    @Override
    public void visitEnter(long authToken, User user) {
        this.authToken = authToken;
        mainStage.enter(user);
    }
}
