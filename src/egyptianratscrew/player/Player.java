package egyptianratscrew.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import egyptianratscrew.card.Card;
public class Player {
	
	private List<Card> myHand;
	private int score;
	
	public Player() {
		myHand = new ArrayList<Card>();
		score = 0;
	}
	
	public List<Card> getHand() {
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
	
	public int getHandSize(){
		return myHand.size();
	}
}


