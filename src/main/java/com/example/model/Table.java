package com.example.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Table {
    private static final int MAX_CARDS = 6;

    private Map<Card, Card> tableCards = new LinkedHashMap<>();

    public void addAttackCard(Card attackCard) {
        if (attackCard != null && tableCards.size() < MAX_CARDS) {
            tableCards.put(attackCard, null);
        }
    }

    public void addDefenseCard(Card attackCard, Card defenseCard) {
        if (defenseCard != null && tableCards.containsKey(attackCard)) {
            tableCards.put(attackCard, defenseCard);
        }
    }

    public boolean isAllDefended() {
        for (Card defenseCard : tableCards.values()) {
            if (defenseCard == null) {
                return false;
            }
        }
        return true;
    }

    public void clearTable() {
        tableCards.clear();
    }
}
