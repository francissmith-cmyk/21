import java.util.ArrayList;
import java.util.Scanner;
public class Playgame
{
    static ArrayList <Card> userPlayer = new ArrayList<Card>();
    static ArrayList <Card> botPlayer = new ArrayList<Card>();
    static ArrayList <TrumpCard> userTrump = new ArrayList<TrumpCard>();
    static ArrayList <TrumpCard> botTrump = new ArrayList<TrumpCard>();
    static boolean playingGame = false;
    static int bet = 1;
    static int sawPlace = 7;
    static int sawDistance = 14;
    static int userValue = 0;
    static int botValue = 0;
    static boolean userStood = false;
    static boolean botStood = false;
    static boolean isUserTurn = true;
    static int target = 21;
    static Scanner userIntInput = new Scanner (System.in);
    static int PlayerTrumps = 0;
    static int BotTrumps = 0;
    static boolean bless = false;
    static ArrayList<String> userTrumpHistory = new ArrayList<String>();
    static ArrayList<String> botTrumpHistory = new ArrayList<String>();
    public static void main(String[] args) throws InterruptedException {
        Greetplayer();
        while (playingGame) 
        {
            dealCard();
            userStood = false;
            botStood = false;
            isUserTurn = true; 
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
            clearScreen(); 
        }
}
    public static void Greetplayer()
    {
    	  for (int i = 0; i < 60; i++)
          	{
          		System.out.println("");
          	}
        int greeting = (int)(Math.random()*4);
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
        String DrawnTrump = userTrump.get(PlayerTrumps-1).getFunction();
		System.out.println("You received the "+ DrawnTrump+ " card.");
        userTrump.add(TrumpDeck.deck.get(Trump2));
        PlayerTrumps++;
        DrawnTrump = userTrump.get(PlayerTrumps-1).getFunction();
      	System.out.println("You received the "+ DrawnTrump+ " card.");
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
    }
    public static void userTurn()
    {
    boolean turnOver = false;
    while (!turnOver) 
    	{
        System.out.println("\n--- YOUR TURN ---");
        int botKnown = 0;
        for (Card c : botPlayer) 
        {
            if (c != null && !c.getHidden()) 
            {
                botKnown += c.getNumber();
            }
        }
        int opponentSaw = sawDistance - sawPlace;
        System.out.println("Target: " + target + " | Your Score: " + userValue + " | Bot's Visible Score: " + botKnown);
        System.out.println("Your Lives: " + sawPlace + " | Opponent's Lives: " + opponentSaw + " | Current Bet: " + bet + "\n");
        System.out.print("Your Hand: ");
        int looper = 0;
        for (Card c : userPlayer) 
        {
            if (c != null) 
            {
                String hiddenTag = c.getHidden() ? " (Hidden)" : "";
                String comma = (looper == userPlayer.size() - 1) ? "" : ", ";
                System.out.print(" [" + c.getNumber() + "]"+ hiddenTag + comma);
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
            String cardText = c.getHidden() ? "[?]" : "[" + c.getNumber() + "]";
            String comma = (looper == botPlayer.size() - 1) ? "" : ", ";
            System.out.print(cardText + comma);
            looper++;
        }
        System.out.println("\n\n1: Hit (Draw)");
        System.out.println("2: Stand (Stay)");
        System.out.println("3: View Trump Cards");
        if (userIntInput.hasNextInt()) 
        {
            int choice = userIntInput.nextInt();
            userIntInput.nextLine();
            if (choice == 1) 
            { 
                if (userValue > target) 
                {
                    System.out.println("Overkill of points.");
                } else if (Deck.deck.isEmpty()) 
                {
                    System.out.println("Deck is empty.");
                } else 
                {
                    if ((int)(Math.random() * 4) == 0) 
                    {
                        int trumpIdx = (int)(Math.random() * TrumpDeck.deck.size());
                        TrumpCard drawnTrump = TrumpDeck.deck.get(trumpIdx);
                        userTrump.add(drawnTrump);
                        PlayerTrumps++;
                        System.out.println("You drew a(n) " + drawnTrump.getFunction());
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
            else if (choice == 3) 
            {
             PlayerTrump(); 
            } 
            else if (choice == 2008)
        		{
                    System.out.println("\n--- [DEBUG MODE ACTIVE] ---");
                    System.out.println("Bot's Actual Value: " + botValue);
                    System.out.print("Bot's Full Hand: ");
                    for (Card c : botPlayer) 
                    {
                        System.out.print("[" + c.getNumber() + "] ");
                    }
                    System.out.print("\nBot's Trump Cards: ");
                    if (botTrump.isEmpty()) 
                    {
                        System.out.print("None");
                    } else 
                    {
                        for (TrumpCard tc : botTrump) 
                        {
                            System.out.print("[" + tc.getFunction() + "] ");
                        }
                    }
                    System.out.println("");
                    System.out.print("Current deck: ");
                    for (int i = 0; i < Deck.deck.size(); i++) 
                    {
                        System.out.print(Deck.deck.get(i).getNumber() + " ");
                    }
                    System.out.println("\n---------------------------\n");
                    continue; 
                }
            else 
            {
            System.out.println("You decided to stay.");
            userStood = true;
            turnOver = true;
            }
       }
        else 
        {
        	 System.out.println("You decided to stay.");
             userStood = true;
             turnOver = true;
        }
    }
}
    public static void PlayerTrump() {
        if (userTrump.isEmpty()) 
        {
            System.out.println(">> You have no Trump Cards!");
            return;
        }
        System.out.println("\n--- TRUMP CARDS ---");
        System.out.println("1) Cancel / Back");
        for (int i = 0; i < userTrump.size(); i++) 
        {
            System.out.println((i + 2) + ") " + userTrump.get(i).getFunction() + " (" + userTrump.get(i).getExplanation() + ")");
        }
        System.out.print("Select a card to use: ");
        if (userIntInput.hasNextInt()) 
        {
            int choice = userIntInput.nextInt();
            userIntInput.nextLine();
            int listIndex = choice - 2; 
            if (listIndex >= 0 && listIndex < userTrump.size()) 
            {
                TrumpCard chosenCard = userTrump.remove(listIndex);
                PlayerTrumps--;
                userStood = false;
                System.out.println("You used the " + chosenCard.getFunction() + " trump.");
                executeTrumpEffect(chosenCard.getFunction());
            } 
            else 
            {
                System.out.println(">> Action cancelled.");
            }
        }
    }
    public static void executeTrumpEffect(String effect) {
        String actorName = isUserTurn ? "You" : "Your opponent";
        String targetName = isUserTurn ? "your opponent's" : "your";
        ArrayList<Card> currentPlayerList = isUserTurn ? userPlayer : botPlayer;
        ArrayList<Card> opponentPlayerList = isUserTurn ? botPlayer : userPlayer;
        ArrayList<String> currentHistory = isUserTurn ? userTrumpHistory : botTrumpHistory;

        System.out.println(actorName + " played: " + effect);

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
                System.out.println(actorName + " removed " + targetName + " last card (" + removedCardVal + ").");
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
                System.out.println(actorName + " returned " + (isUserTurn ? "your" : "their") + " last card (" + removedCardVal + ").");
            } 
            else 
            {
                System.out.println("Cannot return the first card!");
            }
        }
        else if (effect.equals("Exchange")) 
        	{
            if (userPlayer.size() > 1 && botPlayer.size() > 1) 
            	{
                Card uCard = userPlayer.remove(userPlayer.size() - 1);
                Card bCard = botPlayer.remove(botPlayer.size() - 1);
                userPlayer.add(bCard);
                botPlayer.add(uCard);  
                userValue += bCard.getNumber() - uCard.getNumber();
                botValue += uCard.getNumber() - bCard.getNumber();
                System.out.println(actorName + " swapped the last cards.");
            } 
            else 
            {
                System.out.println("Need at least 2 cards each to exchange.");
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
            	System.out.println("You drew the "+ drawnCard.getNumber()+ "card. Your opponent cannot see it.");
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
        	System.out.println(actorName+ " used a friendship card.");
            for(int i = 0; i < 2; i++) 
            {
                TrumpCard DrawnCard = TrumpDeck.deck.get((int)(Math.random() * TrumpDeck.size()));
                userTrump.add(DrawnCard);
                PlayerTrumps++;
                System.out.println("You drew the "+ DrawnCard.getFunction()+ " card.");
                botTrump.add(TrumpDeck.deck.get((int)(Math.random() * TrumpDeck.size())));
                System.out.println("Your opponent drew a trump card.");
                BotTrumps++;
            }
        }
        else if (effect.equals("Refresh")) 
        	{
            Deck.deck.addAll(currentPlayerList);
            Deck.shuffle();
            currentPlayerList.clear();       
            if (Deck.deck.size() >= 2) 
            {
                currentPlayerList.add(Deck.deck.remove(0));
                currentPlayerList.add(Deck.deck.remove(0));
                int newScore = currentPlayerList.get(0).getNumber() + currentPlayerList.get(1).getNumber();
                if (isUserTurn) userValue = newScore;
                else botValue = newScore;
                System.out.println(actorName + " drew a fresh hand. New total is " + newScore + ".");
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
                    System.out.println("You revealed your opponent's hidden card (Card " + (targetIdx + 1) + "): " + revealed.getNumber());
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
                        System.out.print("Select which card to reveal (enter card number, e.g. 1 for Card 1): ");
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
                    System.out.println("You revealed opponent's Card " + (chosenIdx + 1) + "! It was a " + revealed.getNumber() + ".");
                } 
                else 
                {
                    System.out.println("Your opponent peeked at your Card " + (chosenIdx + 1) + " (" + revealed.getNumber() + ")!");
                }
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
                System.out.println(actorName + " perfectly drew a " + bestCard.getNumber() + ".");
            } else 
            {
                System.out.println("No card in the deck will help reach the target.");
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
                System.out.println(actorName + " forcefully drew a " + targetNumber + " from the deck.");
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
            undoOpponentTrump();
            int TrumpIdx = (int)(Math.random() * TrumpDeck.size());
            TrumpCard drawn = TrumpDeck.deck.get(TrumpIdx);
            if (isUserTurn) {
                userTrump.add(drawn);
                PlayerTrumps++;
            } else {
                botTrump.add(drawn);
                BotTrumps++;
            }
            System.out.println(actorName + " gained a new Trump via Reincarnation.");
        }
        else 
        {
            System.out.println("Effect for " + effect + " is not yet implemented.");
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
            case "One-up": bet--; System.out.println(actorName + " removed " + oppPossessive + " One-Up Card."); break;
            case "Two-up": bet -= 2; System.out.println(actorName + " removed " + oppPossessive + " Two-Up Card."); break;
            case "Shield": bet++; System.out.println(actorName + " removed " + oppPossessive + " Shield Card."); break;
            case "Shield+": bet += 2; System.out.println(actorName + " removed " + oppPossessive + " Shield + Card."); break;
            case "Bloodshed": bet--; System.out.println(actorName + " removed " + oppPossessive + " Bloodshed Card."); break;
            case "Go for 17": 
            case "Go for 24": 
            case "Go for 27": 
                target = 21; 
                System.out.println(actorName + " removed " + oppPossessive + " " + lastEffect + " Card."); 
                break;
            case "Bless": bless = false; System.out.println(actorName + " removed " + oppPossessive + " Bless Card."); break;
            default:
                System.out.println("That card cannot be countered.");
                break;
        }
        System.out.println("The bet is now " + bet + " and the target is " + target);
    }
    public static void botTurn() {
        System.out.println("\n--- OPPONENT'S TURN ---");
        int visibleUserScore = getVisibleUserScore();
        double bustChance = getBustProbability(botValue);
        boolean usingTrumps = true;
        int safetyCounter = 0; 
        while (usingTrumps && safetyCounter < 10) {
            safetyCounter++;
            int botLives = sawDistance - sawPlace;
            int userLives = sawPlace;
            boolean botWouldDie = (botLives <= bet);
            boolean highStakes = (bet >= 2 || botLives < 4);
            boolean botIsBust = (botValue > target);
            boolean botIsLosing = botIsBust || (botValue < visibleUserScore);
            if (botIsBust) {
                if (botHasTrump("Go for 24") && botValue <= 24) { 
                    playBotTrump("Go for 24"); userStood = false; botStood = false; continue; 
                }
                if (botHasTrump("Go for 27") && botValue <= 27) { 
                    playBotTrump("Go for 27"); userStood = false; botStood = false; continue; 
                }
                if (botHasTrump("Return")) { 
                    playBotTrump("Return"); userStood = false; botStood = false; continue; 
                }
                if (botHasTrump("Refresh")) { 
                    playBotTrump("Refresh"); userStood = false; botStood = false; continue; 
                }
                if (botHasTrump("Exchange") && visibleUserScore <= target) { 
                    playBotTrump("Exchange"); userStood = false; botStood = false; continue; 
                }
            }

            if (highStakes) {
                if (botHasTrump("Reveal") && userPlayer.get(0).getHidden()) {
                    playBotTrump("Reveal"); userStood = false; botStood = false; continue;
                }
                if (botHasTrump("Exchange") && visibleUserScore > botValue && visibleUserScore <= target) 
                {
                    playBotTrump("Exchange"); userStood = false; botStood = false; continue;
                }
            }

            int gap = target - botValue;
            if (gap >= 1 && gap <= 11 && !botStood) {
                String cardNeeded = gap + " card";
                if (botHasTrump(cardNeeded) && Card.isCardInDeck(gap)) {
                    playBotTrump(cardNeeded); userStood = false; botStood = false; continue;
                }
                if (botHasTrump("Perfect Draw")) {
                    playBotTrump("Perfect Draw"); userStood = false; botStood = false; continue;
                }
            }

            if (!botIsLosing && botValue >= target - 2 && highStakes) {
                if (botHasTrump("Two-up")) { 
                    playBotTrump("Two-up"); userStood = false; botStood = false; continue; 
                }
                if (botHasTrump("One-up")) { 
                    playBotTrump("One-up"); userStood = false; botStood = false; continue; 
                }
            }
            if (botWouldDie && (botIsLosing || botIsBust)) {
                if (botHasTrump("Shield+")) { 
                    playBotTrump("Shield+"); userStood = false; botStood = false; continue; 
                }
                if (botHasTrump("Shield")) { 
                    playBotTrump("Shield"); userStood = false; botStood = false; continue; 
                }
            }
            usingTrumps = false;
        }
        boolean finalIsLosing = (botValue > target) || (botValue < visibleUserScore);
        
        if (finalIsLosing && bustChance < 0.65) {
            botActionDraw();
        } else if (bustChance < 0.18) {
            botActionDraw();
        } else {
            botStood = true;
            System.out.println("Your opponent decided to stay.");
        }
    }
    private static void botActionDraw() {
        if (Deck.deck.isEmpty()) {
            botStood = true;
            System.out.println("Your opponent decided to stay.");
        } else {
            Card drawn = Deck.deck.remove(0);
            botPlayer.add(drawn);
            botValue += drawn.getNumber();
            System.out.println("Opponent drew a card.");
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
                executeTrumpEffect(effectName);
                return;
            }
        }
    }
    public static int getVisibleUserScore() 
    {
        int score = 0;
        if (userPlayer.size() > 1) {
            for (int i = 1; i < userPlayer.size(); i++) {
                score += userPlayer.get(i).getNumber();
            }
        }
        return score;
    }
    public static boolean isUserGuaranteedBust(int visibleScore) {
        if (visibleScore >= target) return true;
        int maxNeededCard = target - visibleScore;
        for (int val = 1; val <= maxNeededCard; val++) {
            boolean cardIsGone = false;
            for (Card c : botPlayer) if (c.getNumber() == val) cardIsGone = true;
            for (int i = 1; i < userPlayer.size(); i++) {
                if (userPlayer.get(i).getNumber() == val) cardIsGone = true;
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
    public static void getWinner()
    {
    	for (Card c : userPlayer) c.setHidden(false);
        for (Card c : botPlayer) c.setHidden(false);
        if (userValue == botValue)
        {
            System.out.println("It's a tie, as both players got "+ userValue+ ".");
            bet++;
        }
        else if (botValue > target && userValue <= target||(botValue > target && userValue > target && botValue > userValue)||(userValue <= target && botValue < userValue))
        {
            sawPlace+= bet;
            bet++;
            System.out.println("You beat your opponent's "+ botValue+ " with your "+ userValue+ ".");
        }
        else 
        {
            sawPlace-= bet;
            bet++;
            System.out.println("You lost to your opponent's "+ botValue+ " with your "+ userValue+ ".");
        }
        if (sawPlace <= 0)
        {
        	if (!bless)
        		{
            System.out.println("You died!");
            playingGame = false;
        		}
        	else
        		{
        			bet--;
        			sawPlace += bet;
        			bet--;
        			System.out.println("Bless card active. Bet reduced by one.");
        		}
        }
        else if (sawPlace >= sawDistance)
        {
        	if (!bless)
        	{
            System.out.println("Your opponent died!");
            playingGame = false;
        	}
        	else 
        		{
        			bet--;
        			sawPlace -= bet;
        			bet--;
        			System.out.println("Bless card active. Bet reduced by one.");
        		}
        }
    }
    public static void clearScreen() throws InterruptedException 
    {
		Thread.sleep(3000);
        for (int i = 0; i < 60; i++)
        	{
        		System.out.println("");
        	}
        System.out.flush();
    }
}