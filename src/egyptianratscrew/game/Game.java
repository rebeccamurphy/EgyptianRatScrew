package egyptianratscrew.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.content.res.Resources;

import egyptianratscrew.activity.R;
import egyptianratscrew.card.Card;
import egyptianratscrew.card.Deck;
import egyptianratscrew.card.DiscardPile;
import egyptianratscrew.player.Player;

public class Game {

	public DiscardPile discardPile;
	public HashMap<Integer , Player> Players; //change to more efficient structure
	private int numPlayers;
	public int turn;
	public boolean touchDisabled;
	private ArrayList<Integer> turnList;
	public String faceCard;
	public int numCardsPlayed;
	public int chances;
	public int secDelay;
	public int cardsDrawn;
	public boolean gameOver;
	public boolean firstTurn;
	public boolean playerGetsPile = false;
	public boolean computerGetsPile = false;
	public int loser;
	public int winner;
	
	public Game() {
		discardPile = new DiscardPile();
		numPlayers =2;
		Players = new HashMap<Integer, Player>();
		numPlayers =2;
		touchDisabled =false;
		gameOver = false;
		firstTurn = true;
		secDelay = 3000;
		cardsDrawn = 5;
		chances = 0;
		Players.put(1, new Player(1));//computer
		Players.put(2, new Player(2));
		turn = 2;
		turnList = new ArrayList<Integer>() ;
		turnList.add(1);
		turnList.add(2);
	}
	
	public Game(int Decks, int numberPlayers, int secsDelay) {
		discardPile= new DiscardPile (Decks);
		numPlayers = numberPlayers;
		turn =2;
		secDelay = secsDelay;
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
				discardPile.drawCard(Players.get(i));
		}

	}
	public void updateScores(){
		for (int i=1; i<= numPlayers; i++){
			Players.get(i).setScore();
			if (Players.get(i).getScore() == 0)
				gameOver = true;
				//add somethign forloser
		}
		
		//draw score to screen on next lines
			
	}
	
	public HashMap<Integer, Player> getPlayers(){
		return Players;
	}
	public DiscardPile getDiscardPile(){
		return discardPile;
	}
	
	public void start(Context myContext){
		discardPile.fillDeck(myContext, myContext.getResources().getDisplayMetrics().widthPixels);
		discardPile.shuffle();
		dealCards();
		updateScores();
		
	}
	public void nextTurn(){
		if(turn == turnList.size() )
			turn = 1;
		else
			turn +=1;
	}
	public int previousTurn(){
		if(turn == 1 )
			 return turnList.size();
		else
			 return turn -1;
		
	}
		
	public void makePlay(int playerID){
		//TODO debug next turn 
		//have player touch discard pile to take it when computer chances are 0?
		if (playerID != turn){
			//make toast
			Log.v("Turn", "not yours");
		}
		else if (faceCard == null){
			discardPile.add(Players.get(playerID).playCard()); //if no current facecard in play player plays card normally
			if (discardPile.get(discardPile.size()-1).getRank() > 10){ //first time facecard played 
					faceCard = discardPile.get(discardPile.size()-1).getFace();
					chances = discardPile.rules.get(faceCard).getNum();
			}
			nextTurn(); //next turn no matter face card in play 
		}
		else if (faceCard !=null && chances>0){ //face card still in play 
			discardPile.add(Players.get(playerID).playCard());
			Log.d("Chances Before", Integer.toString(chances));
			chances--;
			Log.d("Chances After", Integer.toString(chances));
				if (discardPile.get(discardPile.size()-1).getRank() > 10){ //facecard played on facecard
					faceCard = discardPile.get(discardPile.size()-1).getFace();
					chances = discardPile.rules.get(faceCard).getNum();
					nextTurn();
				}
				else if (chances == 0){
					faceCard = null;
					switch(previousTurn()){
					
					case 1: computerGetsPile = true; break; //player1 gets pile
					case 2: playerGetsPile = true; nextTurn(); break; //player2 gets pile
					}
								
				}
		}
		
		
			
	}
		
	public void checkFaceCard(){
		if (discardPile.checkFaceCard()){
			faceCard = discardPile.get(discardPile.size()-1).getFace();
			numCardsPlayed = 0;
		}
			
	}
	
	public void slap(Player player){
		//TODO reset facecard
		if (discardPile.checkAllSlapRules()){
			playerGetsPile = false;
			computerGetsPile = false;
			discardPile.addPileToHand(player);
			faceCard = null;
			chances = 0;
			if (turn != player.getId())
				turn = player.getId();
				//nextTurn(); // the person who gets the discard pile places the card
				// Toast Player 2 turn 
			discardPile.updateUpCards();
		}
		
		
	}
	public void slap(int playerID){
		//TODO reset facecard
		if (discardPile.checkAllSlapRules()){
			playerGetsPile = false;
			computerGetsPile = false;
			discardPile.addPileToHand(Players.get(playerID));
			faceCard = null;
			chances = 0;
			if (turn != playerID)
				turn = playerID;
			discardPile.updateUpCards();
				//nextTurn(); // the person who gets the discard pile places the card
				// Toast Player 2 turn 
		}
		else
			Log.d("toast", "not a slap");
			//possible penalty
		
	}
	 public void checkGameOver(){
		 
		for (int i =1; i< Players.size(); i++){
			if (Players.get(i).getScore() == 0){
				loser = i;
				gameOver = true;
				break;
			}
		}
			
	 }
	 public void setGameOver(){
		 	loser =1;
		 	gameOver = true;
	 }
}
