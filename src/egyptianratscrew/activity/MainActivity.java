package egyptianratscrew.activity;

import egyptianratscrew.view.TitleView;

import android.os.Bundle;
import android.app.Activity;
import android.view.WindowManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TitleView tView = new TitleView(this);
		tView.setKeepScreenOn(true); // keeps screen on for this view
		
		 //makes it full screen requestWindowFeature(Window.FEATURE_NO_TITLE);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 
		setContentView(tView);
	}
}