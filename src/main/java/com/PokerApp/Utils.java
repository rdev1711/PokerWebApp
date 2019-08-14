package com.PokerApp;

class Utils {
    static int fourth = 14*14*14*14;
    static int third = 14*14*14;
    static int second = 14*14;
    static int pairBase = 1000000;
    static int twoPairBase = 2*pairBase;
    static int tripsBase = 3*pairBase;
    static int straightBase = 4*pairBase;
    static int flushBase = 5*pairBase;
    static int fullHouseBase = 6*pairBase;
    static int quadsBase = 7*pairBase;
    static int straightFlushBase = 8*pairBase;




    static long factorial(int n) {
        if (n == 1) {
            return 1;
        }
        return n * factorial(n-1);
    }


     static void printList(int[] lst) {
        String lstString = "[";
        for (int item: lst){
            lstString += Integer.toString(item);
            lstString += " ";
        }
        lstString = lstString.substring(0, lstString.length() - 1) + "]";
        System.out.println(lstString);
    }
}
