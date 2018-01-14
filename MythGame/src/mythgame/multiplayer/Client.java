package mythgame.multiplayer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import mythgame.gui.MainMenu;
import mythgame.main.Main;
import mythgame.main.Movement;
import mythgame.main.Start;
import mythgame.objective.DrawFinish;
import mythgame.objective.FinishArea;

public class Client {
	
	public static DataOutputStream outFromClient;
	public static BufferedReader fromServer; 
	public static int clientNum;
	
	public Client(String ipAddress) throws UnknownHostException, IOException{
		Socket client = null;
		System.out.println("Client");
		try {
		client = new Socket(ipAddress, 25580);
		System.out.println("Client Connected");
		} catch (IOException e){
			JOptionPane.showMessageDialog(null, "Unknown IP: "+ipAddress);
			new MainMenu();
		}

		Start.mythframe.getContentPane().removeAll();
		
		
	 	 Movement.life=Movement.MaxHealth;
	 	 Movement.badGuyLifeMax.clear();
	 	 Movement.badGuyLife.clear();
	 	 Start.badGuyCoordsX.clear();
	 	 Start.badGuyCoordsY.clear();
	 	 Main.m.LastAttacked=0;
	 	 Main.m.beingAttacked=false;
	 	 Main.m.x=0;
	 	 Main.m.y=0;
	 	 Main.m.velX=0;
	 	 Main.m.velY=0;
	 	 Main.m.wPressed=false;
	 	 Main.m.dPressed=false;
	 	 Main.m.aPressed=false;
	 	Movement.multiOn=true;
	 	 Server.serverOn=false;
	 	 
	 	fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
	 	outFromClient = new DataOutputStream(client.getOutputStream());
	 	
	 	System.out.println("Reader esstablished");
	 	
	 	clientNum = Integer.parseInt(fromServer.readLine());
	 	
	 	System.out.println("Loading level...");
		String line = null;
		while ((line = fromServer.readLine()) != " "){
			if(line.equals(" ")){break;}//THis new to James
			Start.Builder(Integer.parseInt(line), Integer.parseInt(fromServer.readLine()), "block");
			
			
		}
		line = null;
		while ((line = fromServer.readLine()) != " "){
			if(line.equals(" ")){break;}//THis new to James
			Start.badGuyCoordsX.add(Integer.parseInt(line));
			Start.badGuyCoordsY.add(Integer.parseInt(fromServer.readLine()));
			Movement.badGuyLife.add(Integer.parseInt(fromServer.readLine()));
			Movement.badGuyLifeMax.add(Integer.parseInt(fromServer.readLine()));
			Start.mythframe.revalidate();
		}
		line = null;
		 switch (fromServer.readLine()){
		 case("FinishArea"):
			FinishArea.FinishX=Integer.parseInt(fromServer.readLine())*10-5;
		 	FinishArea.FinishY=Integer.parseInt(fromServer.readLine())*10-5;
			Start.mythframe.add(new DrawFinish((FinishArea.FinishX+5)/10, (FinishArea.FinishY+5)/10));
			Start.mythframe.revalidate();
		 }
		 System.out.println((FinishArea.FinishX+5)/10+", "+ (FinishArea.FinishY+5)/10);
		 System.out.println(FinishArea.FinishX);
		 System.out.println(FinishArea.FinishY);
		 try {//Stats
			 BufferedReader attackReader=null;
			 if (Main.os.contains("Windows")) {
				  attackReader = new BufferedReader(new FileReader(System.getProperty("user.home")+ "/AppData/Roaming/MythGame/Player_Stats.stats"));
			  } else  if (Main.os.contains("Windows")){
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
		 System.out.println("Done!");
		//TODO: Get Finish area/objective
		 System.out.println("Starting Game");
			Main.m.tm.start();
			Movement.money=0;
			Start.mythframe.add(Main.m);
		 	Start.mythframe.setVisible(false);
		 	Start.mythframe.setVisible(true);
			Start.mythframe.revalidate();
			System.out.println("Game Started");
			PlayerPanel.multiX.add(-10);
			PlayerPanel.multiX.add(-10);
			PlayerPanel.multiX.add(-10);
			PlayerPanel.multiX.add(-10);
			PlayerPanel.multiX.add(-10);
			PlayerPanel.multiX.add(-10);
			PlayerPanel.multiX.add(-10);
			PlayerPanel.multiX.add(-10);
			PlayerPanel.multiX.add(-10);
			PlayerPanel.multiX.add(-10);
			PlayerPanel.multiY.add(-10);
			PlayerPanel.multiY.add(-10);
			PlayerPanel.multiY.add(-10);
			PlayerPanel.multiY.add(-10);
			PlayerPanel.multiY.add(-10);
			PlayerPanel.multiY.add(-10);
			PlayerPanel.multiY.add(-10);
			PlayerPanel.multiY.add(-10);
			PlayerPanel.multiY.add(-10);
			PlayerPanel.multiY.add(-10);
			PlayerPanel p = new PlayerPanel();
			new Thread(p).start();
			Start.mythframe.revalidate();
			new Thread(new RunningClient()).start();
	} 
}
