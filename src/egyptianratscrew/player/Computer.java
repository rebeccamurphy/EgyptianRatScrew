package egyptianratscrew.player;

import java.util.concurrent.TimeUnit;

import android.util.Log;

import egyptianratscrew.game.Game;

public class Computer {

	
	public Computer(Game game, Player player, int secDelay) {
		Log.i("Discard pile Before comp", Integer.toString(game.discardPile.upCards.size()));
		try { TimeUnit.SECONDS.sleep(3);}
		catch (Exception e) {
			//Handle exception
			Log.d("Wait Exception", "Computer broke");
		}
		if (game.discardPile.checkSlappable()){
			try {
			    
			    TimeUnit.SECONDS.sleep(secDelay);
			    //sleep 5 seconds should be able to be changes in game rules
			} catch (Exception e) {
			    //Handle exception
				Log.d("Wait Exception", "Computer broke");
			}
			game.slap(player);
		}
		else
			game.makePlay(player);
		
	}

}
