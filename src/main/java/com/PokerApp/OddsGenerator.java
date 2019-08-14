package com.PokerApp;

import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.*;

public class OddsGenerator {

    private List<Card> deck = new ArrayList<>();
    private boolean solo = false;
    private List<List<Card>> players = new ArrayList<>();
    private int numPlayers = 0;
    private List<Card> tableCards = new ArrayList<>();
    private HashMap<Integer, Double> odds = new HashMap<>();


    private Utils utils = new Utils();

    OddsGenerator(List<List<Card>> players, List<Card> tableCards) {
        this.players = players;
        this.tableCards = tableCards;


    }
    }




