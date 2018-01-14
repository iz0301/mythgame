package mythgame.main;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;

import mythgame.draw.Draw;
import mythgame.draw.IncrementBars;
import mythgame.files.Updater;
import mythgame.gui.*;


public class Main {
    public static String os;
    public static Movement m;
    public static int WD;
    public static int HT;
    //TODO: CHANGE THIS FOR EACH UPDATE!!!
    public static final String version = "1.3";
    
	public static void main(String[] args){
		os = System.getProperty("os.name");
		Start.setup();
		
		m=new Movement();
		new MainMenu();
		try {
			new Updater();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void movement(){
		m.money=0;
		m.tm.start();
		Start.mythframe.add(m);
	 	Start.mythframe.setVisible(false);
	 	Start.mythframe.setVisible(true);
	 	Start.mythframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Start.mythframe.revalidate();
	}

}
