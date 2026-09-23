import java.util.ArrayList;
import java.util.Collections;
public class TrumpDeck
{
	public static ArrayList<TrumpCard> deck = new ArrayList<TrumpCard>();
	public static void fillDeck()
		{
			deck.clear();
			deck.add(new TrumpCard("Go for 17", "Change target to 17"));
			deck.add(new TrumpCard("Go for 24", "Change target to 24"));
			deck.add(new TrumpCard("Go for 27", "Change target to 27"));
			deck.add(new TrumpCard("Go for 17", "Change target to 17"));
			deck.add(new TrumpCard("Go for 24", "Change target to 24"));
			deck.add(new TrumpCard("Go for 27", "Change target to 27"));
			deck.add(new TrumpCard("Remove", "Removes the last card in your opponent's hand, but it cannot be their first (hidden) card."));
			deck.add(new TrumpCard("Return", "Removes the last card in your hand. Cannot be your first card."));
			deck.add(new TrumpCard ("Exchange", "Switch out your last card with your opponent's last card."));
			deck.add(new TrumpCard("Remove", "Removes the last card in your opponent's hand, but it cannot be their first (hidden) card."));
			deck.add(new TrumpCard("Return", "Removes the last card in your hand. Cannot be your first card."));
			deck.add(new TrumpCard ("Exchange", "Switch out your last card with your opponent's last card."));
			deck.add(new TrumpCard ("Refresh", "Removes all of your cards, gives you two fresh ones."));
			deck.add(new TrumpCard ("Disservice", "Forces your opponent to draw a random card."));
			deck.add(new TrumpCard ("Hush", "Draw a card your opponent cannot see."));
			deck.add(new TrumpCard ("Hush", "Draw a card your opponent cannot see."));
			deck.add(new TrumpCard ("Hush", "Draw a card your opponent cannot see."));
			deck.add(new TrumpCard ("Reveal", "Tells you your opponent's first card."));
			deck.add(new TrumpCard ("Reveal", "Tells you your opponent's first card."));
			deck.add(new TrumpCard ("Perfect Draw", "Draw the card still in the deck that gets you closest to the target while remaining under it."));
			deck.add(new TrumpCard ("One-up", "Increases bet by 1."));
			deck.add(new TrumpCard ("Two-up", "Increases bet by 2."));
			deck.add(new TrumpCard ("Shield", "Decreases bet by 1."));
			deck.add(new TrumpCard ("Shield +", "Decreases bet by 2."));
			deck.add(new TrumpCard ("One-up", "Increases bet by 1."));
			deck.add(new TrumpCard ("Two-up", "Increases bet by 2."));
			deck.add(new TrumpCard ("Shield", "Decreases bet by 1."));
			deck.add(new TrumpCard ("Shield +", "Decreases bet by 2."));
			deck.add(new TrumpCard ("Destroy", "Removes opponent's last trump."));
			deck.add(new TrumpCard ("Destroy", "Removes opponent's last trump."));
			deck.add(new TrumpCard ("Reincarnation", "Removes opponent's last trump + gives you one trump."));
			deck.add(new TrumpCard ("Bloodshed", "Increases bet by one and gives you one trump card."));
			deck.add(new TrumpCard ("Friendship", "Gives both players two trump cards."));
			deck.add(new TrumpCard ("Bless", "Nobody will die on this round. If someone would have, the bet is decreased by 1."));
			deck.add(new TrumpCard ("2 Card", "Gives you the two card if it is in the deck."));
			deck.add(new TrumpCard ("3 Card", "Gives you the three card if it is in the deck."));
			deck.add(new TrumpCard ("4 Card", "Gives you the four card if it is in the deck."));
			deck.add(new TrumpCard ("5 Card", "Gives you the five card if it is in the deck."));
			deck.add(new TrumpCard ("6 Card", "Gives you the six card if it is in the deck."));
			deck.add(new TrumpCard ("7 Card", "Gives you the seven card if it is in the deck."));
			deck.add(new TrumpCard ("2 Card", "Gives you the two card if it is in the deck."));
			deck.add(new TrumpCard ("3 Card", "Gives you the three card if it is in the deck."));
			deck.add(new TrumpCard ("4 Card", "Gives you the four card if it is in the deck."));
			deck.add(new TrumpCard ("5 Card", "Gives you the five card if it is in the deck."));
			deck.add(new TrumpCard ("6 Card", "Gives you the six card if it is in the deck."));
			deck.add(new TrumpCard ("7 Card", "Gives you the seven card if it is in the deck."));
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
