package egyptianratscrew.activity;



//import egyptianratscrew.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class OptionsActivity extends Activity {
	
	private Spinner spinnerRules;
	private Button btnSave;
	
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
	 
		spinnerRules = (Spinner) findViewById(R.id.spinnerRules);
		btnSave = (Button) findViewById(R.id.save_btn);
	 
		btnSave.setOnClickListener(new OnClickListener() {
	 
		  @Override
		  public void onClick(View v) {
			// check all options and initialize global game accordingly  
			if (String.valueOf(spinnerRules.getSelectedItem()).equals("Custom Rules") ){
				Toast.makeText(getApplicationContext(), "Custom rules not available.", Toast.LENGTH_SHORT).show();

			}
		  }
	 
		});
	  }

}