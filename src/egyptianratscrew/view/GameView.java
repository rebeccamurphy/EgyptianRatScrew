package egyptianratscrew.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import egyptianratscrew.card.Card;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;


public class GameView extends View {


	private int screenW;
	private int screenH;

	private Context myContext;
	private int scaledCardW;
	private int scaledCardH;
	
	private List<Card> deck = new ArrayList<Card>();
	private List<Card> myHand = new ArrayList<Card>();
	private List<Card> oppHand = new ArrayList<Card>();
	private List<Card> discardPile = new ArrayList<Card>();
	
	private float scale;
	private Paint whitePaint;
	
	private int oppScore;
	private int myScore;

	public GameView(Context context) {
		super(context);
		myContext = context;
		scale = myContext.getResources().getDisplayMetrics().density;
		whitePaint = new Paint();
		whitePaint.setAntiAlias(true);
		whitePaint.setColor(Color.WHITE);
		whitePaint.setStyle(Paint.Style.STROKE);
		whitePaint.setTextAlign(Paint.Align.LEFT);
		whitePaint.setTextSize(scale*15);
		
	}

	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		initCards();
		dealCards();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawText(
				"Computer Score: " + Integer.toString(oppScore) , 
				10, 
				whitePaint.getTextSize()+10,
				whitePaint);
		canvas.drawText(
				"My Score: " + Integer.toString(myScore) , 
				10, 
				screenH - whitePaint.getTextSize()-10,
				whitePaint);
		for (int i =0; i < myHand.size(); i++)
		{
			if (i<7)
			{
				canvas.drawBitmap(
						myHand.get(i).getBitmap(), 
						i*(scaledCardW+5),
						screenH-scaledCardH-whitePaint.getTextSize()-(50*scale),
						null);
			}
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();

		switch (eventaction) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		}

		invalidate();
		return true;
	}

	// change to private later
	public void initCards() {
		for (int i = 0; i < 4; i++) {
			for (int j = 102; j < 115; j++) {
				int tempId = j + (i * 100);
				Card tempCard = new Card(tempId);
				int resourceId = getResources().getIdentifier("card" + tempId,
						"drawable", myContext.getPackageName());
				Bitmap tempBitmap = BitmapFactory.decodeResource(
						myContext.getResources(), resourceId);
				scaledCardW = (int) (screenW / 8);
				scaledCardH = (int) (screenH / 8);
				Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap,
						scaledCardW, scaledCardH, false);
				tempCard.setBitmap(scaledBitmap);
				deck.add(tempCard);
			}
		}
	}
	
	private void drawCard(List<Card> handToDraw){
		handToDraw.add(0, deck.get(0));
		deck.remove(0);
		if (deck.isEmpty()){
			for (int i = discardPile.size()-1; i>0; i--)
			{
				deck.add(discardPile.get(i));
				discardPile.remove(i);
				Collections.shuffle(deck, new Random());
			}
		}
	}
	
	private void dealCards(){
		Collections.shuffle(deck, new Random());
		for (int i =0; i < 7; i++)
		{
			drawCard(myHand);
			drawCard(oppHand);
		}
	}
}
