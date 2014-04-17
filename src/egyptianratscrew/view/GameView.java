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



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
	
	private String winner;
	private GameThread gameThread;
	//private boolean firstTurn = true;

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

         //move to onsurface create
		

        setFocusable(true);

    }

 

    @Override

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    	//used when shifted portrait/landscape
    	gameThread.setSurfaceSize(width, height, screenW, screenH);
    	
    }

 

    @Override

    public void surfaceCreated(SurfaceHolder holder) {
    	egyptianratscrew.game.GameInfo.game = new Game();
        egyptianratscrew.game.GameInfo.game.start(gameThread.context);
        
        gameThread.setRunning(true);
        gameThread.computer.setRunning(true);
        if (gameThread.getState() == Thread.State.NEW){
        	gameThread.start();
        	gameThread.computer.start();	
        }	
    }

 

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    	gameThread.setRunning(false);
    	gameThread.computer.setRunning(false);
    }

 

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	return gameThread.doTouchEvent(event);
    }

    public void gameOver() {
    	Intent createIntent = new Intent(gameThread.context, egyptianratscrew.activity.GameActivity.class);
    	gameThread.context.startActivity(createIntent);
    	
    }


    public void draw(Canvas canvas) {
    	try{
    		
    		canvas.drawColor(Color.WHITE); //clears screen
    		
    		for (int i =1; i<= egyptianratscrew.game.GameInfo.game.Players.size(); i++)
    		{
    			//draws player decks
    			try{
    				
    			egyptianratscrew.game.GameInfo.game.Players.get(i).getHand()
    			.drawPlayerDeck(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackPaint, i);
    			;}

    			catch(Exception e){
    				Log.d("Player Not Drawn", Integer.toString(i));
    			}
    		}

    		egyptianratscrew.game.GameInfo.game.discardPile.drawDiscardPile(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack);
    		egyptianratscrew.game.GameInfo.game.updateScores();

    		canvas.drawText(
    				"Computer Score: " + Integer.toString(egyptianratscrew.game.GameInfo.game.Players.get(1).getScore()) , 
    				10, 
    				blackPaint.getTextSize()+10,
    				blackPaint);
    		canvas.drawText(
    				"My Score: " + Integer.toString(egyptianratscrew.game.GameInfo.game.Players.get(2).getScore()) , 
    				10, 
    				screenH - blackPaint.getTextSize(),
    				blackPaint);
    		canvas.drawText(
    				"Turn " + Integer.toString(egyptianratscrew.game.GameInfo.game.turn) , 
    				10, 
    				screenH/2,
    				blackPaint);

    	}
    	catch (Exception e){
    		
    	}
      
    }
    
}


	
	

