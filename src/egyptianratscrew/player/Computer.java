package egyptianratscrew.player;

import java.util.concurrent.TimeUnit;

import android.graphics.Canvas;
import android.util.Log;
import android.widget.Toast;

import egyptianratscrew.game.Game;
import egyptianratscrew.view.GameView;

public class Computer extends Thread{
	public boolean running = false;
	public int secDelay;
	private GameView view;
	public boolean makingMove = false;
	public Toast grabPileToast;
	public Toast slapToast;
	
	public Computer(int secDelay, GameView view) {
		this.secDelay = secDelay;
		this.view = view;
		this.makingMove = false;
		this.running = false;
		
	}
	
	public void setRunning(boolean running){
		this.running = running;
	}
	
	@Override
	public void run(){
		while(running){
			
		//TODO computer not slapping on its turn. is computer slapping at all? need to test 
			try {if (egyptianratscrew.game.GameInfo.game.discardPile.checkSlappable()){
				running = false;
				slapTry();
				
			}
			}
			catch(Exception e){
				Log.d("CheckSlappable", "index out of bounds");
			}
			
			if (egyptianratscrew.game.GameInfo.game.computerGetsPile){
				running = false;
		    	//egyptianratscrew.game.GameInfo.game.computerGetsPile = false;
		    	Log.d("Computer", "gets pile");
		    	takePile();
		    	
		    }
				
			else if (egyptianratscrew.game.GameInfo.game.turn == 1 && makingMove ==false){
				if (egyptianratscrew.game.GameInfo.game.discardPile.checkSlappable()){
					//second check put in before move made. 
					running = false;
					slapTry();
				}
				else {
					running = false;	
					Log.d("Time", "before");
					Log.i("Discard pile Before comp", Integer.toString(egyptianratscrew.game.GameInfo.game.discardPile.size()));
					makeMove();
					Log.d("Time", "after");
				}
			}
		}
	}
	public void makeMove(){
		
		try {
			running = false; 
			makingMove = true;
			
			Thread.sleep(2000);
			egyptianratscrew.game.GameInfo.game.makePlay(1);
		
			
		    Log.i("Computer", "made play");
		   
		    makingMove = false;
			running = true;
			}
		    
		 catch(Exception ex) {
		    Thread.currentThread().interrupt();
		    Log.d("Interrupt", "with player slap");
		    makingMove = false;
		    running = false;
		 }	
		
	}
	public void slapTry(){

		try {
		makingMove = true;
		Thread.sleep(secDelay);
		egyptianratscrew.game.GameInfo.game.slap(1);
    	//slapToast.show();
    	Log.d("Computer Slap", "Computer slapped pile");
    	makingMove =  false;
    	running = true;
    	
		}
		catch(Exception ex) {
		    Thread.currentThread().interrupt();   
		}

	}
	
	public void takePile(){
		try {
			running = false; 
			makingMove = true;
			Thread.sleep(2000);
			egyptianratscrew.game.GameInfo.game.discardPile.addPileToHand(1);
			//egyptianratscrew.game.GameInfo.game.nextTurn();
			//grabPileToast.show();
		    Log.i("Computer", "got pile");
		    egyptianratscrew.game.GameInfo.game.computerGetsPile = false;
		    egyptianratscrew.game.GameInfo.game.nextTurn();
		    makingMove = false;
			running = true;
			}
		    
		 catch(Exception ex) {
		    Thread.currentThread().interrupt();
		    Log.d("Interrupt", "with player slap");
		    makingMove = false;
		    running = false;
		 }	
	}
}
