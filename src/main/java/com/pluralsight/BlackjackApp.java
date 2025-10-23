package com.pluralsight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class BlackjackApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        ArrayList<Player> table = new ArrayList<>();

        System.out.println("Welcome to a HotSeat game of Blackjack!");
        System.out.println("Let's start by gathering all the players.");

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Enter player name: ");

            String name = getValidString(input);
            table.add(new Player(name));
            System.out.println(name + " added to the table.");

            System.out.print("""
                    Would you like to add another player?
                    (Y) Yes, add another player
                    (N) No, begin the game""");

            char choice = Character.toUpperCase(getValidString(input).charAt(0));
            switch (choice) {
                case 'Y':
                    if (table.size() == 10) {
                        System.out.println("Sorry, this game only supports 10 players.");
                        break;
                    }
                    continue;
                case 'N':
                    isRunning = false;
                    break;
                default:
                    System.out.println("That is not a valid menu option.");
            }
        }
        playGame(input, table);
    }

    public static void playGame(Scanner input, ArrayList<Player> table) {

        ArrayList<Card> discardPile = new ArrayList<>();
        Deck deck = new Deck();
        deck.shuffle();
        System.out.println("Shuffling deck....");

        for (Player player : table) {
            if (deck.getDeckSize() <= 5) {
                Collections.shuffle(discardPile);
                deck.addToDeck(discardPile);
            }
            player.deal(deck.deal());
            player.deal(deck.deal());
        }

        System.out.println("Cards have been dealt, each player will look at their hand individually.");
        boolean isRunning = true;
        while (isRunning) {
            for (Player player : table) {
                System.out.println(player.getName() + ", you have the following cards: ");
                for (Card card : player.getHand()) {
                    System.out.println(card.getName());
                }

                System.out.println("Your current total card value is: " + player.getValueOfHand());
                System.out.println("""
                        Would you like to hit or stay?
                        (H) Hit, give me another card
                        (S) Stay, I am satisfied with my hand""");

                char choice = Character.toUpperCase(getValidString(input).charAt(0));
                switch (choice) {
                    case 'H':
                        player.deal(deck.deal());
                        break;
                    case 'S':
                        isRunning = false;
                    default:
                        System.out.println("That is not a valid menu option.");
                }
            }
            System.out.println("Moving onto next player....");
            printBufferScreen();
        }


    }

    public static String getValidString(Scanner input) {
        // Initializes String called string, set to null
        String string;
        // Uses do/while loop with a boolean badInput to get a non-empty String from user
        boolean badInput = false;
        do {
            // Sets badInput to false first, to ensure loop doesn't run continuously
            badInput = false;
            string = input.nextLine().trim(); // Accepts next user input as a String, trimming it
            if (string.isEmpty()) {
                System.out.println("You have not entered anything, please try again.");
                badInput = true; // Sets badInput to true if input is empty after trimming
            }
        } while (badInput);
        return string; // Returns string after input is confirmed to be non-empty
    }


}
