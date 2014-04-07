package egyptianratscrew.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.res.Resources;

import egyptianratscrew.activity.R;
import egyptianratscrew.card.Card;
import egyptianratscrew.card.Deck;
import egyptianratscrew.card.DiscardPile;
import egyptianratscrew.player.Player;

public class Game {

	private DiscardPile discardPile;
	private HashMap<String, Player> Players;
	private int numPlayers;
	private String currentTurn;
	
	
	public Game() {
		discardPile = new DiscardPile();
		numPlayers =2;
		Players = new HashMap<String, Player>();
		Players.put("Player1", new Player());//computer
		Players.put("Player2", new Player());
	}
	
	public Game(int Decks, int numberPlayers) {
		discardPile= new DiscardPile (Decks);
		numPlayers = numberPlayers;
		for (int i=1; i <= numPlayers;i++ ){
			Players.put("Player" + Integer.toString(i), new Player());
		}
	}
	
	private void dealCards(){
		while (!discardPile.isEmpty())
		{
			for (int i=1; i<= numPlayers; i++)
				discardPile.drawCard(Players.get("Player"+Integer.toString(i)));
		}
		
	}
	public void updateScores(){
		for (int i=1; i<= numPlayers; i++){
			String PlayerID = "Player"+Integer.toString(i);
			Players.get(PlayerID).setScore();
		}
			
	}
	public void gameStart(Context myContext, int screenW){
		discardPile.fillDeck(myContext, screenW);
		discardPile.shuffle();
		dealCards();
		updateScores();
	}
	
}
