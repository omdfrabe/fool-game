package com.example.strategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.example.model.Card;
import com.example.model.Deck;
import com.example.model.Hand;
import com.example.model.Table;

public class NetworkStrategy implements PlayerStrategy {
    private Socket clientSocket;
    private final BufferedReader in;
    private final PrintWriter out;

    public NetworkStrategy(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
        this.out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    @Override
    public int chooseAttackCard(Hand hand, Card trumpCard, Deck deck, Table table) {
        while (true) {
            if (table.getSize() > 0) {
              out.println(table.getTable());  
            }
            multiLine(
                "Trump of current game: " + trumpCard.getSuit() + ". There are " + deck.getSize() + " cards left in the deck. (The last is " + trumpCard + ".)" +
                "\n" + hand
            );
            out.println("ASK:Enter card you want to play (if you ended your attack type \"P\"): ");
            String input = null;
            try {
                input = in.readLine();
            }
            catch (IOException exception) {
                throw new RuntimeException("Player disconnected");
            }
            if (input == null) {
                throw new RuntimeException("Player disconnected");
            }
            input = input.trim();
            if (input.equalsIgnoreCase("p")) {
                    return -1;
            }
            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < hand.getSize()) {
                    return index;
                }
                else {
                    out.println("MSG:Card number is out of range!");
                }
            } 
            catch (NumberFormatException exception) {
                out.println("MSG:Invalid input");
            }
        }
    }

    @Override
    public int chooseDefenseCard(Hand hand, Card attackCard, Card trumpCard, Deck deck, Table table) {
        while (true) {
            if (table.getSize() > 0) {
              out.println(table.getTable());  
            }
            multiLine(
                "Trump of current game: " + trumpCard.getSuit() + ". There are " + deck.getSize() + "cards left in the deck. (The last is " + trumpCard + ".)" +
                "\n" + hand
            );
            out.println("ASK:Enter card you will defend (if impossible type \"T\"): ");
            String input = null;
            try {
                input = in.readLine();
            }
            catch (IOException exception){
                throw new RuntimeException("Player disconnected");
            }
            if (input == null) {
                throw new RuntimeException("Player disconnected");
            }
            input = input.trim();
            if (input.equalsIgnoreCase("t")) {
                return -1;
            }
            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < hand.getSize()) {
                    return index;
                }
                else {
                    out.println("MSG:Card number is out of range!");
                }
            } 
            catch (NumberFormatException exception) {
                out.println("MSG:Invalid input");
            }
        }
    }

    public void sendMessage(String msg) {
        multiLine(msg);
    }
    
    public void multiLine(String text) {
        if (text == null) {
            return;
        }
        String[] lines = text.split("\n");
        for (String line : lines) {
            out.println("MSG:" + line);
        }
    }

    public String askName() {
        out.println("ASK:Enter your nickname: ");
        try {
            String nickname = in.readLine();
            return nickname;
        }
        catch (IOException exception){
            return null;
        }  
    }
}
