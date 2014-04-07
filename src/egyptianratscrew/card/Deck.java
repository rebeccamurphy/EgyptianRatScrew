package egyptianratscrew.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
	
	
	
	public void drawCard(List<Card> handToDraw){
		handToDraw.add(deck.get(0));
		deck.remove(0);
	}
	
	public void drawCard(Player player){
		player.addCard(deck.get(0));
		deck.remove(0);
	}
	
	
	public void drawDownCards(){
		
	}
	public void drawPlayerDeck(){
		//draws down cards
	}
	
	
	
	
}
