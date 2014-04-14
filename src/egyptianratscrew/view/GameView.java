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
import egyptianratscrew.game.GameThread;
import egyptianratscrew.player.Player;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {


	private int screenW;
	private int screenH;

	private Context myContext;
	private int scaledCardW;
	private int scaledCardH;
	Canvas canvas = new Canvas();
	private GameThread gameThread;
	
	private float scale;
	private Paint blackpaint;
	private Bitmap cardBack;

	public GameView(Context context) {
		super(context);
		myContext = context;
		
		getHolder().addCallback(this);
		gameThread = new GameThread(getHolder(), this, myContext);
		setFocusable(true);
		
		
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
		
		
		//Log.d("Turn " , Integer.toString(game.turn));
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

	//@Override
	public void Draw(Canvas canvas) {
		
		{
			canvas.drawColor(Color.WHITE);
		Log.d("onDraw", "being called.");

			
		for (int i =1; i<= gameThread.game.Players.size(); i++)
		{
			//draws player decks
			try{
			gameThread.game.Players.get(i).getHand()
			.drawPlayerDeck(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackpaint, i);
			;}
			
			catch(Exception e){}
		}
		
		gameThread.game.getDiscardPile().drawDiscardPile(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackpaint, gameThread.game);
		gameThread.game.updateScores();
		
				}
			
			
		
		canvas.drawText(
				"Computer Score: " + Integer.toString(gameThread.game.Players.get(1).getScore()) , 
				10, 
				blackpaint.getTextSize()+10,
				blackpaint);
		canvas.drawText(
				"My Score: " + Integer.toString(gameThread.game.Players.get(2).getScore()) , 
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
			hitDiscard = gameThread.game.discardPile.checkActiveArea(X, Y);
			hitPlayerPile = gameThread.game.Players.get(gameThread.game.turn).getHand().checkActiveArea(X, Y);
			Log.d("Touch", "Touchevent is happening. X: " + Integer.toString(X) + "Y: " + Integer.toString(Y) );
			Log.d("Touch Discard Pile", Boolean.toString(hitDiscard));
			if (hitDiscard && gameThread.game.discardPile.checkSlappable())//&& hit
				{  
				gameThread.playerSlapped();
				//invalidate();
				return true;
				}	//player2 (human) gets discard pile if the pile is			
				//if not slappable, slap method will make toast not valid
			Log.d("HitplayerPile:", Boolean.toString(hitPlayerPile));
			//Log.d("TouchDisabled:", Boolean.toString(game.touchDisabled));
			 if (hitPlayerPile)  //&& some thing to check turn and face card stuff) 
			{
				gameThread.playerMoved();
				return true;
			}
			 else 
				Log.d("turn", "not yours"); //not Your turn!
			Log.d("Here", "should be right after player card");
			return true;
			 
		
		case MotionEvent.ACTION_UP:
			
			 
			//maybe put in an ontouchevent action up on the else? 
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		}
		
		return true;
	}



	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w,int h) {
	
		screenW = w;
		screenH = h;
		scaledCardW = (int) (screenW /3);
		scaledCardH = (int) (scaledCardW*1.28);
		Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.card_back);
		cardBack = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);

		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		gameThread.setRunning(true);
		gameThread.start();
		setWillNotDraw(false);
		Draw(canvas);
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
               try {
                     gameThread.join();
                     retry = false;
               } catch (InterruptedException e) {
               }
        }
 }

	
	
}
