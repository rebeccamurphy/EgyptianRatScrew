package egyptianratscrew.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import egyptianratscrew.card.Card;
import egyptianratscrew.card.Deck;
import egyptianratscrew.card.DiscardPile;
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
}


