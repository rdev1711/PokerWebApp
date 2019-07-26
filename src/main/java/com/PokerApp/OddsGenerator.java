package com.PokerApp;

import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.*;

public class OddsGenerator {

    private List<Card> deck = new ArrayList<>();
    private List<List<Card>> players = new ArrayList<>();
    int numPlayers = 0;
    private List<Card> tableCards = new ArrayList<>();
    HashMap<Integer, Double> odds = new HashMap<>();
    private int cardsToSim;
    private HashMap<Integer, Card> cardIdentifier = new HashMap<>();
    private Utils utils = new Utils();

    OddsGenerator(List<List<Card>> players, List<Card> tableCards) {
            this.players = players;
            for (List<Card> player : this.players) {
                player.addAll(tableCards);
            }
            this.numPlayers = players.size();
            this.tableCards = tableCards;
            this.cardsToSim = 5 - tableCards.size();
            long startTime = System.currentTimeMillis();
            setUp();
            long setupTime = System.currentTimeMillis();
            //System.out.println("Setup Time: " + (setupTime - startTime));
            doTheMath();
            long mathTime = System.currentTimeMillis();
            System.out.println("Time: " + (mathTime - startTime));



    }

    private void setUp() {

        cardsToSim = 5 - tableCards.size();
        createDeck();
        List<Card> knownCards = new ArrayList<>();
        for (List<Card> player: players) {
            knownCards.addAll(player);
        }
        if (tableCards.size() > 0) {
            knownCards.addAll(tableCards);
        }
        getRemainingCards(knownCards);
    }

    private void createDeck() {
       List<String> suits = new ArrayList<>(Arrays.asList("H", "C", "D", "S"));
       List<String> ranks = new ArrayList<>(Arrays.asList("A", "K", "Q", "J"));
       int val = 10;
       while (val >= 2) {
           ranks.add(Integer.toString(val));
           val--;
       }

       for (String rank: ranks) {
           for (String suit: suits) {
               Card newCard = new Card(rank, suit);
               deck.add(newCard);
               cardIdentifier.put(newCard.cardVal, newCard);
           }
       }

    }
    private List<Card> makeCards(int[] comb, List<Card> cards) {
        List<Card> handCards = new ArrayList<>();
        for (int i = 0; i < comb.length; i++) {
            handCards.add(cards.get(comb[i]));
        }
        return handCards;
    }

    private void doTheMath() {
        HashMap<Integer, Double> wins = new HashMap<>();
        for (int i = 1; i <= numPlayers; i++) {
            wins.put(i, 0.0);
        }

        double total = 0;
        int count = 0;
        long seed = System.currentTimeMillis();
        Random gen = new Random(seed);
        Iterator<int[]> combIterator = CombinatoricsUtils.combinationsIterator(deck.size(),cardsToSim);
        boolean debug = false;
        while(combIterator.hasNext()) {
            List<Card> combCards = makeCards(combIterator.next(), deck);
            for (Card card: combCards) {
               //card.printCard();
            }
            //System.out.println();
            if (count == 10) {
                //System.out.println(count);
                //debug = true;
            }
            if (count == 1000010) {
                //break;
            }
            int result = whoWins(combCards, debug);
            if (debug) {
                System.out.println(result);
            }
            total++;
            wins.replace(result, wins.get(result) + 1);
            count++;

        }

        for (Map.Entry<Integer, Double> e : wins.entrySet()){
            e.setValue(100*e.getValue() / total);
        }

        this.odds = wins;

    }



    private int whoWins(List<Card> cardComb, boolean debug) {
        int maxVal = 0, winningPlayer = 1, count = 1;
        for (List<Card> player: players) {
            List<Card> tempPlayer = new ArrayList(player);
            tempPlayer.addAll(cardComb);
            Hand tempHand = new Hand(tempPlayer);
           //tempHand.printHand();
            //System.out.println();
            if (tempHand.value > maxVal) {
                maxVal = tempHand.value;
                winningPlayer = count;
            }
            count++;
        }
        return winningPlayer;

    }


    private void getRemainingCards(List<Card> knownCards) {
        for (Card card : knownCards) {
            deck.remove(cardIdentifier.get(card.cardVal));
        }
    }

}
