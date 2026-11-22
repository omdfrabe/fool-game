package com.example.strategy;

import com.example.model.Hand;
import com.example.model.Table;
import com.example.model.Card;
import com.example.model.Deck;

public interface PlayerStrategy {
    int chooseAttackCard(Hand hand, Card trumpCard, Deck deck, Table table);
    int chooseDefenseCard(Hand hand, Card attackCard, Card trumpCard, Deck deck, Table table);
}
