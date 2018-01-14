package mythgame.multiplayer;

import java.io.IOException;
import java.net.Socket;

public class Connection implements Runnable{

	public void run() {
		
		int count = 1;
		
		while (true){
			try {
				
				new Thread(new playServer(Server.server.accept(), count)).start();
				count++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
