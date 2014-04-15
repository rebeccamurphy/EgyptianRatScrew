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
public Game game;

public GameThread(SurfaceHolder surfaceHolder, Context context,GameView view, Handler handler ) {
    this.surfaceHolder = surfaceHolder;  
	this.context = context;
	this.view = view;
	this.x =50;
	this.y =50;
	game = new Game(); //add options to constructor later
	game.start(context);
}

public void setRunning(boolean run) {
      running = run;
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
             if (firstTurn){
            	 drawGame();
            	 firstTurn = false;
             }
             else if (move ==2)
             {
            	 move =0;
            	 drawGame();
            	 //game.nextTurn();
            	 Log.d("Test", "Player made move, Next turn:" + Integer.toString(game.turn));
             }
             else if (game.turn ==1 && game.Players.get(game.turn).drawn ==true)
             {
            	 Log.d("TimeX", "before");
            	 //game.Players.get(1).Computer(game, 2000);
            	 try {
     				Thread.sleep(2000);
     			    game.makePlay(1);
     			    Log.i("Computer", "made play");
     			    
     				}
     			    
     			 catch(Exception ex) {
     			    Thread.currentThread().interrupt();
     			 }	
            	 //game.Players.get(2).getHand().enableActiveArea();
            	 game.Players.get(game.turn).drawn = false;
            	 drawGame();
            	 Log.d("TimeX", "after");
            	 //game.nextTurn();
            	 Log.d("Test", "Computer made move, Next turn:" + Integer.toString(game.turn));
            	 
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
			hitDiscard = game.discardPile.checkActiveArea(X, Y);
			hitPlayerPile = game.Players.get(game.turn).getHand().checkActiveArea(X, Y);
			if (hitPlayerPile && game.turn == 2){
				//game.Players.get(game.turn).getHand().disableActiveArea();
				game.makePlay(game.turn);
				game.Players.get(game.turn).drawn = false;
				move = 2;
				Log.d("Touch", "move true");
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
