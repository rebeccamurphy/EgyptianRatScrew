package egyptianratscrew.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import egyptianratscrew.game.Rule;
import egyptianratscrew.player.Player;

public class Deck {

	//either deck or card will have active area 
	protected List<Card> deck;
	protected List<Card> downCards;
	private int numDecks;
	//private HashMap<String, Boolean> Rules;
	
	public Deck() {
		deck= new ArrayList<Card>();	
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
	
	
	public void drawCard(List<Card> handToDraw){
		handToDraw.add(deck.get(0));
		deck.remove(0);
	}
	
	public void drawCard(Player player){
		player.addCard(deck.get(0));
		deck.remove(0);
	}
	
	
	public void drawPlayerDeck(Canvas canvas, int screenW,int screenH, int scaledCardW, int scaledCardH, float scale,Bitmap cardBack, Paint paint, int p){
		//draws down cards
		
		
		
		
		for (int i = 0; i < 3; i++) 
		{ 
			float X =0, Y=0;
			switch(p){
			case 1: 
				X = screenW/2 + i*(5) - (scaledCardW/2); 
				Y = screenH-scaledCardH-paint.getTextSize()-(10*scale); 
				break;
			case 2: 
				X = screenW/2- i*5 - (scaledCardW/2); 
				Y = paint.getTextSize()+(10*scale); 
				break;
			case 3: break;
			case 4: break;
		}
			if  (deck.size() > 3 || deck.size() < i)
			{	
			canvas.drawBitmap(cardBack, 
					X, 
					Y, 
					null);
		
			}
			
		}
	}
	
	
	
	
}