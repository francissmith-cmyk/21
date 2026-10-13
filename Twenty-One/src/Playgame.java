import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.List;
public class Playgame
{
    static ArrayList <Card> userPlayer = new ArrayList<Card>();
    static ArrayList <Card> botPlayer = new ArrayList<Card>();
    static ArrayList <TrumpCard> userTrump = new ArrayList<TrumpCard>();
    static ArrayList <TrumpCard> botTrump = new ArrayList<TrumpCard>();
    static boolean playingGame = false;
    static long currentEndTime = 0;
    static int bet = 1;
    static int sawPlace = 7;
    static int sawDistance = 14;
    static int userValue = 0;
    static int botValue = 0;
    static int moveTimer = 30000;
    static final int defaultMoveTimer = 30000;
    static boolean userTime = true;
    static boolean userStood = false;
    static boolean botStood = false;
    static boolean isUserTurn = true;
    static boolean usedTrump = false;
    static boolean firstTurnUser = true;
    static int target = 21;
    static Scanner userIntInput = new Scanner (System.in);
    static int PlayerTrumps = 0;
    static int BotTrumps = 0;
    static boolean bless = false;
    static ArrayList<String> userTrumpHistory = new ArrayList<String>();
    static ArrayList<String> botTrumpHistory = new ArrayList<String>();
    public static void main(String[] args) throws InterruptedException 
    {
        Greetplayer();
        while (playingGame) 
        {
            dealCard();
            userStood = false;
            botStood = false;
            isUserTurn = firstTurnUser;
            while (!userStood || !botStood) 
            {
                if (isUserTurn) {
                    if (!userStood) 
                    {
                        userTurn();
                    }
                    isUserTurn = false;
                } 
                else {
                    if (!botStood) 
                    {
                        botTurn();
                    }
                    isUserTurn = true;
                }
                if (userStood && botStood)  
                {
                    break; 
                }
            } 
            getWinner();
            if (playingGame) clearScreen(); 
        }
}
    public static void Greetplayer()
    {
        int greeting = (int)(Math.random()*5);
        if (greeting == 0)
        {
            System.out.println("You woke up? Amazing!");
        }
        else if (greeting == 1)
        {
            System.out.println("Look who finally woke up!");
        }
        else if (greeting == 2) 
        {
        	System.out.println("Wake up!");
        }
        else if (greeting == 3)
        {
        	System.out.println("Rise and shine!");
        }
        else 
        {
        	System.out.println("Hello, wake up!");
        }
        playingGame = true;
    }
    public static void dealCard()
    {    
    	target = 21;
        userPlayer.clear();
        botPlayer.clear();
        Deck.fillDeck();
        Deck.shuffle();
        TrumpDeck.fillDeck();
        TrumpDeck.shuffle();
        System.out.println("");
        int Trump1 = (int)(Math.random() * TrumpDeck.size());
        int Trump2 = (int)(Math.random() * TrumpDeck.size());
        int Trump3 = (int)(Math.random() * TrumpDeck.size());
        int Trump4 = (int)(Math.random() * TrumpDeck.size());
        userPlayer.add(Deck.deck.remove(0));
        userPlayer.add(Deck.deck.remove(0));
        botPlayer.add(Deck.deck.remove(0));
        botPlayer.get(0).setHidden(true);
        userPlayer.get(0).setHidden(true);
        botPlayer.add(Deck.deck.remove(0));
        userTrump.add(TrumpDeck.deck.get(Trump1));
        PlayerTrumps++;
		System.out.println("You received the "+ userTrump.get(PlayerTrumps-1).getColor() + userTrump.get(PlayerTrumps-1).getFunction() + "\u001B[0m" + " card.");
        userTrump.add(TrumpDeck.deck.get(Trump2));
        PlayerTrumps++;
        System.out.println("You received the "+ userTrump.get(PlayerTrumps-1).getColor() + userTrump.get(PlayerTrumps-1).getFunction() + "\u001B[0m" + " card.");
        botTrump.add(TrumpDeck.deck.get(Trump3));
        BotTrumps++;
        botTrump.add(TrumpDeck.deck.get(Trump4));
        BotTrumps++;
        userStood = false;
        botStood = false;
        botValue = (int) (botPlayer.get(0).getNumber() + botPlayer.get(1).getNumber());
        userValue = (int) (userPlayer.get(0).getNumber() + userPlayer.get(1).getNumber());
        userTrumpHistory.clear();
        botTrumpHistory.clear();
        bless = false;
        moveTimer = defaultMoveTimer;
    }
    public static void userTurn() 
    {
    	currentEndTime = System.currentTimeMillis() + moveTimer;
    	moveTimer = defaultMoveTimer;
        boolean turnOver = false;
        usedTrump = false;
        userTime = true;
        while (!turnOver) 
        {
            System.out.println("\u001B[0m" + "\n--- YOUR TURN ---");
            int botKnown = 0;
            for (Card c : botPlayer) 
            {
                if (c != null && !c.getHidden()) 
                {
                    botKnown += c.getNumber();
                }
            }
            int opponentSaw = sawDistance - sawPlace;
            String displayUserScore = (userValue > 99) ? "\u001B[31mOverkill\u001B[39m" : "\u001B[32m" + userValue + "\u001B[39m";
            String displayBotScore = (botKnown > 99) ? "\u001B[31mOverkill\u001B[39m" : "\u001B[31m" + botKnown + "\u001B[39m";
            System.out.println("Target: " + "\u001B[34m" + target + "\u001B[39m" + " | Your Score: " + displayUserScore + " | Bot's Visible Score: " + displayBotScore);
            System.out.println("Your Lives: " + "\u001B[32m" + sawPlace + "\u001B[39m" + " | Opponent's Lives: " + "\u001B[31m" + opponentSaw + "\u001B[39m" + " | Current Bet: " + "\u001B[34m" + bet + "\u001B[39m" + "\n");            
            
            if (bless) 
            {
                System.out.println("\u001B[96m" + "The bless card is currently active!" + "\u001B[0m");
            }
            System.out.print("Your Hand: ");
            int looper = 0;
            for (Card c : userPlayer) {
                if (c != null) 
                {
                    String hiddenTag = c.getHidden() ? " (Hidden)" : "";
                    String comma = (looper == userPlayer.size() - 1) ? "" : ", ";
                    String cardStr = (c.getNumber() >= 100) ? "\u001B[31mL\u001B[0m" : String.valueOf(c.getNumber());
                    System.out.print(" [" + cardStr + "]" + hiddenTag + comma);
                    looper++;
                }
            }            
            System.out.print("\nOpponent Hand: ");
            looper = 0;
            for (Card c : botPlayer) 
            {
                if (c == null) 
                {
                    looper++;
                    continue;
                }
                String cardStr = (c.getNumber() >= 100) ? "\u001B[31mL\u001B[0m" : String.valueOf(c.getNumber());
                String cardText = c.getHidden() ? "[?]" : "[" + cardStr + "]";
                String comma = (looper == botPlayer.size() - 1) ? "" : ", ";
                System.out.print(cardText + comma);
                looper++;
            }
            System.out.print("\n\n\u001B[32mYour\u001B[0m Trump History: ");
            if (userTrumpHistory.isEmpty()) 
            {
                System.out.println("None");
            } 
            else 
            {
                System.out.println(String.join(", ", userTrumpHistory));
            }
            System.out.print("\u001B[31mOpponent's\u001B[0m Trump History: ");
            if (botTrumpHistory.isEmpty()) 
            {
                System.out.println("None");
            } 
            else 
            {
                System.out.println(String.join(", ", botTrumpHistory));
            }
            System.out.println("\n\n0: Stand (Stay)");
            System.out.println("1: Hit (Draw)");
            System.out.println("2: View Trump Cards");
            int choice = getTimedChoice(currentEndTime, "Select an option");
            if (choice == 1) 
            { 
                if (userValue > target) 
                {
                    System.out.println("\u001B[31mOverkill of points.\u001B[39m");
                } 
                else if (Deck.deck.isEmpty()) 
                {
                    System.out.println("Deck is empty.");
                } 
                else 
                {
                    if ((int)(Math.random() * 4) == 0) 
                    {
                        int trumpIdx = (int)(Math.random() * TrumpDeck.deck.size());
                        TrumpCard drawnTrump = TrumpDeck.deck.get(trumpIdx);
                        userTrump.add(drawnTrump);
                        PlayerTrumps++;
                        System.out.println("You drew a(n) " + drawnTrump.getColor() + drawnTrump.getFunction() + "\u001B[39m");
                    }
                    Card drawnCard = Deck.deck.remove(0);
                    userPlayer.add(drawnCard);
                    userValue += drawnCard.getNumber();
                    System.out.println("You drew a " + drawnCard.getNumber() + ". New score: " + userValue);
                    userStood = false;
                    botStood = false;
                    turnOver = true;
                }
            }
            else if (choice == 0) 
            {
                System.out.println("You decided to stay.");
                if (!usedTrump) 
                {
                    userStood = true;
                } 
                else 
                {
                    botStood = false;
                }
                turnOver = true;
            }
            else if (choice == 2) 
            {
                PlayerTrump(); 
            } 
            else if (choice == 2008) 
            {
                long remainingMillis = currentEndTime - System.currentTimeMillis();
                if (remainingMillis < 0) remainingMillis = 0;
                remainingMillis = runDebugMenu(remainingMillis);
                currentEndTime = System.currentTimeMillis() + remainingMillis;
                continue; 
            }
            else if (choice == -2 && !userTime) 
            {
                boolean hasLCard = false;
                for (Card c : userPlayer) 
                {
                    if (c.getNumber() >= 100) hasLCard = true;
                }
                if (!hasLCard) 
                {
                    userPlayer.add(new Card(100, false));
                    userValue += 100;
                }
                int text = (int)(Math.random() * 2);
                if (text == 0)
                {
                    System.out.println("\u001B[31mGood luck, loser.\u001B[0m");
                }
                else if (text == 1)
                {
                    System.out.println("\u001B[31mPathetic.\u001B[0m");
                }
                userStood = true;
                turnOver = true;
            }
            else 
            {
                System.out.println("\u001B[31mInvalid option! Please enter 0, 1, 2.\u001B[0m");
            }
        }
    }
    public static void PlayerTrump()
    {
        System.out.println("Remember: You can use as many trumps as you like before you choose to hit or stand.");
        if (userTrump.isEmpty())
        {
            System.out.println(">> You have no Trump Cards!");
            return;
        }
        String[][] categories = {
            {"GO FOR CARDS", "Go for 17", "Go for 24", "Go for 27"},
            {"HAND MANIPULATION CARDS", "Remove", "Return", "Exchange", "Reincarnation", "Refresh", "Disservice", "Reveal"},
            {"BET MANIPULATION CARDS", "One-up", "Two-up", "Shield", "Shield+", "Bloodshed", "Bless"},
            {"FORCE-DRAW CARDS", "2 Card", "3 Card", "4 Card", "5 Card", "6 Card", "7 Card", "Perfect Draw"},
            {"TIMER MANIPULATION CARDS", "Extend", "Rush"},
            {"MISCELLANEOUS CARDS", "Friendship", "Destroy", "Hush", "Clairvoyance", "Dud"}
        };
        java.util.Map<String, Integer> counts = new java.util.LinkedHashMap<>();
        java.util.Map<String, TrumpCard> cardMap = new java.util.HashMap<>();
        for (TrumpCard tc : userTrump) {
            counts.put(tc.getFunction(), counts.getOrDefault(tc.getFunction(), 0) + 1);
            if (!cardMap.containsKey(tc.getFunction())) {
                cardMap.put(tc.getFunction(), tc);
            }
        }
        System.out.println("\n\u001B[1m--- TRUMP CARDS ---\u001B[0m");
        System.out.println("0) Cancel / Back");
        ArrayList<String> displayMenuNames = new ArrayList<>();
        int menuOptionNumber = 1;
        for (String[] cat : categories) {
            String catName = cat[0];
            boolean headerPrinted = false;
            for (int i = 1; i < cat.length; i++) {
                String cardName = cat[i];
                if (counts.containsKey(cardName) && counts.get(cardName) > 0) {
                    if (!headerPrinted) 
                    {
                        System.out.println("\n\u001B[1m-- " + catName + " --\u001B[0m");
                        headerPrinted = true;
                    }
                    int qty = counts.get(cardName);
                    String qtyTag = (qty > 1) ? " (x" + qty + ")" : "";
                    TrumpCard sample = cardMap.get(cardName);

                    System.out.println(sample.getColor() + menuOptionNumber + ") " 
                            + sample.getFunction() + qtyTag + " (" + sample.getExplanation() + ")\u001B[0m");

                    displayMenuNames.add(cardName);
                    menuOptionNumber++;
                }
            }
        }
        int choice = getTimedChoice(currentEndTime, "Select a card to use");
        if (choice == 0)
        {
            System.out.println(">> Action cancelled.");
            return;
        }
        int selectedIdx = choice - 1;
        if (selectedIdx >= 0 && selectedIdx < displayMenuNames.size())
        {
            String chosenFunctionName = displayMenuNames.get(selectedIdx);
            TrumpCard chosenCard = null;
            for (int i = 0; i < userTrump.size(); i++) {
                if (userTrump.get(i).getFunction().equals(chosenFunctionName)) {
                    chosenCard = userTrump.remove(i);
                    break;
                }
            }
            if (chosenCard != null) 
            {
                PlayerTrumps--;
                System.out.println("You used the " + chosenCard.getFunction() + " trump.");
                usedTrump = true;
                executeTrumpEffect(chosenCard.getFunction());
            }
        }
        else
        {
            System.out.println(">> Invalid selection.");
        }
    }
    public static int getTimedChoice(long endTime, String promptMsg)
    {
        System.out.print(promptMsg + ": ");
        boolean warned10 = false;
        String message = "";
        try {
            while (System.currentTimeMillis() < endTime)
            {
                long timeLeft = (endTime - System.currentTimeMillis()) / 1000;
                if (timeLeft <= 10 && !warned10)
                {
                    int i = (int)(Math.random() * 2);
                    if (i == 0)
                    {
                        message = "Hurry up!";
                    }
                    else
                    {
                        message = "Make your choice or lose.";
                    }
                    System.out.println("\n\u001B[31m" + message + "\u001B[0m");
                    System.out.print("> ");
                    warned10 = true;
                }
                if (System.in.available() > 0)
                {
                    String input = userIntInput.nextLine().trim();
                    try
                    {
                        return Integer.parseInt(input);
                    }
                    catch (NumberFormatException e)
                    {
                        return -1;
                    }
                }
                Thread.sleep(100);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error reading input.");
        }
       
        System.out.println();
        userTime = false;
        return -2;
    }
    public static long runDebugMenu(long initialRemainingMillis)
    {
        long remainingMillis = initialRemainingMillis;
        boolean inDebug = true;
        while (inDebug)
        {
            System.out.println("\n\u001B[36m--- [DEBUG MENU] ---" + "\u001B[0m");
            System.out.println("0) Exit Debug Menu");
            System.out.println("1) View Game State");
            System.out.println("2) Give any Card from the Deck or Remove Cards from either hand/Hide, or Reveal any Card in Either Hand");
            System.out.println("3) Give or Remove Trump Cards from Either Hand");
            System.out.println("4) Bet, Saw, and Target Modifier");
            System.out.println("5) Edit a Player's Turn Timer");
            System.out.print("Select an option: ");
            if (!userIntInput.hasNextInt()) {
                userIntInput.nextLine();
                continue;
            }
            int dbgChoice = userIntInput.nextInt();
            userIntInput.nextLine();
            switch (dbgChoice)
            {
                case 1:
                    printDebugState(remainingMillis);
                    break;
                case 2:
                    managePlayerCards();
                    break;
                case 3:
                    manageTrumpCards();
                    break;
                case 4:
                    manageBetAndSaw();
                    break;    
                case 5: 
                    remainingMillis = editTimer(remainingMillis);
                    break;
                case 0:
                    inDebug = false;
                    break;
                default:
                    System.out.println("\u001B[31mInvalid debug option.\u001B[0m");
            }
        }
        return remainingMillis;
    }
    private static void printDebugState(long remainingMillis)
    {
        long secondsLeft = remainingMillis / 1000;
        System.out.println("\n--- CURRENT GAME STATE ---");
        System.out.println("Your remaining move timer: " + secondsLeft + "s");
        System.out.println("Target: " + "\u001B[34m" + target + "\u001B[0m\n");
        int opponentSaw = sawDistance - sawPlace;
        System.out.println("Your Lives: " + "\u001B[32m"+ sawPlace + "\u001B[39m" + " | Opponent's Lives: " + "\u001B[31m" + opponentSaw + "\u001B[39m" + " | Current Bet: " + "\u001B[34m" + bet + "\u001B[32m" + "\u001B[39m" + "\n");            
        System.out.println("Your Score: " + "\u001B[32m" + userValue + "\u001B[0m");
        System.out.print("Your Hand: ");
        for (int i = 0; i < userPlayer.size(); i++) 
        {
            Card c = userPlayer.get(i);
            if (c != null) 
            {
                String hiddenTag = c.getHidden() ? " (hidden)" : "";
                String comma = (i == userPlayer.size() - 1) ? "" : ", ";
                System.out.print("[" + c.getDisplay() + "]" + hiddenTag + comma);
            }
        }
        System.out.print("\nYour Trumps: ");
        if (userTrump.isEmpty()) 
        {
            System.out.print("None");
        } 
        else 
        {
            for (int i = 0; i < userTrump.size(); i++) 
            {
                TrumpCard tc = userTrump.get(i);
                String comma = (i == userTrump.size() - 1) ? "" : ", ";
                System.out.print(tc.getColor() + "[" + tc.getFunction() + "]\u001B[0m" + comma);
            }
        }
        System.out.println("\n\nBot's Score: " + "\u001B[31m" + botValue + "\u001B[0m");
        System.out.print("Bot's Full Hand: ");
        for (int i = 0; i < botPlayer.size(); i++) 
        {
            Card c = botPlayer.get(i);
            if (c != null) 
            {
                String hiddenTag = c.getHidden() ? " (hidden)" : "";
                String comma = (i == botPlayer.size() - 1) ? "" : ", ";
                System.out.print("[" + c.getDisplay() + "]" + hiddenTag + comma);
            }
        }
        System.out.print("\nBot's Trumps: ");
        if (botTrump.isEmpty()) 
        {
            System.out.print("None");
        } 
        else 
        {
            for (int i = 0; i < botTrump.size(); i++) 
            {
                TrumpCard tc = botTrump.get(i);
                String comma = (i == botTrump.size() - 1) ? "" : ", ";
                System.out.print(tc.getColor() + "[" + tc.getFunction() + "]\u001B[0m" + comma);
            }
        }
        System.out.println();
        System.out.println();
        System.out.print("\u001B[32m" + "Your" + "\u001B[0m Played Trumps: ");
        if (userTrumpHistory.isEmpty()) 
        {
            System.out.println("None");
        } 
        else 
        {
            System.out.println(String.join(", ", userTrumpHistory));
        }
        System.out.print("\u001B[31mOpponent's\u001B[0m Played Trumps: ");
        if (botTrumpHistory.isEmpty()) 
        {
            System.out.println("None");
        } 
        else 
        {
            System.out.println(String.join(", ", botTrumpHistory));
        }
        System.out.print("\n\nThe Remaining Deck, in Order: ");
        for (int i = 0; i < Deck.deck.size(); i++) 
        {
            String comma = (i == Deck.deck.size() - 1) ? "" : ", ";
            System.out.print(Deck.deck.get(i).getNumber() + comma);
        }
        System.out.println();
    }
    private static void managePlayerCards()
    {
        System.out.println("\n--- MANAGE CARDS ---");
        System.out.println("1) Add a Specific Card from the Deck to a Player");
        System.out.println("2) Remove a Specific Card from a Player");
        System.out.println("3) Reveal or Hide a Card");
        System.out.println("4) Impose L Card");
        System.out.print("Choice: ");
        if (!userIntInput.hasNextInt()) {
            userIntInput.nextLine();
            return;
        }
        int action = userIntInput.nextInt();
        userIntInput.nextLine();
        if (action >= 1 && action <= 3)
        {
            System.out.print("Target (\u001B[31m0 = Bot\u001B[0m, \u001B[32m1 = User\u001B[0m): ");
            if (!userIntInput.hasNextInt()) {
                userIntInput.nextLine();
                return;
            }
            int targetPlayer = userIntInput.nextInt();
            userIntInput.nextLine();

            if (targetPlayer != 0 && targetPlayer != 1) {
                System.out.println("\u001B[31mInvalid target player.\u001B[0m");
                return;
            }
            String targetName = (targetPlayer == 1) ? "User" : "Bot";
            ArrayList<Card> hand = (targetPlayer == 1) ? userPlayer : botPlayer;
            if (action == 1)
            {
                System.out.print("Enter Card Value to take from Deck: ");
                if (!userIntInput.hasNextInt()) {
                    userIntInput.nextLine();
                    return;
                }
                int val = userIntInput.nextInt();
                userIntInput.nextLine();

                Card found = null;
                for (int i = 0; i < Deck.deck.size(); i++) {
                    if (Deck.deck.get(i).getNumber() == val) {
                        found = Deck.deck.remove(i);
                        break;
                    }
                }
                if (found != null) 
                {
                    hand.add(found);
                    recalculateScores();
                    System.out.println("Successfully added card [" + val + "] to " + targetName + ".");
                } else 
                {
                    System.out.println("\u001B[31mCard not found in deck!\u001B[0m");
                }
            }
            else if (action == 2)
            {
                if (hand.size() <= 1) {
                    System.out.println("\u001B[31mCannot remove the first card!\u001B[0m");
                    return;
                }
                System.out.print("Select card index to remove (1 to " + (hand.size() - 1) + "): ");
                if (!userIntInput.hasNextInt()) {
                    userIntInput.nextLine();
                    return;
                }
                int idx = userIntInput.nextInt();
                userIntInput.nextLine();

                if (idx > 0 && idx < hand.size()) {
                    Card removed = hand.remove(idx);
                    if (removed.getNumber() < 100) {
                        Deck.deck.add(removed);
                    }
                    recalculateScores();
                    System.out.println("Removed card [" + removed.getDisplay() + "] from " + targetName + ".");
                } else {
                    System.out.println("\u001B[31mInvalid index or attempted to remove first card.\u001B[0m");
                }
            }
            else if (action == 3)
            {
                System.out.print("Select card index to toggle hide/reveal (0 to " + (hand.size() - 1) + "): ");
                if (!userIntInput.hasNextInt()) {
                    userIntInput.nextLine();
                    return;
                }
                int idx = userIntInput.nextInt();
                userIntInput.nextLine();

                if (idx >= 0 && idx < hand.size()) {
                    Card c = hand.get(idx);
                    c.setHidden(!c.getHidden());
                    System.out.println("Card [" + c.getDisplay() + "] hidden set to: " + c.getHidden() + " for " + targetName + ".");
                } else {
                    System.out.println("\u001B[31mInvalid index.\u001B[0m");
                }
            }
        }
        else if (action == 4)
        {
            System.out.print("Target (\u001B[31m0 = Bot\u001B[0m, \u001B[32m1 = User\u001B[0m): ");
            if (!userIntInput.hasNextInt()) 
            {
                userIntInput.nextLine();
                return;
            }
            int lTarget = userIntInput.nextInt();
            userIntInput.nextLine();

            if (lTarget != 0 && lTarget != 1) 
            {
                System.out.println("\u001B[31mInvalid target player.\u001B[0m");
                return;
            }
            String localTargetName = (lTarget == 1) ? "User" : "Bot";
            ArrayList<Card> localTargetHand = (lTarget == 1) ? userPlayer : botPlayer;

            boolean hasLCard = false;
            for (Card c : localTargetHand) 
            {
                if (c != null && c.getNumber() >= 100) 
                {
                    hasLCard = true;
                    break;
                }
            }
            if (hasLCard) 
            {
                System.out.println(localTargetName + " already has an \u001B[31mL\u001B[0m Card!");
            } 
            else 
            {
                localTargetHand.add(new Card(100, false));
                recalculateScores();
                System.out.println("Imposed \u001B[31mL\u001B[0m Card on " + localTargetName + "!");
            }
        }
        else
        {
            System.out.println("\u001B[31mInvalid option.\u001B[0m");
        }
    }
    private static void manageTrumpCards()
    {
        System.out.println("\n--- MANAGE TRUMP CARDS ---");
        System.out.println("1) Add Specific Trump Card");
        System.out.println("2) Remove Trump Card");
        System.out.print("Choice: ");
        if (!userIntInput.hasNextInt()) {
            userIntInput.nextLine();
            return;
        }
        int action = userIntInput.nextInt();
        userIntInput.nextLine();
        System.out.print("Target (\u001B[31m0 = Bot\u001B[0m, \u001B[32m1 = User\u001B[0m): ");
        if (!userIntInput.hasNextInt()) {
            userIntInput.nextLine();
            return;
        }
        int targetPlayer = userIntInput.nextInt();
        userIntInput.nextLine();

        if (targetPlayer != 0 && targetPlayer != 1) {
            System.out.println("\u001B[31mInvalid target player.\u001B[0m");
            return;
        }
        String targetName = (targetPlayer == 1) ? "User" : "Bot";
        ArrayList<TrumpCard> trumps = (targetPlayer == 1) ? userTrump : botTrump;
        if (action == 1)
        {
            TrumpCard[] catalog = new TrumpCard[] {
                new TrumpCard("Go for 17", "Change target to 17", "\u001B[35m"),
                new TrumpCard("Go for 24", "Change target to 24", "\u001B[35m"),
                new TrumpCard("Go for 27", "Change target to 27", "\u001B[35m"),
                new TrumpCard("Remove", "Removes the last card in your opponent's hand, but it cannot be their first (hidden) card.", "\u001B[33m"),
                new TrumpCard("Return", "Removes the last card in your hand. Cannot be your first card.", "\u001B[33m"),
                new TrumpCard("Exchange", "Switch out your last card with your opponent's last card.", "\u001B[33m"),
                new TrumpCard("Reincarnation", "Removes opponent's last trump + gives you one trump.", "\u001B[93m"),
                new TrumpCard("Refresh", "Removes all of your cards, gives you two fresh ones.", "\u001B[33m"),
                new TrumpCard("Disservice", "Forces your opponent to draw a random card.", "\u001B[93m"),
                new TrumpCard("Reveal", "Tells you your opponent's first card.", "\u001B[33m"),
                new TrumpCard("One-up", "Increases bet by 1.", "\u001B[31m"),
                new TrumpCard("Two-up", "Increases bet by 2.", "\u001B[91m"),
                new TrumpCard("Shield", "Decreases bet by 1.", "\u001B[34m"),
                new TrumpCard("Shield+", "Decreases bet by 2.", "\u001B[94m"),
                new TrumpCard("Bloodshed", "Increases bet by one and gives you one trump card.", "\u001B[91m"),
                new TrumpCard("Bless", "Nobody will die on this round. If someone would have, the bet is decreased by 1.", "\u001B[96m"),
                new TrumpCard("2 Card", "Gives you the two card if it is in the deck.", "\u001B[32m"),
                new TrumpCard("3 Card", "Gives you the three card if it is in the deck.", "\u001B[32m"),
                new TrumpCard("4 Card", "Gives you the four card if it is in the deck.", "\u001B[32m"),
                new TrumpCard("5 Card", "Gives you the five card if it is in the deck.", "\u001B[32m"),
                new TrumpCard("6 Card", "Gives you the six card if it is in the deck.", "\u001B[32m"),
                new TrumpCard("7 Card", "Gives you the seven card if it is in the deck.", "\u001B[32m"),
                new TrumpCard("Perfect Draw", "Draw the card still in the deck that gets you closest to the target while remaining under it.", "\u001B[92m"),
                new TrumpCard("Extend", "Add 15 seconds to your move timer.", "\u001B[96m"),
                new TrumpCard ("Rush", "Subtract 10 seconds from your opponent's move timer","\u001B[93m"),
                new TrumpCard("Friendship", "Gives both players two trump cards.", "\u001B[95m"),
                new TrumpCard("Destroy", "Removes opponent's last trump.", "\u001B[36m"),
                new TrumpCard("Hush", "Draw a card your opponent cannot see.", "\u001B[36m"),
                new TrumpCard("Clairvoyance", "Peeks at the top 3 cards remaining in the deck.", "\u001B[36m"),
                new TrumpCard("Dud", "This Trump Card does literally nothing. Sorry!", "\u001B[90m"),
            };
            System.out.println("\n--- AVAILABLE TRUMP CARDS ---");
            for (int i = 0; i < catalog.length; i++) {
                TrumpCard card = catalog[i];
                String name = card.getFunction();
                if (i == 0) 
                {
                    System.out.println("\u001B[1m-- GO FOR CARDS --\u001B[0m");
                } 
                else if (i == 3) 
                {
                    System.out.println("\n\u001B[1m-- HAND MANIPULATION CARDS --\u001B[0m");
                } 
                else if (i == 10) 
                {
                    System.out.println("\n\u001B[1m-- BET MANIPULATION CARDS --\u001B[0m");
                } else if (i == 16) 
                {
                    System.out.println("\n\u001B[1m-- FORCE-DRAW CARDS --\u001B[0m");
                } else if (i == 23) 
                {
                    System.out.println("\n\u001B[1m-- TIMER MANIPULATION CARDS --\u001B[0m");
                }
                else if (i == 25)
                {
                    System.out.println("\n\u001B[1m-- MISCELLANEOUS CARDS --\u001B[0m");
                }
                System.out.println(card.getColor() + i + ") " + name + "\u001B[0m");
            }
            System.out.print("\nSelect index of Trump Card to grant: ");
            if (userIntInput.hasNextInt()) 
            {
                int selection = userIntInput.nextInt();
                userIntInput.nextLine();
                if (selection >= 0 && selection < catalog.length) 
                {
                    TrumpCard selected = catalog[selection];
                    trumps.add(new TrumpCard(selected.getFunction(), selected.getExplanation(), selected.getColor()));
                    if (targetPlayer == 1) PlayerTrumps++; else BotTrumps++;
                    System.out.println("Added " + selected.getColor() + selected.getFunction() + "\u001B[0m to " + targetName + ".");
                } 
                else 
                {
                    System.out.println("\u001B[31mInvalid index.\u001B[0m");
                }
            } 
            else 
            {
                userIntInput.nextLine();
            }
        }
        else if (action == 2)
        {
            if (trumps.isEmpty()) 
            {
                System.out.println("\u001B[31m" + targetName + " has no Trump cards to remove.\u001B[0m");
                return;
            }
            System.out.println("\n--- CURRENT TRUMP CARDS (" + targetName.toUpperCase() + ") ---");
            for (int i = 0; i < trumps.size(); i++) 
            {
                TrumpCard tc = trumps.get(i);
                System.out.println(i + ") " + tc.getColor() + tc.getFunction() + "\u001B[0m");
            }
            System.out.print("Select index to remove: ");
            if (userIntInput.hasNextInt()) 
            {
                int rIdx = userIntInput.nextInt();
                userIntInput.nextLine();
                if (rIdx >= 0 && rIdx < trumps.size()) 
                {
                    TrumpCard removed = trumps.remove(rIdx);
                    if (targetPlayer == 1) PlayerTrumps--; else BotTrumps--;
                    System.out.println("Removed " + removed.getColor() + removed.getFunction() + "\u001B[0m from " + targetName + ".");
                } 
                else 
                {
                    System.out.println("\u001B[31mInvalid index.\u001B[0m");
                }
            } else {
                userIntInput.nextLine();
            }
        }
    }
    private static void manageBetAndSaw()
    {
        System.out.println("\n--- BET & SAW MODIFIER ---");
        System.out.println("1) Change Current Bet (Current: " + bet + ")");
        System.out.println("2) Move Saw Position (Current: " + sawPlace + " / Max: " + sawDistance + ")");
        System.out.println("3) Change Saw Total Distance (Current: " + sawDistance + ")");
        System.out.println("4) Toggle \u001B[96mBless\u001B[0m Effect (Currently: " + (bless ? "\u001B[32mON\u001B[0m" : "\u001B[31mOFF\u001B[0m") + ")");
        System.out.println("5) Change the Target");
        System.out.print("Choice: ");
        if (!userIntInput.hasNextInt()) 
        {
            userIntInput.nextLine();
            return;
        }
        int subChoice = userIntInput.nextInt();
        userIntInput.nextLine();
        switch (subChoice)
        {
        case 1:
            System.out.print("Enter new bet amount: ");
            if (userIntInput.hasNextInt()) 
            {
                int newBet = userIntInput.nextInt();
                userIntInput.nextLine();
                if (newBet >= 0)
                {
                    bet = newBet;
                    System.out.println("Bet updated to: " + bet);
                }
                else 
                {
                    System.out.println("\u001B[31mBet must be 0 or higher.\u001B[0m");
                    manageBetAndSaw();
                }
            } 
            else 
            {
                userIntInput.nextLine();
                System.out.println("\u001B[31mInvalid input.\u001B[0m");
                manageBetAndSaw();
            }
            break;
        case 2:
            System.out.print("Enter new saw position (0 to " + sawDistance + "): ");
            if (userIntInput.hasNextInt()) 
            {
                int newPos = userIntInput.nextInt();
                userIntInput.nextLine();
                sawPlace = newPos;
                if (sawPlace < 0) sawPlace = 0;
                if (sawPlace > sawDistance) sawPlace = sawDistance;
                System.out.println("Saw position updated to: " + sawPlace);
                System.out.println("\u001B[32mYou\u001B[0m are " + sawPlace + " spaces from death.");
                int opponentSaw = sawDistance - sawPlace;
                System.out.println("\u001B[31mYour opponent\u001B[0m is " + opponentSaw + " spaces from death.");
            } 
            else 
            {
                userIntInput.nextLine();
                System.out.println("\u001B[31mInvalid input.\u001B[0m");
            }
            break;
        case 3:
            System.out.print("Enter new total saw distance: ");
            if (userIntInput.hasNextInt()) 
            {
                int newDist = userIntInput.nextInt();
                userIntInput.nextLine();
                if (newDist > 2) 
                {
                    sawDistance = newDist;
                    if (sawPlace > sawDistance) sawPlace = sawDistance;
                    System.out.println("Saw distance updated to: " + sawDistance);
                    System.out.println("\u001B[32mYou\u001B[0m are " + sawPlace + " spaces from death.");
                    int opponentSaw = sawDistance - sawPlace;
                    System.out.println("\u001B[31mYour opponent\u001B[0m is " + opponentSaw + " spaces from death.");
                } 
                else 
                {
                    System.out.println("\u001B[31mDistance must be greater than 2.\u001B[0m");
                }
            } 
            else 
            {
                userIntInput.nextLine();
                System.out.println("\u001B[31mInvalid input.\u001B[0m");
            }
            break;
        case 4:
            bless = !bless;
            System.out.println("Bless status toggled to: " + (bless ? "\u001B[96mACTIVE\u001B[0m" : "\u001B[90mINACTIVE\u001B[0m"));
            break;
        case 5:
            System.out.print("Enter new target score (0 to 66): ");
            int newTarget = userIntInput.nextInt();
            userIntInput.nextLine();
            if (newTarget >= 0 && newTarget <= 66) target = newTarget;
            else System.out.println("\u001B[31mInvalid target.\u001B[0m");
            break;
        default:
            System.out.println("\u001B[31mInvalid choice.\u001B[0m");
        }
    }
    private static long editTimer(long currentMillis)
    {
        System.out.println("\n--- EDIT TIMER ---");
        System.out.println("1) Edit \u001B[32mUser's\u001B[0m Current Turn Timer (Current: " + (currentMillis / 1000) + "s)");
        System.out.println("2) Edit \u001B[31mBot's\u001B[0m Next Move Timer (Current: " + (moveTimer / 1000) + "s)");
        System.out.print("Choice: ");
        long newTime = currentMillis;
        if (userIntInput.hasNextInt())
        {
            int targetChoice = userIntInput.nextInt();
            userIntInput.nextLine();
            if (targetChoice == 1)
            {
                System.out.print("Enter new remaining time for YOUR turn in seconds (must be >= 0): ");
                if (userIntInput.hasNextInt())
                {
                    int secs = userIntInput.nextInt();
                    userIntInput.nextLine();
                    if (secs >= 0)
                    {
                        newTime = secs * 1000L;
                        System.out.println("Your turn timer updated to " + secs + " seconds.");
                    }
                    else
                    {
                        System.out.println("\u001B[31mInvalid duration. Must be at least 0.\u001B[0m");
                    }
                }
                else
                {
                    userIntInput.nextLine();
                    System.out.println("\u001B[31mInvalid input.\u001B[0m");
                }
            }
            else if (targetChoice == 2)
            {
                System.out.print("Enter new duration for OPPONENT'S next turn in seconds (must be >= 0): ");
                if (userIntInput.hasNextInt())
                {
                    int secs = userIntInput.nextInt();
                    userIntInput.nextLine();
                    if (secs >= 0)
                    {
                        moveTimer = secs * 1000;
                        System.out.println("Opponent's next move timer updated to " + secs + " seconds.");
                    }
                    else
                    {
                        System.out.println("\u001B[31mInvalid duration. Must be at least 0.\u001B[0m");
                    }
                }
                else
                {
                    userIntInput.nextLine();
                    System.out.println("\u001B[31mInvalid input.\u001B[0m");
                }
            }
            else
            {
                System.out.println("\u001B[31mInvalid selection.\u001B[0m");
            }
        }
        else
        {
            userIntInput.nextLine();
            System.out.println("\u001B[31mInvalid input.\u001B[0m");
        }
        return newTime;
    }
    private static void recalculateScores()
    {
        userValue = 0;
        for (Card c : userPlayer) userValue += c.getNumber();
        botValue = 0;
        for (Card c : botPlayer) botValue += c.getNumber();
    }
    public static void executeTrumpEffect(String effect) 
    {
        String actorName = isUserTurn ? "\u001B[32mYou\u001B[0m" : "\u001B[31mYour opponent\u001B[0m";
        String targetName = isUserTurn ? "\u001B[31myour opponent's\u001B[0m" : "\u001B[32myour\u001B[0m";
        ArrayList<Card> currentPlayerList = isUserTurn ? userPlayer : botPlayer;
        ArrayList<Card> opponentPlayerList = isUserTurn ? botPlayer : userPlayer;
        ArrayList<String> currentHistory = isUserTurn ? userTrumpHistory : botTrumpHistory;
        if (!isUserTurn) System.out.println(actorName + " played: " + effect);
        if (effect.startsWith("Go for")) 
        {
            userTrumpHistory.removeIf(e -> e.contains("Go for"));
            botTrumpHistory.removeIf(e -> e.contains("Go for"));
            if (effect.equals("Go for 17")) target = 17;
            else if (effect.equals("Go for 24")) target = 24;
            else if (effect.equals("Go for 27")) target = 27;
            System.out.println("Target set to " + target + ".");
            currentHistory.add(effect);
        }
        else if (effect.equals("Remove")) 
        {
            if (opponentPlayerList.size() > 1) 
            {
                int removedCardVal = opponentPlayerList.get(opponentPlayerList.size() - 1).getNumber();
                if (isUserTurn) botValue -= removedCardVal;
                else userValue -= removedCardVal;
                
                opponentPlayerList.remove(opponentPlayerList.size() - 1);
                
                String valStr = (removedCardVal >= 100) ? "\u001B[31mL\u001B[0m" : String.valueOf(removedCardVal);
                System.out.println(actorName + " removed " + targetName + " last card (" + valStr + ").");
            } 
            else 
            {
                System.out.println("Cannot remove " + targetName + " first card!");
            }
        }
        else if (effect.equals("Return")) 
        {
            if (currentPlayerList.size() > 1) 
            {
                int removedCardVal = currentPlayerList.get(currentPlayerList.size() - 1).getNumber();
                if (isUserTurn) userValue -= removedCardVal;
                else botValue -= removedCardVal; 
                currentPlayerList.remove(currentPlayerList.size() - 1);
                
                String valStr = (removedCardVal >= 100) ? "\u001B[31mL\u001B[0m" : String.valueOf(removedCardVal);
                System.out.println(actorName + "\u001B[33m returned \u001B[0m" + (isUserTurn ? "your" : "their") + " last card (" + valStr + ").");
            } 
            else 
            {
                System.out.println("Cannot \u001B[33mreturn\u001B[0m the first card!");
            }
        }
        else if (effect.equals("Exchange"))
        {
            if (userPlayer.size() > 1 && botPlayer.size() > 1)
            {
                Card uCard = userPlayer.remove(userPlayer.size() - 1);
                Card bCard = botPlayer.remove(botPlayer.size() - 1);
                userValue -= uCard.getNumber();
                botValue -= bCard.getNumber();
                boolean userHasL = false;
                for (Card c : userPlayer)
                {
                    if (c.getNumber() >= 100) userHasL = true;
                }
                boolean botHasL = false;
                for (Card c : botPlayer)
                {
                    if (c.getNumber() >= 100) botHasL = true;
                }
                if (bCard.getNumber() < 100 || !userHasL)
                {
                    userPlayer.add(bCard);
                    userValue += bCard.getNumber();
                    bCard.setHidden(false);
                }
                if (uCard.getNumber() < 100 || !botHasL)
                {
                    botPlayer.add(uCard);
                    botValue += uCard.getNumber();
                    uCard.setHidden(false);
                }
                System.out.println(actorName + " swapped the last cards.");
            }
            else
            {
                System.out.println("Need at least 2 cards each to " + "\u001B[33m" + "exchange" + "\u001B[0m" + ".");
            }
        }
        else if (effect.equals("Hush")) 
        {
            Card drawnCard = Deck.deck.remove(0);
            drawnCard.setHidden(true);
            currentPlayerList.add(drawnCard);
            if (isUserTurn) 
            	{
            	userValue += drawnCard.getNumber();
            	System.out.println(actorName + " drew the "+ drawnCard.getNumber()+ " card. \u001B[31mYour opponent\u001b[0m cannot see it.");
            	}
            else
            	{
                botValue += drawnCard.getNumber();
                System.out.println(actorName + " drew a card and hid it from view.");
            	}
        }
        else if (effect.equals("One-up")) 
        {
            bet++;
            System.out.println("The bet is now " + bet);
            currentHistory.add(effect);
        }
        else if (effect.equals("Two-up")) 
        {
            bet += 2;
            System.out.println("The bet is now " + bet);
            currentHistory.add(effect);
        }
        else if (effect.equals("Shield"))
        {
            bet--;
            System.out.println("The bet is now " + bet);
            currentHistory.add(effect);
        }
        else if (effect.equals("Shield+")) 
        {
            bet -= 2;
            System.out.println("The bet is now " + bet);
            currentHistory.add(effect);
        }
        else if (effect.equals("Bloodshed")) 
        {
            bet++;
            System.out.println("The bet is now " + bet);
            
            int TrumpIdx = (int)(Math.random() * TrumpDeck.size());
            TrumpCard drawnCard = TrumpDeck.deck.get(TrumpIdx);
            if (isUserTurn) 
            {
                userTrump.add(drawnCard);
                PlayerTrumps++;
            } else 
            {
                botTrump.add(drawnCard);
                BotTrumps++;
            }
            System.out.println(actorName + " received the " + drawnCard.getFunction() + " card.");
            currentHistory.add(effect);
        }
        else if (effect.equals("Friendship")) 
        {
        	System.out.println(actorName+ " used a " + "\u001B[95m" + "friendship " + "\u001B[0m" + "card.");
            for(int i = 0; i < 2; i++) 
            {
                TrumpCard DrawnCard = TrumpDeck.deck.get((int)(Math.random() * TrumpDeck.size()));
                userTrump.add(DrawnCard);
                PlayerTrumps++;
                System.out.println("You drew the "+ DrawnCard.getColor() + DrawnCard.getFunction()+ "\u001B[0m" + " card.");
                botTrump.add(TrumpDeck.deck.get((int)(Math.random() * TrumpDeck.size())));
                System.out.println("Your opponent drew a trump card.");
                BotTrumps++;
            }
        }
        else if (effect.equals("Refresh")) 
        {
            for (Card c : currentPlayerList) 
            {
                if (c.getNumber() < 100) 
                {
                    Deck.deck.add(c);
                }
            }
            Deck.shuffle();
            currentPlayerList.clear();       
            
            if (Deck.deck.size() >= 2) 
            {
                currentPlayerList.add(Deck.deck.remove(0));
                currentPlayerList.add(Deck.deck.remove(0));
                int newScore = currentPlayerList.get(0).getNumber() + currentPlayerList.get(1).getNumber();
                if (isUserTurn) 
                {
                    userValue = newScore;
                }
                else 
                {
                    botValue = newScore;
                    botPlayer.get(0).setHidden(true);
                    int visibleCardNum = botPlayer.get(1).getNumber();
                    System.out.println("Your opponent drew a fresh hand. Visible card is " + visibleCardNum + ".");
                }
            }
            else 
            {
                System.out.println("Not enough cards in the deck to refresh!");
            }
        }
        else if (effect.equals("Reveal")) 
        {
            ArrayList<Integer> hiddenIndices = new ArrayList<Integer>();
            for (int i = 0; i < opponentPlayerList.size(); i++) 
            {
                Card c = opponentPlayerList.get(i);
                if (c != null && c.getHidden()) 
                {
                    hiddenIndices.add(i);
                }
            }
            if (hiddenIndices.isEmpty()) 
            {
                System.out.println(">> Opponent has no hidden cards!");
            } 
            else if (hiddenIndices.size() == 1) {
                int targetIdx = hiddenIndices.get(0);
                Card revealed = opponentPlayerList.get(targetIdx);
                revealed.setHidden(false);
                if (isUserTurn) 
                {
                    System.out.println("You " + "\u001B[33m" + "revealed" + "\u001B[0m" + "your opponent's hidden card (Card " + (targetIdx + 1) + "): " + revealed.getNumber());
                } else 
                {
                    System.out.println("Your opponent peeked at your hidden card (Card " + (targetIdx + 1) + "): " + revealed.getNumber());
                }
            } 
            else 
            {
                int chosenIdx = -1;
                if (isUserTurn) {
                    System.out.println("\nOpponent has hidden cards at positions:");
                    for (int idx : hiddenIndices) 
                    {
                        System.out.println("  - Card " + (idx + 1));
                    }
                    boolean validChoice = false;
                    while (!validChoice) 
                    {
                        System.out.print("Select which card to " + "\u001B[33m" + "reveal" + "\u001B[33m" + "(enter card number, e.g. 1 for Card 1): ");
                        if (userIntInput.hasNextInt()) {
                            int selectedNum = userIntInput.nextInt();
                            userIntInput.nextLine();
                            int mappedIndex = selectedNum - 1;
                            if (hiddenIndices.contains(mappedIndex)) 
                            {
                                chosenIdx = mappedIndex;
                                validChoice = true;
                            } else 
                            {
                                System.out.println(">> Invalid selection or that card is not hidden. Try again.");
                            }
                        } 
                        else 
                        {
                            userIntInput.nextLine();
                            System.out.println(">> Please enter a valid number.");
                        }
                    }
                } else 
                {
                    int randomPickIndex = (int) (Math.random() * hiddenIndices.size());
                    chosenIdx = hiddenIndices.get(randomPickIndex);
                }
                Card revealed = opponentPlayerList.get(chosenIdx);
                revealed.setHidden(false);
                if (isUserTurn) 
                {
                    System.out.println("You " + "\u001B[33m"  + " revealed" + "\u001B[0m" + "opponent's Card " + (chosenIdx + 1) + "! It was a " + revealed.getNumber() + ".");
                } 
                else 
                {
                    System.out.println("Your opponent peeked at your Card " + (chosenIdx + 1) + " (" + revealed.getNumber() + ")!");
                }
            }
        }
        else if (effect.equals("Clairvoyance"))
        {
            if (isUserTurn)
            {
                int count = Math.min(3, Deck.deck.size());
                if (count == 0)
                {
                    System.out.println("The deck is completely empty!");
                }
                else
                {
                    System.out.print("The next " + count + " card(s) in the deck: ");
                    for (int i = 0; i < count; i++)
                    {
                        String comma = (i == count - 1) ? "" : ", ";
                        System.out.print("[" + "\u001B[36m" + Deck.deck.get(i).getDisplay() + "\u001B[0m]" + comma);
                    }
                }
            }
            else
            {
                System.out.println(actorName + " used \u001B[36mClairvoyance\u001B[0m to peek at the deck's upcoming cards!");
            }
        }
        else if (effect.equals("Bless")) 
        {
            bless = true;
            System.out.println("Nobody will die this round.");
            currentHistory.add(effect);
        }
        else if (effect.equals("Disservice")) 
        {
            Card drawnCard = Deck.deck.remove(0);
            opponentPlayerList.add(drawnCard);
            int cardVal = drawnCard.getNumber();
            if (isUserTurn) 
            {
                botValue += cardVal;
                System.out.println("Your opponent was forced to draw a " + cardVal + ".");
            } else 
            {
                userValue += cardVal;
                System.out.println("You were forced by your opponent to draw a " + cardVal + ".");
            }
        }
        else if (effect.equals("Perfect Draw")) 
        {
            int needed = target - (isUserTurn ? userValue : botValue);
            Card bestCard = null;
            int foundIndex = -1;
            for (int val = needed; val > 0; val--) 
            {
                for (int i = 0; i < Deck.deck.size(); i++) 
                {
                    if (Deck.deck.get(i).getNumber() == val) 
                    {
                        bestCard = Deck.deck.get(i);
                        foundIndex = i;
                        break;
                    }
                }
                if (bestCard != null) break;
            }        
            if (bestCard != null) 
            {
                Deck.deck.remove(foundIndex);
                currentPlayerList.add(bestCard);
                if (isUserTurn) userValue += bestCard.getNumber();
                else botValue += bestCard.getNumber();
                System.out.println(actorName + "\u001B[92m" + " perfectly drew " + "\u001B[0m" + "a " + bestCard.getNumber() + ".");
            } 
            else 
            {
                System.out.println("No card in the deck will help reach the target.");
            }
        }
        else if (effect.equals("Extend"))
        {
            currentEndTime += 15000;
            System.out.println(actorName + " \u001B[96mextended\u001B[0m the current turn timer by 15 seconds!");
        }
        else if (effect.equals("Rush"))
        {
        	if (moveTimer > 15000) 
        		{
        		moveTimer -= 10000;
        		System.out.println(actorName + " \u001B[93mrushed\u001B[0m the next turn by 10 seconds!");
        		}
        	else 
        	{
        		System.out.println("You know, you have to let your opponent at least play.");
        	}
        }
        else if (effect.contains("Card")) 
        	{
            int targetNumber = Character.getNumericValue(effect.charAt(0)); 
            Card foundCard = null;
            int foundIndex = -1;
            for (int i = 0; i < Deck.deck.size(); i++) 
            {
                if (Deck.deck.get(i).getNumber() == targetNumber) 
                {
                    foundCard = Deck.deck.get(i);
                    foundIndex = i;
                    break;
                }
            }
            if (foundCard != null) 
            {
                Deck.deck.remove(foundIndex);
                currentPlayerList.add(foundCard);
                if (isUserTurn) userValue += foundCard.getNumber();
                else botValue += foundCard.getNumber();
                System.out.println(actorName + "\u001B[32m" + " forcefully drew " + "\u001B[0m" + "a " + targetNumber + " from the deck.");
            } else 
            {
                System.out.println("The " + targetNumber + " card is no longer in the deck.");
            }
        }
        else if (effect.equals("Destroy")) 
        {
            undoOpponentTrump();
        }
        else if (effect.equals("Reincarnation")) 
        {
            int TrumpIdx = (int)(Math.random() * TrumpDeck.size());
            TrumpCard drawn = TrumpDeck.deck.get(TrumpIdx);
            if (isUserTurn) 
            {
                userTrump.add(drawn);
                PlayerTrumps++;
            } 
            else 
            {
                botTrump.add(drawn);
                BotTrumps++;
            }
            System.out.println(actorName + " gained a new Trump via " + "\u001B[93m" + "Reincarnation" + "\u001B[0m" + ".");
        undoOpponentTrump();
        }
        else 
        {
        	if (isUserTurn)
        	{
            System.out.println("The " + "\u001B[90m" + "dud " + "\u001B[0m" + "card doesn't do anything. Your opponent looks at you a little funnily for wasting your mutual time.");
        	}
        	else
        	{
        	System.out.println("Your opponent just used a " + "\u001B[90m" + "dud " + "\u001B[0m" + "card. You can't help but wonder why.");
        	}
            currentHistory.add(effect);
        }
    }
    public static void undoOpponentTrump() 
    {
        ArrayList<String> oppHistory = isUserTurn ? botTrumpHistory : userTrumpHistory;
        String actorName = isUserTurn ? "You" : "Your opponent";
        String oppPossessive = isUserTurn ? "your opponent's" : "your";
        if (oppHistory.isEmpty()) 
        {
            System.out.println(actorName + " played a counter, but there was no valid trump to remove.");
            return;
        }
        String lastEffect = oppHistory.remove(oppHistory.size() - 1);
        switch (lastEffect) 
        {
            case "One-up": bet--; System.out.println(actorName + "\u001B[33m" + " removed " + "\u001B[0m" + oppPossessive + "\u001B[31m" + " One-Up " + "\u001B[0m" + "Card."); System.out.println("The bet is now " + bet + " and the target is " + target); break;
            case "Two-up": bet -= 2; System.out.println(actorName + "\u001B[33m" + " removed " + "\u001B[0m" + oppPossessive + "\u001B[91m" + " Two-Up " + "\u001B[0m" + "Card."); System.out.println("The bet is now " + bet + " and the target is " + target); break;
            case "Shield": bet++; System.out.println(actorName + "\u001B[33m" + " removed " + "\u001B[0m" + oppPossessive + "\u001B[34m" + " Shield " + "\u001B[0m" + "Card."); System.out.println("The bet is now " + bet + " and the target is " + target); break;
            case "Shield+": bet += 2; System.out.println(actorName + "\u001B[33m" + " removed " + "\u001B[0m" + oppPossessive + "\u001B[94m" + " Shield+ " + "\u001B[0m" + "Card."); System.out.println("The bet is now " + bet + " and the target is " + target); break;
            case "Bloodshed": bet--; System.out.println(actorName + "\u001B[33m" + " removed " + "\u001B[0m" + oppPossessive + "\u001B[91m" + " Bloodshed " + "\u001B[0m" + "Card."); System.out.println("The bet is now " + bet + " and the target is " + target); break;
            case "Go for 17": 
            case "Go for 24": 
            case "Go for 27": 
                target = 21; 
                System.out.println(actorName + "\u001B[33m" + " removed " + "\u001B[0m" + oppPossessive + "\u001B[35m" + " " + lastEffect + "\u001B[0m" + " Card."); 
                break;
            case "Bless": bless = false; 
            System.out.println(actorName + "\u001B[33m" + " removed " + "\u001B[0m" + oppPossessive + "\u001B[96m" + " Bless " + "\u001B[0m" + "Card."); 
            break;
            case "Dud":
                System.out.println(actorName + "\u001B[33m" + " destroyed " + "\u001B[0m" + oppPossessive + "\u001B[90m" + " Dud " + "\u001B[0m" + "Card.");
                break;
            default:
                System.out.println("That card cannot be countered.");
                break;
        }
    }
    public static void botTurn() 
    {
        System.out.println("\n--- OPPONENT'S TURN ---");
        System.out.println("Your opponent is thinking.");
        long botEndTime = System.currentTimeMillis() + moveTimer;
        moveTimer = defaultMoveTimer;
        boolean warned10 = false;
        int visibleUserScore;
        double estimatedUserScore;
        boolean userGuaranteedBust;
        double bustChance;
        int botLives;
        int userLives;
        boolean botWouldDie;
        boolean highStakes;
        boolean botIsBust;
        boolean botIsLosing;
        boolean continueTrumpPhase = true;
        while (continueTrumpPhase)
        {
            long timeLeft = botEndTime - System.currentTimeMillis();
            if (timeLeft <= 0)
            {
                System.out.println("\u001B[31mYour opponent ran out of time.\u001B[0m");
                boolean hasLCard = false;
                for (Card c : botPlayer) 
                {
                    if (c.getNumber() >= 100) hasLCard = true;
                }
                if (!hasLCard) 
                {
                    botPlayer.add(new Card(100, false));
                    botValue += 100;
                }
                botStood = true;
                return;
            }
            if (timeLeft <= 10000 && !warned10)
            {
                System.out.println("\n\u001B[31mYou hear the voice say 'Hurry up', and feel relieved it's not talking to you this time.\u001B[0m");
                System.out.println();
                warned10 = true;
            }
            boolean isHurrying = (timeLeft <= 8000);
            usedTrump = false;
            visibleUserScore = getVisibleUserScore();
            estimatedUserScore = isHurrying ? visibleUserScore : getEstimatedUserScore();
            userGuaranteedBust = isUserGuaranteedBust(visibleUserScore);
            bustChance = getBustProbability(botValue);
            botLives = sawDistance - sawPlace;
            userLives = sawPlace;
            botWouldDie = (botLives <= bet && !bless);
            highStakes = ((bet * 2) > botLives || userLives <= bet);
            botIsBust = (botValue > target);
            botIsLosing = botIsBust || (!userGuaranteedBust && botValue < estimatedUserScore);
            if (!usedTrump && timeLeft <= 10000 && highStakes && botHasTrump("Extend"))
            {
                if (!isHurrying) botPause(200);
                playBotTrump("Extend");
                botEndTime += 15000;
                usedTrump = true;
            }
            if (!usedTrump && botIsBust && highStakes) 
            {
                if (!isHurrying) botPause(1000);
                double probUserBustedWorse = isHurrying ? 0.0 : getProbabilityUserBustedWorseThan(botValue);
                if (probUserBustedWorse < 0.70)
                {
                    if (botHasTrump("Go for 24") && botValue <= 24) 
                    { 
                        if (!isHurrying) botPause(100);
                        playBotTrump("Go for 24"); 
                        usedTrump = true;
                        if (botHasTrump("Dud")) playBotTrump("Dud");
                    }
                    else if (botHasTrump("Go for 27") && botValue <= 27) 
                    { 
                        if (!isHurrying) botPause(100);
                        playBotTrump("Go for 27"); 
                        usedTrump = true;
                        if (botHasTrump("Dud")) playBotTrump("Dud");
                    }
                    else if (botHasTrump("Return")) 
                    { 
                        if (!isHurrying) botPause(100);
                        playBotTrump("Return"); 
                        usedTrump = true;
                    }
                    else if (botHasTrump("Refresh")) 
                    { 
                        if (!isHurrying) botPause(100);
                        playBotTrump("Refresh"); 
                        usedTrump = true;
                    }
                    else if (botHasTrump("Exchange") && visibleUserScore <= target) 
                    { 
                        Card botLastCard = botPlayer.get(botPlayer.size() - 1);
                        Card userLastCard = userPlayer.get(userPlayer.size() - 1);
                        boolean userLastIsLowerOrHidden = userLastCard.getHidden() || (userLastCard.getNumber() < botLastCard.getNumber());
                        if (userLastIsLowerOrHidden)
                        {
                            if (!isHurrying) botPause(100);
                            playBotTrump("Exchange"); 
                            usedTrump = true;
                        }
                    }
                }
            }
            if (!usedTrump && botHasTrump("Clairvoyance") && !Deck.deck.isEmpty())
            {
                Card topCard = Deck.deck.get(0);
                int nextScore = botValue + topCard.getNumber();
                if (botIsLosing || (nextScore <= target && nextScore > botValue) || botValue >= target - 5)
                {
                    if (!isHurrying) botPause(300);
                    playBotTrump("Clairvoyance");
                    usedTrump = true;
                    if (nextScore > target)
                    {
                        if (botHasTrump("Refresh"))
                        {
                            if (!isHurrying) botPause(200);
                            playBotTrump("Refresh");
                        }
                        else if (botHasTrump("Return") && botPlayer.size() > 1)
                        {
                            if (!isHurrying) botPause(200);
                            playBotTrump("Return");
                        }
                    }
                }
            }
            if (!usedTrump && highStakes) 
            {
                if (!isHurrying) botPause(300);
                if (botHasTrump("Reveal") && userPlayer.get(0).getHidden()) 
                {
                    playBotTrump("Reveal"); 
                    usedTrump = true;
                }
                else if (!usedTrump && highStakes && moveTimer >= 15000 && botHasTrump("Rush"))
                {
                    if (!isHurrying) botPause(200);
                    playBotTrump("Rush");
                    usedTrump = true;
                }
            }
            int gap = target - botValue;
            if (!usedTrump && botIsLosing && highStakes && gap >= 1 && gap < 8) 
            {
                if (!isHurrying) botPause(400);
                String cardNeeded = gap + " card";
                if (botHasTrump(cardNeeded) && Card.isCardInDeck(gap)) 
                {
                    playBotTrump(cardNeeded); 
                    usedTrump = true;
                }
                else if (gap - 1 >= 1)
                {
                    String altCardNeeded = (gap - 1) + " card";
                    if (botHasTrump(altCardNeeded) && Card.isCardInDeck(gap - 1)) 
                    {
                        playBotTrump(altCardNeeded); 
                        usedTrump = true;
                    }
                }
            }
            if (!usedTrump && botIsLosing && bustChance >= 0.50 && gap >= 1 && gap <= 8 && Card.isCardInDeck(gap) && botHasTrump("Perfect Draw")) 
            {
                if (!isHurrying) botPause(100);
                playBotTrump("Perfect Draw"); 
                usedTrump = true;
            }
            if (!usedTrump && (!botIsLosing || botWouldDie)) 
            {
                if (botValue >= target - 1 && (highStakes || userLives - 2 <= bet)) 
                {
                    if (botHasTrump("Two-up")) 
                    { 
                        if (!isHurrying) botPause(100);
                        playBotTrump("Two-up"); 
                        usedTrump = true;
                        if (botHasTrump("Dud")) playBotTrump("Dud");
                    }
                }
                if (!usedTrump && botValue >= target - 2 && (highStakes || userLives - 1 <= bet)) 
                {
                    if (botHasTrump("One-up")) 
                    { 
                        if (!isHurrying) botPause(100);
                        playBotTrump("One-up"); 
                        usedTrump = true;
                        if (botHasTrump("Dud")) playBotTrump("Dud");
                    }
                }
            }
            if (!usedTrump && botWouldDie && (botIsLosing || botIsBust)) 
            {
                if (!isHurrying) botPause(200);
                if (botHasTrump("Bless")) 
                { 
                    playBotTrump("Bless"); 
                    usedTrump = true;
                    while (botHasTrump("Dud")) 
                    {
                        playBotTrump("Dud");
                    }
                }
                else if (botHasTrump("Shield+")) 
                { 
                    if (!isHurrying) botPause(100);
                    playBotTrump("Shield+"); 
                    usedTrump = true;
                    if (botHasTrump("Dud")) playBotTrump("Dud");
                }
                else if (botHasTrump("Shield")) 
                { 
                    if (!isHurrying) botPause(100);
                    playBotTrump("Shield"); 
                    usedTrump = true;
                    if (botHasTrump("Dud")) playBotTrump("Dud");
                }
            }

            if (!usedTrump) 
            {
                continueTrumpPhase = false;
            }
        }
        visibleUserScore = getVisibleUserScore();
        estimatedUserScore = getEstimatedUserScore();
        userGuaranteedBust = isUserGuaranteedBust(visibleUserScore);
        bustChance = getBustProbability(botValue);
        boolean finalIsLosing = (botValue > target) || (!userGuaranteedBust && botValue < estimatedUserScore);
        boolean usedClairvoyanceThisTurn = botTrumpHistory.contains("Clairvoyance");
        if (usedClairvoyanceThisTurn && !Deck.deck.isEmpty())
        {
            Card nextCard = Deck.deck.get(0);
            if (botValue + nextCard.getNumber() <= target)
            {
                botActionDraw();
            }
            else
            {
                botStood = true;
                System.out.println("Your opponent decided to stay.");
            }
        }
        else
        {
            if (finalIsLosing && bustChance < 0.65)
            {
                botActionDraw();
            }
            else if (bustChance < 0.18)
            {
                botActionDraw();
            }
            else
            {
                botStood = true;
                System.out.println("Your opponent decided to stay.");
            }
        }
    }
    private static void botActionDraw() 
    {
        botPause(1000);
        if (Deck.deck.isEmpty()) 
        {
            if (!usedTrump)
            {
                botStood = true;
            }
            else
            {
                userStood = false;
            }
            System.out.println("Your opponent decided to stay.");
        } 
        else 
        {
            Card drawn = Deck.deck.remove(0);
            botPlayer.add(drawn);
            botValue += drawn.getNumber();
            System.out.println("Your opponent drew a card.");
            botStood = false;
            userStood = false;
        }
    }
    public static boolean botHasTrump(String effectName) 
    {
        for (TrumpCard card : botTrump) {
            if (card.getFunction().equals(effectName)) return true;
        }
        return false;
    }
    public static void playBotTrump(String effectName) 
    {
        for (int i = 0; i < botTrump.size(); i++) 
        {
            if (botTrump.get(i).getFunction().equals(effectName)) 
            {
                System.out.println("Opponent played: " + effectName);
                botTrump.remove(i);
                BotTrumps--;
                usedTrump = true;
                userStood = false;
                executeTrumpEffect(effectName);
                return;
            }
        }
    }
    public static int getVisibleUserScore() 
    {
        int score = 0;
        if (userPlayer.size() > 1) 
        {
            for (int i = 1; i < userPlayer.size(); i++) 
            {
                if (!userPlayer.get(i).getHidden())
                {
                    score += userPlayer.get(i).getNumber();
                }
            }
        }
        return score;
    }
    public static boolean isUserGuaranteedBust(int visibleScore) 
    {
        if (visibleScore > target) return true;
        int maxNeededCard = target - visibleScore;
        for (int val = 0; val <= maxNeededCard; val++) 
        {
            boolean cardIsGone = false;
            for (Card c : botPlayer) if (c.getNumber() == val) cardIsGone = true;
            for (int i = 1; i < userPlayer.size(); i++) 
            {
                if (userPlayer.get(i).getNumber() == val && !userPlayer.get(i).getHidden()) cardIsGone = true;
            }
            if (!cardIsGone) return false;
        }
        return true;
    }
    public static double getBustProbability(int currentScore) {
        int totalCardsLeft = Deck.deck.size();
        if (totalCardsLeft == 0) return 1.0;
        int bustCards = 0;
        for (Card c : Deck.deck) 
        {
            if (currentScore + c.getNumber() > target) {
                bustCards++;
            }
        }
        return (double) bustCards / totalCardsLeft;
    }
    public static int getUserHiddenCardCount() 
    {
        int hiddenCount = 0;
        for (Card c : userPlayer) 
        {
            if (c.getHidden()) 
            {
                hiddenCount++;
            }
        }
        return hiddenCount;
    }
    public static double getEstimatedUserScore() 
    {
        int visibleScore = getVisibleUserScore();
        int hiddenCount = getUserHiddenCardCount();
        if (hiddenCount == 0) 
        {
            return visibleScore;
        }
        List<Integer> knownCards = new ArrayList<>();
        for (Card c : botPlayer) 
        {
            knownCards.add(c.getNumber());
        }
        for (Card c : userPlayer) 
        {
            if (!c.getHidden()) 
            {
                knownCards.add(c.getNumber());
            }
        }
        List<Integer> availableCards = new ArrayList<>();
        for (int val = 0; val <= 11; val++) 
        {
            if (!knownCards.contains(val)) 
            {
                availableCards.add(val);
            }
        }
        if (availableCards.isEmpty()) 
        {
            return visibleScore;
        }
        Collections.sort(availableCards);
        double medianCardValue;
        int size = availableCards.size();
        if (size % 2 == 1) 
        {
            medianCardValue = availableCards.get(size / 2);
        } 
        else 
        {
            medianCardValue = (availableCards.get((size / 2) - 1) + availableCards.get(size / 2)) / 2.0;
        }
        return visibleScore + (medianCardValue * hiddenCount);
    }
    public static double getProbabilityUserBustedWorseThan(int botScore) 
    {
        int visibleScore = getVisibleUserScore();
        if (visibleScore > botScore) 
        {
            return 1.0;
        }
        int hiddenCount = getUserHiddenCardCount();
        if (hiddenCount == 0) 
        {
            return visibleScore > botScore ? 1.0 : 0.0;
        }
        List<Integer> knownCards = new ArrayList<>();
        for (Card c : botPlayer) knownCards.add(c.getNumber());
        for (Card c : userPlayer) if (!c.getHidden()) knownCards.add(c.getNumber());
        List<Integer> availableCards = new ArrayList<>();
        for (int val = 0; val <= 11; val++) 
        {
            if (!knownCards.contains(val)) availableCards.add(val);
        }
        if (availableCards.isEmpty()) return 0.0;
        int neededMinHiddenSum = botScore - visibleScore + 1;
        int totalCombinations = 0;
        int worseBustCombinations = 0;
        if (hiddenCount == 1) 
        {
            for (int cardVal : availableCards) 
            {
                totalCombinations++;
                if (cardVal >= neededMinHiddenSum) 
                {
                    worseBustCombinations++;
                }
            }
        } 
        else
        {
            for (int i = 0; i < availableCards.size(); i++) 
            {
                for (int j = i + 1; j < availableCards.size(); j++) 
                {
                    totalCombinations++;
                    if (availableCards.get(i) + availableCards.get(j) >= neededMinHiddenSum) 
                    {
                        worseBustCombinations++;
                    }
                }
            }
        }
        return totalCombinations == 0 ? 0.0 : ((double) worseBustCombinations / totalCombinations);
    }
    public static void botPause(int millis) 
    {
        try 
        {
            Thread.sleep(millis);
        } 
        catch (InterruptedException e) 
        {
            Thread.currentThread().interrupt();
        }
    }
    public static void getWinner()
    {
    	System.out.println();
    	for (Card c : userPlayer) c.setHidden(false);
        for (Card c : botPlayer) c.setHidden(false);
        try 
        {
        	System.out.println("And the winner is...");
			Thread.sleep(3000);
		} 
        catch (InterruptedException e) 
        {
			e.printStackTrace();
		}
        String botScoreStr = (botValue >= 100) ? "\u001B[31mL\u001B[39m" : "\u001B[31m" + botValue + "\u001B[39m";
        String userScoreStr = (userValue >= 100) ? "\u001B[31mL\u001B[39m" : "\u001B[32m" + userValue + "\u001B[39m";
        if (userValue == botValue)
        {
            String tieScoreStr = (botValue >= 100) ? "\u001B[31mL\u001B[0m" : "\u001B[33m" + botValue + "\u001B[0m";
            System.out.println("\u001B[33m" + "Nobody." + "\u001B[0m");
            System.out.println("(Both players had an equal score of " + tieScoreStr + ").");
            bet++;
            try 
            {
				Thread.sleep(3000);
			} 
            catch (InterruptedException e) 
            {
				e.printStackTrace();
			}
        }
        else if (botValue > target && userValue <= target || (botValue > target && userValue > target && botValue > userValue) || (userValue <= target && botValue < userValue))
        {
            sawPlace += bet;
            System.out.println("\u001B[32m" + "You!" + "\u001B[39m");
            System.out.println("(Your opponent's score of " + botScoreStr + " lost to your " + userScoreStr + ").");
            bet++;
            firstTurnUser = false;
            try 
            {
				Thread.sleep(3000);
			} 
            catch (InterruptedException e) 
            {
				e.printStackTrace();
			}
        }
        else 
        {
            sawPlace -= bet;
            System.out.println("\u001B[31m" + "Your opponent!" + "\u001B[39m");
            System.out.println("(Your opponent's score of " + botScoreStr + " beat your " + userScoreStr + ").");
            bet++;
            firstTurnUser = true;
            try 
            {
				Thread.sleep(3000);
			} 
            catch (InterruptedException e) 
            {
				e.printStackTrace();
			}
        }
        if (sawPlace <= 0)
        {
        	if (!bless)
        		{
            System.out.println("\u001B[1m" + "\u001B[31m" + "The saw before you moves towards your face, closing the meager remaining distance.");
            System.out.println("All that's left to do is to reflect on your life as you face an inevitable end.");
            playingGame = false;
        		}
        	else
        		{
        			sawPlace += bet;
        			bet--;
        			bet--;
        			System.out.println("\u001B[37m" + "You were rescued by the bet card, which also reduced your bet by one." + "\u001B[0m");
        		}
        }
        else if (sawPlace >= sawDistance)
        {
        	if (!bless)
        	{
            System.out.println("\u001B[1m" + "\u001B[32m" + "You win, having to watch your opponent die in front of you.");
            System.out.println("All you can do is wonder if it was worth winning at the expense of another life.");
            playingGame = false;
        	}
        	else 
        		{
        			sawPlace -= bet;
        			bet--;
        			bet--;
        			System.out.println("\u001B[37m" + "Your opponent has been saved by the bless card, which also reduced the bet by 1." + "\u001B[0m");
        		}
        }
    }
    public static void clearScreen() throws InterruptedException 
    {
    	for (int i = 0; i < 60; i++) 
    	{
    	    System.out.println();
    	}
    }
}