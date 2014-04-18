package egyptianratscrew.card;

import java.util.ArrayList;
import java.util.List;
import egyptianratscrew.player.Player;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


public class Deck {

	protected List<Card> deck;
	protected int[] activeArea; //topleftX, topleftY, toprightX,toprightX
	protected boolean activeAreaDisabled;

	/***
	 * Deck Constructor
	 * Initializes deck, and activeArea
	 */
	public Deck() {
		deck= new ArrayList<Card>();
		activeArea = new int[4];
	}
	
	/***
	 * Method to add a card to the deck
	 * @param card
	 */
	
	public void add(Card card){
		deck.add(card);
	}
	
	/***
	 * Method to add a list of cards to a deck
	 * @param cards
	 */
	
	public void addAll(List<Card> cards){
		deck.addAll(cards);
	}
	
	/***
	 * Method to remove card from the deck
	 * @param int index
	 * @return Card the removed card
	 */
	
	public Card remove(int index){
		return deck.remove(index);
	}
	
	/***
	 * Returns the size of the deck
	 * @return int size
	 */
	public int size(){
		return deck.size();
	}
	
	/***
	 * Method to return the deck
	 * @return List<Card> deck
	 */
	public List<Card> getDeck(){
		return deck;
	}
	
	/***
	 * Method to draw a card from a deck.
	 * Adds the first card in the deck Player and removes that card from deck
	 * @param Player player
	 */
	
	public void drawCard(Player player){
		player.addCard(deck.get(0));
		deck.remove(0);
	}
	/**
	 * Method to check ActiveArea of Deck
	 * Passed X and Y are  checked to see if the touch was on the deck being checked, 
	 * if the deck was drawn/activeArea isn't null.
	 * @param X
	 * @param Y
	 * @return true if deck was touched, false if not.
	 */
	public boolean checkActiveArea(int X, int Y){
		if (activeArea !=null)
			return ((X> activeArea[0] &&  X< activeArea[2]) &&(Y > activeArea[1] && Y< activeArea[3]));
		return false;
	}
	
	/***
	 * Method to disable the deck's ActiveArea
	 */
	public void disableActiveArea(){
		activeArea = new int[4];
		activeAreaDisabled = true;
	}
	/***
	 * Method to enable the deck's ActiveArea
	 */
	public void enableActiveArea(){
		activeAreaDisabled = false;
	}
	
	/***
	 * Method to draw a Player's deck to the screen, and sets the ActiveArea if the playerID is 2 (human).  
	 * @param canvas
	 * @param screenW
	 * @param screenH
	 * @param scaledCardW
	 * @param scaledCardH
	 * @param scale
	 * @param cardBack
	 * @param paint
	 * @param playerID
	 */
	
	public void drawPlayerDeck(Canvas canvas, int screenW,int screenH, int scaledCardW, int scaledCardH, float scale,Bitmap cardBack, Paint paint, int playerID){
		//draws down cards
		
		for (int i = 0; i < 3; i++) 
		{ 
			float X =0, Y=0;
			switch(playerID){
			case 2: 
				X = screenW/2 + i*(5) - (scaledCardW/2);
				Y = screenH-scaledCardH-paint.getTextSize()-(10*scale);
				//if (!activeAreaDisabled){
				activeArea[0] = (int) (screenW/2 + 0*(5) - (scaledCardW/2));				//topLeftX
				activeArea[1] = (int) (screenH-scaledCardH-paint.getTextSize()-(10*scale));	//topLeftY
				activeArea[2] = (int) (screenW/2 + 3*(5) - (scaledCardW/2) + scaledCardW);	//botRightX
				activeArea[3] = activeArea[1] + scaledCardH;								//botRightY
				//}
				
		
				break;
			case 1: 
				X = screenW/2- i*5 - (scaledCardW/2); 
				Y = paint.getTextSize()+(10*scale); 
				
				break;
			case 3: break;
			case 4: break;
			}
			
			if  (deck.size() > 1)
			{	//this might draw the wrong number of cards when cards are less than 3 TODO
			canvas.drawBitmap(cardBack, 
					X, 
					Y, 
					null);
		
			}
			else 
			{	//draw 1 card if deck only has one card.
				canvas.drawBitmap(cardBack, 
						X, 
						Y, 
						null);
			
				}
		}
	}

}
