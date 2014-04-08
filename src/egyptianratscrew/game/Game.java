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
	private HashMap<Integer , Player> Players;
	private int numPlayers;
	private int turn;
	private ArrayList<Integer> turnList;
	
	public Game() {
		discardPile = new DiscardPile();
		numPlayers =2;
		Players = new HashMap<Integer, Player>();
		Players.put(1, new Player(1));//computer
		Players.put(2, new Player(2));
		turn = 1;
		turnList = new ArrayList<Integer>() ;
		turnList.add(1);
		turnList.add(2);
	}
	
	public Game(int Decks, int numberPlayers) {
		discardPile= new DiscardPile (Decks);
		numPlayers = numberPlayers;
		turn =1;
		turnList = new ArrayList<Integer>() ;
		for (int i=1; i <= numPlayers;i++ ){
			Players.put(i, new Player(i));
			turnList.add(i);
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
	
	public HashMap<Integer, Player> getPlayers(){
		return Players;
	}
	public DiscardPile getDiscardPile(){
		return discardPile;
	}
	
	public void gameStart(Context myContext, int screenW){
		discardPile.fillDeck(myContext, screenW);
		discardPile.shuffle();
		dealCards();
		updateScores();
	}
	public void nextTurn(){
		if(turn == turnList.size() )
			turn = turn % turnList.size();
		else
			turn +=1;
	}
	
	public void makePlay(Player player){
		if (player.getId() == turn){
			discardPile.add(player.playCard());
			nextTurn();
		}
		
	}
	
	public void slap(Player player){
		if (discardPile.checkAllSlapRules())
			player.addCard(discardPile);
		//else
			//toast not a slap
			//possible penalty
		
	}
	
}
