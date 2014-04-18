package egyptianratscrew.activity;

import egyptianratscrew.view.GameView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

	private GameView gView; 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.gameview_layout);
		
		gView = (GameView) findViewById(R.id.GAME);
		gView.setKeepScreenOn(true);
		//TODO remove to bar, in xml?
		
		
	}

}
