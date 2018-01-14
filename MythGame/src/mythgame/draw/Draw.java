package mythgame.draw;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Draw extends JPanel{
  
 public static final int left = -1;
 public static final int right = 1;
 public static final int up = -1;
 public static final int down = 1;
 public static final int stay = 0;
  
 int x;
 int y;
 Image picture;
 JFrame frame;
  
 	/* 
  * @param x The Starting X coordinate
  * @param y The Starting Y coordinate
  * @param icon The Picture Used
  * @param frame The Frame To Be Used
  */
 public Draw(int x, int y, Image icon, JFrame frame){
 	this.x=x;
 	this.y=y;
 	picture=icon;
 	this.frame=frame;
 	frame.add(this);
 	frame.repaint(x, y, 10, 10);
 }
  
 public void paintComponent(Graphics g){
 	g.drawImage(picture, x, y, null);
 	System.out.println("Draw "+this);
 }
  
 public void move(int xDirection, int yDirection){
 	int OldX=x;
 	int OldY=y;
 	x=x+xDirection;
 	y=y+yDirection;
 	frame.repaint(OldX, OldY, 10, 10);
 	frame.repaint(x, y, 10, 10);
 }
  
 public void remove(){
 	picture=null;
 	frame.repaint(x, y, 10, 10);
 }
  
 public void setPos(int x, int y){
 	int OldX=this.x;
 	int OldY=this.y;
 	this.x=x;
 	this.y=y;
 	frame.repaint(OldX, OldY, 10, 10);
 	frame.repaint(x, y, 10, 10);
 }
  
 public int getX(){
 	return x;
 }

 public int getY(){
 	return y;
 }
}
