package com.example.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {
    private List<Card>hand = new ArrayList<>();

    @Override 
    public String toString() {
        if (!hand.isEmpty()) {
           StringBuilder handList = new StringBuilder();
            for(int i = 0; i < hand.size(); i++) {
                handList
                .append((i + 1) + ".")
                .append("[")
                .append(hand.get(i))
                .append(i + 1 != hand.size() ? "], " : "]"); 
            }
            return handList.toString(); 
        }
        return "Hand is empty!";
    }

    public void addCard(Card card) {
        if (card != null) {
            hand.add(card);
        }
    } 

    public Card removeCard(int index) {
        if (index >= 0 && index < hand.size()) {
            return hand.remove(index);
        }
        return null;
    }

    public int getSize() {
        return hand.size();
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(hand);
    }
}
