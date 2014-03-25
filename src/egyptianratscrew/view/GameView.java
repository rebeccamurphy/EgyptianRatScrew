package egyptianratscrew.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import egyptianratscrew.activity.R;
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
	private Paint blackpaint;
	
	private int oppScore;
	private int myScore;
	
	private Bitmap cardBack;

	public GameView(Context context) {
		super(context);
		myContext = context;
		scale = myContext.getResources().getDisplayMetrics().density;
		blackpaint = new Paint();
		blackpaint.setAntiAlias(true);
		blackpaint.setColor(Color.BLACK);
		blackpaint.setStyle(Paint.Style.STROKE);
		blackpaint.setTextAlign(Paint.Align.LEFT);
		blackpaint.setTextSize(scale*15);
		
	}

	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		initCards();
		dealCards();
		myScore = myHand.size();
		oppScore = oppHand.size();
		Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.card_back);
		scaledCardW = (int) (screenW /3);
		scaledCardH = (int) (scaledCardW*1.28);
		cardBack = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawText(
				"Computer Score: " + Integer.toString(oppScore) , 
				10, 
				blackpaint.getTextSize()+10,
				blackpaint);
		canvas.drawText(
				"My Score: " + Integer.toString(myScore) , 
				10, 
				screenH - blackpaint.getTextSize(),
				blackpaint);

		for (int i =0; i < 3; i++)
		{
			if (myHand.size() > 1)
			{
				canvas.drawBitmap(
						cardBack, 
						screenW/2 + i*(5) - (scaledCardW/2),
						screenH-scaledCardH-blackpaint.getTextSize()-(10*scale),
						null);
			}
		}
		
		for (int i = 0; i < 3; i++) 
		{ 
			if (oppHand.size() > 1)
			{	
			canvas.drawBitmap(cardBack, 
					screenW/2- i*5 - (scaledCardW/2), 
					blackpaint.getTextSize()+(10*scale), 
					null);
		
			}
		}
	
		
		
		if (!discardPile.isEmpty())
		{ 
			canvas.drawBitmap(discardPile.get(0).getBitmap(),
					(screenW/2)- (scaledCardW/2),
					(screenH/2)-(cardBack.getHeight()/2),
					null); 
		} 
	}

	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();

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

	
	private void initCards() {
		for (int i = 0; i < 4; i++) {
			for (int j = 102; j < 115; j++) {
				int tempId = j + (i * 100);
				Card tempCard = new Card(tempId);
				int resourceId = getResources().getIdentifier("card" + tempId,
						"drawable", myContext.getPackageName());
				Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(), resourceId);
				scaledCardW = (int) (screenW / 3);
				scaledCardH = (int) (scaledCardW *1.28); 
				Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);
				tempCard.setBitmap(scaledBitmap);
				deck.add(tempCard);
			}
			
		}
	}
	
	private void drawCard(List<Card> handToDraw){
		handToDraw.add(deck.get(0));
		deck.remove(0);
	}
	
	private void discard (List<Card> handToDiscard){
		discardPile.add(handToDiscard.get(0));
		handToDiscard.remove(0);
	}
	
	
	private void dealCards(){
		Collections.shuffle(deck, new Random());
		while (!deck.isEmpty())
		{
			drawCard(myHand);
			drawCard(oppHand);
		}
		
		discard(oppHand);
	}
}
