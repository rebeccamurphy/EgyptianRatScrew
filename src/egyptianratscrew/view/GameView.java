package egyptianratscrew.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import egyptianratscrew.activity.R;
import egyptianratscrew.card.Card;
import egyptianratscrew.game.Game;


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
	private Game game;
	private int scaledCardW;
	private int scaledCardH;
	
	private float scale;
	private Paint blackpaint;

	public GameView(Context context) {
		super(context);
		myContext = context;
		game = new Game(); //updated when options are available
		
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
		scaledCardW = (int) (screenW /3);
		scaledCardH = (int) (scaledCardW*1.28);
		game.gameStart(myContext, screenW);
		
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
			if (myHand.size() > 3)
			{
				canvas.drawBitmap(
						cardBack, 
						screenW/2 + i*(5) - (scaledCardW/2),
						screenH-scaledCardH-blackpaint.getTextSize()-(10*scale),
						null);
			}
			else if (myHand.size() < i) {
				canvas.drawBitmap(
						cardBack, 
						screenW/2 + i*(5) - (scaledCardW/2),
						screenH-scaledCardH-blackpaint.getTextSize()-(10*scale),
						null);
			}
		}
		
		for (int i = 0; i < 3; i++) 
		{ 
			if (oppHand.size() > 3)
			{	
			canvas.drawBitmap(cardBack, 
					screenW/2- i*5 - (scaledCardW/2), 
					blackpaint.getTextSize()+(10*scale), 
					null);
		
			}
			else if (oppHand.size() < i) {
				canvas.drawBitmap(
						cardBack, 
						screenW/2 + i*(5) - (scaledCardW/2),
						screenH-scaledCardH-blackpaint.getTextSize()-(10*scale),
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

	
	
	
	
}
