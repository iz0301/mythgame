package mythgame.gui;

import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

import mythgame.files.Downloader;
import mythgame.files.FolderZipper;
import mythgame.main.Main;
import mythgame.main.Movement;
import mythgame.main.Start;
import mythgame.objective.FinishArea;

public class Levels {
  
	public static String objective;
 public static boolean CustomErrorShowed=false;
 public static String loadedLevel;
 static BufferedReader reader;
 ArrayList<String> levels = new ArrayList<String>();
 static JFrame frame = Start.mythframe;
 JPanel levelselect = new JPanel();
 JButton start = new JButton("Start");
 JComboBox levelList;
  static String os = Main.os;

 JPanel buttons = new JPanel();
  
 public Levels() throws IOException {
 	frame.getContentPane().removeAll();
 	AddPanel();
 }
  
 @SuppressWarnings("unchecked")
public void AddPanel(){
 	try {
 	 GetLevels();
 	} catch (IOException e2) {
 	 new Downloader();
 	 new FolderZipper();
 	 try {
 	 GetLevels();
 	 } catch (IOException e1) {
 	 e1.printStackTrace();
 	 }
 	}
 	levelList = new JComboBox(levels.toArray());
 	levelselect.add(levelList);
 	levelselect.add(start);
 	frame.add(levelselect);
 	frame.revalidate();

 	start.addActionListener(new ActionListener(){
 	public void actionPerformed(ActionEvent e){

 	 LoadLevel(levels.get(Integer.valueOf(String.valueOf(levelList.getSelectedIndex()))));

 	}});
 }
  
  
 public void GetLevels() throws IOException {
 	 
 	 if (os.contains("Windows")) {
	 reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/levelList.list"));
 	 } else if (os.contains("Mac")) {
 		reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/levelList.list"));
 	 } else {
 		reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/MythGame/Maps/levelList.list"));
 	 }
  String line;
 	 while ((line=reader.readLine()) != null) {
 	 levels.add(line);
 	 }
 }
  
