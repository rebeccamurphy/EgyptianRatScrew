package egyptianratscrew.game;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
private Toast gameOverToast; 
private Toast notTurnToast;
private Toast grabPileToast;
private Toast slapToast;
private Toast notSlapToast;
private String winner;
public GameThread(SurfaceHolder surfaceHolder, Context context,GameView view, Handler handler ) {
    this.surfaceHolder = surfaceHolder;  
	this.context = context;
	this.view = view;
	computer = new Computer(egyptianratscrew.game.GameInfo.game.secDelay, view);
	
	notTurnToast = Toast.makeText(context, "Not your turn.", Toast.LENGTH_SHORT);
	grabPileToast = Toast.makeText(context, "The pile is yours, tap it to take it.", Toast.LENGTH_SHORT);
	slapToast = Toast.makeText(context, "You got the slap! Now play a card.", Toast.LENGTH_SHORT);
	notSlapToast = Toast.makeText(context, "Not a slap!", Toast.LENGTH_SHORT);
	
	computer.slapToast = Toast.makeText(context, "Computer got the slap!", Toast.LENGTH_SHORT);
	grabPileToast = Toast.makeText(context, "Computer won the pile", Toast.LENGTH_SHORT);
	
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
    	  try {
	  		Thread.sleep(33);	  		
	    	drawGame();
	    	egyptianratscrew.game.GameInfo.game.checkGameOver();
	    	  
	    	if (egyptianratscrew.game.GameInfo.game.gameOver){
	    		  //TOAST
	    		if (egyptianratscrew.game.GameInfo.game.loser ==1)
	    			winner = "You";
	    		else
	    			winner = "Computer";
	    		new AlertDialog.Builder(context)
			    .setTitle("Game Over!")
			    .setMessage(winner +" win!")
			    .setCancelable(false)
			    .setPositiveButton("Play again?", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			        	//com.kgght.studybug.objects.PreTest.qNum = 0;
			        	//com.kgght.studybug.objects.PreTest.flipq = false;
			        	Intent createIntent = new Intent(context, egyptianratscrew.view.GameView.class);
						context.startActivity(createIntent);
						
			        }
			    })
			    .setNegativeButton("Quit?", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			        	//com.kgght.studybug.objects.PreTest.qNum = 0;
			        	//com.kgght.studybug.objects.PreTest.flipq = false;
			        	Intent createIntent = new Intent(context, egyptianratscrew.view.TitleView.class);
						context.startActivity(createIntent);
			        }
			    }).show();
			    
	    		  Log.d("Player: ", Integer.toString(egyptianratscrew.game.GameInfo.game.loser) + " Wins");
	    		  computer.setRunning(false);
	    		  setRunning(false);
	    		
	    	}
    	  }
    	  catch(Exception ex) {
    		  Thread.currentThread().interrupt();
    		  Log.d("Interrupt", "Draw wait");   
  		 }	
      }
}


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
				//HIT DISCARD 
				if (egyptianratscrew.game.GameInfo.game.playerGetsPile){
					//Human takes pile if the computer has run out of face card chances
					egyptianratscrew.game.GameInfo.game.discardPile.addPileToHand(2);
					egyptianratscrew.game.GameInfo.game.playerGetsPile = false;
					
				}
					
				else if (egyptianratscrew.game.GameInfo.game.discardPile.checkSlappable()&& computer.makingMove ==true){
					// human player slaps pile while computer is trying to take pile
					computer.interrupt();
					egyptianratscrew.game.GameInfo.game.slap(2);
					slapToast.show();
					Log.d("Computer Status", "restarted");
					computer = new Computer(egyptianratscrew.game.GameInfo.game.secDelay, view);
					computer.setRunning(true);
					computer.start();
				}
				
				else if (hitPlayerPile && egyptianratscrew.game.GameInfo.game.turn == 2 && 
						computer.makingMove && !egyptianratscrew.game.GameInfo.game.playerGetsPile &&
						 !egyptianratscrew.game.GameInfo.game.computerGetsPile){
					//Human player slaps before (interrupts) Computer slap
					computer.interrupt();
					egyptianratscrew.game.GameInfo.game.makePlay(2);
					Log.d("Computer Status", "restarted");
					computer = new Computer(egyptianratscrew.game.GameInfo.game.secDelay, view);
					computer.setRunning(true);
					computer.start();
				}
				else if (egyptianratscrew.game.GameInfo.game.discardPile.checkSlappable())
					//player slaps without having to interrupt the computer
					egyptianratscrew.game.GameInfo.game.slap(2); 
				else 
					notSlapToast.show();
			}
			
			else if (hitPlayerPile && egyptianratscrew.game.GameInfo.game.turn == 2 && 
					!computer.makingMove && !egyptianratscrew.game.GameInfo.game.playerGetsPile){
				//HIT PLAYER PILE
				//Human player makes move
				egyptianratscrew.game.GameInfo.game.makePlay(2);
				//egyptianratscrew.game.GameInfo.game.nextTurn();
				Log.d("Touch", "move true");
				Log.d("Computer is running, alive, moving ", Boolean.toString(computer.running) + " " + Boolean.toString(computer.isAlive())
						+ " " +Boolean.toString(computer.makingMove));
			
			}
			
			else if (egyptianratscrew.game.GameInfo.game.playerGetsPile){
				//TOAST
				grabPileToast.show();
				Log.d("Grab pile", "to get pile");
				}
			else{
				Log.d("Turn", "not yous");
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
}
