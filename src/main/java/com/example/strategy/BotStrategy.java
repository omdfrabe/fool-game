package com.example.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.model.Card;
import com.example.model.Deck;
import com.example.model.Hand;
import com.example.model.Table;


public class BotStrategy implements PlayerStrategy {
    private final Random random = new Random();

    @Override
    public int chooseAttackCard(Hand hand, Card trumpCard, Deck deck, Table table) {
        thinkingEmilation();
        if (table.getSize() == 0) {
            return random.nextInt(hand.getSize());
        }

        List<Card>attackPool = new ArrayList<>();
        if (table.getSize() != 0) {
                for (Card card : hand.getCards()) {
                    if (table.getRanks().contains(card.getRank())) {
                        attackPool.add(card);
                    }
                }
        }
        if (attackPool.isEmpty() && table.getSize() > 0) {
            return -1;
        }
        return hand.getCards().indexOf(attackPool.get(random.nextInt(attackPool.size())));
    }

    @Override
    public int chooseDefenseCard(Hand hand, Card attackCard, Card trumpCard, Deck deck, Table table) {
        List<Card>choicePool = new ArrayList<>();
        List<Card>choiceTrumpPool = new ArrayList<>();

        thinkingEmilation();

        for (Card card : hand.getCards()) {
            if (attackCard.getSuit() == trumpCard.getSuit()) {
                if (card.getSuit() == trumpCard.getSuit() && card.getRank() > attackCard.getRank()) {
                    choiceTrumpPool.add(card);
                }
            }
            else {
                if (card.getSuit() == trumpCard.getSuit()) {
                    choiceTrumpPool.add(card);
                }
                else {
                    if (card.getSuit() == attackCard.getSuit() && card.getRank() > attackCard.getRank()) {
                        choicePool.add(card);
                    }
                }
            }
        }
        if (choicePool.isEmpty() && choiceTrumpPool.isEmpty()) {
            return -1;
        }
        if (!choicePool.isEmpty() && !choiceTrumpPool.isEmpty()) {
            if (random.nextInt(10) == 0) {
                Card chosenTrumpCard = choiceTrumpPool.get(random.nextInt(choiceTrumpPool.size()));
                return hand.getCards().indexOf(chosenTrumpCard);
            }
        }
        if (!choicePool.isEmpty()) {
            Card chosenCard = choicePool.get(random.nextInt(choicePool.size()));
            return hand.getCards().indexOf(chosenCard);
        }
        Card chosenTrumpCard = choiceTrumpPool.get(random.nextInt(choiceTrumpPool.size()));
        return hand.getCards().indexOf(chosenTrumpCard);
    }

    public void thinkingEmilation() {
        try {
            int time = 800 + random.nextInt(1400);
            Thread.sleep(time);
        }
        catch (InterruptedException exception){
            Thread.currentThread().interrupt();
        }
    }
}
