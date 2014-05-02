package egyptianratscrew.activity;



//import egyptianratscrew.activity.R;

import egyptianratscrew.game.Game;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class OptionsActivity extends Activity {
	
	//private Spinner spinnerRules;
	private Button btnSave,  btnReset;
	private EditText sSpeed, tSpeed;
	private ToggleButton soundTog, hintsTog;
	private RadioGroup deckNum;
	private RadioButton selectedDeckNum;
	private boolean sound, hints;
	private int slapSpeed, turnSpeed;
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		  //create slider for computer speed
		  //checkbox for toasts on
		  //save button
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.options_layout);
		 addListenerOnButton();
	  }
	  
	  // get the selected dropdown list value
	  public void addListenerOnButton() {
	 
		
		btnSave = (Button) findViewById(R.id.SaveBtn);
		btnReset = (Button) findViewById(R.id.ResetBtn);
		sSpeed = (EditText) findViewById(R.id.slapSpeed);
		tSpeed = (EditText) findViewById(R.id.turnSpeed);
		soundTog = (ToggleButton) findViewById(R.id.SoundBtn);
		hintsTog = (ToggleButton) findViewById(R.id.HintBtn);
		deckNum = (RadioGroup) findViewById(R.id.DeckNum);
		
	
		btnSave.setOnClickListener(new OnClickListener() {
	 
		  @Override
		  public void onClick(View v) {
			// check all options and initialize global game accordingly
			 sound = soundTog.getText().toString().equals("Sound On") ? true: false;
			 hints = hintsTog.getText().toString().equals("Hints On") ? true: false; 
			 selectedDeckNum = (RadioButton) findViewById(deckNum.getCheckedRadioButtonId());
			 turnSpeed = tSpeed.getText().toString().isEmpty()? 2000 : Integer.parseInt(tSpeed.getText().toString()) *1000;
			 slapSpeed = sSpeed.getText().toString().isEmpty()? 2000 : Integer.parseInt(sSpeed.getText().toString()) *1000;
			 Log.d("butt", selectedDeckNum.getText().toString());
			 //create new game
			 egyptianratscrew.game.GameInfo.game = new Game(Integer.parseInt(selectedDeckNum.getText().toString()), 
					 										turnSpeed, 
					 										slapSpeed,
					 										hints, 
					 										sound);
			  
			 //toast settings saved
			 Toast.makeText(getApplicationContext(),"Saved!", Toast.LENGTH_SHORT).show();
			 //return to main screen
			 Intent createIntent = new Intent(getApplicationContext(), MainActivity.class);
			 startActivity(createIntent);
		  }
		  
		});
		
		btnReset.setOnClickListener(new OnClickListener() {
			 
			  @Override
			  public void onClick(View v) {
				  //makes default game  
				  egyptianratscrew.game.GameInfo.game = new Game();
				  //make toast
				  Toast.makeText(getApplicationContext(),"Reset!", Toast.LENGTH_SHORT).show();
				  //return to main screen
				  Intent createIntent = new Intent(getApplicationContext(), MainActivity.class);
				  startActivity(createIntent);
			  }
		 
			});
	  }

}
