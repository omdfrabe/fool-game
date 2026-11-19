package com.example.model;

public class Card {
    private char suit;
    private int rank;

    public Card(int rank, char suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override 
    public String toString() {
        String suitNames = switch (suit) {
            case 'S' -> "♠";
            case 'H' -> "♥";
            case 'D' -> "♦";
            case 'C' -> "♣";

            default -> "Invalid";
        };
        String rankNames = switch(rank) {
            case 14 -> "A";
            case 13 -> "K";
            case 12 -> "Q";
            case 11 -> "J";

            default -> String.valueOf(rank);
        };
        return rankNames + suitNames; 
    }

    public Card getCard() {
        return this;
    }

    public char getSuit() {
        return this.suit;
    }

    public int getRank() {
        return this.rank;
    }
}
