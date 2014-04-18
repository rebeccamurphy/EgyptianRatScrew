package egyptianratscrew.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import egyptianratscrew.game.Rule;
import egyptianratscrew.player.Player;

public class DiscardPile extends Deck{

	private int numDecks;
	public HashMap<String,Rule> rules;
	private boolean slappable;

	/***
	 * Discard Pile Default Constructor
	 */
	public DiscardPile() {
		deck= new ArrayList<Card>();
		numDecks = 1;
		slappable = false;
		
		//default rules
		rules = new HashMap<String, Rule>();
		rules.put("ace", new Rule(4, true));
		rules.put("king",new Rule( 3, true));
		rules.put("queen",new Rule( 2, true));
		rules.put("jack", new Rule(1, true));
		
		rules.put("sandwich", new Rule( 3, true));
		rules.put("double", new Rule( 2, true));
		
	}
	
	
	//TODO add another constructor with rules when rule options are added
	
	
	/*
	 * Rules 
	 */
	
	/***
	 * Method to check if top card of Discard Pile is Ace
	 * @return boolean true if ace, false otherwise
	 */
	
	public boolean checkAce(){
		//(1-4)14 ace id
		return deck.get(deck.size()-1).getRank() == 14;
	}
	
	/***
	 * Method to check if top card of Discard Pile is King
	 * @return boolean true if King, false otherwise
	 */
	
	public boolean checkKing(){
		//(1-4)13 king id
		return deck.get(deck.size()-1).getRank() == 13;
	}
	
	/***
	 * Method to check if top card of Discard Pile is Queen 
	 * @return boolean true if queen, false otherwise
	 */
	public boolean checkQueen(){
		//(1-4)13 king id
		return deck.get(deck.size()-1).getRank() == 12;
	}
	
	/***
	 * Method to check if top card of Discard Pile is Jack 
	 * @return boolean true if Jack, false otherwise
	 */
	
	public boolean checkJack(){
		//(1-4)13 king id
		return deck.get(deck.size()-1).getRank() == 11;
	}
	
	/***
	 * Method to check if top 3 cards of Discard Pile is a Sandwich   
	 * @return boolean true if sandwich, false otherwise
	 */
	
	public boolean checkSandwich(){
		Rule tempRule = rules.get("sandwich");
		//add more here once different rules are available
		if (tempRule.getNum() == 3&& deck.size() >2)
			return deck.get(deck.size()-1).getRank() ==deck.get(deck.size()-3).getRank();
		return false;
	}
	
	/***
	 * Method to check if top 2 cards of Discard Pile is a Double   
	 * @return boolean true if double, false otherwise
	 */
	
	public boolean checkDouble(){
		Rule tempRule = rules.get("double");
		if (tempRule.getNum() == 2 && deck.size()>1 ){ //null pointer?
			//Log.d("Null pointer","deck size"+ Integer.toString(deck.size()));
			return deck.get(deck.size()-1).getRank() ==deck.get(deck.size()-2).getRank();
			
		}
		return false;
	} 
	
	/***
	 * Checks all discardPile for all default slap rules
	 * @return boolean true if deck is slappable false if otherwise
	 */
	private boolean checkAllSlapRules(){
		//separate list for slap rules when more options are available
		return checkDouble() || checkSandwich();
	}
	
	/***
	 * Checks all discardPile for all default slap rules
	 * @return boolean true if deck is slappable false if otherwise
	 */
	public boolean checkSlappable(){
		//should be called after every turn. 
			slappable = checkAllSlapRules();
		return slappable;
	}
	/**
	 * Checks if a FaceCard is the top Card on the discard pile 
	 * @return true is facecard, false otherwise
	 */
	public boolean checkFaceCard(){
		return (checkAce() ||checkKing()|| checkQueen()|| checkJack());
					
	}
	
	/*
	 * End Rules
	 */

	/*
	 * Deck Methods
	 */
	/**
	 * Returns card in deck at specified index
	 * @param index
	 * @return Card card at index
	 */
	public Card get(int index){
		return deck.get(index);
	}
	
	/***
	 * Returns if discardPile is empty
	 * @return boolean true if deck is empty, false otherwise
	 */
	public boolean isEmpty(){
		return deck.isEmpty();
	}
	
	/***
	 *	Shuffles the discard pile. 
	 */
	public void shuffle(){
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));
	}
	/***
	 * Method to add the discard pile to a player's hand, 
	 * and clears the discardPile  
	 * @param Player player
	 */
	public void addPileToHand(Player player){
		//add current discard to players hand
		player.addCard(deck);
		deck = new ArrayList<Card>();
	}
	
	/***
	 * Method to add the discard pile to a player's hand, 
	 * and clears the discardPile  
	 * @param Int player
	 */
	public void addPileToHand(int player){
		//add current discard to players hand
		egyptianratscrew.game.GameInfo.game.Players.get(player).addCard(deck);
		deck = new ArrayList<Card>();

				
	}
	
	/***
	 * Creates the discardPile, called when the came is started.
	 * @param Context myContext
	 * @param int screenW
	 */
	public void fillDeck(Context myContext, int screenW) {
		int scaledCardW =0;
		int scaledCardH =0;
		for (int k=0; k<numDecks; k++){
			for (int i = 0; i < 4; i++) {
				for (int j = 102; j < 115; j++) {
					int tempId = j + (i * 100);
					Card tempCard = new Card(tempId);
					int resourceId = myContext.getResources().getIdentifier("card" + tempId,
							"drawable", myContext.getPackageName());
					Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(), resourceId);
					scaledCardW = (int) (screenW / 3);
					scaledCardH = (int) (scaledCardW *1.28); 
					Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);
					tempCard.setBitmap(scaledBitmap);
					deck.add(tempCard);
				}	
			}
		}
	}
	
	/*
	 * End Deck Methods
	 */
	
	/*
	 * Start Draw Methods
	 */
	/***
	 * Method to draw the discardPile
	 * @param canvas
	 * @param screenW
	 * @param screenH
	 * @param scaledCardW
	 * @param scaledCardH
	 * @param scale
	 * @param cardBack
	 */
	public void drawDiscardPile(Canvas canvas, int screenW,int screenH, int scaledCardW, int scaledCardH, float scale,Bitmap cardBack){
		//should draw atleast 3 cards
		//posibly down Cards as well
		//should draw last card of discard pile on top.
	
		if (!deck.isEmpty())
		{ //5 cards will be displayed on the screen 
			int j =0;
			for(int i = deck.size()-egyptianratscrew.game.GameInfo.game.cardsDrawn; i <deck.size(); i++ ){
				try {
					canvas.drawBitmap(deck.get(i).getBitmap(),
					(screenW/2)- (scaledCardW/2) + j*scaledCardW/5,
					(screenH/2)-(cardBack.getHeight()/2),
					null);
					j++;
					
				}
				catch(Exception e) {}
				activeArea[0] = (int) ((screenW/2)- (scaledCardW/2) + 0*scaledCardW/5);
				activeArea[1] = (int) ((screenH/2)-(cardBack.getHeight()/2));
				activeArea[2] = (int) ((screenW/2)- (scaledCardW/2) + j*scaledCardW/5+ scaledCardW);
				activeArea[3] = activeArea[1] + scaledCardH;
			}
		}
	}
	
	/*
	 *	End Draw Methods 
	 */
	
	
}
