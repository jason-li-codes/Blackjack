package com.pluralsight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class BlackjackApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        ArrayList<Player> table = new ArrayList<>();

        printBufferScreen();

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
                    (N) No, begin the game
                    """);

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
        printResults(table);
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
                discardPile.clear();
            }
            player.deal(deck.deal());
            player.deal(deck.deal());
        }

        System.out.println("Cards have been dealt, each player will look at their hand individually.");
        for (Player player : table) {
            printBufferScreen();
            boolean isRunning = true;
            while (isRunning) {
                System.out.println(player.getName() + ", you have the following cards: ");
                for (Card card : player.getHand()) {
                    System.out.print(card.getName() + " ");
                }

                System.out.println(": your current total card value is " + player.getValueOfHand());
                System.out.println("""
                        Would you like to hit or stay?
                        (H) Hit, give me another card
                        (S) Stay, I am satisfied with my hand
                        """);

                char choice = Character.toUpperCase(getValidString(input).charAt(0));
                switch (choice) {
                    case 'H':
                        if (player.getValueOfHand() >= 21) {
                            System.out.println("Sorry, your hand cannot take another card.");
                            isRunning = false;
                        } else {
                            player.deal(deck.deal());
                        }
                        break;
                    case 'S':
                        isRunning = false;
                        break;
                    default:
                        System.out.println("That is not a valid menu option.");
                }
            }
            System.out.println("Moving on....");
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

    public static void printBufferScreen() {
        System.out.println("""
                 /$$   /$$  /$$$$$$  /$$$$$$$$ /$$$$$$  /$$$$$$$$  /$$$$$$  /$$$$$$$$
                | $$  | $$ /$$__  $$|__  $$__//$$__  $$| $$_____/ /$$__  $$|__  $$__/
                | $$  | $$| $$  \\ $$   | $$  | $$  \\__/| $$      | $$  \\ $$   | $$  \s
                | $$$$$$$$| $$  | $$   | $$  |  $$$$$$ | $$$$$   | $$$$$$$$   | $$  \s
                | $$__  $$| $$  | $$   | $$   \\____  $$| $$__/   | $$__  $$   | $$  \s
                | $$  | $$| $$  | $$   | $$   /$$  \\ $$| $$      | $$  | $$   | $$  \s
                | $$  | $$|  $$$$$$/   | $$  |  $$$$$$/| $$$$$$$$| $$  | $$   | $$  \s
                |__/  |__/ \\______/    |__/   \\______/ |________/|__/  |__/   |__/  \s""");
        System.out.println("""
                 /$$$$$$$  /$$        /$$$$$$   /$$$$$$  /$$   /$$    /$$$$$  /$$$$$$   /$$$$$$  /$$   /$$
                | $$__  $$| $$       /$$__  $$ /$$__  $$| $$  /$$/   |__  $$ /$$__  $$ /$$__  $$| $$  /$$/
                | $$  \\ $$| $$      | $$  \\ $$| $$  \\__/| $$ /$$/       | $$| $$  \\ $$| $$  \\__/| $$ /$$/\s
                | $$$$$$$ | $$      | $$$$$$$$| $$      | $$$$$/        | $$| $$$$$$$$| $$      | $$$$$/ \s
                | $$__  $$| $$      | $$__  $$| $$      | $$  $$   /$$  | $$| $$__  $$| $$      | $$  $$ \s
                | $$  \\ $$| $$      | $$  | $$| $$    $$| $$\\  $$ | $$  | $$| $$  | $$| $$    $$| $$\\  $$\s
                | $$$$$$$/| $$$$$$$$| $$  | $$|  $$$$$$/| $$ \\  $$|  $$$$$$/| $$  | $$|  $$$$$$/| $$ \\  $$
                |_______/ |________/|__/  |__/ \\______/ |__/  \\__/ \\______/ |__/  |__/ \\______/ |__/  \\__/""");

    }

    public static String getHandCardsStr(Player player) {

        StringBuilder cardsList = new StringBuilder();
        for (Card card : player.getHand()) {
            cardsList.append(card.getName()).append(" ");
        }
        return cardsList.toString();
    }

    public static void printResults(ArrayList<Player> table) {

        ArrayList<String> winners = new ArrayList<>();
        int highScore = 0;

        System.out.println("Now to reveal the results:");
        System.out.println("=====================================================================");
        System.out.printf("| %-15s | %-25s | %-5s |%n", "Player Name", "Cards", "Total");
        System.out.println("=====================================================================");
        for (Player player : table) {
            String playerName = player.getName();
            int score = player.getValueOfHand();
            System.out.printf("| %-15s | %-25s | %-5d |%n",
                    playerName, getHandCardsStr(player), score);
            if (score <= 21) {
                if (score > highScore) {
                    highScore = score;
                    winners.clear();
                    winners.add(playerName);
                } else if (score == highScore) {
                    winners.add(playerName);
                }
            }
        }
        System.out.println("=====================================================================");

        if (winners.isEmpty()) {
            System.out.println("No valid winners this round, sorry!");
        } else {
            System.out.println("Congratulations to....");
            System.out.println(String.join(", ", winners) + " with " + highScore + " points!");
        }
    }

}
