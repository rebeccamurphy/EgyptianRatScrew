package egyptianratscrew.game;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import egyptianratscrew.activity.R;
import egyptianratscrew.view.GameView;


public class GameThread extends Thread {
private GameView view;
private boolean running = false;
public SurfaceHolder surfaceHolder;
public Context context;
public Handler handler;
public float x;
public float y;
public boolean move = false;

public GameThread(SurfaceHolder surfaceHolder, Context context,GameView view, Handler handler ) {
    this.surfaceHolder = surfaceHolder;  
	this.context = context;
	this.view = view;
	this.x =50;
	this.y =50;
	
}

public void setRunning(boolean run) {
      running = run;
}
public void setSurfaceSize(int width, int height, int screenW, int screenH){
	synchronized(this.surfaceHolder){
		screenW = width;
		screenH = height;
	}
}

@Override
public void run() {
      while (running) {
             if (move){
            	 drawPlayerTurn();
            	 move = false;
             }
      }
}

public void drawPlayerTurn(){
	Canvas c = null;
    try {
           c = view.getHolder().lockCanvas();
           synchronized (view.getHolder()) {
           	
           	view.draw(c);
                 // view.onDraw(c);
           }
    } finally {
           if (c != null) {
                  view.getHolder().unlockCanvasAndPost(c);
           }
    }
}

public boolean doTouchEvent(MotionEvent event){
	synchronized(surfaceHolder){
		int eventaction = event.getAction();
		int X = (int) event.getX();
		int Y = (int) event.getY();
		
		switch (eventaction){
		case MotionEvent.ACTION_DOWN:
			move = true;
			x = X;
			y = Y;
			break;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		}
	}
	return true;
}
}
