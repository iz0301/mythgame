package mythgame.multiplayer;

import java.io.IOException;

import javax.swing.JOptionPane;

import mythgame.main.Main;
import mythgame.main.Movement;

public class RunningClient implements Runnable{

	public void run() {
		System.out.println("Ran!");
		while(true){
			//SEND:	
			System.out.println("SEND");
			try {
				Client.outFromClient.writeBytes("position"+'\n');
				Client.outFromClient.writeBytes(String.valueOf(Main.m.x)+ '\n');
				Client.outFromClient.writeBytes(String.valueOf(Main.m.y)+ '\n');
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Server Closed");
				Movement.multiOn = false;
			}
			//RECIVE:
			try {
				String line = null;
				int count = 0;
				switch (Client.fromServer.readLine()){
				case "characters":
					while((line = Client.fromServer.readLine()) != " "){
						if(line.equals(" ")){break;}
						PlayerPanel.multiX.set(count, Integer.parseInt(line));
						PlayerPanel.multiY.set(count, Integer.parseInt(Client.fromServer.readLine()));
						count++;
					}
					count=0;
					break;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}	
}
