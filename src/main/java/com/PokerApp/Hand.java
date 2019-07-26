package com.PokerApp;

import java.util.Arrays;
import java.util.List;

class Hand {
    private Card[] cards;
    String kind;
    int value;
    private boolean validHand = true;

    Hand(List<Card> cards) {
        for (Card c : cards) {
            if (!c.validCard) {
                validHand = false;
                break;
            }
        }

        if (cards.size() == 7) {
            this.cards = new Card[7];
            this.cards = cards.toArray(this.cards);
            this.sortByRank(this.cards);
            this.valueHand();

            //valueHand();
        } else {
        }
    }


     void printHand() {
        for (Card card: this.cards) {
            card.printCard();
        }
        System.out.println("Kind of Hand: " + kind);
        System.out.println("Value of Hand: " + value);
    }


    private void valueHand() {
        int handVal = getStraightFlush();
        if (handVal != 0) {
            value = handVal;
            kind = "Straight Flush";
            return;
        }
        handVal = getQuads();
        if (handVal != 0) {
            value = handVal;
            kind = "Quads";
            return;
        }
        handVal = getFullHouse();
        if (handVal != 0) {
            value = handVal;
            kind = "Full House";
            return;
        }
        handVal = getFlush();
        if (handVal != 0) {
            value = handVal;
            kind = "Flush";
            return;
        }
        handVal = getStraight();
        if (handVal != 0) {
            value = handVal;
            kind = "Straight";
            return;
        }
        handVal = getTrips();
        if (handVal != 0) {
            value = handVal;
            kind = "Trips";
            return;
        }
        handVal = getTwoPair();
        if (handVal != 0) {
            value = handVal;
            kind = "Two Pair";
            return;
        }
        handVal = getPair();
        if (handVal != 0) {
            value = handVal;
            kind = "Pair";
            return;
        }
        handVal = getHighCard();
        value = handVal;
        kind = "High Card";
    }



    private int getHighCard() {
        //cards = sortByRank(cards);

        return cards[6].rank * Utils.fourth + cards[5].rank * Utils.third + cards[4].rank * Utils.second + cards[3].rank * 14 + cards[2].rank;
    }

    private int getStraightFlush() {
       int straight = getStraight();
       int flush = getFlush();
       if (straight != 0 && flush != 0) {
           return Utils.straightFlushBase + straight;
       }
       return 0;

    }

    private int getQuads() {
        // cards = sortByRank(cards);
        boolean quads = false;
        int quadRank = 0;
        int kickerRank = 0;
        if (cards[3].rank == cards[6].rank) {
            quads = true;
            quadRank = cards[3].rank;
            kickerRank = cards[2].rank;
        } else if (cards[2].rank == cards[5].rank) {
            quads = true;
            quadRank = cards[2].rank;
            kickerRank = cards[6].rank;
        } else if (cards[1].rank == cards[4].rank) {
            quads = true;
            quadRank = cards[1].rank;
            kickerRank = cards[6].rank;
        } else if (cards[0].rank == cards[3].rank) {
            quads = true;
            quadRank = cards[0].rank;
            kickerRank = cards[6].rank;
        }

        if (quads) {
            return Utils.quadsBase + 14*quadRank + kickerRank;
        }
        return 0;
    }

    private int getFullHouse() {
        boolean fullHouse = false;
        int topRank = 0;
        int botRank = 0;
        if (cards[4].rank == cards[6].rank) {
            if (cards[2].rank == cards[3].rank) {
                fullHouse = true;
                topRank = cards[4].rank;
                botRank = cards[2].rank;
            } else if (cards[1].rank == cards[2].rank) {
                fullHouse = true;
                topRank = cards[4].rank;
                botRank = cards[1].rank;
            } else if (cards[0].rank == cards[1].rank) {
                fullHouse = true;
                topRank = cards[4].rank;
                botRank = cards[0].rank;
            }
        } else if (cards[3].rank == cards[5].rank) {
            if (cards[1].rank == cards[2].rank) {
                fullHouse = true;
                topRank = cards[3].rank;
                botRank = cards[1].rank;
            } else if (cards[0].rank == cards[1].rank) {
                fullHouse = true;
                topRank = cards[3].rank;
                botRank = cards[0].rank;
            }
        } else if (cards[2].rank == cards[4].rank) {
            if (cards[5].rank == cards[6].rank) {
                fullHouse = true;
                topRank = cards[2].rank;
                botRank = cards[5].rank;
            } else if (cards[0].rank == cards[1].rank) {
                fullHouse = true;
                topRank = cards[2].rank;
                botRank = cards[0].rank;
            }
        } else if (cards[1].rank == cards[3].rank) {
            if (cards[5].rank == cards[6].rank) {
                fullHouse = true;
                topRank = cards[1].rank;
                botRank = cards[5].rank;
            } else if (cards[4].rank == cards[5].rank) {
                fullHouse = true;
                topRank = cards[1].rank;
                botRank = cards[4].rank;
            }
        } else if (cards[0].rank == cards[2].rank) {
            if (cards[5].rank == cards[6].rank) {
                fullHouse = true;
                topRank = cards[0].rank;
                botRank = cards[5].rank;
            } else if (cards[4].rank == cards[5].rank) {
                fullHouse = true;
                topRank = cards[0].rank;
                botRank = cards[4].rank;
            } else if (cards[3].rank == cards[4].rank) {
                fullHouse = true;
                topRank = cards[0].rank;
                botRank = cards[3].rank;
            }
        }

        if(fullHouse) {
            return Utils.fullHouseBase + 14*topRank + botRank;
        }
        return 0;

    }

