package mythgame.objective;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import mythgame.*;
import mythgame.gui.Levels;
import mythgame.main.Main;
import mythgame.main.Start;

public class FinishArea {
  
	int x;
	int y;
	public static int FinishX;
	public static int FinishY;
	
	public FinishArea(){
		try {
			BufferedReader reader;
		if (Main.os.contains("Windows")){
			reader = new BufferedReader(new 
					FileReader(System.getProperty("user.home")+
							"/AppData/Roaming/MythGame/Maps/"+Levels.loadedLevel+"/"
							+"LevelType.map"));
		} else if (Main.os.contains("Mac")){
			reader = new BufferedReader(new 
					FileReader(System.getProperty("user.home")+
							"/Library/Application Support/MythGame/Maps/"+Levels.loadedLevel+"/"
							+"LevelType.map"));
		} else {
			reader = new BufferedReader(new 
					FileReader(System.getProperty("user.home")+
							"/MythGame/Maps/"+Levels.loadedLevel+"/"
							+"LevelType.map"));
		}
		
		reader.readLine();
		String[] args = reader.readLine().split(" ");
		x = Integer.parseInt(args[0]);
	 	y = Integer.parseInt(args[1]); 
		Start.mythframe.add(new DrawFinish(x, y));
		Start.mythframe.revalidate();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
 		e.printStackTrace();
 		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
  
		FinishX=x*10-5;
		FinishY=y*10-5;
	 }
}