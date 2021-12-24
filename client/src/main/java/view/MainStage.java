package view;

import javafx.application.Platform;
import javafx.stage.Stage;
import listener.StringListener;
import shared.event.ExitEvent;
import shared.event.GetGameEvent;
import shared.event.GetGamesListEvent;
import shared.event.GetRankingEvent;
import shared.listener.EventListener;
import shared.model.Game;
import shared.model.User;
import shared.util.Loop;
import view.authentication.LoginScene;
import view.authentication.SignUpScene;
import view.game.EndGameScene;
import view.game.MainGameScene;
import view.mainMenu.MainMenuScene;
import view.scoreBoard.ScoreBoardScene;
import view.watchGame.GamesBarScene;

import java.util.LinkedList;

public class MainStage {
    private final Stage stage;
    private final SignUpScene signUpScene;
    private final LoginScene loginScene;
    private final MainMenuScene mainMenuScene;
    private final MainGameScene mainGameScene;
    private final GamesBarScene gamesBarScene;
    private final ScoreBoardScene scoreBoardScene;
    private final EndGameScene endGameScene;
    private final EventListener listener;
    private User user;
    private Loop loop;

    public MainStage(Stage stage, EventListener listener)
    {
        this.stage = stage;
        this.listener = listener;

        signUpScene = new SignUpScene();
        loginScene = new LoginScene();
        mainMenuScene = new MainMenuScene();
        mainGameScene = new MainGameScene();
        gamesBarScene = new GamesBarScene();
        scoreBoardScene = new ScoreBoardScene();
        endGameScene = new EndGameScene();

        StringListener stringListener = str -> {
            switch (str) {
                case "Login" -> stage.setScene(loginScene.getScene());
                case "Sign up" -> stage.setScene(signUpScene.getScene());
                case "Main menu" -> {
                    if(loop != null)
                        loop.stop();

                    mainMenuScene.getLoader().setData(user.getUsername(), user.getLost(), user.getWon());
                    stage.setScene(mainMenuScene.getScene());
                }
            }
        };

        signUpScene.setListeners(listener, stringListener);
        loginScene.setListeners(listener, stringListener);
        mainMenuScene.setListeners(listener);
        mainGameScene.setListeners(listener);
        gamesBarScene.setListeners(listener, stringListener);
        scoreBoardScene.setListeners(stringListener);
        endGameScene.setListeners(stringListener);

        stage.setTitle("Sea Battle");
        stage.setScene(loginScene.getScene());
        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(event -> {
            listener.listen(new ExitEvent());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) { }

            if (loop != null)
                loop.stop();

            Platform.exit();
            System.exit(0);
        });
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setVisible(boolean signingUp, int type)
    {
        if(!signingUp)
            loginScene.getLoader().setErrorVisibility(type);

        else
            signUpScene.getLoader().setErrorVisibility(type);
    }

    public void enter(User user)
    {
        this.user = user;
        Platform.runLater(
                () -> {
                    stage.setScene(mainMenuScene.getScene());
                    mainMenuScene.getLoader().setData(user.getUsername(), user.getLost(), user.getWon());
                }
        );
    }

    public void gameScene(Game game) {
        if (stage.getScene() != mainGameScene.getScene()) {
            Platform.runLater(
                    () -> {
                        if(loop != null)
                            loop.stop();

                        stage.setScene(mainGameScene.getScene());
                    }
            );

            loop = new Loop(2, this::updateGame);
            loop.start();
        }

        else {

            Platform.runLater(
                    () -> {
                        if(game != null) {
                            if (game.isFinished()) {
                                if(loop != null)
                                    loop.stop();

                                showWinner(game.getBoard()[game.getWinner()].getUser().getUsername());
                                return;
                            }
                        }
                        mainGameScene.getLoader().setData();
                        mainGameScene.getLoader().setGame(game, user);
                    }
            );
        }
    }

    public void getRunningGames(LinkedList<Game> games)
    {
        if (stage.getScene() != gamesBarScene.getScene()) {
            Platform.runLater(
                    () -> {
                        if(loop != null)
                            loop.stop();

                        stage.setScene(gamesBarScene.getScene());
                    }
            );

            loop = new Loop(2, this::updateGamesList);
            loop.start();
        }

        else {

            Platform.runLater(
                    () -> {
                        gamesBarScene.getLoader().setData(games);
                    }
            );
        }
    }

    public void getRanking(LinkedList<User> users)
    {
        if (stage.getScene() != scoreBoardScene.getScene()) {
            Platform.runLater(
                    () -> {
                        if(loop != null)
                            loop.stop();

                        stage.setScene(scoreBoardScene.getScene());
                    }
            );

            loop = new Loop(2, this::updateChart);
            loop.start();
        }

        else {

            Platform.runLater(
                    () -> {
                        scoreBoardScene.getLoader().updateRanking(users);
                    }
            );
        }
    }

    public void showWinner(String str)
    {
        Platform.runLater(
                () -> {
                    if(loop != null)
                        loop.stop();

                    endGameScene.getLoader().setWinner(str);
                    stage.setScene(endGameScene.getScene());
                }
        );
    }

    private void updateGame() {
        listener.listen(new GetGameEvent());
    }

    private void updateGamesList() {
        listener.listen(new GetGamesListEvent());
    }

    private void updateChart()
    {
        listener.listen(new GetRankingEvent());
    }

}
