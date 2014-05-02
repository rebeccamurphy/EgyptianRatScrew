package egyptianratscrew.player;

import egyptianratscrew.activity.R;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;

public class Computer extends Thread {
	public boolean running = false;
	public boolean slap = false;
	public int secDelay;
	public int moveDelay;
	public boolean makingMove = false;
	public Handler handler;
	
	private AudioManager audioManager;
	private float volume;
	private static SoundPool sounds;
	private static int cardSound;
	private static int compSlapSound;
	/***
	 * Constructor for Computer Player 
	 * @param secDelay
	 * @param handler
	 */
	public Computer(Context context, int secDelay, int moveDelay, Handler handler) {
		this.secDelay = secDelay;
		this.makingMove = false;
		this.running = false;
		this.handler = handler;
		this.moveDelay = moveDelay;
		sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		cardSound = sounds.load(context, R.raw.cardsound, 1);
		compSlapSound = sounds.load(context, R.raw.compslapsound, 1);
		audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
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
			volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC); 
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
			Thread.sleep(moveDelay);
			playCardSound(volume);
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
			compSlapSound(volume);
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
			Thread.sleep(moveDelay);
			compSlapSound(volume);
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

	public void playCardSound(float volume){
		if (egyptianratscrew.game.GameInfo.game.sound){
			sounds.play(cardSound, volume, volume, 1, 0, 1);
		}
	}
	public void compSlapSound(float volume){
		if (egyptianratscrew.game.GameInfo.game.sound){
			sounds.play(compSlapSound, volume, volume, 1, 0, 1);
		}
	}
}
