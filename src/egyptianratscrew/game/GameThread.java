package egyptianratscrew.game;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import egyptianratscrew.activity.R;
import egyptianratscrew.player.Computer;
import egyptianratscrew.view.GameView;


public class GameThread extends Thread {
private GameView view;
private boolean running = false;
public SurfaceHolder surfaceHolder;
public Context context;
public Handler handler;
public float x;
public float y;
int move = 0;
int slap =0;
public boolean firstTurn = true;
public boolean nextTurn = false;
//public static Game egyptianratscrew.game.GameInfo.game;
private Computer computer;

public GameThread(SurfaceHolder surfaceHolder, Context context,GameView view, Handler handler ) {
    this.surfaceHolder = surfaceHolder;  
	this.context = context;
	this.view = view;
	this.x =50;
	this.y =50;
	//egyptianratscrew.game.GameInfo.game = new Game(); //add options to constructor later
	//egyptianratscrew.game.GameInfo.game.start(context);
	computer = new Computer(egyptianratscrew.game.GameInfo.game.secDelay, view);
}

public void setRunning(boolean run) {
      running = run;
      computer.setRunning(run);
      //computer.start();
      
}
public void setSurfaceSize(int width, int height, int screenW, int screenH){
	synchronized(this.surfaceHolder){
		screenW = width;
		screenH = height;
	}
}

@Override
public void run() {
      while (running) {
    	  drawGame();
    	  /*if (firstTurn){
    		  Log.d("First turn", "drawn");
    		  drawGame(0);
    		  firstTurn = false;
    	  }
    	  	
    	  else if (egyptianratscrew.game.GameInfo.game.turn ==2  && move ==2)
            {
            	Log.d("What", "Where");
            	move =0;
            	egyptianratscrew.game.GameInfo.game.Players.get(2).drawn = false;
            	drawGame(2);
            	egyptianratscrew.game.GameInfo.game.nextTurn();
            }
    	  */
            /*
            else if (egyptianratscrew.game.GameInfo.game.turn ==2 && nextTurn == true){
            	Log.d("What", "Why");
            	egyptianratscrew.game.GameInfo.game.nextTurn();
            	nextTurn = false;
            }*/
             
      }
}

public void drawGame(int turn){
	Canvas c = null;
    try {
           c = view.getHolder().lockCanvas();
           synchronized (view.getHolder()) {
           	
           	//view.draw(c, turn);
                 // view.onDraw(c);
           }
    } finally {
           if (c != null) {
                  view.getHolder().unlockCanvasAndPost(c);
           }
    }
}
public void drawGame(){
	Canvas c = null;
    try {
           c = view.getHolder().lockCanvas();
           synchronized (view.getHolder()) {
           	
           	view.draw(c);
                 // view.onDraw(c);
           }
    } finally {
           if (c != null) {
                  view.getHolder().unlockCanvasAndPost(c);
           }
    }
}

public boolean doTouchEvent(MotionEvent event){
	synchronized(surfaceHolder){
		int eventaction = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();
		boolean hitDiscard, hitPlayerPile  = false;
		
		switch (eventaction){
		case MotionEvent.ACTION_DOWN:
			Log.d("Touch", "true");
			hitDiscard = egyptianratscrew.game.GameInfo.game.discardPile.checkActiveArea(X, Y);
			hitPlayerPile = egyptianratscrew.game.GameInfo.game.Players.get(egyptianratscrew.game.GameInfo.game.turn).getHand().checkActiveArea(X, Y);
			if (hitPlayerPile && egyptianratscrew.game.GameInfo.game.turn == 2 &&egyptianratscrew.game.GameInfo.game.Players.get(2).drawn && move==0){
				//egyptianratscrew.game.GameInfo.game.Players.get(egyptianratscrew.game.GameInfo.game.turn).getHand().disableActiveArea();
				egyptianratscrew.game.GameInfo.game.makePlay(egyptianratscrew.game.GameInfo.game.turn);
				egyptianratscrew.game.GameInfo.game.Players.get(egyptianratscrew.game.GameInfo.game.turn).drawn = false;
				move = 2;
				Log.d("Touch", "move true");
				//drawGame();
			}
			else 
				Log.d("Turn", "not yous");
			x = X;
			y = Y;
			break;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		}
	}
	return true;
}
}
