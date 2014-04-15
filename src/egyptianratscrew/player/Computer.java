package egyptianratscrew.player;

import java.util.concurrent.TimeUnit;

import android.graphics.Canvas;
import android.util.Log;

import egyptianratscrew.game.Game;
import egyptianratscrew.view.GameView;

public class Computer extends Thread{
	public boolean running = false;
	private int secDelay;
	private GameView view;
	public boolean makingMove = false;
	public Computer(int secDelay, GameView view) {
		this.secDelay = secDelay;
		this.view = view;
	}
	
	public void setRunning(boolean running){
		this.running = running;
	}
	
	@Override
	public void run(){
		while(running){
			//Log.d("C", "t");
		if (egyptianratscrew.game.GameInfo.game.turn == 1 && makingMove ==false){
		running = false;	
		Log.d("Time", "before");
		Log.i("Discard pile Before comp", Integer.toString(egyptianratscrew.game.GameInfo.game.discardPile.upCards.size()));
		makeMove();
		Log.d("Time", "after");
		}
		}
	}
	public void makeMove(){
		
		try {
			makingMove = true;
			Thread.sleep(3000);
			egyptianratscrew.game.GameInfo.game.makePlay(1);
			egyptianratscrew.game.GameInfo.game.nextTurn();
			makingMove = false;
			running = true;
		    Log.i("Computer", "made play");
		   
			}
		    
		 catch(Exception ex) {
		    Thread.currentThread().interrupt();
		    Log.d("Interrupt", "with player slap");
		    makingMove = false;
		    running = false;
		 }	
		
	}
	public void slapTry(Game game, int secDelay){

		try {
		Thread.sleep(secDelay);
	
    	game.slap(1);
    	Log.d("Computer Slap", "Computer slapped pile");
    	
		}
		catch(Exception ex) {
		    Thread.currentThread().interrupt();   
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

}
