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
import android.util.Log;
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
	Canvas canvas = new Canvas();

	
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
		Log.d("Turn " , Integer.toString(game.turn));
		//gameLoop();
		//toast press to start or make start button
		//game.Players.get(1).Computer(game, 5);
		
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
		if (!game.gameOver){
		//iterate through players

		/*TODO
		 * Order is fucked up, player goes, then ondraw sould go, then computer goes
		 * instead player goes, computer goes, ondraw is called*/
		//if (game.touchDisabled)
		
		
		//Log.d("Computer Card", game.discardPile.get(game.discardPile.size()-1).toString());
		//if (game.firstTurn ==true){}
		for (int i =1; i<= game.Players.size(); i++)
		{
			//game.discardPile.add((game.Players.get(i).playCard()));
			try{
			Log.d("start", "Got here");
			game.Players.get(i).getHand()
			.drawPlayerDeck(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackpaint, i);
			Log.d("end", "got here");}
			catch(Exception e){}
		}
		Log.d("Draw Discard","Game turn " + Integer.toString(game.turn) + ", " + Boolean.toString(game.Players.get(game.turn).drawn) );
		if (game.firstTurn == true) //actually not necessary 
		game.getDiscardPile().drawDiscardPile(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackpaint, game);
		else if (game.Players.get(game.turn).drawn == false) {
			game.getDiscardPile().drawDiscardPile(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackpaint, game);
			game.Players.get(game.turn).drawn = true;
			if (game.faceCard != null)
			game.nextTurn();
			Log.d("Here", "discard is being drawn");
			if (game.turn!=1)
			{
				game.touchDisabled = false;
			}
		}
		}	
		game.updateScores();
		
		canvas.drawText(
				"Computer Score: " + Integer.toString(game.Players.get(1).getScore()) , 
				10, 
				blackpaint.getTextSize()+10,
				blackpaint);
		canvas.drawText(
				"My Score: " + Integer.toString(game.Players.get(2).getScore()) , 
				10, 
				screenH - blackpaint.getTextSize(),
				blackpaint);

	}

	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();
		boolean hitDiscard, hitPlayerPile  = false;
		
		switch (eventaction) {
		case MotionEvent.ACTION_DOWN:
			//only for one player for now
			hitDiscard = game.discardPile.checkActiveArea(X, Y);
			hitPlayerPile = game.Players.get(game.turn).getHand().checkActiveArea(X, Y);
			Log.d("Touch", "Touchevent is happening. X: " + Integer.toString(X) + "Y: " + Integer.toString(Y) );
			Log.d("Touch Discard Pile", Boolean.toString(hitDiscard));
			if (hitDiscard && game.discardPile.checkSlappable())//&& hit
				{  
				game.slap(game.Players.get(2));
				invalidate();
				return true;
				}	//player2 (human) gets discard pile if the pile is			
				//if not slappable, slap method will make toast not valid
			Log.d("HitplayerPile:", Boolean.toString(hitPlayerPile));
			Log.d("TouchDisabled:", Boolean.toString(game.touchDisabled));
			 if (hitPlayerPile && game.touchDisabled ==false)  //&& some thing to check turn and face card stuff) 

			{
				game.firstTurn = false;
				game.touchDisabled=true;
				Log.d("Testing Player input","Touch detected. "+ Integer.toString(game.turn));
				game.makePlay(game.turn);
				Log.d("Player Card", game.discardPile.get(game.discardPile.size()-1).toString());
				game.Players.get(game.turn).drawn = false;
				invalidate();
				return true;
			}
			 else 
				Log.d("turn", "not yours"); //not Your turn!
			Log.d("Here", "should be right after player card");
			return true;
			 
			//break;
		case MotionEvent.ACTION_UP:
			Log.d("when is onDrawCalled", Boolean.toString(game.touchDisabled));
			if (game.turn ==1 && game.touchDisabled ==  true && game.Players.get(game.turn).drawn == true){
			
			//do computer move
			//draw discard again draw goes through all things

			game.Players.get(1).Computer(game, 3000);

			game.Players.get(game.turn).drawn = false;
			//game.getDiscardPile().drawDiscardPile(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackpaint);
			
				invalidate();
			//onDraw(canvas);
			return true;
			} 
			//maybe put in an ontouchevent action up on the else? 
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		}
		
		//invalidate();
		return true;
	}

	public void gameLoop(){
		while(!game.gameOver){
			if (game.turn ==1){
				
				//do computer move
				//draw discard again draw goes through all things
				game.touchDisabled = true;
				game.Players.get(1).Computer(game, 3000);
				game.Players.get(game.turn).drawn = false;
				//game.getDiscardPile().drawDiscardPile(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackpaint);
				invalidate();
				//onDraw(canvas);
		}
	}
	}
	
	
}
