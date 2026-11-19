package com.example.model;

import java.util.stream.IntStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private List<Card>deck = new ArrayList<>(); 

    public void generateDeck() {
        int[] ranks = IntStream.rangeClosed(6, 14).toArray(); 
        char[] suits = {'S', 'H', 'D', 'C'};

        for (int s = 0; s < suits.length; s++) {
            for (int r = 0; r < ranks.length; r++) {
                Card card = new Card(ranks[r], suits[s]);
                deck.add(card);
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public Card dealCard() {
        if (getSize() == 0) {
            return null;
        }
        return deck.remove(0);
    }

    public Card drawTrump() {
        Card trumpCard = deck.remove(0);
        deck.add(trumpCard);
        return trumpCard.getCard();
    }

    public int getSize() {
        return  deck.size();
    }
}
