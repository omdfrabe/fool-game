package com.example.model;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Table {
    public final int MAX_CARDS = 6;

    private Map<Card, Card> tableCards = new LinkedHashMap<>();

    @Override
    public String toString() {
        if (!tableCards.isEmpty()) {
            StringBuilder tableCardsList = new StringBuilder();
            int i = 1;
            for(Map.Entry<Card, Card>entry : tableCards.entrySet()) {
                Card attackCard = entry.getKey();
                Card defenseCard = entry.getValue();
                tableCardsList.append((i++) + ". ")
                .append("[")
                .append(attackCard)
                .append("] <- [")
                .append(defenseCard != null ? defenseCard : "?")
                .append(i <= tableCards.size() ? "],\n" : "].");
            }
            return tableCardsList.toString();
        }
        else {
            return "Table is empty!";
        }    
    }

    public Card addAttackCard(Card attackCard) {
        if (attackCard != null && tableCards.size() < MAX_CARDS) {
            tableCards.put(attackCard, null);
        }
        return attackCard;
    }

    public Card addDefenseCard(Card attackCard, Card defenseCard) {
        if (defenseCard != null && tableCards.containsKey(attackCard)) {
            tableCards.put(attackCard, defenseCard);
        }
        return defenseCard;
    }

    public boolean isAllDefended() {
        for (Card defenseCard : tableCards.values()) {
            if (defenseCard == null) {
                return false;
            }
        }
        return true;
    }

    public Card getFirstCard() {
        for (Map.Entry<Card, Card> card : tableCards.entrySet()) {
            if (card.getValue() == null) {
                return card.getKey();
            }
        }
        return null;
    }

    public List<Card> takeCards() {
        List<Card>tableCardsList = new ArrayList<>();
        for (Map.Entry<Card, Card> card : tableCards.entrySet()) {
            tableCardsList.add(card.getKey());
            if (card.getValue() != null) {
                tableCardsList.add(card.getValue());
            }
        }
        clearTable();
        return tableCardsList;
    }

    public void clearTable() {
        tableCards.clear();
    }

    public Table getTable() {
        return this;
    }

    public int getSize() {
        return tableCards.size();
    }

    public Set<Integer> getRanks() {
        Set<Integer>ranks = new HashSet<>();
        for (Map.Entry<Card, Card>card : tableCards.entrySet()) {
            ranks.add(card.getKey().getRank());
            if (card.getValue() != null) {
                ranks.add(card.getValue().getRank());
            }
        }    
        return ranks;
    }
}
