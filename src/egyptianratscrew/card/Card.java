package egyptianratscrew.card;

import android.graphics.Bitmap;

public class Card {

	private int id;
	private Bitmap bmp;
	
	public Card(int newId) {
		id = newId;
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
	public int getFaceId(){
		int tempid = id;
		while (tempid > 100)
			tempid -= 100;
		return tempid;
	}
	
	public String getFace(){
		switch (getFaceId())
		{
		case 11:return "jack";
		case 12:return "queen";
		case 13:return "king";
		case 14:return "ace";
		}
		return "Not Face Card";
	}
}
