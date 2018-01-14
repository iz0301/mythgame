package mythgame.gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mythgame.files.Delete;
import mythgame.files.Downloader;
import mythgame.files.FolderZipper;
import mythgame.files.GameUpdate;
import mythgame.main.Main;
import mythgame.main.MapMaker;
import mythgame.main.Start;
import mythgame.multiplayer.Client;

public class MainMenu {
	
	public static JFrame frame = Start.mythframe;
	public static JPanel mainmenu = new JPanel();
	public static JButton play = new JButton("Play");
	public static JButton shop = new JButton("Shop");
	public static JButton forceupdate = new JButton("Force Update");
	public static JButton mapMaker = new JButton("Map Maker");
	public static JButton exit = new JButton("Exit");
	public static JButton multiplayer = new JButton("Multiplayer");
	
	String os = Main.os;
	
	public MainMenu() {
			
		try{
		frame.removeMouseListener(frame.getMouseListeners()[0]);
		frame.removeMouseMotionListener(frame.getMouseMotionListeners()[0]);
		} catch(ArrayIndexOutOfBoundsException e){
		}
		 	Start.blockCoordsX.clear();
		 	Start.blockCoordsY.clear();

		 	Start.badGuyCoordsX.clear();
		 	Start.badGuyCoordsY.clear();
		 	
		 	Main.m.tm.stop();
		 	

		frame.getContentPane().removeAll();
		

		
		frame.repaint();
		mainmenu.add(play);
		mainmenu.add(shop);
		mainmenu.add(forceupdate);
		mainmenu.add(mapMaker);
		mainmenu.add(multiplayer);
		mainmenu.add(exit);
		mainmenu.setOpaque(false);
		frame.add(mainmenu);
		
		frame.revalidate();
		
		JLabel label = new JLabel();
		BufferedImage pic = null;
		try {
			if (Main.os.contains("Windows")){
				pic = ImageIO.read(new File(System.getProperty("user.home")+"\\AppData\\Roaming\\MythGame\\Textures\\Title.png"));
			} else if (Main.os.contains("Mac")){
				pic = ImageIO.read(new File(System.getProperty("user.home")+"/Library/Application Support/MythGame/Textures/Title.png"));
			}else {
				pic = ImageIO.read(new File(System.getProperty("user.home")+"/MythGame/Textures/Title.png"));				
			}
		} catch (IOException e2) {
			frame.setVisible(false);
			JOptionPane.showMessageDialog(null, "We have detected you do not have the \nNessasary files to play MythGame.\nPress \"OK\" to download them automaticly.");
		 	 new Downloader();
		 	 new FolderZipper();
		 	 JOptionPane.showMessageDialog(null, "Game Install Complete! Please press \"OK\" and restart.");
		 	 System.exit(0);
		}
		int small;
		if(frame.getWidth()<frame.getHeight()){
			small=frame.getWidth();
		} else if (frame.getHeight()<frame.getWidth()){
			small=frame.getHeight();
		} else {
			small=frame.getWidth();
		}
		Image icon = pic.getScaledInstance(small, small, 100);
		JPanel pane = new JPanel();
		label.setIcon(new ImageIcon(icon));
		pane.add(label);
		label.setBounds(0, 0, pane.getWidth(), pane.getHeight());
		pane.setBackground(Color.black);
		frame.add(pane);
		mainmenu.repaint();
		frame.revalidate();
		mainmenu.repaint();
		
		play.addActionListener(new ActionListener() {
		public void	actionPerformed(ActionEvent e){
		    try {
				new Levels();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("An error has occoured");
			}
		}});
		
		shop.addActionListener(new ActionListener() {
			public void	actionPerformed(ActionEvent e){
				frame.getContentPane().removeAll();
				frame.repaint();
				new Shop();
				new Inventory();
			}});
		
		forceupdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int n = JOptionPane.showConfirmDialog(frame, "Are you sure you would like to Force Update, doing so would cause all progress to be lost and costom levels deleted.", "Force Update", JOptionPane.OK_CANCEL_OPTION);
				if (n==0){
					new Downloader();
					if (os.contains("Windows")){
						File file = new File(System.getProperty("user.home")+"/AppData/Roaming/Mythgame");
					 	Delete.removeDirectory(file);
					} else if (os.contains("Mac")){
						File file = new File(System.getProperty("user.home")+"/Library/Application Support/Mythgame");
					 	Delete.removeDirectory(file);
					} else {
						File file = new File(System.getProperty("user.home")+"/Mythgame");
					 	Delete.removeDirectory(file);
					}
					
					new FolderZipper();
					new GameUpdate();
				}
			}
		});
		mapMaker.addActionListener(new ActionListener() {
			public void	actionPerformed(ActionEvent e){
				frame.getContentPane().removeAll();
				frame.repaint();
				frame.revalidate();
				new MapMaker();
			}});
		exit.addActionListener(new ActionListener() {
			public void	actionPerformed(ActionEvent e){
				System.exit(0);
			}});
		multiplayer.addActionListener(new ActionListener(){
		 	 public void actionPerformed(ActionEvent e) {
		 	// frame.getContentPane().removeAll();
		 	 String ipAddress = JOptionPane.showInputDialog("Enter ip address");
		 	 try {
				new Client(ipAddress);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "ERROR: "+e1.getMessage());
			}
		 	 }
		 	});
	}
}