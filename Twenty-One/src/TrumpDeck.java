import java.util.ArrayList;
import java.util.Collections;
public class TrumpDeck
{
	public static ArrayList<TrumpCard> deck = new ArrayList<TrumpCard>();
	public static void fillDeck()
		{
			deck.clear();
			// Go for cards
			deck.add(new TrumpCard("Go for 17", "Change target to 17","\u001B[35m"));
			deck.add(new TrumpCard("Go for 24", "Change target to 24","\u001B[35m"));
			deck.add(new TrumpCard("Go for 27", "Change target to 27","\u001B[35m"));
			deck.add(new TrumpCard("Go for 17", "Change target to 17","\u001B[35m"));
			deck.add(new TrumpCard("Go for 24", "Change target to 24","\u001B[35m"));
			deck.add(new TrumpCard("Go for 27", "Change target to 27","\u001B[35m"));
			// Hand Manipulation Cards
			deck.add(new TrumpCard("Remove", "Removes the last card in your opponent's hand, but it cannot be their first (hidden) card.","\u001B[33m"));
			deck.add(new TrumpCard("Return", "Removes the last card in your hand. Cannot be your first card.","\u001B[33m"));
			deck.add(new TrumpCard ("Exchange", "Switch out your last card with your opponent's last card.","\u001B[33m"));
			deck.add(new TrumpCard("Remove", "Removes the last card in your opponent's hand, but it cannot be their first (hidden) card.","\u001B[33m"));
			deck.add(new TrumpCard("Return", "Removes the last card in your hand. Cannot be your first card.","\u001B[33m"));
			deck.add(new TrumpCard ("Exchange", "Switch out your last card with your opponent's last card.","\u001B[33m"));
			deck.add(new TrumpCard ("Reincarnation", "Removes opponent's last trump + gives you one trump.","\u001B[93m"));
			deck.add(new TrumpCard ("Refresh", "Removes all of your cards, gives you two fresh ones.","\u001B[33m"));
			deck.add(new TrumpCard ("Disservice", "Forces your opponent to draw a random card.","\u001B[93m"));
			deck.add(new TrumpCard ("Reveal", "Tells you your opponent's first card.","\u001B[33m"));
			deck.add(new TrumpCard ("Reveal", "Tells you your opponent's first card.","\u001B[33m"));
			// Bet Manipulation Cards
			deck.add(new TrumpCard ("One-up", "Increases bet by 1.","\u001B[31m"));
			deck.add(new TrumpCard ("Two-up", "Increases bet by 2.","\u001B[91m"));
			deck.add(new TrumpCard ("Shield", "Decreases bet by 1.","\u001B[34m"));
			deck.add(new TrumpCard ("Shield+", "Decreases bet by 2.","\u001B[94m"));
			deck.add(new TrumpCard ("Bloodshed", "Increases bet by one and gives you one trump card.","\u001B[91m"));
			deck.add(new TrumpCard ("One-up", "Increases bet by 1.","\u001B[31m"));
			deck.add(new TrumpCard ("Two-up", "Increases bet by 2.","\u001B[91m"));
			deck.add(new TrumpCard ("Bless", "Nobody will die on this round. If someone would have, the bet is decreased by 1.","\u001B[96m"));
			// Force-draw Cards
			deck.add(new TrumpCard ("2 Card", "Gives you the two card if it is in the deck.","\u001B[32m"));
			deck.add(new TrumpCard ("3 Card", "Gives you the three card if it is in the deck.","\u001B[32m"));
			deck.add(new TrumpCard ("4 Card", "Gives you the four card if it is in the deck.","\u001B[32m"));
			deck.add(new TrumpCard ("5 Card", "Gives you the five card if it is in the deck.","\u001B[32m"));
			deck.add(new TrumpCard ("6 Card", "Gives you the six card if it is in the deck.","\u001B[32m"));
			deck.add(new TrumpCard ("7 Card", "Gives you the seven card if it is in the deck.","\u001B[32m"));
			deck.add(new TrumpCard ("2 Card", "Gives you the two card if it is in the deck.","\u001B[32m"));
			deck.add(new TrumpCard ("3 Card", "Gives you the three card if it is in the deck.","\u001B[32m"));
			deck.add(new TrumpCard ("4 Card", "Gives you the four card if it is in the deck.","\u001B[32m"));
			deck.add(new TrumpCard ("5 Card", "Gives you the five card if it is in the deck.","\u001B[32m"));
			deck.add(new TrumpCard ("6 Card", "Gives you the six card if it is in the deck.","\u001B[32m"));
			deck.add(new TrumpCard ("7 Card", "Gives you the seven card if it is in the deck.","\u001B[32m"));
			deck.add(new TrumpCard ("Perfect Draw", "Draw the card still in the deck that gets you closest to the target while remaining under it.","\u001B[92m"));
			// Miscellaneous
			deck.add(new TrumpCard ("Friendship", "Gives both players two trump cards.","\u001B[95m"));
			deck.add(new TrumpCard ("Friendship", "Gives both players two trump cards.","\u001B[95m"));
			deck.add(new TrumpCard ("Destroy", "Removes opponent's last trump.","\u001B[36m"));
			deck.add(new TrumpCard ("Destroy", "Removes opponent's last trump.","\u001B[36m"));
			deck.add(new TrumpCard ("Hush", "Draw a card your opponent cannot see.","\u001B[36m"));
			deck.add(new TrumpCard ("Hush", "Draw a card your opponent cannot see.","\u001B[36m"));
			deck.add(new TrumpCard ("Hush", "Draw a card your opponent cannot see.","\u001B[36m"));
			deck.add(new TrumpCard ("Dud", "This Trump Card does literally nothing. Sorry!","\u001B[90m"));
			deck.add(new TrumpCard ("Dud", "This Trump Card does literally nothing. Sorry!","\u001B[90m"));
			deck.add(new TrumpCard ("Dud", "This Trump Card does literally nothing. Sorry!","\u001B[90m"));
			deck.add(new TrumpCard ("Dud", "This Trump Card does literally nothing. Sorry!","\u001B[90m"));
			deck.add(new TrumpCard ("Dud", "This Trump Card does literally nothing. Sorry!","\u001B[90m"));
			// (TBA) Timer Manipulation Cards
			// deck.add(new TrumpCard ("Extend", "Add 15 seconds to your move timer","\u001B[96m"));
			// deck.add(new TrumpCard ("Rush", "Subtract 10 seconds from your opponent's move timer","\u001B[93m"));
		}
	public static void shuffle()
		{
		Collections.shuffle(deck);
		}
	public static int size()
		{
			return deck.size();
		}	
	}
