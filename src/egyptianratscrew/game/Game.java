package egyptianratscrew.game;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import egyptianratscrew.card.DiscardPile;
import egyptianratscrew.player.Player;

@SuppressLint("UseSparseArrays")
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
	public int moveDelay;
	public int cardsDrawn;
	public int loser;
	public int winner;
	public boolean gameOver;
	public boolean firstTurn;
	public boolean playerGetsPile = false;
	public boolean computerGetsPile = false;
	public boolean hints = true;
	public boolean sound = false;

	
	/***
	 * Default Constructor for game
	 */
	public Game() {
		discardPile = new DiscardPile();
		numPlayers =2;
		Players = new HashMap<Integer, Player>();
		touchDisabled =false;
		gameOver = false;
		firstTurn = true;
		secDelay = 3000;
		moveDelay = 2000;
		cardsDrawn = 5;
		chances = 0;
		Players.put(1, new Player(1));//computer
		Players.put(2, new Player(2));//human
		turn = 2;
		turnList = new ArrayList<Integer>() ;
		turnList.add(1);
		turnList.add(2);
	}
	/***
	 * Custom Game construct for when options are implemented
	 * @param Decks
	 * @param secsDelay
	 */
	public Game(int numDecks, int secDelay, int moveDelay, boolean hints, boolean sound) {
		//default values
		Log.d("deck", "num decks in game" +numDecks);
		discardPile = new DiscardPile(numDecks);
		Players = new HashMap<Integer, Player>();
		touchDisabled =false;
		gameOver = false;
		firstTurn = true;
		cardsDrawn = 5;
		chances = 0;
		numPlayers = 2;
		turn =2;
		
		//custom values
		this.secDelay = secDelay;
		this.moveDelay = moveDelay;
		this.hints = hints;
		this.sound = sound;
		turnList = new ArrayList<Integer>() ;
		for (int i=1; i <= numPlayers;i++ ){
			Players.put(i, new Player(i));
			turnList.add(i);
		}
	}
	public Game(Game gameCopy) {
		//default values
		
		Players = new HashMap<Integer, Player>();
		touchDisabled =false;
		gameOver = false;
		firstTurn = true;
		cardsDrawn = 5;
		chances = 0;
		numPlayers = 2;
		turn =2;
		
		//custom values
		discardPile = new DiscardPile(gameCopy.discardPile.numDecks);
		this.secDelay = gameCopy.secDelay;
		this.moveDelay = gameCopy.moveDelay;
		this.hints = gameCopy.hints;
		this.sound = gameCopy.sound;
		turnList = new ArrayList<Integer>() ;
		for (int i=1; i <= numPlayers;i++ ){
			Players.put(i, new Player(i));
			turnList.add(i);
		}
	}
	public Game(SharedPreferences settings){
		//default values
		
		Players = new HashMap<Integer, Player>();
		touchDisabled =false;
		gameOver = false;
		firstTurn = true;
		cardsDrawn = 5;
		chances = 0;
		numPlayers = 2;
		turn =2;
				
		//custom values 
		discardPile = new DiscardPile(settings.getInt("deckNum", 1));
		this.secDelay = settings.getInt("slapSpeed", 2000) *1000;
		this.moveDelay = settings.getInt("turnSpeed", 2000)*1000;
		this.hints = settings.getBoolean("hints", false);
		this.sound = settings.getBoolean("sound", false);
		turnList = new ArrayList<Integer>() ;
		for (int i=1; i <= numPlayers;i++ ){
			Players.put(i, new Player(i));
			turnList.add(i);
		}
	}
	
	/**
	 * Deals cards to players at the start of the game.
	 */
	private void dealCards(){
		while (!discardPile.isEmpty())
		{
			for (int i=1; i<= numPlayers; i++)
				discardPile.drawCard(Players.get(i));
		}

	}
	/***
	 * Method updates the player scores displayed and sets game over
	 */
	public void updateScores(){
		for (int i=1; i<= numPlayers; i++){
			Players.get(i).setScore();
			if (Players.get(i).getScore() == 0)
				gameOver = true;
		}	
	}
	/***
	 * Method to return hashmap of players
	 * @return Players
	 */
	public HashMap<Integer, Player> getPlayers(){
		return Players;
	}
	
	/***
	 * Method to return the discardPile
	 * @return discardPile
	 */
	public DiscardPile getDiscardPile(){
		return discardPile;
	}
	
	/***
	 * Starts the game by filling the game deck, shuffling the deck, deals the 
	 * cards to the players and updates the scores. 
	 * @param myContext
	 */
	public void start(Context myContext){
		discardPile.fillDeck(myContext, myContext.getResources().getDisplayMetrics().widthPixels);
		discardPile.shuffle();
		dealCards();
		updateScores();
	}
	/***
	 * Sets the next turn of the game.
	 */
	public void nextTurn(){
		if(turn == turnList.size() )
			turn = 1;
		else
			turn +=1;
	}
	/***
	 * Returns the previous turn of the game
	 * @return int previousTurn
	 */
	public int previousTurn(){
		if(turn == 1 )
			 return turnList.size();
		else
			 return turn -1;
		
	}
	/***
	 * Makes a play based off the playerID
	 * @param playerID
	 */
	public void makePlay(int playerID){
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
	/***
	 * Checks if a faceCard was played on the top of the discardPile
	 */
	public void checkFaceCard(){
		if (discardPile.checkFaceCard()){
			faceCard = discardPile.get(discardPile.size()-1).getFace();
			numCardsPlayed = 0;
		}
			
	}
	/***
	 * Method for when a player/computer slaps
	 * @param player
	 */
	public void slap(Player player){
		if (discardPile.checkSlappable()){
			playerGetsPile = false;
			computerGetsPile = false;
			discardPile.addPileToHand(player);
			faceCard = null;
			chances = 0;
			if (turn != player.getId())
				turn = player.getId(); //the person who slapped automatically goes next
		}
	}
	/***
	 * Method for when a player/computer slaps
	 * @param player
	 */
	public void slap(int playerID){
		if (discardPile.checkSlappable()){
			playerGetsPile = false;
			computerGetsPile = false;
			discardPile.addPileToHand(Players.get(playerID));
			faceCard = null;
			chances = 0;
			if (turn != playerID)
				turn = playerID;//the person who slapped automatically goes next
		}
	}
	/***
	 * Checks for Gameover by checking if a player's score is 0. 
	 */
	 public void checkGameOver(){
		 
		for (int i =1; i< Players.size(); i++){
			if (Players.get(i).getScore() == 0){
				loser = i;
				gameOver = true;
				break;
			}
		}
			
	 }
	 
}
