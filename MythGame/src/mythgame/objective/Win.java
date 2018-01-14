package mythgame.objective;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mythgame.gui.Levels;
import mythgame.gui.MainMenu;
import mythgame.main.Main;
import mythgame.main.Movement;
import mythgame.main.Start;

public class Win {
 String os = Main.os;
 BufferedReader reader;
 BufferedReader reader2;
 BufferedWriter writer;
	public Win(){
 
  JPanel gameover = new JPanel();
  JButton retry = new JButton("Retry");
  JButton mainmenu = new JButton("Return to Main Menu");
  JLabel message = new JLabel("YOU WIN!");
  message.setForeground(Color.blue);
  message.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 100));
  gameover.add(message);
 	  
  Start.mythframe.getContentPane().removeAll();
 	  
  gameover.add(retry);
  gameover.add(mainmenu);
  Start.mythframe.add(gameover);
  gameover.revalidate();
 	  
  retry.addActionListener(new ActionListener(){
  public void actionPerformed(ActionEvent e){
 	  
  Start.mythframe.getContentPane().removeAll();
 	  
  Levels.LoadLevel(Levels.loadedLevel);

 	  
  }
  });
  mainmenu.addActionListener(new ActionListener() {
 	 public void	actionPerformed(ActionEvent e){
 	 new MainMenu();
 	 }});
 	 
 	  
 	  
 	 try {
 	 if (os.contains("Windows")) {
 		reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/levelOrder.list"));
 	 } else if (os.contains("Mac")) {
 		reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/levelOrder.list"));
 	 } else {
 		reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/MythGame/Maps/levelOrder.list"));

 	 }
 	 String line;
 	 while ((line=reader.readLine())!= null){
 	 if (line.equals(Levels.loadedLevel)) {
 	 String nextLevel=reader.readLine();
 	  
 	 if (os.contains("Windows")) {
 		  reader2 = new BufferedReader(new FileReader(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/levelList.list"));
 	  } else if (os.contains("Mac")){
 	 	 reader2 = new BufferedReader(new FileReader(System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/levelList.list"));
 	  } else {
 		 reader2 = new BufferedReader(new FileReader(System.getProperty("user.home")+"/MythGame/Maps/levelList.list"));
 	  }
 	 String line2;
 	 boolean addLevel=true;
 	 while ((line2=reader2.readLine()) != null){
 	 if (line2.equals(nextLevel)){
 	 addLevel=false;
 	 }
 	 }
 	  
 	 if (addLevel){
 		 Movement.money = Movement.money + 50;
 		if (os.contains("Windows")) {
 		 writer = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/levelList.list", true));
 		} else if (os.contains("Mac")) {
 	 	writer = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/levelList.list", true));
 		} else {
 			writer = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/MythGame/Maps/levelList.list", true));
 		}
 		 writer.newLine();
 	 writer.write(nextLevel);
 	 writer.flush();
 	 }
 	  
 	 }
 	 }
 	 } catch (IOException e1) {
 	 e1.printStackTrace();
 	 }
 	 
 	 if(Levels.loadedLevel.equals("Custom_Level")){
 	 } else{
 		 System.out.println(Levels.loadedLevel);
 	 try {
 		String path = null;
 		 if (os.contains("Windows")){
 			 path = System.getProperty("user.home")+"/AppData/Roaming/MythGame/";
 		 } else if (os.contains("Mac")){
 			 path = System.getProperty("user.home")+"/Library/Application Support/MythGame/";
 		 } else {
 			path = System.getProperty("user.home")+"/MythGame/";		 
 		 }
 		 BufferedReader read = new BufferedReader(new FileReader(new File(path+"Player_Stats.stats")));
 		 int money = Integer.parseInt(read.readLine());
 		 String line2 = read.readLine();
 		 String line3 = read.readLine();
 		 BufferedWriter write = new BufferedWriter(new FileWriter(new File(path+"Player_Stats.stats")));
 		 write.write(String.valueOf(money+Movement.money));
 		 write.newLine();
 		 write.write(line2);
 		 write.newLine();
 		 write.write(line3);
 		 write.flush();
 		 write.close();
 		 read.close();
 	 } catch(IOException e1){
 		 e1.printStackTrace();
 	 }
 	 }
 	 
 	 
 }
}
 	 
 	  

 	  


 	  
 	  
 


