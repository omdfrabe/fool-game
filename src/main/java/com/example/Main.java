package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.game.Game;
import com.example.model.*;
import com.example.strategy.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Player player1 = new Player("123", new HumanStrategy());
        Player player2 = new Player("BOT", new HumanStrategy());


        List<Player>players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        Game game = new Game(players);
        game.startGame();
    }
}
