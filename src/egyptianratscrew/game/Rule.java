package egyptianratscrew.game;

public class Rule {

	private int cardNum; //number of chances associated with face card.  
	private boolean active; 
	
	/***
	 * Constructor for Rule
	 * @param int cards
	 * @param boolean on
	 */
	public Rule(int cards, boolean on) {
		cardNum =cards;
		active = on;
	}
	/***
	 * Returns number of chances associated with rule
	 * @return int chances
	 */
	public int getNum(){
		return cardNum;
	}
	/***
	 * Method to check if rule is in play
	 * @return boolean active
	 */
	public boolean checkActive(){
		return active;
	}
	

}
