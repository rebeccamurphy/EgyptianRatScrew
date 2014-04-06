package egyptianratscrew.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.res.Resources;

import egyptianratscrew.activity.R;
import egyptianratscrew.card.Card;
import egyptianratscrew.player.Player;

public class Game {

	private List<Card> deck;
	private List<Card> discardPile;
	private HashMap<String, Player> Players;
	private int numDecks;
	private int numPlayers;
	
	
	public Game() {
		deck= new ArrayList<Card>();
		numDecks =1;
		numPlayers =2;
		Players = new HashMap<String, Player>();
		Players.put("Player1", new Player());//computer
		Players.put("Player2", new Player());
	}
	
	public Game(int Decks, int numberPlayers) {
		deck= new ArrayList<Card>();
		numDecks =Decks;
		numPlayers = numberPlayers;
		for (int i=1; i <= numPlayers;i++ ){
			Players.put("Player" + Integer.toString(i), new Player());
		}
	}
	
	private void fillDeck(Context myContext, int screenW) {
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
	
	private void drawCard(List<Card> handToDraw){
		handToDraw.add(deck.get(0));
		deck.remove(0);
	}
	
	private void drawCard(Player player){
		player.addCard(deck.get(0));
		deck.remove(0);
	}
	private void discard (List<Card> handToDiscard){
		discardPile.add(handToDiscard.get(0));
		handToDiscard.remove(0);
	}
	private void discard (Player player){
		discardPile.add(player.playCard());
	}
	
	
	private void dealCards(){
		Collections.shuffle(deck, new Random());
		while (!deck.isEmpty())
		{
			for (int i=1; i<= numPlayers; i++)
				drawCard(Players.get("Player"+Integer.toString(i)));
		}
		
	}
	public void updateScores(){
		for (int i=1; i<= numPlayers; i++){
			String PlayerID = "Player"+Integer.toString(i);
			Players.get(PlayerID).setScore();
		}
			
	}
	public void gameStart(Context myContext, int screenW){
		fillDeck(myContext, screenW);
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));
		dealCards();
		updateScores();
	}
	
}
