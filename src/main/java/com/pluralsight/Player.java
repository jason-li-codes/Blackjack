package com.pluralsight;

import java.util.ArrayList;
import java.util.Objects;

public class Player {

    private final String name;
    private ArrayList<Card> hand;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void deal(Card card) {
        hand.add(card);
    }

    public int getNumOfCards() {
        return hand.size();
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public int getValueOfHand() {

        int totalValue = 0;
        int aceCounter = 0;

        for (Card card : hand) {
            card.flip();
            if (!Objects.equals(card.getValue(), "A")) {
                totalValue += card.getPointValue();
            } else {
                aceCounter += 1;
            }
            card.flip();
        }
        if (totalValue + aceCounter + 10 > 21) {
            return totalValue + aceCounter;
        } else {
            return totalValue + aceCounter + 10;
        }
    }

}
