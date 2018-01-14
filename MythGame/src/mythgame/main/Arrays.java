package mythgame.main;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Arrays extends JPanel{


 public static void blockCoordsAdd(String string) {
 	MapMaker.blockCoords.add(string);
 }
 public static void badGuyCoordsAdd(String string){
 	MapMaker.badGuyCoords.add(string);
 }
 public static void blockCoordsRemove(String string){
 	MapMaker.blockCoords.remove(string);
 }
 public static void badGuyCoordsRemove(String string){
 	MapMaker.badGuyCoords.remove(string);
 }
  
  
  
  
  public void paintComponent(Graphics g) {
 	 for (int loop=0; loop<MapMaker.blockCoords.size(); loop++){
 	 String pointBlock=MapMaker.blockCoords.get(loop);
 	 String[] pointArrayBlock = pointBlock.split(" ");
 	 int x=Integer.parseInt(pointArrayBlock[0]);
 	 int y=Integer.parseInt(pointArrayBlock[1]);
 	 g.drawImage(MapMaker.img1, x*10-5, y*10-5, null); 
 	 }
 	  
 	 for (int loop=0; loop<MapMaker.badGuyCoords.size(); loop++){
 	 String point=MapMaker.badGuyCoords.get(loop);
 	 String[] pointArray = point.split(" ");
 	 int x=Integer.parseInt(pointArray[0]);
 	 int y=Integer.parseInt(pointArray[1]);
 	 g.drawImage(MapMaker.img, x*10-5, y*10-5, null); 
 	 }
 	 int finX = (MapMaker.FinishX+5)/10;
 	 int finY = (MapMaker.FinishY+5)/10;
 	 g.drawImage(MapMaker.img2, (finX)*10-5, (finY)*10-5, null);
  }
}
