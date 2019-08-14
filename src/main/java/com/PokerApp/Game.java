package com.PokerApp;

import org.apache.commons.math3.util.CombinatoricsUtils;
import org.apache.commons.collections4.iterators.PermutationIterator;
import java.util.*;

class Game {
    int numPlayers;
    List<Card> deck = new ArrayList<>();
    List<List<Card>> players = new ArrayList<>();
    List<Card> tableCards = new ArrayList<>();
    HashMap<Integer, Double> odds = new HashMap<>();
    int tableCardsToSim, playerCardstoSim;
    OddsGenerator oG;
    boolean solo;
    private HashMap<Integer, Card> cardIdentifier = new HashMap<>();

    public Game(int numPlayers, List<List<Card>> players, List<Card> tableCards) {
        this.numPlayers = numPlayers;
        this.players = players;
        this.tableCards = tableCards;
        if (players.size() == 1) {
            solo = true;
        }
        tableCardsToSim = 5 - tableCards.size();
        setUp();
        if (solo) {
            odds = simulateSolo(players, tableCards,1000);
        } else {
            odds = simulate(players, tableCards, 100000);
        }
    }
    private void setUp() {

        createDeck();
        List<Card> knownCards = new ArrayList<>();
        for (List<Card> player: players) {
            knownCards.addAll(player);
            for (Card card: player) {
                card.printCard();
            }
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
    private void getRemainingCards(List<Card> knownCards) {
        for (Card card : knownCards) {
            deck.remove(cardIdentifier.get(card.cardVal));
        }
    }

    private List<Card> makeCards(int[] comb, List<Card> cards) {
        List<Card> handCards = new ArrayList<>();
        for (int c: comb) {
            handCards.add(cards.get(c));
        }
        return handCards;
    }

    private HashMap<Integer, Double> simulate(List<List<Card>> playerCards, List<Card> tableCards, int numSims) {
        HashMap<Integer, Double> wins = new HashMap<>();
        for (int i = 0; i <= numPlayers; i++) {
            wins.put(i, 0.0);
        }
        double total = 0;
        int count = 0;
        Iterator<int[]> combIterator = CombinatoricsUtils.combinationsIterator(deck.size(),tableCardsToSim);
        long numerator = Utils.factorial(deck.size());
        long denominator = Utils.factorial(deck.size()-tableCardsToSim)*Utils.factorial(tableCardsToSim);
        long numCombs = numerator / denominator;

        long interval = numCombs / numSims;


        boolean debug = false;
        while(combIterator.hasNext()) {
            for (int i = 0; i < interval; i++) {
                combIterator.next();
            }
            List<Card> allTableCards = new ArrayList<>(tableCards);
            allTableCards.addAll(makeCards(combIterator.next(), deck));
            int result = whoWins(playerCards, allTableCards);
            total++;
            wins.replace(result, wins.get(result) + 1);
            count++;

        }

        for (Map.Entry<Integer, Double> e : wins.entrySet()){
            e.setValue(100*e.getValue() / total);
        }
        return wins;

    }

    private HashMap<Integer, Double> simulateSolo(List<List<Card>> players, List<Card> tableCards, int numSims) {
        double playerWins = 0;
        double total = 0;
        playerCardstoSim = 2*(numPlayers - 1);
        int totalSim = playerCardstoSim + tableCardsToSim;
        Iterator<int[]> soloCombIterator = CombinatoricsUtils.combinationsIterator(deck.size(),tableCardsToSim + playerCardstoSim);
        long numCombs = Utils.factorial(deck.size())/(Utils.factorial(deck.size()-totalSim)*Utils.factorial(totalSim));
        long interval = numCombs / numSims;
        while (soloCombIterator.hasNext()) {
            for (int i = 0; i < interval;i++) {
                soloCombIterator.next();
            }
            int [] allSimmedCards = soloCombIterator.next();
            List<List<Card>> allPlayers = new ArrayList<>(players);
            List<Card> playerCards = makeCards(Arrays.copyOfRange(allSimmedCards, 0,playerCardstoSim), deck);
            List<Card> tempTableCards = new ArrayList<>(tableCards);
            List<Card> tblCardsToSim = makeCards(Arrays.copyOfRange(allSimmedCards, playerCardstoSim, allSimmedCards.length), deck);
            tempTableCards.addAll(tblCardsToSim);
            for (int i = 0; i < numPlayers - 1;i++) {
                List<Card> simPlayer = new ArrayList<>(Arrays.asList(playerCards.get(2*i), playerCards.get(2*i+1)));
                allPlayers.add(simPlayer);
            }
            if (whoWins(allPlayers, tempTableCards) == 1) {
                playerWins+=1;
            }
            total+=1;

        }
        HashMap<Integer, Double> soloOdds = new HashMap<>();
        soloOdds.put(1, 100*playerWins/total);
        return soloOdds;


    }



    private int whoWins(List<List<Card>> playerCards, List<Card> tableCards) {
        boolean tie = false;
        int maxVal = 0, winningPlayer = 1, count = 1;
        for (List<Card> player : playerCards) {
            List<Card> tempPlayer = new ArrayList(player);
            tempPlayer.addAll(tableCards);
            Hand tempHand = new Hand(tempPlayer);
            //tempHand.printHand();
            //System.out.println();
            if (tempHand.value > maxVal) {
                maxVal = tempHand.value;
                winningPlayer = count;
                tie = false;
            } else if (tempHand.value == maxVal) {
                tie = true;
            }
            count++;
        }
        if (tie) {
            return 0;
        }
        return winningPlayer;

    }
}