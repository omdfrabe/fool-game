package com.example.network;

import com.example.game.Game;
import com.example.model.Player;
import com.example.strategy.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final int PORT;
    private final List<NetworkStrategy>playerPool = new ArrayList<>();

    public Server(int PORT) {
        this.PORT = PORT;
    }

    public Server() {
        this.PORT = 8998;
    }

    public void startServer(int playerCount, int botCount) {
        List<Socket>clientSockets = new ArrayList<>();
        playerPool.clear();

        int humanCount = playerCount - botCount;
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("Server is running on: " + PORT + ".");
            List<Player>players = new ArrayList<>();
            while (players.size() < humanCount) {
                Socket clientSocket =  serverSocket.accept();
                clientSockets.add(clientSocket);
                NetworkStrategy strategy = new NetworkStrategy(clientSocket);
                playerPool.add(strategy);
                String nickname = strategy.askName();
                Player player = new Player(nickname == null || nickname.trim().isEmpty() ? "Player " + (players.size() + 1) : nickname.trim(), strategy);
                players.add(player);
                System.out.println(player.getName() + " has joined game.");
            }
            for (int i = 0; i < botCount; i++) {
                players.add(new Player("BOT " + (i + 1), new BotStrategy()));
            }
            Game game = new Game(players, this::broadcast);
            game.startGame();
        }
        catch (IOException exception){
            System.err.println(exception);
        }
        finally {
            for (Socket socket : clientSockets) {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    } 
                } 
                catch (IOException exception) {
                    System.err.println("FINALL ERROR: " + exception);
                }
            }
        }
    }

    public int getPort() {
        return this.PORT;
    }

    public void broadcast(String msg) {
        for (NetworkStrategy strategy : playerPool) {
            strategy.sendMessage(msg);
        }
    }
}
