package egyptianratscrew.game;

import java.util.ArrayList;

public class Rule {

	//private ArrayList<Rule> rules;
	private String ruleName;
	private int cardNum; //card num allowances 
	private boolean active;
	
	public Rule(int cards, boolean on) {
		cardNum =cards;
		active = on;
	}
	/*
	public Rule(String name, int cards, boolean on) {
		ruleName = name;
		cardNum =cards;
		active = on;
	}
	
	public String getName(){
		return ruleName;
	}
	*/
	public int getNum(){
		return cardNum;
	}
	
	public boolean checkActive(){
		return active;
	}
	

}
