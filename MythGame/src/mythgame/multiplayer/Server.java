package mythgame.multiplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import mythgame.main.Movement;



public class Server {


	public static ServerSocket server=null;
	public static boolean serverOn;
	
	public Server()  throws IOException {
		
		/*
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	        	
	            desktop.browse(new URI("http://192.168.0.1/Forward.htm"));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
        */
	   
	    
        Movement.multiOn=true;
        
		try {
			server = new ServerSocket(25580);
			serverOn=true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "ERROR: Port in use, please close all other running MythGame servers.");
		}
		Thread threadconnect = new Thread(new Connection());
		threadconnect.start();
	}
}
