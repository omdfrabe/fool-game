package com.example.model;

public enum Suit {
        SPADES("♠", "\u001B[90m"),
        HEARTS("♥", "\u001B[31m"),
        DIAMONDS("♦", "\u001B[31m"),
        CLUBS("♣", "\u001B[90m");

        private final String RESET = "\u001B[0m";

        private final String symbol;
        private final String color;
        
    Suit(String symbol, String color) {
        this.symbol = symbol;
        this.color = color;
    }

    @Override
    public String toString() {
        return color + symbol + RESET;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getColor() {
        return color;
    }
}
