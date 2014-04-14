package egyptianratscrew.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import egyptianratscrew.activity.R;
import egyptianratscrew.view.GameView;

@SuppressLint("WrongCall")
public class GameThread extends Thread {
	private boolean running = false;
	private GameView view;
	private SurfaceHolder surfaceHolder;
	private Context myContext;
	public Game game; 
	public boolean gameOver;
	private boolean playerMoved = false;
	private boolean playerSlapped = false;

	public GameThread(SurfaceHolder surfaceHolder, GameView view, Context myContext) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.view = view;
		this.game = new Game();
		this.myContext = myContext;
		game.gameStart(myContext);
	}
	
	public void setRunning (boolean running) {
		this.running = running;
	}
	
	@Override
	public void run(){
		while (running){
			//update game state
			//render screen 
			//Log.v("Thread is running", "this should print a lot");
			Canvas canvas = null;
			if (playerMoved)
				
			{	Log.v("PlayerMoved", "true");
				
				game.Players.get(game.turn).getHand().disableActiveArea();
				game.makePlay(game.turn);
				
				 Paint blackPaint = new Paint();
			     blackPaint.setARGB(255, 0, 0, 0);
				
				try { 
					canvas =view.getHolder().lockCanvas();
					synchronized (view.getHolder()){
						canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), blackPaint);
						//drawPlayerMove(canvas);
						view.onDraw(canvas);
						//surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
					
					finally {
	                    if (canvas != null) {
	                        surfaceHolder.unlockCanvasAndPost(canvas);
	                    }
				}
				
				//game.nextTurn();
				playerMoved = false;
				
			}
			/*
			if (playerSlapped){
				Canvas canvas = view.getHolder().lockCanvas(null);
				game.slap(game.turn);
				playerSlapped= false;
				//view.reDraw(canvas);
			}
			if (game.turn == 1)
			{	Canvas canvas = view.getHolder().lockCanvas(null);
				game.Players.get(1).Computer(game, 3000);
				//view.reDraw(canvas);
			}
			*/
		} 
	}
	
	public void playerMoved(){
		playerMoved = true;
	} 
	public void playerSlapped(){
		playerSlapped = true;
	}
	public void drawPlayerMove(Canvas canvas){
		canvas.drawColor(Color.WHITE);
	Log.d("onDraw", "being called.");
	float scale = myContext.getResources().getDisplayMetrics().density;
	int screenH = myContext.getResources().getDisplayMetrics().heightPixels;
	int screenW = myContext.getResources().getDisplayMetrics().widthPixels;
	
	int scaledCardW = (int) (screenW /3);
	int scaledCardH = (int) (scaledCardW*1.28);
	Bitmap tempBitmap = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.card_back);
	Bitmap cardBack = Bitmap.createScaledBitmap(tempBitmap, scaledCardW, scaledCardH, false);
	
	Paint blackpaint = new Paint();
	blackpaint.setAntiAlias(true);
	blackpaint.setColor(Color.BLACK);
	blackpaint.setStyle(Paint.Style.STROKE);
	blackpaint.setTextAlign(Paint.Align.LEFT);
	blackpaint.setTextSize(scale*15);
		
	for (int i =1; i<= game.Players.size(); i++)
	{
		//draws player decks
		try{
		game.Players.get(i).getHand()
		.drawPlayerDeck(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackpaint, i);
		;}
		
		catch(Exception e){}
	}
	
	game.getDiscardPile().drawDiscardPile(canvas, screenW, screenH, scaledCardW, scaledCardH, scale, cardBack, blackpaint, game);
	game.updateScores();
	
		
		
	
	canvas.drawText(
			"Computer Score: " + Integer.toString(game.Players.get(1).getScore()), 
			10, 
			blackpaint.getTextSize()+10,
			blackpaint);
	canvas.drawText(
			"My Score: " + Integer.toString(game.Players.get(2).getScore()) , 
			10, 
			screenH - blackpaint.getTextSize(),
			blackpaint);
	
	}
}
