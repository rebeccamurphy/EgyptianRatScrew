package egyptianratscrew.card;

import android.graphics.Bitmap;

public class Card {

	private int id;
	private Bitmap bmp;
	private int rank;
	private int suit;
	public Card(int newId) {
		id = newId;
		suit = Math.round((id/100) * 100);
		rank = id -suit;
	}
	
	public void setBitmap(Bitmap newBitmap){
		bmp = newBitmap;
	}
	
	public Bitmap getBitmap() {
		return bmp;
	}
	
	public int getId() {
		return id;
	}
	public int getSuit(){
		return suit;
	}
	
	public int getRank(){
		return rank;
	}
	
	public String getFace(){
		switch (getRank())
		{
		case 11:return "jack";
		case 12:return "queen";
		case 13:return "king";
		case 14:return "ace";
		}
		return "Not Face Card";
	}
	public String toString(){
		return "Suit: " + suit + "Rank: " + rank; 
	}
}
