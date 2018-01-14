package mythgame.main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import mythgame.files.Downloader;
import mythgame.files.FolderZipper;
import mythgame.gui.MainMenu;

import java.util.ArrayList;
  
public class Start extends JPanel {
  public static JFrame mythframe = new JFrame();
  public static ArrayList<Integer> blockCoordsX = new ArrayList<Integer>();
  public static ArrayList<Integer> blockCoordsY = new ArrayList<Integer>();
  public static ArrayList<Integer> badGuyCoordsX = new ArrayList<Integer>();
  public static ArrayList<Integer> badGuyCoordsY = new ArrayList<Integer>();
 public static BufferedImage img = null;

   
  public static void setup() {
 	  
  try {
	  if(Main.os.contains("Windows")){
		  img = ImageIO.read(new File(System.getProperty("user.home")+ "/AppData/Roaming/MythGame/Textures/Texture_Block.png"));
	  } else if(Main.os.contains("Mac")){
		  img = ImageIO.read(new File(System.getProperty("user.home")+ "/Library/Application Support/MythGame/Textures/Texture_Block.png"));
		  
	  } else {
		  img = ImageIO.read(new File(System.getProperty("user.home")+ "/MythGame/Textures/Texture_Block.png"));		  
	  }
	  
  } catch (IOException e) {
  }
 	  
  mythframe.setTitle("MythGame");
   
  //mythframe.setUndecorated(true);
  //Dimension size = Toolkit.getDefaultToolkit().getScreenSize();  
 // mythframe.setBounds( 0, 0, size.width, size.height );
 // mythframe.setBounds( 20, 20, 300, 300 );

  mythframe.setExtendedState(Frame.MAXIMIZED_BOTH);
   
  mythframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setLookAndFeel();
  mythframe.setVisible(true);
  mythframe.addWindowStateListener(new WindowStateListener(){

	@Override
	public void windowStateChanged(WindowEvent arg0) {
		int s=(mythframe.getWidth()<mythframe.getWidth()?mythframe.getWidth():mythframe.getHeight());
		Main.WD=s/10;
		Main.HT=s/10;
	}});
  }
  static void setLookAndFeel() {
  try {
  UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
 	  
  } catch (Exception exc) {
  System.out.println(exc.getMessage());
  }
 	  
  }
  public static void Builder(int x, int y, String type){
  if (type=="block"){
  blockCoordsX.add(x*10-5);
  blockCoordsY.add(y*10-5);
 // mythframe.add(new Drawstuff(0, 0, img));
 // mythframe.revalidate();
  }

  }
}