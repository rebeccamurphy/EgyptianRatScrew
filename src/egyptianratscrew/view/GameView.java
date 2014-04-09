package egyptianratscrew.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import egyptianratscrew.activity.R;
import egyptianratscrew.card.Card;
import egyptianratscrew.card.DiscardPile;
import egyptianratscrew.game.Game;
import egyptianratscrew.player.Player;



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
	private HashMap<Integer, Player> players;
	private DiscardPile discardPile;
	private int scaledCardW;
	private int scaledCardH;

	
	private float scale;
	private Paint blackpaint;
	private Bitmap cardBack;

	public GameView(Context context) {
		super(context);
		myContext = context;
		game = new Game(); //updated when options are available, with more players and stuff
		
		scale = myContext.getResources().getDisplayMetrics().density;
		screenH = myContext.getResources().getDisplayMetrics().heightPixels;
		screenW = myContext.getResources().getDisplayMetrics().widthPixels;
		
		scaledCardW = (int) (screenW /3);
		scaledCardH = (int) (scaledCardW*1.28);
		Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.card_back);
		cardBack = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);
		
		blackpaint = new Paint();
		blackpaint.setAntiAlias(true);
		blackpaint.setColor(Color.BLACK);
		blackpaint.setStyle(Paint.Style.STROKE);
		blackpaint.setTextAlign(Paint.Align.LEFT);
		blackpaint.setTextSize(scale*15);
		
		game.gameStart(myContext, screenW);
		players = game.getPlayers();
		discardPile = game.getDiscardPile();
		
	}

	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		scaledCardW = (int) (screenW /3);
		scaledCardH = (int) (scaledCardW*1.28);
		Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.card_back);
		cardBack = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);

		//game.gameStart(myContext, screenW);
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		//iterate through players
		for (int i =1; i<= players.size(); i++)
		{
			discardPile.add((players.get("Player"+ Integer.toString(i)).playCard()));
			players.get("Player"+ Integer.toString(i)).getHand()
			.drawPlayerDeck(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackpaint, i);
		}
		
		game.getDiscardPile().drawDiscardPile(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackpaint);

		/*
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
		*/
		
		/*
		if (!discardPile.isEmpty())
		{ 
			canvas.drawBitmap(discardPile.get(0).getBitmap(),
					(screenW/2)- (scaledCardW/2),
					(screenH/2)-(cardBack.getHeight()/2),
					null); 
		} */
	}

	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();
		boolean hit = false;
		switch (eventaction) {
		case MotionEvent.ACTION_DOWN:
			//only for one player for now
			// hit = game.discardPile.checkActiveArea(X, Y);
			if (game.discardPile.checkSlappable())//&& hit 
				//player2 (human) gets discard pile if the pile is s
				game.Players.get(2).addCard(game.discardPile);
			//else if (!game.discardPile.checkSlappable()) //&& hit
				//toast not valid slap
				//possibly pay penalty
			//hit = game.Players.get(2).getHand().checkActiveArea(X, Y);
			//some thing to check turn and ace stuff.  
			
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
