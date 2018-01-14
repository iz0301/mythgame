package mythgame.gui;

import java.util.ArrayList;

import javax.swing.JButton;

public class Buttons {
	
	ArrayList<Integer> price, type, value;
	ArrayList<JButton> buttons;
	
	public Buttons(ArrayList<Integer> price, ArrayList<Integer> type, ArrayList<Integer> value, ArrayList<JButton> buttons){
		this.type=type;
		this.price=price;
		this.value=value;
		this.buttons = buttons;
	}
	public ArrayList<Integer> type(){
		return type;
	}
	public ArrayList<Integer> price(){
		return price;
	}
	public ArrayList<Integer> value(){
		return value;
	}
	public ArrayList<JButton> buttons(){
		return buttons;
	}

}
