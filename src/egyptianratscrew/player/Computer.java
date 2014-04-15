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
		if (egyptianratscrew.game.GameInfo.game.turn == 1 && egyptianratscrew.game.GameInfo.game.Players.get(2).drawn){
			egyptianratscrew.game.GameInfo.game.Players.get(1).drawn = false;
		Log.d("Time", "before");
		Log.i("Discard pile Before comp", Integer.toString(egyptianratscrew.game.GameInfo.game.discardPile.upCards.size()));
		//while (game.discardPile.checkSlappable()){
		//this wont work if it is ot already the computers turn. there needs to be a check in makeMove if the player is the computer
		//that it will start a slapTryThread. maybe put a check on discardPile.size !=1 in there? TODO
		/*
		if (egyptianratscrew.game.GameThread.game.discardPile.checkSlappable()){
			Log.d("Time", "after");
				slapTry(egyptianratscrew.game.GameThread.game, secDelay);
				makeMove(egyptianratscrew.game.GameThread.game);
		}		
		else*/ makeMove(egyptianratscrew.game.GameInfo.game);
			
		drawGame();
		
		Log.d("Time", "after");
		}
		}
	}
	public void makeMove(Game game){
		
		try {
			sleep(2000);
		    game.makePlay(1);
		    Log.i("Computer", "made play");
		    
			}
		    
		 catch(Exception ex) {
		    Thread.currentThread().interrupt();
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
