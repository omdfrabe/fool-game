package com.example.strategy;

import java.util.Scanner;

import com.example.model.Card;
import com.example.model.Deck;
import com.example.model.Hand;

public class HumanStrategy implements PlayerStrategy {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public int chooseAttackCard(Hand hand, Card trumpCard, Deck deck) {
        while (true) {
            System.out.printf(
            "Trump of current game: %s. There are %d cards left in the deck." +
            "\n%s." +
            "\nEnter card you want to play: ",
            trumpCard.getCard(), deck.getSize(), hand 
        );
            if (!scanner.hasNextInt()) {
                scanner.next();
                continue;
            }
            int index = scanner.nextInt() - 1;
            if (index >= 0 && index < hand.getSize()) {
                return index;
            } 
        } 
    }

    @Override
    public int chooseDefenseCard(Hand hand, Card attackCard, Card trumpCard, Deck deck) {
        while (true) {
            System.out.printf(
                "Opponent played with %s." +
                "\nTrump of current game: %s. There are %d cards left in the deck." +
                "\n%s." +
                "\nEnter card you will defend (if impossible type \"T\"): ",
                attackCard, trumpCard.getCard(), deck.getSize(), hand 
            );
            if (!scanner.hasNextInt()) {
                String index = scanner.next();
                if (index.equalsIgnoreCase("t")) {
                    return -1;
                }
                continue;
            }
            int index = scanner.nextInt() - 1;
            if (index >= 0 && index < hand.getSize()) {
                return index;
            } 
        }
    }

}
