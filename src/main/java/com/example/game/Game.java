package com.example.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.model.*;

public class Game {
    private final Random random = new Random();

    private List<Player>players;
    private Table table;
    private Deck deck;
    private Card trumpCard;
    private int attackerIndex, defenderIndex;
    private Player winner;   

    public Game(List<Player>players) {
        this.players = new ArrayList<>(players);
        this.deck = new Deck();
        this.table = new Table();
    }  

    private void findAttacker() {
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

    private void updateDefender() {
        this.defenderIndex = (attackerIndex + 1) % players.size();
    }

    public void startGame() {
        prepareGame();
        while (winner == null) {
            playRound();
        }
        System.out.printf("Winner is %s!\n", winner.getName());
    }

    private void prepareGame() {
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

    private void playRound() {
        boolean isDefending = true;
        Player attacker = players.get(attackerIndex);
        Player defender = players.get(defenderIndex);
        List<Player>throwers = getThrowers();
        final int maxCards = Math.min(table.MAX_CARDS, defender.getHand().getSize());

        while (true) {
            int passedInRow = 0;
            int indexThrowers = 0;
            while (passedInRow < throwers.size() && table.getSize() < maxCards) {
                if (throwers.get(indexThrowers).getHand().getSize() == 0) {
                    passedInRow++;
                    indexThrowers = (indexThrowers + 1) % throwers.size();
                    continue;
                }
                System.out.printf("%s's turn!\n", attacker.getName());
                Card attackCard = requestAttackCard(throwers.get(indexThrowers), defender, table.getSize() > 0);
                if (attackCard == null) {
                    passedInRow++;
                    indexThrowers = (indexThrowers + 1) % throwers.size();
                }
                else {
                    passedInRow = 0;
                }
            }
            if (table.isAllDefended()) {
                break;
            }
            while (!table.isAllDefended()) {
                if (!requestDefenseCard(defender, table.getFirstCard())) {
                    isDefending = false;
                    break;
                }
            }
            if (!isDefending) {
                break;
            }    
        }
        if (!isDefending) {
                System.out.printf("%s takes cards.\n", defender.getName());
                defender.takeCards(table.takeCards());
        }
        else {
            System.out.println("Beated!");
            table.clearTable();
        }      
        if (deck.getSize() > 0) {
            refilHands();
        }
        checkWinner();
        attackerIndex = (attackerIndex + (isDefending ? 1 : 2)) % players.size();
        updateDefender();    
    }

    private void refilHands() {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get((attackerIndex + i) % players.size());
            while (player.getHand().getCards().size() < 6 && deck.getSize() > 0) {
                player.takeCard(deck.dealCard());
            }
        }
    }

    private boolean canAttack(Card attackCard) {
        if (attackCard == null) {
            return false;          
        }
        return table.getSize() == 0 || table.getRanks().contains(attackCard.getRank());
    }

    private boolean canBeat(Card attackCard, Card defenseCard) {
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

    private void checkWinner() {
        if (deck.getSize() == 0) {
            for (Player player : players) {
                if (!player.hasCards()) {
                    this.winner = player;
                    break;
                }
            }
        }
    }

    private List<Player> getThrowers() {
        List<Player>throwers = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            int index = (attackerIndex + i) % players.size();
            if (index != defenderIndex) {
                throwers.add(players.get(index));
            }
        }

        return throwers;
    }

    private Card requestAttackCard(Player attacker, Player defender, boolean allowPass) {
        while (true) {
            Card attackCard = attacker.attack(trumpCard, deck, table);
            if (attackCard == null) {
                if (allowPass) {
                    return null;
                }
                System.out.println("You can not pass on first turn!");
                continue;
            }
            if (canAttack(attackCard)) {
                attacker.playCard(attacker.getHand().getCards().indexOf(attackCard));
                table.addAttackCard(attackCard);
                System.out.printf("%s attacks %s with %s.\n",attacker.getName(), defender.getName(), attackCard);
                return attackCard;
            }
            System.out.println("You can only attack with rank already on the table!");
        }
    }
    
    private boolean requestDefenseCard(Player defender, Card attackCard) {
        while (true) {
            Card defenseCard = defender.defend(attackCard, trumpCard, deck, table);
            if (defenseCard == null) {
                return false;
            }
            if (canBeat(attackCard, defenseCard)) {
                defender.playCard(defender.getHand().getCards().indexOf(defenseCard));
                table.addDefenseCard(attackCard, defenseCard);
                System.out.printf("%s defends %s with %s.\n", defender.getName(), attackCard, defenseCard);
                return true;
            }
            System.out.println("The card can not beat attacker card!");
        }    
    }
}
