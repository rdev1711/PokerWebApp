package com.PokerApp;


import java.util.HashMap;

class Card {
    int rank, suit;
    private String wordRank, wordSuit;
    boolean validCard = true;
    int cardVal;

    Card(String rank, String suit) {
        HashMap<String, Integer> conv = new HashMap<>();
        HashMap<String, String> name = new HashMap<>();
        name.put("C", "Clubs");
        name.put("D", "Diamonds");
        name.put("H", "Hearts");
        name.put("S", "Spades");
        name.put("A", "Ace");
        name.put("K", "King");
        name.put("Q", "Queen");
        name.put("J", "Jack");
        conv.put("A", 14);
        conv.put("K", 13);
        conv.put("Q", 12);
        conv.put("J", 11);
        conv.put("Clubs", 0);
        conv.put("Diamonds", 1);
        conv.put("Hearts", 2);
        conv.put("Spades", 3);
        try {
            if (rank.equals("A") || rank.equals("K") || rank.equals("Q") || rank.equals("J")) {
                this.rank = conv.get(rank);
                this.wordRank = name.get(rank);
            } else {
                this.rank = Integer.parseInt(rank);
                this.wordRank = rank;
            }
            if (suit.length() > 1) {
                this.wordSuit = suit;
            } else {
                this.wordSuit = name.get(suit);
            }
            this.suit = conv.get(this.wordSuit);
            this.cardVal = 4*(this.rank - 2) + this.suit;
        } catch (Exception e) {
            e.printStackTrace();
            validCard = false;
        }


    }
    String printCard () {
        String pString = this.wordRank + " of " + this.wordSuit;
        System.out.println(pString);
        return pString;
    }



}