    private int getFlush() {
        //cards = sortByRank(cards);
        boolean flush = false;
        int first = 0, second = 0, third = 0, fourth = 0, fifth = 0;
        sortBySuit(cards);
        if (cards[2].suit == cards[6].suit) {
            flush = true;
            first = cards[6].rank;
            second = cards[5].rank;
            third = cards[4].rank;
            fourth = cards[3].rank;
            fifth = cards[2].rank;
        } else if (cards[1].suit == cards[5].suit) {
            flush = true;
            first = cards[5].rank;
            second = cards[4].rank;
            third = cards[3].rank;
            fourth = cards[2].rank;
            fifth = cards[1].rank;
        } else if (cards[0].suit == cards[4].suit) {
            flush = true;
            first = cards[4].rank;
            second = cards[3].rank;
            third = cards[2].rank;
            fourth = cards[1].rank;
            fifth = cards[0].rank;
        }
        sortByRank(cards);

        if (flush) {
            return Utils.flushBase + Utils.fourth*first + Utils.third*second + Utils.second*third + 14*fourth + fifth;
        }
        return 0;
    }

    private int getStraight() {
        int [] ranks = new int[cards.length];
        int dup = 0;

        for (int i = 0; i < cards.length; i++) {
            ranks[i] = cards[i].rank;
        }

        for (int i = 0; i < cards.length - 1; i++) {
            if (ranks[i] == ranks[i+1]) {
                ranks[i] = 0;
                dup++;
            }
        }
        Arrays.sort(ranks);
        int [] non_dups = Arrays.copyOfRange(ranks, dup, ranks.length);

        int inArow = 1;
        for (int i = non_dups.length - 1; i > 0; i--) {
            if (non_dups[i] == non_dups[i-1] + 1) {
                inArow++;
            } else {
                inArow = 1;
            }
            if (inArow == 5) {
                return Utils.straightBase + non_dups[i];
            }
        }
        return 0;
    }

    private int getTrips() {
        boolean trips = false;
        int tripsVal = 0;
        int firstKicker = 0;
        int secondKicker = 0;
       if (cards[4].rank == cards[6].rank) {
           trips = true;
           tripsVal = cards[4].rank;
           firstKicker = cards[3].rank;
           secondKicker = cards[2].rank;
       } else if (cards[3].rank == cards[5].rank) {
           trips = true;
           tripsVal = cards[3].rank;
           firstKicker = cards[6].rank;
           secondKicker = cards[2].rank;
       } else if (cards[2].rank == cards[4].rank) {
           trips = true;
           tripsVal = cards[2].rank;
           firstKicker = cards[6].rank;
           secondKicker = cards[5].rank;
       } else if (cards[1].rank == cards[3].rank) {
           trips = true;
           tripsVal = cards[1].rank;
           firstKicker = cards[6].rank;
           secondKicker = cards[5].rank;
       } else if (cards[0].rank == cards[2].rank) {
           trips = true;
           tripsVal = cards[0].rank;
           firstKicker = cards[6].rank;
           secondKicker = cards[5].rank;
       }
       if (trips) {
           return Utils.tripsBase + Utils.second*tripsVal+ 14*firstKicker + secondKicker;
       }
       return 0;

    }

