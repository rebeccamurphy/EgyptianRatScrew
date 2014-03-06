package egyptianratscrew.view;

import java.util.ArrayList;
import java.util.List;

import egyptianratscrew.card.Card;

import android.content.Context; 
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;


public class GameView extends View{

			private Paint redPaint;
			private int circleX;
			private int circleY;
			private float radius;
			
			private int screenW;
			private int screenH;
			
			private Context myContext;
			private List<Card> deck = new ArrayList<Card>();
			private int scaledCardW;
			private int scaledCardH;
			
			public GameView(Context context) 
			{ 
				super(context);
				redPaint = new Paint();
				redPaint.setAntiAlias(true);
				redPaint.setColor(Color.RED);
				circleX =100;
				circleY = 100;
				radius = 50;
				
				myContext = context;
			}
			public void onSizeChanged (int w, int h, int oldw, int oldh) 
			{
				super.onSizeChanged(w, h, oldw, oldh);
				screenW = w;
				screenH = h;
				
			
			}
			
			@Override
			protected void onDraw(Canvas canvas) 
			{
				canvas.drawCircle(circleX, circleY, radius, redPaint);
			}
			
			public boolean onTouchEvent(MotionEvent event)
			{
				int eventaction = event.getAction();
				int X = (int) event.getX();
				int Y = (int) event.getY();
				
				switch (eventaction)
				{
				case MotionEvent.ACTION_DOWN:
					break; 
				case MotionEvent.ACTION_UP:
					circleX =X;
					circleY =Y;
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				}
				
				invalidate();
				return true;
			}
			//change to private later
			public void initCards() {
				for (int i = 0; i <4; i++) {
					for (int j = 102; j<115; j++) {
						int tempId = j + (i*100);
						Card tempCard = new Card(tempId);
						int resourceId = getResources().getIdentifier("card"+ tempId, "drawable", myContext.getPackageName());
						Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(), resourceId);
						scaledCardW = (int) (screenW/8);
						scaledCardH = (int) (screenH/8);
						Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);
						tempCard.setBitmap(scaledBitmap);
						deck.add(tempCard);
					}
				}				
			}
	}
	
