package egyptianratscrew.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import egyptianratscrew.card.Card;
import egyptianratscrew.card.Deck;
public class Player {
	
	private Deck myHand;
	private int score;
	
	public Player() {
		myHand = new Deck();
		score = 0;
	}
	
	public Deck getHand() {
		return myHand;
	}
	
	public void addCard(Card card){
		myHand.add(card);
	}
	
	public void addCard(List<Card> cards){
		myHand.addAll(cards);
		}
	
	public Card playCard(){
		return myHand.remove(0);
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