    private int getTwoPair() {
        boolean twoPair = false;
        int topPairVal = 0, botPairVal = 0;
        int kicker = 0;

        if (cards[5].rank == cards[6].rank) {
            if (cards[3].rank == cards[4].rank) {
                twoPair = true;
                topPairVal = cards[5].rank;
                botPairVal = cards[3].rank;
                kicker = cards[2].rank;
            } else if (cards[2].rank == cards[3].rank) {
                twoPair = true;
                topPairVal = cards[5].rank;
                botPairVal = cards[2].rank;
                kicker = cards[4].rank;
            } else if (cards[1].rank == cards[2].rank) {
                twoPair = true;
                topPairVal = cards[5].rank;
                botPairVal = cards[1].rank;
                kicker = cards[4].rank;
            } else if (cards[0].rank == cards[1].rank) {
                twoPair = true;
                topPairVal = cards[5].rank;
                botPairVal = cards[0].rank;
                kicker = cards[4].rank;
            }
        } else if (cards[4].rank == cards[5].rank) {
            if (cards[2].rank == cards[3].rank) {
                twoPair = true;
                topPairVal = cards[4].rank;
                botPairVal = cards[2].rank;
                kicker = cards[6].rank;
            } else if (cards[1].rank == cards[2].rank) {
                twoPair = true;
                topPairVal = cards[4].rank;
                botPairVal = cards[1].rank;
                kicker = cards[6].rank;
            } else if (cards[0].rank == cards[1].rank) {
                twoPair = true;
                topPairVal = cards[4].rank;
                botPairVal = cards[0].rank;
                kicker = cards[6].rank;
            }
        } else if (cards[3].rank == cards[4].rank) {
            if (cards[1].rank == cards[2].rank) {
                twoPair = true;
                topPairVal = cards[3].rank;
                botPairVal = cards[1].rank;
                kicker = cards[6].rank;
            } else if (cards[0].rank == cards[1].rank) {
                twoPair = true;
                topPairVal = cards[3].rank;
                botPairVal = cards[0].rank;
                kicker = cards[6].rank;
            }
        } else if (cards[2].rank == cards[3].rank) {
            if (cards[0].rank == cards[1].rank) {
                twoPair = true;
                topPairVal = cards[2].rank;
                botPairVal = cards[0].rank;
                kicker = cards[6].rank;
            }
        }
        if (twoPair) {
            return Utils.twoPairBase + Utils.second * topPairVal + 14*botPairVal + kicker;
        }
        return 0;
    }


    private int getPair() {
        boolean pair = false;
        int pairVal = 0;
        int firstKicker = 0, secondKicker = 0, thirdKicker = 0;
       if (cards[5].rank == cards[6].rank) {
           pair = true;
           pairVal = cards[5].rank;
           firstKicker = cards[4].rank;
           secondKicker = cards[3].rank;
           thirdKicker = cards[2].rank;
       } else if (cards[4].rank == cards[5].rank) {
           pair = true;
           pairVal = cards[4].rank;
           firstKicker = cards[6].rank;
           secondKicker = cards[3].rank;
           thirdKicker = cards[2].rank;
       } else if (cards[3].rank == cards[4].rank) {
           pair = true;
           pairVal = cards[3].rank;
           firstKicker = cards[6].rank;
           secondKicker = cards[5].rank;
           thirdKicker = cards[2].rank;
       } else if (cards[2].rank == cards[3].rank) {
           pair = true;
           pairVal = cards[2].rank;
           firstKicker = cards[6].rank;
           secondKicker = cards[5].rank;
           thirdKicker = cards[4].rank;
       } else if (cards[1].rank == cards[2].rank) {
           pair = true;
           pairVal = cards[1].rank;
           firstKicker = cards[6].rank;
           secondKicker = cards[5].rank;
           thirdKicker = cards[4].rank;
       } else if (cards[0].rank == cards[1].rank) {
           pair = true;
           pairVal = cards[0].rank;
           firstKicker = cards[6].rank;
           secondKicker = cards[5].rank;
           thirdKicker = cards[4].rank;
       }

       if (pair) {
           return Utils.pairBase + Utils.third * pairVal + Utils.second * firstKicker + 14*secondKicker + thirdKicker;
       }
       return 0;
    }

    private void sortBySuit(Card[] cards) {
        int curr = 0;
        while (curr < cards.length) {
            int min_rem = curr;
            for (int i = curr + 1; i < cards.length; i++) {
                if (cards[i].suit < cards[min_rem].suit) {
                    min_rem = i;
                }
            }
            Card temp = cards[curr];
            cards[curr] = cards[min_rem];
            cards[min_rem] = temp;
            curr++;
        }
        this.cards = cards;
    }

    private void sortByRank(Card[] cards) {
        int curr = 0;
        while (curr < cards.length) {
            int min_rem = curr;
            for (int i = curr + 1; i < cards.length; i++) {
                if (cards[i].rank < cards[min_rem].rank) {
                    min_rem = i;
                }
            }
            Card temp = cards[curr];
            cards[curr] = cards[min_rem];
            cards[min_rem] = temp;
            curr++;
        }
        this.cards = cards;
    }

}






