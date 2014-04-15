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
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	
	private int screenW =1;
	private int screenH =1;
	private int scaledCardW;
	private int scaledCardH;
	private float scale;
	private Paint blackPaint;
	private Paint redPaint;
	private Bitmap cardBack;
	
	private GameThread gameThread;
	

    public GameView(Context context, AttributeSet attrs) {

        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        
        gameThread = new GameThread(holder, context,this, new Handler() {
            @Override
            public void handleMessage(Message m) {

            }
        });
 
        redPaint = new Paint();
        redPaint.setColor(Color.RED);
       
        scale = context.getResources().getDisplayMetrics().density;
		screenH = context.getResources().getDisplayMetrics().heightPixels;
		screenW = context.getResources().getDisplayMetrics().widthPixels;

		scaledCardW = (int) (screenW /3);
		scaledCardH = (int) (scaledCardW*1.28);
		Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.card_back);
		cardBack = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);

		blackPaint = new Paint();
		blackPaint.setAntiAlias(true);
		blackPaint.setColor(Color.BLACK);
		blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		blackPaint.setTextAlign(Paint.Align.LEFT);
		blackPaint.setTextSize(scale*15);

        //egyptianratscrew.game.GameInfo.game.start(context);
		

        setFocusable(true);

    }

 

    @Override

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    	gameThread.setSurfaceSize(width, height, screenW, screenH);
    }

 

    @Override

    public void surfaceCreated(SurfaceHolder holder) {

        gameThread.setRunning(true);
        if (gameThread.getState() == Thread.State.NEW){
        gameThread.start();
        }
    }

 

    @Override

    public void surfaceDestroyed(SurfaceHolder holder) {
    	gameThread.setRunning(false);
       /* boolean retry = true;

        while (retry) {

            try {

                gameThread.join();

                retry = false;

            } catch (InterruptedException e) {

                // try again shutting down the thread

            }

        }*/

    }

 

    @Override

    public boolean onTouchEvent(MotionEvent event) {

        //return super.onTouchEvent(event);
    	return gameThread.doTouchEvent(event);

    }

 


    public void draw(Canvas canvas) {
    	try{
    		canvas.drawColor(Color.WHITE); //clears screen
    		//canvas.drawCircle(gameThread.x, gameThread.y, 30, redPaint);
    		for (int i =1; i<= gameThread.game.Players.size(); i++)
    		{
    			//draws player decks
    			try{
    				
    			gameThread.game.Players.get(i).getHand()
    			.drawPlayerDeck(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackPaint, i);
    			;}

    			catch(Exception e){
    				Log.d("Player Not Drawn", Integer.toString(i));
    			}
    		}

    		//gameThread.game.getDiscardPile().drawDiscardPile(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackPaint, gameThread.game);
    		if (gameThread.game.Players.get(gameThread.game.turn).drawn == false) {
    			gameThread.game.getDiscardPile().drawDiscardPile(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackPaint, gameThread.game);
    			gameThread.game.Players.get(gameThread.game.turn).drawn = true;
    			//if (gameThread.game.faceCard == null)
    				GameThread.game.nextTurn();
    			/*else if (gameThread.game.chances ==0){
    				//player didnt get a face card or slap in chances
    					gameThread.game.faceCard = null;
    					gameThread.game.chances = 0;
    					if (gameThread.game.turn == 1 )//it was player 2's face card
    						gameThread.game.discardPile.addPiletoHand(gameThread.game.Players.get(2));					
    					else
    						gameThread.game.discardPile.addPiletoHand(gameThread.game.Players.get(1));
    					gameThread.game.getDiscardPile().drawDiscardPile(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackPaint, gameThread.game);
    					gameThread.game.nextTurn();

    			}*/	
    		}	

    		
    		
    		gameThread.game.updateScores();

    		canvas.drawText(
    				"Computer Score: " + Integer.toString(gameThread.game.Players.get(1).getScore()) , 
    				10, 
    				blackPaint.getTextSize()+10,
    				blackPaint);
    		canvas.drawText(
    				"My Score: " + Integer.toString(gameThread.game.Players.get(2).getScore()) , 
    				10, 
    				screenH - blackPaint.getTextSize(),
    				blackPaint);
    		
    		//gameThread.game.nextTurn();
    	}
    	catch (Exception e){
    		
    	}
      
    }

}


	
	

