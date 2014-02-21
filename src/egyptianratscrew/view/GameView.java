package egyptianratscrew.view;

import android.content.Context; 
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
			
			public GameView(Context context) 
			{ 
				super(context); // TODO Auto-generated constructor stub
				redPaint = new Paint();
				redPaint.setAntiAlias(true);
				redPaint.setColor(Color.RED);
				circleX =100;
				circleY = 100;
				radius = 50;
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
	}
	
