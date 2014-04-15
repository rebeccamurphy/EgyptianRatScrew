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
	private Context myContext;
	private SurfaceHolder mySurfaceHolder;
	private int screenW =1;
	private int screenH =1;
	private GameThread gameThread;
	private Paint redPaint;

 

    public GameView(Context context, AttributeSet attrs) {

        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        
        gameThread = new GameThread(holder, context, this, 
        		new Handler(){
        		@Override
        		public void handleMessage(Message m){
        		}
        }); 
 
        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        // create the game loop thread

        

 

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
    		canvas.drawColor(Color.BLACK);
    		canvas.drawCircle(gameThread.x, gameThread.y, 30, redPaint);
    	}
    	catch (Exception e){
    		
    	}
      
    }

}


	
	

