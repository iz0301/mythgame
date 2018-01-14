package mythgame.multiplayer;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import mythgame.main.Main;
import mythgame.main.Movement;
import mythgame.main.Start;

public class PlayerPanel extends JPanel implements Runnable{
	
	public static ArrayList<Integer> multiX = new ArrayList<Integer>();
	public static ArrayList<Integer> multiY = new ArrayList<Integer>();
	
	public PlayerPanel(){
		Start.mythframe.add(this);
		Start.mythframe.revalidate();
	}
	
	public void run() {
		while (true){
			this.repaint();
		}
	}
	public void paintComponent(Graphics g){
		for (int cnt = 0; cnt < multiX.size(); cnt++){
			//System.out.println("PAintPLayers "+ multiX.get(cnt)+ "," + multiY.get(cnt));
			g.drawImage(Movement.img1, multiX.get(cnt), multiY.get(cnt), null);
		}	
	}

}
