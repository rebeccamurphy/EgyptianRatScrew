package egyptianratscrew.view;

import egyptianratscrew.activity.GameActivity;
import egyptianratscrew.activity.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
/**
 * @author Rebecca
 *
 */
public class TitleView extends View {

	private Bitmap titleGraphic;
	private Bitmap playButtonUp;
	private Bitmap optionButtonUp;
	private Bitmap playButtonDown;
	private Bitmap optionButtonDown;
	
	
	private boolean playButtonPressed;
	private boolean optionButtonPressed;
	
	private Context myContext;
	
	private int screenW;
	private int screenH;
	
	public TitleView(Context context) 
	{
		super(context);
		myContext = context;
		
		titleGraphic = BitmapFactory.decodeResource(getResources(), R.drawable.title_graphic);
		
		playButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_up);
		optionButtonUp = BitmapFactory.decodeResource(getResources(), R.drawable.option_button_up);
		
		playButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_down);
		optionButtonDown = BitmapFactory.decodeResource(getResources(), R.drawable.option_button_down);
	}
	
	@Override
	public void onSizeChanged (int w, int h, int oldw, int oldh) 
	{
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{	
		canvas.drawBitmap(titleGraphic, (screenW - titleGraphic.getWidth())/2, 0,null); // writes title at the top of the screen
			
		if (playButtonPressed) 
		{
			canvas.drawBitmap(playButtonDown, (screenW-playButtonDown.getWidth())/2, (int) (screenH*0.7), null);
			canvas.drawBitmap(optionButtonUp, (screenW-playButtonUp.getWidth())/2, (int) (screenH*0.7 + playButtonUp.getHeight()), null);
		}
		else if (optionButtonPressed)
		{
			canvas.drawBitmap(optionButtonDown, (screenW-playButtonUp.getWidth())/2, (int) (screenH*0.7 + playButtonUp.getHeight()), null);
			canvas.drawBitmap(playButtonUp, (screenW-playButtonUp.getWidth())/2, (int) (screenH*0.7), null);
		}
		else
		{	//what is first drawn to the canvas when nothing is touched. The option button is draw underneath the play button.
			canvas.drawBitmap(playButtonUp, (screenW-playButtonUp.getWidth())/2, (int) (screenH*0.7), null);
			canvas.drawBitmap(optionButtonUp, (screenW-playButtonUp.getWidth())/2, (int) (screenH*0.7 + playButtonUp.getHeight()), null);
		}
		 
	}
	
	public boolean onTouchEvent(MotionEvent event )
	{
		int eventaction = event.getAction();
		int X = (int) event.getX();
		int Y =  (int) event.getY();
		
		switch (eventaction) {
		
		case MotionEvent.ACTION_DOWN:
			buttonPressCheck(X,Y);
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			if (playButtonPressed) //Goes to next Activity
			{
				Intent gameIntent = new Intent(myContext, GameActivity.class);
				myContext.startActivity(gameIntent);
			}
			else if (optionButtonPressed)
			{
				//Intent gameIntent = new Intent(myContext, OptionActivity.class);
				//myContext.startActivity(gameIntent);
				//For a later Assignment
			}
			//makes sure both buttons are no longer being pressed
			playButtonPressed =false; 
			optionButtonPressed = false;
			break;
		}
		
		invalidate();
		return true;
	}

	public void buttonPressCheck (int X, int Y)
	/*
	 * A small function to check if a button was pressed. Just to make the onTouchEvent cleaner looking. 
	 * @params int X, and int Y from event
	 * */
	{
		if (X > (screenW-playButtonUp.getWidth())/2 && X < ((screenW-playButtonUp.getWidth())/2) + playButtonUp.getWidth() 
				&& Y > (int)(screenH*0.7) && Y < (int)(screenH*0.7) +playButtonUp.getHeight()) 
		{ 
			playButtonPressed = true; 
		}
		//else if instead of if, just to make sure buttons never overlap
		else if (X > (screenW-playButtonUp.getWidth())/2 && X < ((screenW-playButtonUp.getWidth())/2) + playButtonUp.getWidth() 
				&& Y > (int)(screenH*0.7 + playButtonUp.getHeight()) && Y < (int)(screenH*0.7) +playButtonUp.getHeight()*2)
		{
			optionButtonPressed = true;
		}
	}
}
