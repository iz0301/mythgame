package mythgame.multiplayer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import mythgame.gui.Levels;
import mythgame.main.Main;
import mythgame.main.Movement;
import mythgame.main.Start;
import mythgame.objective.FinishArea;

public class playServer implements Runnable{
	private int clientNum;
	private BufferedReader in;
	private DataOutputStream out;

	public playServer(Socket server, int clientNum) throws IOException{
		this.clientNum=clientNum;
		out = new DataOutputStream(server.getOutputStream());
		out.writeBytes(String.valueOf(clientNum)+'\n');			//ORDER: BLOCKS, BADGUYS
		out.writeBytes(String.valueOf(0)+'\n');
		out.writeBytes(String.valueOf(0)+'\n');
		for (int loop=1; loop<Start.blockCoordsX.size(); loop++){//x, y
			out.writeBytes(String.valueOf((Start.blockCoordsX.get(loop)+5)/10)+'\n');
			out.writeBytes(String.valueOf((Start.blockCoordsY.get(loop)+5)/10)+'\n');
		}
		out.writeBytes(" "+'\n');
		out.writeBytes(String.valueOf(0)+'\n');
		out.writeBytes(String.valueOf(0)+'\n');
		out.writeBytes(String.valueOf(0)+'\n');
		out.writeBytes(String.valueOf(0)+'\n');
		for (int loop=1; loop<Start.badGuyCoordsX.size(); loop++){//x, y, life, max
			out.writeBytes(String.valueOf(Start.badGuyCoordsX.get(loop))+'\n');
			out.writeBytes(String.valueOf(Start.badGuyCoordsY.get(loop))+'\n');
			out.writeBytes(String.valueOf(Movement.badGuyLife.get(loop))+'\n');
			out.writeBytes(String.valueOf(Movement.badGuyLifeMax.get(loop))+'\n');
		}
		out.writeBytes(" "+'\n');
		out.writeBytes(Levels.objective+'\n');
		switch (Levels.objective){
		case("FinishArea"):
			out.writeBytes(String.valueOf((FinishArea.FinishX+5)/10)+'\n');
			out.writeBytes(String.valueOf((FinishArea.FinishY+5)/10)+'\n');
		}

		in = new BufferedReader(new InputStreamReader(server.getInputStream()));
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
		
	}

	public void run() {
		while (true){
			PlayerPanel.multiX.clear();
			PlayerPanel.multiY.clear();
			System.out.println(System.currentTimeMillis()+" Setting Pos");
			System.out.println("-");
			System.out.println(System.currentTimeMillis()+" Setting Pos Mine");
			PlayerPanel.multiX.set(0, Main.m.x);
			PlayerPanel.multiY.set(0, Main.m.y);
			System.out.println(System.currentTimeMillis()+" Setting Pos Mine Done");
			System.out.println("-");
			System.out.println(System.currentTimeMillis()+" Setting Pos Client");

//RECIVE:
			try {
				
				String ln;
				ln=in.readLine();
				System.out.println(ln);
				switch (ln){
			
				case "position":
					PlayerPanel.multiX.set(clientNum, Integer.parseInt(in.readLine()));
					System.out.println(System.currentTimeMillis()+" Setting Pos Client 50% done");
					PlayerPanel.multiY.set(clientNum, Integer.parseInt(in.readLine()));
					break;
				}
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}//LAG IS MASIVE
			System.out.println(System.currentTimeMillis()+" Setting Pos Client Done");
			System.out.println("-");
			System.out.println(System.currentTimeMillis()+" Done Setting Pos");
//SEND:
			try{
				out.writeBytes("characters"+'\n');
				for (int loop = 0; loop < PlayerPanel.multiX.size(); loop++){
					out.writeBytes(String.valueOf(PlayerPanel.multiX.get(loop))+'\n');
					out.writeBytes(String.valueOf(PlayerPanel.multiY.get(loop))+'\n');
				}
				out.writeBytes(" "+'\n');
			}catch(IOException e){
				e.printStackTrace();
			}
			System.out.println(" ");
		}
	}
}