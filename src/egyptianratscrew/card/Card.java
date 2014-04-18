package egyptianratscrew.card;

import android.graphics.Bitmap;

public class Card {
	/***
	 * Card Class for Egyptian Rat Screw 
	 */
	private int id;
	private Bitmap bmp;
	private int rank;
	private int suit;
	
	/**
	 *  Card Constructor
	 *  sets id, suit and rank of card
	 * @param newId
	 */
	
	public Card(int newId) {
		id = newId;
		suit = Math.round((id/100) * 100);
		rank = id -suit;
	}
	
	/**
	 * Sets bitmap of card
	 * @param newBitmap
	 */

	public void setBitmap(Bitmap newBitmap){
		bmp = newBitmap;
	}
	
	/**
	 * returns bitmap of card
	 * @return bmp
	 */
	
	public Bitmap getBitmap() {
		return bmp;
	}
	
	/**
	 * Returns id of card
	 * @return int id
	 */
	
	public int getId() {
		return id;
	}
	
	/**
	 * returns suit of card 
	 * @return int suit
	 */ 
	public int getSuit(){
		return suit;
	}
	
	/**
	 * returns rank of card 
	 * @return int rank 
	 */
	public int getRank(){
		return rank;
	}
	/**
	 * Returns specific kind face of Facecard
	 * @return String face : ace, king, queen, jack, or not face card
	 */
	public String getFace(){
		switch (rank)
		{
		case 11:return "jack";
		case 12:return "queen";
		case 13:return "king";
		case 14:return "ace";
		}
		return "Not Face Card";
	}
	
	/**
	 * toString for Card
	 * @return  String Suit of card and rank of card
	 */
	public String toString(){
		return "Suit: " + suit + "Rank: " + rank; 
	}
}
