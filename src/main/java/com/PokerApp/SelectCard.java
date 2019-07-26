package com.PokerApp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@WebServlet(
        name = "selectCardRank",
        urlPatterns = "/SelectCardRank"
)
public class SelectCard extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        Card p1Card1 = new Card(req.getParameter("p1c1r"), req.getParameter("p1c1s"));
        Card p1Card2 = new Card(req.getParameter("p1c2r"), req.getParameter("p1c2s"));
        Card p2Card1 = new Card(req.getParameter("p2c1r"), req.getParameter("p2c1s"));
        Card p2Card2 = new Card(req.getParameter("p2c2r"), req.getParameter("p2c2s"));

        List<Card> player1 = new ArrayList<>(Arrays.asList(p1Card1, p1Card2));
        List<Card> player2 = new ArrayList<>(Arrays.asList(p2Card1, p2Card2));
        List<Card> tableCards = new ArrayList<>();
        List<List<Card>> players = Arrays.asList(player1, player2);

        OddsGenerator oG = new OddsGenerator(players, tableCards);


        RequestDispatcher view = req.getRequestDispatcher("result.jsp");
        view.forward(req, resp);

    }
}