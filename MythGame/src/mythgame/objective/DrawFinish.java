package mythgame.objective;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import mythgame.main.Start;

public class DrawFinish extends JPanel {

	int x;
	int y;
	
	public DrawFinish(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void paintComponent(Graphics g) {
	 	g.setColor(new Color(0, 200, 0));
	 	g.fillRect(x*10-5, y*10-5, 10, 10);
	}
	
}
