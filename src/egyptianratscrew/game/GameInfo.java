package egyptianratscrew.game;

import android.app.Application;

public class GameInfo extends Application {
	public static Game game = new Game();

	
	public Game getGame(){
		return game;
	}

}