 public static void LoadLevel(String levelToLoad) {
 	  
 	 loadedLevel=levelToLoad;
 	 Movement.life=Movement.MaxHealth;
 	 Movement.badGuyLifeMax.clear();
 	 Movement.badGuyLife.clear();
 	 Start.badGuyCoordsX.clear();
 	 Start.badGuyCoordsY.clear();
 	 Movement.money=0;
 	 Main.m.LastAttacked=0;
 	 Main.m.beingAttacked=false;
 	 Main.m.x=0;
 	 Main.m.y=0;
 	 Main.m.velX=0;
 	 Main.m.velY=0;
 	 Main.m.wPressed=false;
 	 Main.m.dPressed=false;
 	 Main.m.aPressed=false;

 	 BufferedReader attackReader;
	 try {//Stats
		 if (os.contains("Windows")) {
			  attackReader = new BufferedReader(new FileReader(System.getProperty("user.home")+ "/AppData/Roaming/MythGame/Player_Stats.stats"));
		  } else  if (os.contains("Mac")){
			   attackReader = new BufferedReader(new FileReader(System.getProperty("user.home")+ "/Library/Application Support/MythGame/Player_Stats.stats"));
		  } else {
			  attackReader = new BufferedReader(new FileReader(System.getProperty("user.home")+ "/MythGame/Player_Stats.stats"));
		  }
		 attackReader.readLine();
		 Movement.attackDamage = Integer.parseInt(attackReader.readLine());
		 Movement.defense = Integer.parseInt(attackReader.readLine());
		 attackReader.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	 
 	try {//badguys
  if (os.contains("Windows")) {
 		reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/"+Levels.loadedLevel+"/" +"BadGuys.map"));
  } else if (os.contains("Mac")){
		reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/"+Levels.loadedLevel+"/" +"BadGuys.map"));
  } else {
	  reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/MythGame/Maps/"+Levels.loadedLevel+"/" +"BadGuys.map"));
  }
  String BadGuyLine=null;
   
 	 while ((BadGuyLine=reader.readLine()) != null) {
 	 String[] badGuyCoords = BadGuyLine.split(" ");
 	 int BadGuyXadd = Integer.parseInt(badGuyCoords[0]);
 	int BadGuyYadd = Integer.parseInt(badGuyCoords[1]);
 	 int BadLife = Integer.parseInt(badGuyCoords[2]);
 	 Start.mythframe.repaint();
 	 Start.badGuyCoordsX.add(BadGuyXadd*10-5);
 	 Start.badGuyCoordsY.add(BadGuyYadd*10-5);
 	Movement.badGuyLife.add(BadLife);
 	Movement.badGuyLifeMax.add(BadLife);
 	 Start.mythframe.revalidate();
 	 }
 	} catch (FileNotFoundException e) {
 	 // TODO Auto-generated catch block
 	 e.printStackTrace();
 	} catch (NumberFormatException e) {
 	 // TODO Auto-generated catch block
 	 e.printStackTrace();
 	} catch (IOException e) {
 	 // TODO Auto-generated catch block
 	 e.printStackTrace();
 	}
  
 	
 	 try {
 	 if (os.contains("Windows")) {
 		 reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/"+levelToLoad+"/"+levelToLoad+".map"));
 	 } else if (os.contains("Mac")) {
 		reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/"+levelToLoad+"/"+levelToLoad+".map"));
 	 } else{
 		reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/MythGame/Maps/"+levelToLoad+"/"+levelToLoad+".map"));
 	 }
 	 } catch (FileNotFoundException e) {
 	 // TODO Auto-generated catch block
 	 e.printStackTrace();
 	 }
 	  
 	 String line=null;
 	 frame.getContentPane().removeAll();
 	  
 	 try {//blocks
 	 while ((line=reader.readLine()) != null) {
 	 String[] cords = line.split(" ");
 	 int x = Integer.parseInt(cords[0]);
 	 int y = Integer.parseInt(cords[1]);
 	 Start.Builder(x, y, "block");
 	 }
 	 } catch (IOException e) {
 	 if (loadedLevel=="Custom_Level"){
 	 JOptionPane.showConfirmDialog(null, "Please Make A Valid Costom Level With MapMaker\n Must Include:\nFinishBlock\nOne Or More Blocks ");
 	 } else{
 	 e.printStackTrace();
 	 }
 	 }
 	  
 	 try {
 	 if (os.contains("Windows")) {
 		 reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/"+levelToLoad+"/"+"LevelType.map"));
 	 } else if (os.contains("Mac")) {
 		 reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/"+levelToLoad+"/"+"LevelType.map"));
 	 } else {
 		reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/MythGame/Maps/"+levelToLoad+"/"+"LevelType.map"));
 	 }
 	 } catch (FileNotFoundException e) {
 	 // TODO Auto-generated catch block
 	 e.printStackTrace();
 	 }
 	  
 	 try {//finish types
 	 switch (reader.readLine()){
 	 case "FinishArea":
 	 new FinishArea();
 	 objective="FinishArea";
 	 break;
 	 }
 	 } catch (IOException e) {
 	 if (Levels.loadedLevel=="Custom_Level"){
 	 JOptionPane.showMessageDialog(null, "Please Make A Valid Costom Level With MapMaker\n Must Include:\nFinishBlock\nOne Or More Blocks\nAnd A BadGuy ");
 	 } else{
 	 e.printStackTrace();
 	 }
 	 } catch(NullPointerException e1){
 	 if (Levels.loadedLevel=="Custom_Level"){
 	 JOptionPane.showMessageDialog(null, "Please Make A Valid Costom Level With MapMaker\n Must Include:\nFinishBlock\nOne Or More Blocks\nAnd A BadGuy ");
 	 } else{
 	 e1.printStackTrace();
 	 }
 	 }
 	  
 	  
 	Main.movement();
 	}

 	 
  
}
