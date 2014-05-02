package egyptianratscrew.game;



import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import egyptianratscrew.player.Computer;
import egyptianratscrew.view.GameView;
import egyptianratscrew.activity.R;

@SuppressLint("ShowToast")
public class GameThread extends Thread {
	
private GameView view;
public boolean running = false;
public SurfaceHolder surfaceHolder;
public Context context;
public Handler handler;
public Computer computer;
private Toast notTurnToast;
private Toast grabPileToast;
private Toast computerGrabPileToast;
private Toast slapToast;
private Toast computerSlapToast;
private Toast notSlapToast;
private String winner;
private Bitmap soundIcon;
private static SoundPool sounds;
private static int cardSound; 
private static int slapSound;
private static int compSlapSound;

/***
 * Constructor for GameThread, this thread runs the game. it handles touch input, drawing to the canvas
 * and managing the computer thread.
 * @param surfaceHolder
 * @param context
 * @param view
 * @param handler
 */

public GameThread(SurfaceHolder surfaceHolder, Context context,GameView view, Handler handler, Bitmap soundIcon) {
    this.surfaceHolder = surfaceHolder;  
	this.context = context;
	this.view = view;
	this.handler = handler;
	this.soundIcon = soundIcon;
	computer = new Computer(context, egyptianratscrew.game.GameInfo.game.secDelay,egyptianratscrew.game.GameInfo.game.moveDelay, handler);
	sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
	cardSound = sounds.load(context, R.raw.cardsound, 1);
	slapSound = sounds.load(context, R.raw.slapsound, 1);
	compSlapSound = sounds.load(context, R.raw.compslapsound, 1);

	notTurnToast = Toast.makeText(context, "Not your turn.", Toast.LENGTH_SHORT);
	grabPileToast = Toast.makeText(context, "The pile is yours, tap it to take it.", Toast.LENGTH_SHORT);
	slapToast = Toast.makeText(context, "You got the slap! Now play a card.", Toast.LENGTH_SHORT);
	notSlapToast = Toast.makeText(context, "Not a slap!", Toast.LENGTH_SHORT);
	computerSlapToast = Toast.makeText(context, "Computer got the slap!", Toast.LENGTH_SHORT);
	computerGrabPileToast = Toast.makeText(context, "Computer won the pile.", Toast.LENGTH_SHORT);

}

/***
 * Sets the thread running or not running.
 * @param boolean run
 */
public void setRunning(boolean run) {
      running = run;
}
/***
 * Method when surface is changed
 * @param width
 * @param height
 * @param screenW
 * @param screenH
 */
public void setSurfaceSize(int width, int height, int screenW, int screenH){
	synchronized(this.surfaceHolder){
		screenW = width;
		screenH = height;
	}
}
/***
 * Threading running
 */
@Override
public void run() {
	
      while (running) {
    	  try {
	  		//Thread.sleep(33);	  		
	    	drawGame();// draw game
	    	if (egyptianratscrew.game.GameInfo.game.computerGetsPile&& egyptianratscrew.game.GameInfo.game.hints){
				//Computer is taking pile
				computerGrabPileToast.show();
				}
	    	else if (computer.slap && egyptianratscrew.game.GameInfo.game.discardPile.isEmpty()&& egyptianratscrew.game.GameInfo.game.hints ){
	    		//computer slapped the pile
	    		computerSlapToast.show();
	    		computer.slap = false;
	    	}
	    	egyptianratscrew.game.GameInfo.game.checkGameOver(); //check game over
	    	if (egyptianratscrew.game.GameInfo.game.gameOver&& !egyptianratscrew.game.GameInfo.game.discardPile.checkSlappable()){
	    		//if game over and the discard pile isn't slappable
	        	if (egyptianratscrew.game.GameInfo.game.loser ==1)
	    			winner = "You";
	    		else
	    			winner = "Computer";
	        	computer.setRunning(false);
	        	running = false; 
	    		handler.post(new Runnable() {
	    			//Game Over Dialog
	    		    @Override
	    		    public void run() {
	    		        	AlertDialog.Builder gameOverAlert =new AlertDialog.Builder(context);
						    gameOverAlert.setTitle("Game Over!");
						    gameOverAlert.setMessage(winner +" win!");
						    gameOverAlert.setCancelable(false);
						    gameOverAlert.setPositiveButton("Play again?", new DialogInterface.OnClickListener() {
							    public void onClick(DialogInterface dialog, int whichButton) {
							    	Intent createIntent = new Intent(context, egyptianratscrew.activity.GameActivity.class);
							    	context.startActivity(createIntent);
							    }
						    });
						   gameOverAlert.setNegativeButton("Quit?", new DialogInterface.OnClickListener() {
							   public void onClick(DialogInterface dialog, int whichButton) {
								   Intent createIntent = new Intent(context, egyptianratscrew.activity.MainActivity.class);
								   context.startActivity(createIntent);
							   }
						   });
					gameOverAlert.show();	
	    		    }
	    		});
	    	}
    	  }
    	  catch(Exception ex) {
    		  Thread.currentThread().interrupt();
    		  Log.d("Interrupt", "Draw wait");
    		  running = false;
  		 }	
      }
}

/***
 * Method to call the draw method on the view
 */

public void drawGame(){
	Canvas c = null;
    try {
           c = view.getHolder().lockCanvas(null); //null says redraw whole cnavas 
           synchronized (view.getHolder()) {
           	view.draw(c);
                
           }
    } finally {
           if (c != null) {
                  view.getHolder().unlockCanvasAndPost(c);
           }
    }
}

/***
 * Method that handles touch imput for player moves.
 * @param event
 * @return boolean true
 */
public boolean doTouchEvent(MotionEvent event){
	synchronized(surfaceHolder){
		int eventaction = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();
		boolean hitDiscard, hitPlayerPile  = false;
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		float volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		switch (eventaction){
		case MotionEvent.ACTION_DOWN:
			//check sound button
			hitDiscard = egyptianratscrew.game.GameInfo.game.discardPile.checkActiveArea(X, Y);
			hitPlayerPile = egyptianratscrew.game.GameInfo.game.Players.get(egyptianratscrew.game.GameInfo.game.turn).getHand().checkActiveArea(X, Y);
			if ((X >context.getResources().getDisplayMetrics().widthPixels - soundIcon.getWidth() -10 && X < context.getResources().getDisplayMetrics().widthPixels -10)
					&& (Y < soundIcon.getHeight() && Y >0)){
				Log.d("touch", "soundicon touched");
				egyptianratscrew.game.GameInfo.game.sound = !egyptianratscrew.game.GameInfo.game.sound; 
			}
			
			
			else if (hitDiscard){
				//HIT DISCARD 
				if (egyptianratscrew.game.GameInfo.game.playerGetsPile){
					//Human takes pile if the computer has run out of face card chances
					playSlapSound(volume);
					egyptianratscrew.game.GameInfo.game.discardPile.addPileToHand(2);
					egyptianratscrew.game.GameInfo.game.playerGetsPile = false;
					
				}
					
				else if (egyptianratscrew.game.GameInfo.game.discardPile.checkSlappable()){
					// human player slaps pile while computer is trying to take pile
					playSlapSound(volume);
					computer.interrupt();
					egyptianratscrew.game.GameInfo.game.slap(2);
					if (egyptianratscrew.game.GameInfo.game.hints)
						slapToast.show();
					Log.d("Computer Status", "restarted");
					computer = new Computer(context, egyptianratscrew.game.GameInfo.game.secDelay,egyptianratscrew.game.GameInfo.game.moveDelay, handler);
					computer.setRunning(true);
					computer.start();
					
				}
				
				else if (hitPlayerPile && egyptianratscrew.game.GameInfo.game.turn == 2 && 
						 !egyptianratscrew.game.GameInfo.game.playerGetsPile && 
						 !egyptianratscrew.game.GameInfo.game.computerGetsPile){
					//Human player slaps before (interrupts) Computer slap
					playSlapSound(volume);
					computer.interrupt();
					egyptianratscrew.game.GameInfo.game.makePlay(2);
					Log.d("Computer Status", "restarted");
					computer =new Computer(context, egyptianratscrew.game.GameInfo.game.secDelay,egyptianratscrew.game.GameInfo.game.moveDelay, handler);
					computer.setRunning(true);
					computer.start();
				}
				else
					if (egyptianratscrew.game.GameInfo.game.hints)
						notSlapToast.show();
			}
			
			else if (hitPlayerPile && egyptianratscrew.game.GameInfo.game.turn == 2 && 
					 !egyptianratscrew.game.GameInfo.game.playerGetsPile ){
				Log.d("Turn", "Not yours hitplayer Pile detected");
				//HIT PLAYER PILE
				if (computer.makingMove && !egyptianratscrew.game.GameInfo.game.computerGetsPile){
					//Human player interrupts computer slap with move
					playCardSound(volume);
					computer.interrupt();
					egyptianratscrew.game.GameInfo.game.makePlay(2);
					Log.d("Computer Status", "restarted");
					computer = new Computer(context, egyptianratscrew.game.GameInfo.game.secDelay,egyptianratscrew.game.GameInfo.game.moveDelay, handler);
					computer.setRunning(true);
					computer.start();
				} 
				else if (!egyptianratscrew.game.GameInfo.game.computerGetsPile && 
							(egyptianratscrew.game.GameInfo.game.chances >0 || 
								egyptianratscrew.game.GameInfo.game.faceCard==null ))
					//Human player can make move normally.
					playCardSound(volume);
					egyptianratscrew.game.GameInfo.game.makePlay(2);
			
				
			}
			
			else if (egyptianratscrew.game.GameInfo.game.playerGetsPile){
				Log.d("Grab pile", "to get pile");
				if (egyptianratscrew.game.GameInfo.game.hints)
					grabPileToast.show();
				
				}
			else if (egyptianratscrew.game.GameInfo.game.turn != 2 ) {
				Log.d("Turn", "not yous");
				if (egyptianratscrew.game.GameInfo.game.hints)
					notTurnToast.show();
			} 
			break;
		case MotionEvent.ACTION_UP:
			
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		}
	}
	return true;
}

public void playCardSound(float volume){
	if (egyptianratscrew.game.GameInfo.game.sound){
		sounds.play(cardSound, volume, volume, 1, 0, 1);
	}
}

public void playSlapSound(float volume){
	if (egyptianratscrew.game.GameInfo.game.sound){
		sounds.play(slapSound, volume, volume, 1, 0, 1);
	}
}

public void compSlapSound(float volume){
	if (egyptianratscrew.game.GameInfo.game.sound){
		sounds.play(compSlapSound, volume, volume, 1, 0, 1);
	}
}

}
