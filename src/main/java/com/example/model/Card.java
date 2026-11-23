package com.example.model;

public class Card {
    private final String RESET = "\u001B[0m";

    private final Suit suit;
    private final int rank;

    public Card(int rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override 
    public String toString() {
        String rankNames = switch(rank) {
            case 14 -> "A";
            case 13 -> "K";
            case 12 -> "Q";
            case 11 -> "J";

            default -> String.valueOf(rank);
        };
        return suit.getColor() + rankNames + suit.getSymbol() + RESET; 
    }

    public Suit getSuit() {
        return this.suit;
    }

    public int getRank() {
        return this.rank;
    }
}
