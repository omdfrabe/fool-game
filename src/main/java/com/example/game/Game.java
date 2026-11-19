package com.example.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.model.*;

public class Game {
    private final Random random = new Random();

    private List<Player>players;
    private Deck deck;
    private Card trumpCard;
    private int attackerIndex, defenderIndex;
    private Player winner;   

    public Game(List<Player>players) {
        this.players = new ArrayList<>(players);
        this.deck = new Deck();
    }  

    public void findAttacker() {
        Card lowestTrumpCard = null;
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            for (Card card : player.getHand().getCards()) {
                if (card.getSuit() == trumpCard.getSuit()) {
                    if (lowestTrumpCard == null || card.getRank() < lowestTrumpCard.getRank()) {
                        lowestTrumpCard = card;
                        this.attackerIndex = i;
                    }
                }
            }
        }
        if (lowestTrumpCard == null) {
            this.attackerIndex = random.nextInt(players.size());
        }
        updateDefender();
    }

    public void updateDefender() {
        this.defenderIndex = (attackerIndex + 1) % players.size();
    }

    public void startGame() {
        prepareGame();
        while (winner == null) {
            playRound();
        }
        System.out.printf("Winner is %s!\n", winner.getName());
    }

    public void prepareGame() {
        deck.generateDeck();
        deck.shuffleDeck();
        for (int i = 0; i < 3; i++) {
            for (Player player : players) {
                for (int j = 0; j < 2; j++) {
                    player.takeCard(deck.dealCard());
                }
            }
        }
        this.trumpCard = deck.drawTrump();
        findAttacker();
    }

    public void playRound() {
        boolean isDefended = false;
        Player attacker = players.get(attackerIndex);
        Player defender = players.get(defenderIndex);
        System.out.printf("%s begins!\n", attacker.getName());
        Card attackCard = attacker.attack(trumpCard, deck);
        System.out.printf("Attacks with %s.\n", attackCard);
        while (true) {
            Card defenseCard = defender.defend(attackCard, trumpCard, deck);
            if (canBeat(attackCard, defenseCard)) {
                defender.playCard(defender.getHand().getCards().indexOf(defenseCard));
                System.out.printf("%s defends with %s.\n", defender.getName(), defenseCard);
                isDefended = true;
                break;
            }
            else {
                if (defenseCard != null) {
                    System.out.println("The card can not beat attacker card!");
                }
                else {
                    defender.takeCard(attackCard);
                    System.out.printf("%s takes cards.\n", defender.getName());
                    break;
                }              
            }  
        }
        if (deck.getSize() > 0) {
            refilHands();
        }
        checkWinner();
        attackerIndex = (attackerIndex + (isDefended ? 1 : 2)) % players.size();
        updateDefender();
    }

    public void refilHands() {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get((attackerIndex + i) % players.size());
            while (player.getHand().getCards().size() < 6 && deck.getSize() > 0) {
                player.takeCard(deck.dealCard());
            }
        }
    }

    public boolean canBeat(Card attackCard, Card defenseCard) {
        if (defenseCard != null) {
            if (attackCard.getSuit() == defenseCard.getSuit()) {
                    return attackCard.getRank() < defenseCard.getRank();
            }
            if (defenseCard.getSuit() == trumpCard.getSuit()) {
                return true;
            }
        }
        return false;
    }

    public void checkWinner() {
        if (deck.getSize() == 0) {
            for (Player player : players) {
                if (!player.hasCards()) {
                    this.winner = player;
                    break;
                }
            }
        }
    }
}
