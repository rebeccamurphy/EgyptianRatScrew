package egyptianratscrew.player;


import java.util.List;

import egyptianratscrew.card.Card;
import egyptianratscrew.card.Deck;
import egyptianratscrew.card.DiscardPile;

public class Player {
	
	private Deck myHand;
	private int score;
	private int playerId;
	public boolean drawn = true;
	
	/***
	 * Constructor for player class
	 * @param playerID
	 */
	public Player(Integer playerID) {
		myHand = new Deck();
		score = 0;
		playerId = playerID;
	}
	/**
	 * Returns the Player's deck 
	 * @return myHand
	 */
	public Deck getHand() {
		return myHand;
	}
	/**
	 * Adds a card to the Player's hand
	 * @param Card card
	 */
	public void addCard(Card card){
		myHand.add(card);
	}
	
	/***
	 * Adds a list of cards to the players hand
	 * @param card list
	 */
	
	public void addCard(List<Card> card){
		myHand.addAll(card);
	}
	/***
	 * Adds discardPile to Player Hand
	 * @param cards
	 */
	public void addCard(DiscardPile cards){
		myHand.addAll(cards.getDeck());
		}
	/**
	 * Returns the card at the top of the player deck
	 * @return card
	 */
	public Card playCard(){
		return myHand.remove(0);
	}
	/***
	 * Returns the playerID
	 * @return
	 */
	public Integer getId(){
		return playerId;
	}
	/***
	 * set's the player's score equal to how many cards in their hand
	 */
	public void setScore(){
		score = myHand.size();
	}
	/***
	 * Returns the player's score
	 * @return int score
	 */
	public int getScore(){
		return score;
	}
	
	public int getHandSize(){
		return myHand.size();
	}

	
	
}
