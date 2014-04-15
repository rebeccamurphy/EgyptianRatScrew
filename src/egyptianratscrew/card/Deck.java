package egyptianratscrew.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import egyptianratscrew.game.Rule;
import egyptianratscrew.player.Player;

public class Deck {

	//either deck or card will have active area 
	protected List<Card> deck;
	protected List<Card> downCards;
	protected int[] activeArea; //topleftX, topleftY, toprightX,toprightX
	protected int[] activeAreaBot;
	private int numDecks;
	public boolean drawn = false;
	//private HashMap<String, Boolean> Rules;
	
	public Deck() {
		deck= new ArrayList<Card>();
		downCards = new ArrayList<Card>();
		activeArea = new int[4];
		activeAreaBot = new int[4];
	}
	
	public void add(Card card){
		deck.add(card);
	}
	public void addAll(List<Card> cards){
		deck.addAll(cards);
	}
	
	public Card remove(int index){
		return deck.remove(index);
	}
	public int size(){
		return deck.size();
	}
	
	public List<Card> getDeck(){
		return deck;
	}
	
	public void drawCard(List<Card> handToDraw){
		handToDraw.add(deck.get(0));
		deck.remove(0);
	}
	
	public void drawCard(Player player){
		player.addCard(deck.get(0));
		deck.remove(0);
	}
	
	public boolean checkActiveArea(int X, int Y){
		//Log.d("Touch Active Area top", "X: " +Integer.toString(activeArea[0])+ " " +Integer.toString(activeArea[1]));
		//Log.d("Touch Active Area bot", "X: " +Integer.toString(activeArea[2])+ " " +Integer.toString(activeArea[3]));
		if (activeArea !=null)
			return ((X> activeArea[0] &&  X< activeArea[2]) &&(Y > activeArea[1] && Y< activeArea[3]));
		return false;
	}
	public void disableActiveArea(){
		activeArea = new int[4];
	}
	public void drawPlayerDeck(Canvas canvas, int screenW,int screenH, int scaledCardW, int scaledCardH, float scale,Bitmap cardBack, Paint paint, int p){
		//draws down cards
		
		for (int i = 0; i < 3; i++) 
		{ 
			float X =0, Y=0;
			switch(p){
			case 2: 
				X = screenW/2 + i*(5) - (scaledCardW/2);
				Y = screenH-scaledCardH-paint.getTextSize()-(10*scale);
				//Log.d("Player2 XY", Integer.toString((int)X)+ " " +Integer.toString((int)Y));
				activeArea[0] = (int) (screenW/2 + 0*(5) - (scaledCardW/2));
				activeArea[1] = (int) (screenH-scaledCardH-paint.getTextSize()-(10*scale));
				activeArea[2] = (int) (screenW/2 + 3*(5) - (scaledCardW/2) + scaledCardW);
				activeArea[3] = activeArea[1] + scaledCardH;
				
				
		
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
