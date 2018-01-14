package mythgame.files;

import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import mythgame.main.Main;
import mythgame.main.Start;

public class Updater {
	private ArrayList<Integer> statsByte = new ArrayList<Integer>();
	private ArrayList<Integer> levelByte = new ArrayList<Integer>();
	private ArrayList<Integer> armorByte = new ArrayList<Integer>();
	private ArrayList<Integer> weaponsByte = new ArrayList<Integer>();
	
	public Updater() throws IOException {
	
		FileOutputStream out;
		BufferedReader reader = null;
		URL url = new URL("https://sites.google.com/site/izjemythgame/newpage/Update.txt");
		  URLConnection conn1 = url.openConnection();
		  InputStream in = conn1.getInputStream();
		  if (Main.os.contains("Windows")){
			  out = new FileOutputStream(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Update.txt");
		  } else if(Main.os.contains("Mac")){
			  out  = new FileOutputStream(System.getProperty("user.home")+"/Library/Application Support/MythGame/Update.txt");
		 } else {
			 out  = new FileOutputStream(System.getProperty("user.home")+"/MythGame/Update.txt");
		 }
		  byte[] b = new byte[1024];
		  int count;
		  while ((count = in.read(b)) >= 0) {
		  out.write(b, 0, count);
		  }
		  out.flush(); out.close(); in.close();  
		  
		  if (Main.os.contains("Windows")){
			  reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Update.txt"));
		  } else if (Main.os.contains("Mac")){
			  reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/Library/Application Support/MythGame/Update.txt"));
		  } else {
			  reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/MythGame/Update.txt"));
		  }
		  
		String type = reader.readLine();
		String version = reader.readLine();
		
		if (version.equals(Main.version)){
			
		} else {
			Toolkit.getDefaultToolkit().beep();
			int confirm = JOptionPane.showConfirmDialog(null, "Do you wish to update to version "+version+", this will update your ."+type+"?", "Update Now?", JOptionPane.YES_NO_OPTION);
			if (confirm == 0){
				switch(type){
				case "zip":
					saveFiles();
					new GameUpdate();
					new Downloader();
					new Delete();
					new FolderZipper();
					restoreFiles();
					break;
				case "jar":
					new GameUpdate();
					break;
				}
			}
			Start.mythframe.revalidate();
			Start.mythframe.repaint();
		}
	}	
	
	public void saveFiles() throws IOException{
		String path = null;
		if (Main.os.contains("Windows")){
			path = System.getProperty("user.home")+"/AppData/Roaming/MythGame/";
		} else if (Main.os.contains("Mac")){
			path = System.getProperty("user.home")+"/Library/Application Suppourt/MythGame/";
		} else {
			path = System.getProperty("user.home")+"MythGame";
		}
		BufferedInputStream stats = new BufferedInputStream(new FileInputStream(path+"/Player_Stats.stats"));
		BufferedInputStream level = new BufferedInputStream(new FileInputStream(path+"/Maps/levelList.list"));
		BufferedInputStream armor = new BufferedInputStream(new FileInputStream(path+"/Shop/Purchased/Armor.shop"));
		BufferedInputStream weapons = new BufferedInputStream(new FileInputStream(path+"/Shop/Purchased/Weapons.shop"));
		int byt = 0;
		while ((byt = stats.read())!=-1){
			statsByte.add(byt);
		}
		while ((byt = level.read())!=-1){
			levelByte.add(byt);
		}
		while ((byt = armor.read())!=-1){
			armorByte.add(byt);
		}
		while ((byt = weapons.read())!=-1){
			weaponsByte.add(byt);
		}
		stats.close();
		level.close();
		armor.close();
		weapons.close();
	}

	public void restoreFiles() throws IOException{
		String path = null;
		if (Main.os.contains("Windows")){
			path = System.getProperty("user.home")+"/AppData/Roaming/MythGame/";
		} else if (Main.os.contains("Mac")){
			path = System.getProperty("user.home")+"/Library/Application Suppourt/MythGame/";
		} else {
			path = System.getProperty("user.home")+"MythGame";
		}
		BufferedOutputStream stats = new BufferedOutputStream(new FileOutputStream(path+"/Player_Stats.stats"));
		BufferedOutputStream level = new BufferedOutputStream(new FileOutputStream(path+"/Maps/levelList.list"));
		BufferedOutputStream armor = new BufferedOutputStream(new FileOutputStream(path+"/Shop/Purchased/Armor.shop"));
		BufferedOutputStream weapons = new BufferedOutputStream(new FileOutputStream(path+"/Shop/Purchased/Weapons.shop"));
		
		for(int b: statsByte){
			stats.write(b);
		}
		for(int b: levelByte){
			level.write(b);
		}
		for(int b: armorByte){
			armor.write(b);
		}
		for(int b: weaponsByte){
			weapons.write(b);
		}
		
		stats.close();
		level.close();
		armor.close();
		weapons.close();
	}
}
