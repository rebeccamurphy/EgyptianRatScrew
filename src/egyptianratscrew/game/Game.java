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
	public HashMap<Integer , Player> Players;
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
		chances = -1;
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
	
	public void gameStart(Context myContext, int screenW){
		discardPile.fillDeck(myContext, screenW);
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
	
	public void makePlay(Player player){
		if (player.getId() != turn){
			//make toast
			Log.v("Turn", "not yours");
		}
		else if (faceCard == null){
			discardPile.add(player.playCard());
			if (discardPile.get(discardPile.size()-1).getRank() > 10){ //first time facecard played
				faceCard = discardPile.get(discardPile.size()-1).getFace();
				chances = discardPile.rules.get(faceCard).getNum();
				nextTurn();// flips to next player
			}
		}
		else if (faceCard !=null && chances>0){ // facecard played on facecard
			discardPile.add(player.playCard());
			chances--;
			if (discardPile.get(discardPile.size()-1).getRank() > 10){
				faceCard = discardPile.get(discardPile.size()-1).getFace();
				chances = discardPile.rules.get(faceCard).getNum();
				nextTurn();
			}
			
		}
		
			
		}
		
		
	
	public void makePlay(int playerID){
	if (playerID != turn){
		//make toast
		Log.v("Turn", "not yours");
	}
	else if (faceCard == null){
		discardPile.add(Players.get(playerID).playCard());
		if (discardPile.get(discardPile.size()-1).getRank() > 10){ //first time facecard played
			faceCard = discardPile.get(discardPile.size()-1).getFace();
			chances = discardPile.rules.get(faceCard).getNum();
			nextTurn();// flips to next player
		}
	}
	else if (faceCard !=null && chances>0){ // facecard played on facecard
		discardPile.add(Players.get(playerID).playCard());
		chances--;
		if (discardPile.get(discardPile.size()-1).getRank() > 10){
			faceCard = discardPile.get(discardPile.size()-1).getFace();
			chances = discardPile.rules.get(faceCard).getNum();
			nextTurn();
		}
		
	}
}
		
	public void checkFaceCard(){
		//if (checkAce() ||checkKing()|| checkQueen()|| checkJack())
		if (discardPile.checkFaceCard()){
			faceCard = discardPile.get(discardPile.size()-1).getFace();
			numCardsPlayed = 0;
		}
			
	}
	
	public void slap(Player player){
		if (discardPile.checkAllSlapRules()){
			discardPile.addPiletoHand(player);
			faceCard = null;
			chances = 0;
			if (turn != player.getId())
				turn = player.getId();
				//nextTurn(); // the person who gets the discard pile places the card
				// Toast Player 2 turn 
		}
		//else
			//toast not a slap
			//possible penalty
		
	}
	public void slap(int playerID){
		if (discardPile.checkAllSlapRules()){
			Players.get(playerID).addCard(discardPile);
			if (turn != playerID)
				turn = playerID;
				//nextTurn(); // the person who gets the discard pile places the card
				// Toast Player 2 turn 
		}
		//else
			//toast not a slap
			//possible penalty
		
	}
	
}
