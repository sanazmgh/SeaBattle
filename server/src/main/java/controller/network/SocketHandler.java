package controller.network;

import controller.gameLogic.GameLobby;
import shared.config.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler extends Thread{

    private Config config;

    public SocketHandler(Config config)
    {
        this.config = config;
    }

    public void run()
    {
        try {
            int port = config.getProperty(Integer.class, "Port").orElse(8000);
            ServerSocket serverSocket = new ServerSocket(port);
            GameLobby lobby = new GameLobby();

            while(true)
            {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(new SocketResponder(socket), lobby);
                clientHandler.start();
            }
        }
        catch (IOException e) {
            System.err.println("couldn't make the port");
        }
    }
}
