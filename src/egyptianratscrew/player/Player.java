package egyptianratscrew.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.util.Log;

import egyptianratscrew.card.Card;
import egyptianratscrew.card.Deck;
import egyptianratscrew.card.DiscardPile;
import egyptianratscrew.game.Game;
public class Player {
	
	private Deck myHand;
	private int score;
	private int playerId;
	public boolean drawn = true;
	
	public Player(Integer playerID) {
		myHand = new Deck();
		score = 0;
		playerId = playerID;
	}
	
	public Deck getHand() {
		return myHand;
	}
	
	public void addCard(Card card){
		myHand.add(card);
	}
	public void addCard(List<Card> card){
		myHand.addAll(card);
	}
	
	public void addCard(DiscardPile cards){
		myHand.addAll(cards.getDeck());
		}
	
	public Card playCard(){
		return myHand.remove(0);
	}
	
	public Integer getId(){
		return playerId;
	}
	
	public void setScore(){
		score = myHand.size();
	}
	
	public int getScore(){
		return score;
	}
	
	public int getHandSize(){
		return myHand.size();
	}
	public void slapTryThread(Game game, int secDelay){

			try {
			Thread.sleep(secDelay);
	    	game.slap(this);
	    	Log.d("Computer Slap", "Computer slapped pile");
	    	
			}
			catch(Exception ex) {
			    Thread.currentThread().interrupt();
			    
			}
	    
	
	}
	public void makeMoveThread(Game game){
		
			try {
				Thread.sleep(2000);
			    game.makePlay(this);
			    Log.i("Computer", "made play");
			    
				}
			    
			 catch(Exception ex) {
			    Thread.currentThread().interrupt();
			 }	
			
	}
	public void Computer(Game game, int secDelay) {
		Log.d("Time", "before");
		Log.i("Discard pile Before comp", Integer.toString(game.discardPile.upCards.size()));
		//while (game.discardPile.checkSlappable()){
		//this wont work if it is ot already the computers turn. there needs to be a check in makeMove if the player is the computer
		//that it will start a slapTryThread. maybe put a check on discardPile.size !=1 in there? TODO
		/*if (game.discardPile.checkSlappable()){
			Log.d("Time", "after");
				slapTryThread(game, secDelay);
				makeMoveThread(game);
		}		
		else*/ makeMoveThread(game);
		Log.d("Time", "after");
	}

}
