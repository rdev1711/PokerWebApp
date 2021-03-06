package com.PokerApp;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class UtilsTest {


    public void testGenerate() {
        Utils utils = new Utils();
        List<Card> cards1 = new ArrayList<>(Arrays.asList(new Card("q", "d"), new Card("a", "h"), new Card("k", "h"),
                new Card("j", "s"), new Card("7", "d"), new Card("8", "h"), new Card("9", "h")));


        List<Card> cards2 = new ArrayList<>(Arrays.asList(new Card("2", "d"), new Card("2", "h"), new Card("3", "c"),
                new Card("4", "s"), new Card("5", "d"), new Card("7", "h"), new Card("8", "h")));


        List<Card> cards3 = new ArrayList<>(Arrays.asList(new Card("j", "h"), new Card("10", "h"), new Card("K", "h"),
                new Card("q", "h"), new Card("a", "h"), new Card("2", "h"), new Card("3", "h")));


        Hand bestHand1 = new Hand(cards1);
        bestHand1.printHand();

        Hand bestHand2 = new Hand(cards2);
        bestHand2.printHand();

        Hand bestHand3 = new Hand(cards3);
        bestHand3.printHand();


    }

    public void testOddsGenerator() {

        List<Card> player1 = new ArrayList<>(Arrays.asList(new Card("a", "h"), new Card("k", "d")));
        List<Card> player2 = new ArrayList<>(Arrays.asList(new Card("7", "s"), new Card("7", "c")));
        List<Card> tableCards = new ArrayList<>();

        //OddsGenerator oG = new OddsGenerator(player1, player2, tableCards);



    }

    public static void testCardCreate() {
        //p1
        Card c1 = new Card("2", "Hearts");
        Card c2 = new Card("2", "Diamonds");
        //p2
        Card c3 = new Card("10", "Hearts");
        Card c4 = new Card("10", "Clubs");
        //p3
        Card c5 = new Card("Q", "Spades");
        Card c6 = new Card("7", "Diamonds");
        //Table Cards
        Card c7 = new Card("10", "Spades");
        Card c8 = new Card("J", "Clubs");
        Card c9 = new Card("Q", "Diamonds");
        Card c10 = new Card("K", "Hearts");

        List<Card> player1 = new ArrayList<>(Arrays.asList(c1, c2));
        List<Card> player2 = new ArrayList<>(Arrays.asList(c3, c4));
        List<Card> player3 = new ArrayList<>(Arrays.asList(c5, c6));
        List<Card> tableCards = new ArrayList<>(Arrays.asList(c7,c8,c9,c10));
        List<List<Card>> players = new ArrayList<>(Arrays.asList(player2));

        Game g = new Game(2, players, tableCards);
        for (Map.Entry<Integer, Double> e : g.odds.entrySet()) {
            String s = "";
            if (e.getKey() == 0) {
                 s = "Tie odds: " + e.getValue();
            } else {
                 s = "Player " + e.getKey() + " odds: " + e.getValue();
            }
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        //System.out.println(Utils.factorial(44));
       testCardCreate();
    }
}
