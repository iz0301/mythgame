package mythgame.draw;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class IncrementBars extends JPanel{

 public static final int UPPER_LEFT=0;
 public static final int UPPER_RIGHT=1;
 public static final int LOWER_RIGHT=2;
 public static final int LOWER_LEFT=3;
  
 int x;
 int y;
 int height=10;
 int maxSize;
 int size;
 Color color;
 JFrame frame;
 	/* 
  * @param Position the position of the bar on the frame
  * @param size The size of the bar, all bars start at max
  * @param color The color used in the increment bar
  * @param frame The Frame To Be Used
 	 */
 public IncrementBars(int Position, int size, Color color, JFrame frame){
 	switch (Position){
 	case 0:
 	 x=1;
 	 y=1;
 	case 1:
 	 x=frame.getWidth()-size;
 	 y=1;
 	case 2:
 	 x=frame.getWidth()-size;
 	 y=frame.getHeight()-height;
 	case 3:
 	 x=1;
 	 y=frame.getHeight()-height;
 	}
 	this.size=size;
 	this.maxSize=size;
 	this.frame=frame;
 	this.color=color;
 	frame.add(this);
 }
 /*  
  * @param x The Starting X coordinate
  * @param y The Starting Y coordinate
  * @param size The size of the bar, all bars start at max
  * @param color The color used in the increment bar
  * @param frame The Frame To Be Used
  */
 public IncrementBars(int x, int y, int size, Color color, JFrame frame){
 	this.x=x;
 	this.y=y;
 	this.size=size;
 	this.maxSize=size;
 	this.frame=frame;
 	this.color=color;
 	frame.add(this);
 }
  
 public void paintComponent(Graphics g){
 	g.setColor(Color.GRAY);
 	g.drawRect(x-1, y-1, maxSize+1,	height+1);
 	g.setColor(color);
 	g.fillRect(x, y, size, height);
 }
  
 public void add(int amount){
 	if (size+amount<maxSize){
 	size=size+amount;
 	} else if(size+amount>=maxSize-1){
 	 size=maxSize-1;
 	}
 	frame.repaint(x, y, maxSize, height);
 }
  
 public void subtract(int amount){
 	if (size-amount<0){
 	 size=0;
 	} else if (size-amount>0){
 	 size=size-amount;
 	}
 	frame.repaint(x, y, maxSize, height);
 }
  
 public void setSize(int newSize){
 	if((newSize<maxSize)&&(newSize>0)){
 	size=newSize;
 	} else if(newSize<0){
 	 size=0;
 	}
 	frame.repaint(x, y, maxSize, height);
 }
  
 public void setMax(int MaximumSize){
 	maxSize=MaximumSize;
 	frame.repaint(x, y, maxSize, height);
 }
  
 public int getAmount(){
 	return size;
 }
  
}
