package com.example.model;

import com.example.strategy.PlayerStrategy;

import java.util.List;

public class Player {
    private String name;
    private Hand hand;
    private PlayerStrategy strategy;

    public Player(String name, PlayerStrategy strategy) {
        this.name = name;
        this.hand = new Hand();
        this.strategy = strategy;
    }

    public String getName() {
        return this.name;
    }

    public Hand getHand() {
        return this.hand;
    }

    public PlayerStrategy getStrategy() {
        return this.strategy;
    }

    public void takeCard(Card card) {
        hand.addCard(card);
    }

    public void takeCards(List<Card> tableCards) {
        for (Card card : tableCards) {
            hand.addCard(card);
        }
    }

    public Card playCard(int index) {
        return hand.removeCard(index);
    }

    public Card peekCard(int index) {
        if (index < hand.getSize() && index >= 0) {
            return hand.getCards().get(index);
        }
        return null;
    }

    public Card attack(Card trumpCard, Deck deck, Table table) {
        int index = strategy.chooseAttackCard(hand, trumpCard, deck, table);
        return index == -1 ? null : peekCard(index);
    }

    public Card defend(Card attackCard, Card trumpCard, Deck deck, Table table) {
        int index = strategy.chooseDefenseCard(hand, attackCard, trumpCard, deck, table);
        return index == -1 ? null : peekCard(index);
    }

    public boolean hasCards() {
        return hand.getSize() > 0;
    }
}


