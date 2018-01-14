package mythgame.main;

import java.awt.Image;
import java.awt.Point;

public class Entity {
	
	private int x;
	private int y;
	private int life;
	
	private boolean hostile;
	private boolean solid;
	
	private Point move1;
	private Point move2;
	private int speed;
	private int motion=1;
	
	public Entity(int x, int y, boolean solid, boolean hostile, int life, Image picture) {
		this.hostile=hostile;
		this.life=life;
	}
	
	public boolean isTouching(Entity object){
		int oy = object.getY();
		int ox = object.getX();
		int WD=Main.WD;
		int HT=Main.HT;
		
		if((x>=ox && x<=ox+WD)||(x+WD>=ox && x+WD<=ox+WD)){
			if((y>=oy+HT && y<=oy)||(y+HT>=oy+HT && y+HT<=oy)){
				return true;
			}
		}
		return false;
	}
	
	public void kill(int life){
		this.life=this.life-life;
	}

	public void setMove(int x1, int y1, int x2, int y2, int speed){
		move1 = new Point((x1>x2?x1:x2), (y1>y2?y1:y2));
		move2 = new Point((x2>x1?x1:x2), (y2>y1?y1:y2));
		this.speed=speed;
	}
	
	public void move(){
		int m = (move1.y-move2.y)/(move1.x-move2.x);
		if(motion==1){
			x=x++;
			y=y+m;
		}else if(motion==0){
			x=-(1*speed);
			y=y-(m*speed);
		}
		if(x==move1.x || y==move1.y){
			motion=1;
		}
		if(x==move2.x || y==move2.y){
			motion=0;
		}
	}

	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getLife(){
		return life;
	}
	
	public void delete(){
		this.delete();
	}
}
