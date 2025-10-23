package com.pluralsight;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private final ArrayList<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();

        String[] suits = {"Hearts", "Spades", "Diamonds", "Clubs"};
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        for (String suit : suits) {
            for (String value: values) {
                Card nextCard = new Card(suit, value);
                cards.add(nextCard);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card deal() {
        if (!cards.isEmpty()) {
            return cards.remove(0);
        }
        return null;
    }

    public int getDeckSize() {
        return cards.size();
    }

    public void addToDeck(ArrayList<Card> pile) {
        this.cards.addAll(pile);
    }

}
