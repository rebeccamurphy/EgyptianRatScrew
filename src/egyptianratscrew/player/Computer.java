package egyptianratscrew.player;

import android.os.Handler;
import android.util.Log;

public class Computer extends Thread {
	public boolean running = false;
	public boolean slap = false;
	public int secDelay;
	public boolean makingMove = false;
	public Handler handler;
	
	/***
	 * Constructor for Computer Player 
	 * @param secDelay
	 * @param handler
	 */
	public Computer(int secDelay, Handler handler) {
		this.secDelay = secDelay;
		this.makingMove = false;
		this.running = false;
		this.handler = handler;
		
	}
	/***
	 * Set computer running
	 * @param boolean running
	 */
	public void setRunning(boolean running){
		this.running = running;
	}
	
	/***
	 * Method when computer is running
	 */
	@Override
	public void run(){
		while(running){
			 
			try {
				if (egyptianratscrew.game.GameInfo.game.discardPile.checkSlappable()){
				//computer tries to slap pile if the pile is slappables
				running = false;
				slapTry();
				}
			}
			catch(Exception e){
				Log.d("CheckSlappable", "index out of bounds");
			}
			
			if (egyptianratscrew.game.GameInfo.game.computerGetsPile &&  running){
				//Gets if the computer gets the discardpile from a faceCard
				running = false;
		    	Log.d("Computer", "gets pile");
		    	takePile();
		    	
		    }
				
			else if (egyptianratscrew.game.GameInfo.game.turn == 1 && makingMove ==false && running){
				if (egyptianratscrew.game.GameInfo.game.discardPile.checkSlappable()){
					//second check put in before move made. 
					running = false;
					slapTry();
				}
				else {
					//computer makes a move
					running = false;	
					Log.d("Time", "before");
					Log.i("Discard pile Before comp", Integer.toString(egyptianratscrew.game.GameInfo.game.discardPile.size()));
					makeMove();
					Log.d("Time", "after");
				}
			}
		}
	}
	
	/***
	 * Computer method to make a move.
	 */
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
	/***
	 * Computer tries to slap the pile
	 */
	public void slapTry(){
		try {
			makingMove = true;
			Thread.sleep(secDelay);
			egyptianratscrew.game.GameInfo.game.slap(1);
			Log.d("Computer Slap", "Computer slapped pile");
			makingMove =  false;
			running = true;
			slap = true;
		}
		catch(Exception ex) {
		    Thread.currentThread().interrupt();   
		}
	}
	/**
	 * Computer takes the pile after chances run out on a face card.
	 */
	public void takePile(){
		try {
			running = false; 
			makingMove = true;
			Thread.sleep(2000);
			egyptianratscrew.game.GameInfo.game.discardPile.addPileToHand(1);
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
