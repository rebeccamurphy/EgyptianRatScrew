package egyptianratscrew.card;

import java.util.ArrayList;
import java.util.List;

public class Deck {

	private List<Card> deck;
	private List<Card> upCards;
	private List<Card> downCards;
	private int numDecks;
	
	public Deck() {
		deck= new ArrayList<Card>();
	}

	public void drawPlayerDeck(){
		//draws down cards
	}
	
	public void drawDiscardPile(){
		//should draw atleast 3 cards
		//posibly down Cards as well
	}
	
}
