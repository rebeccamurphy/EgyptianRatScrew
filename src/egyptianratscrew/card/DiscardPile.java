package egyptianratscrew.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import egyptianratscrew.game.Rule;
import egyptianratscrew.player.Player;

public class DiscardPile extends Deck{

	private int numDecks;
	private ArrayList<Card> upCards;
	private HashMap<String,Rule> rules;
	private boolean slappable;
	
	public DiscardPile() {
		deck= new ArrayList<Card>();
		numDecks = 1;
		upCards = new ArrayList<Card>();
		slappable = false;
		
		//default rules
		rules = new HashMap<String, Rule>();
		rules.put("ace", new Rule(4, true));
		rules.put("king",new Rule( 3, true));
		rules.put("queen",new Rule( 2, true));
		rules.put("jack", new Rule(1, true));
		
		rules.put("sandwich", new Rule( 3, true));
		rules.put("doubles", new Rule( 2, true));
		
	}
	public DiscardPile(int num) {
		deck= new ArrayList<Card>();
		numDecks = num;
		upCards = new ArrayList<Card>();
		slappable = false;
	}
	//add another constructor with rules when rule options are added
	
	
	/*
	 * Rules 
	 */

	public boolean checkAce(){
		//(1-4)14 ace id
		return deck.get(deck.size()-1).getFaceId() == 14;
	}
	
	public boolean checkKing(){
		//(1-4)13 king id
		return deck.get(deck.size()-1).getFaceId() == 13;
	}
	public boolean checkQueen(){
		//(1-4)13 king id
		return deck.get(deck.size()-1).getFaceId() == 12;
	}
	public boolean checkJack(){
		//(1-4)13 king id
		return deck.get(deck.size()-1).getFaceId() == 11;
	}
	
	public boolean checkSandwich(){
		Rule tempRule = rules.get("sandwich");
		//add more here once different rules are available
		if (tempRule.getNum() == 3)
			return upCards.get(upCards.size()-1).getFaceId() ==upCards.get(upCards.size()-3).getFaceId();
		return false;
	}
	
	public boolean checkDouble(){
		Rule tempRule = rules.get("double");
		if (tempRule.getNum() == 2)
			return upCards.get(upCards.size()-1).getFaceId() ==upCards.get(upCards.size()-2).getFaceId();
		return false;
	} 
	
	public boolean checkAllSlapRules(){
		//separate list for slap rules when more options are available
		return checkDouble() || checkSandwich();
	}
	
	public boolean checkSlappable(){
		//should be called after every turn. 
		slappable = checkAllSlapRules();
		return slappable;
	}
	
	/*
	 * End Rules
	 */
	
	/*
	 * Deck Methods
	 */
	public void addUpCards(){
		deck.get(deck.size()-1);
	}
	public boolean isEmpty(){
		return deck.isEmpty();
	}
	
	public void shuffle(){
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));
	}
	/*
	 * End Deck Methods
	 */
	
	/*
	 * Start Draw Methods
	 */
	public void drawUpCards(){
		
	}
	
	public void drawDiscardPile(Canvas canvas, int screenW,int screenH, int scaledCardW, int scaledCardH, float scale,Bitmap cardBack, Paint paint){
		//should draw atleast 3 cards
		//posibly down Cards as well
		//should draw last card of discard pile
		if (!deck.isEmpty())
		{ //5 cards will be displayed on the screen 
			for(int i = deck.size()-1; i >deck.size()-6; i-- )
			canvas.drawBitmap(deck.get(0).getBitmap(),
					(screenW/2)- (scaledCardW/2),
					(screenH/2)-(cardBack.getHeight()/2),
					null); 
		}
	}
	
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
	
	public void addPiletoHand(Player player){
		//add current discard to players hand
	}

}