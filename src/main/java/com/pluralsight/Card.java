package com.pluralsight;

public class Card {

    private final String suit;
    private final String value;
    private final String name;
    private boolean isFaceUp;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        this.name = value + suit;
        this.isFaceUp = false;
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void flip() {
        isFaceUp = !isFaceUp;
    }

    public int getPointValue() {
        if (isFaceUp) {
            return switch (this.value) {
                case "A" -> 11;
                case "J", "Q", "K" -> 10;
                default -> Integer.parseInt(this.value);
            };
        }
        return 0;
    }

}
