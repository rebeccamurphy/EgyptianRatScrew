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
public boolean running = false;
public SurfaceHolder surfaceHolder;
public Context context;
public Handler handler;
public float x;
public float y;
int move = 0;
int slap =0;
public boolean firstTurn = true;
public boolean nextTurn = false;
public Computer computer;

public GameThread(SurfaceHolder surfaceHolder, Context context,GameView view, Handler handler ) {
    this.surfaceHolder = surfaceHolder;  
	this.context = context;
	this.view = view;
	computer = new Computer(egyptianratscrew.game.GameInfo.game.secDelay, view);
}

public void setRunning(boolean run) {
      running = run;
      computer.setRunning(run);
      computer.start();
      
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
			
			if (hitDiscard){
				
				if (egyptianratscrew.game.GameInfo.game.discardPile.checkAllSlapRules()&& computer.makingMove ==true){
					computer.interrupt();
					//computer.makingMove = false;
					//computer.setRunning(false);
					egyptianratscrew.game.GameInfo.game.slap(2);
					Log.d("Computer Status", "restarted");
					computer = new Computer(egyptianratscrew.game.GameInfo.game.secDelay, view);
					computer.setRunning(true);
					computer.start();
				}
				else
				egyptianratscrew.game.GameInfo.game.slap(2);
		
			}
			
			else if (hitPlayerPile && egyptianratscrew.game.GameInfo.game.turn == 2 && !computer.makingMove){
				//Human player makes move
				egyptianratscrew.game.GameInfo.game.makePlay(2);
				egyptianratscrew.game.GameInfo.game.nextTurn();
				Log.d("Touch", "move true");
				Log.d("Computer is running, alive, moving ", Boolean.toString(computer.running) + " " + Boolean.toString(computer.isAlive())
						+ " " +Boolean.toString(computer.makingMove));
			
			}
			else 
				Log.d("Turn", "not yous");
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
