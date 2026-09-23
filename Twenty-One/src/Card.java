public class Card
	{
		private int number;
		private boolean hidden;
		public Card (int n, boolean h)
			{
			number = n;
			hidden = h;
			}
		public int getNumber()
			{
				return number;
			}
		public void setNumber(int number)
			{
				this.number = number;
			}
		public boolean getHidden()
			{
				return hidden;
			}
		public void setHidden(boolean hidden)
			{
				this.hidden = hidden;
			}
		public String getDisplay() 
		{
		    return (this.number >= 100) ? "L" : String.valueOf(this.number);
		}
		public static boolean isCardInDeck(int value) 
		{
		    for (Card c : Deck.deck) 
		    {
		        if (c.getNumber() == value) return true;
		    }
		    return false;
		}
	}
