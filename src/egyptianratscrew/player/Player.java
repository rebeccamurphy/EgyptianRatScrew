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
	
	public void Computer(Game game, int secDelay) {
		Log.d("Time", "before");
	    
		try {
		    Thread.sleep(3000);
		    Log.d("Time", "after");
		    game.makePlay(this);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
	   
		
		/*if (game.discardPile.checkSlappable()){
			try {
			    
			    TimeUnit.SECONDS.sleep(secDelay);
			    //sleep 5 seconds should be able to be changes in game rules
			} catch (Exception e) {
			    //Handle exception
				Log.d("Wait Exception", "Computer broke");
			}
			game.slap(this);
		}
		else
			game.makePlay(this);
		
		*/}
}


