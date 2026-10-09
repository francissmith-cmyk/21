import java.util.ArrayList;
import java.util.Collections;
public class Deck
	{
	static ArrayList<Card> deck = new ArrayList<Card>();
	
	public static void fillDeck()
		{
			deck.clear();
			deck.add(new Card(0, false));
			deck.add(new Card(1, false));
			deck.add(new Card(2, false));
			deck.add(new Card(3, false));
			deck.add(new Card(4, false));
			deck.add(new Card(5, false));
			deck.add(new Card(6, false));
			deck.add(new Card(7, false));
			deck.add(new Card(8, false));
			deck.add(new Card(9, false));
			deck.add(new Card(10, false));
			deck.add(new Card(11, false));
		}
	public static void shuffle()
		{
		Collections.shuffle(deck);
		}
	}