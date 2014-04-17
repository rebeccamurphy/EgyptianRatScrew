package egyptianratscrew.activity;

import egyptianratscrew.activity.R;
import egyptianratscrew.view.TitleView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
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

	//@Override
	//public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		//return true;
	//}

}