package egyptianratscrew.game;

import android.app.Application;
import android.content.SharedPreferences;

public class GameInfo extends Application {
	public static Game game = null;
	public static final String PREFERENCES_NAME = "Preferences";
	
	public Game getGame(){
		return game;
	}
	
}
