package mythgame.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.color.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import mythgame.gui.Levels;
import mythgame.gui.MainMenu;


public class MapMaker extends JPanel implements MouseMotionListener, MouseListener{

	JFrame toolBox = new JFrame();
 boolean listeners=true;
 static ArrayList<String> blockCoords=new ArrayList<String>();
 static ArrayList<String> badGuyCoords=new ArrayList<String>();
 JFrame frame=Start.mythframe;
 BufferedWriter writer;
 boolean finishDoesNotExist=true;
  static int FinishX=-10;
  static int FinishY=-10;
 int x1;
 int y1;
 BufferedWriter badGuyWriter;
 boolean firstLine = true;
 static BufferedImage img;
 static BufferedImage img1;
 static BufferedImage img2;
  int lifeInt;
 Action escapeAction;
 Action FAction;
 boolean eraserOn=false;
 String os = Main.os;
static String tool = "draw"; //TODO: TELL JAMES
static boolean useAutoBadguyLife = false;
 int autoBadguyLife = 0;
 final JTextField badguyDefault = new JTextField(5);
 
 public MapMaker(){

	 Start.mythframe.add(new Arrays());
	 Start.mythframe.revalidate();
	 ToolBox();
	 
  listeners=true;
  frame=Start.mythframe;
  frame.revalidate();
  writer=null;
  finishDoesNotExist=true;
  FinishX=-10;
  FinishY=-10;
  x1=0;
  y1=0;
  badGuyWriter=null;

  img=null;
  img1=null;
  img2=null;
  lifeInt=0;
  eraserOn=false;
 	  
  badGuyCoords.clear();
  blockCoords.clear();
  FinishX=-10;
  FinishY=-10;
 	 
  KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
  escapeAction = new AbstractAction() {
  public void actionPerformed(ActionEvent e) {
  final JFrame esc = new JFrame("Game Paused");
 	 JButton resume = new JButton("Resume");
 	 JButton reset = new JButton("Reset");
 	 JButton mainMenu = new JButton("Main Menu");
 	 JPanel panel = new JPanel();
 	  
 	 panel.add(resume);
 	 panel.add(reset);
 	 panel.add(mainMenu);
 	 esc.add(panel);
 	 esc.setVisible(true);
 	 esc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 	 Dimension size = Toolkit.getDefaultToolkit().getScreenSize();  
 	 esc.setBounds( size.width/2-250, size.height/2-100, 500, 75 );
 	 esc.setAlwaysOnTop(true);
 	 resume.addActionListener(new ActionListener() {
 	 public void	actionPerformed(ActionEvent e){
 	 esc.dispose();
 	 }});
 	 mainMenu.addActionListener(new ActionListener() {
 	 public void	actionPerformed(ActionEvent e){
 	 save();
 	 badGuyCoords.clear();
 	 blockCoords.clear();
 	 listeners=false;
 	 escapeAction.setEnabled(false);
 	 new MainMenu();
 	 toolBox.dispose();
 	 esc.dispose();
 	 }});
 	 reset.addActionListener(new ActionListener() {
 	 public void	actionPerformed(ActionEvent e){
 	 toolBox.dispose();
 	 esc.dispose();
 	 new MapMaker();
 	 
 	 }});
 	  
 	  
  }
  };
 	  
 	  
 frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
 frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);
  try {
 	 if (os.contains("Windows")) {
 		 img1 = ImageIO.read(new File(System.getProperty("user.home")+ "/AppData/Roaming/MythGame/Textures/Texture_Block.png"));
 	 } else if (os.contains("Mac"))  {
 		 img1 = ImageIO.read(new File(System.getProperty("user.home")+ "/Library/Application Support/MythGame/Textures/Texture_block.png"));
 	 } else {
		 img1 = ImageIO.read(new File(System.getProperty("user.home")+ "/MythGame/Textures/Texture_block.png"));
 	 }
 	 } catch (IOException e) {
  
 	 }  
   
 	try {
 	if (os.contains("Windows")) { 
 		img = ImageIO.read(new File(System.getProperty("user.home")+ "/AppData/Roaming/MythGame/Textures/Texture_BadGuy.png"));
 	} else if (os.contains("Mac")){
 		img = ImageIO.read(new File(System.getProperty("user.home")+ "/Library/Application Support/MythGame/Textures/Texture_BadGuy.png"));
 	} else {
 		img = ImageIO.read(new File(System.getProperty("user.home")+ "/MythGame/Textures/Texture_BadGuy.png"));
 	}
 	} catch (IOException e) {
 	 }
 	try {
 	 if (os.contains("Windows")) {
 		img2 = ImageIO.read(new File(System.getProperty("user.home")+ "/AppData/Roaming/MythGame/Textures/Texture_Finish.png"));
 	 } else if (os.contains("Mac")){
  		img2 = ImageIO.read(new File(System.getProperty("user.home")+ "/Library/Application Support/MythGame/Textures/Texture_Finish.png"));
 	 } else {
 		img2 = ImageIO.read(new File(System.getProperty("user.home")+ "/MythGame/Textures/Texture_Finish.png"));
 	 }
 	 } catch (IOException e) {
 	 }
 	Start.mythframe.getContentPane().removeAll();
 	Start.mythframe.revalidate();
 	Start.mythframe.repaint();
 	 try {
   if (os.contains("Windows")) {
 	 badGuyWriter= new BufferedWriter(new FileWriter((System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/Custom_Level/BadGuys.map")));
 	 badGuyWriter.write("-10 -10 0");
 	 badGuyWriter.newLine();
 	 badGuyWriter.flush();
   } else  if (os.contains("Mac")) {
	 	 badGuyWriter= new BufferedWriter(new FileWriter((System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/Custom_Level/BadGuys.map")));
	 	 badGuyWriter.write("-10 -10 0");
	 	 badGuyWriter.newLine();
	 	 badGuyWriter.flush();
   } else {
	   badGuyWriter= new BufferedWriter(new FileWriter((System.getProperty("user.home")+"/MythGame/Maps/Custom_Level/BadGuys.map")));
	 	 badGuyWriter.write("-10 -10 0");
	 	 badGuyWriter.newLine();
	 	 badGuyWriter.flush();
   }
 	 } catch (IOException e1) {
 	 try {
 	 if (os.contains("Windows")) {
 		 new File(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/Custom_Level/BadGuys.map").createNewFile();
 	 badGuyWriter= new BufferedWriter(new FileWriter((System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/Custom_Level/BadGuys.map")));
 	 badGuyWriter.write("-10 -10 0");
 	 badGuyWriter.newLine();
 	 badGuyWriter.flush();
 	 } else  if (os.contains("Mac")) {
 		new File(System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/Custom_Level/BadGuys.map").createNewFile();
 	 	 badGuyWriter= new BufferedWriter(new FileWriter((System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/Custom_Level/BadGuys.map")));
 	 	 badGuyWriter.write("-10 -10 0");
 	 	 badGuyWriter.newLine();
 	 	 badGuyWriter.flush();
 	 } else {
 		new File(System.getProperty("user.home")+"/MythGame/Maps/Custom_Level/BadGuys.map").createNewFile();
	 	 badGuyWriter= new BufferedWriter(new FileWriter((System.getProperty("user.home")+"/MythGame/Maps/Custom_Level/BadGuys.map")));
	 	 badGuyWriter.write("-10 -10 0");
	 	 badGuyWriter.newLine();
	 	 badGuyWriter.flush();
 	 }
 	 } catch (IOException e2) {
 	 e2.printStackTrace();
 	 }
 	 }
 	try {
 	if (os.contains("Windows")) {
 		writer = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/Custom_Level/Custom_Level.map"));
 	} else if (os.contains("Mac"))  {
 		writer = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/Custom_Level/Custom_Level.map"));
 	} else {
 		writer = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/MythGame/Maps/Custom_Level/Custom_Level.map"));
 	}
 	} catch (IOException e1) {
 	 
 	 try {//creates file
 	 if (os.contains("Windows")) {
 		 new File(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/Custom_Level").mkdir();
 	 new File(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/Custom_Level/Custom_Level.map").createNewFile();
 	 } else  if (os.contains("Mac"))  {
 		new File(System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/Custom_Level").mkdir();
 	 	 new File(System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/Custom_Level/Custom_Level.map").createNewFile();
 	 } else {
 		new File(System.getProperty("user.home")+"/MythGame/Maps/Custom_Level").mkdir();
	 	 new File(System.getProperty("user.home")+"/MythGame/Maps/Custom_Level/Custom_Level.map").createNewFile();
 	 }
 	 } catch (IOException e2) {
 	 e2.printStackTrace();
 	 }
 	 
 	 
 	
 	 
 	 
 	 
 	}
   
   
   
 frame.addMouseListener(new MouseListener() {
  public void mousePressed(MouseEvent e) {
  if (listeners){
switch (tool){
  case "draw":

 	 int x = e.getX();
 	 int y = e.getY();
 	 if (blockCoords.contains((x+5)/10+" "+(y+5)/10)==false){
 	 Arrays.blockCoordsAdd((x+5)/10+" "+(y+5)/10);
 	 Start.mythframe.repaint();
 	 }
 	 break;
   
  case "badGuy":
   System.out.println("Badguy");

 	 int x1 = e.getX();
 	 int y1 = e.getY();
   
 	if (isInArray((x1+5)/10, (y1+5)/10)==false){
 	if(useAutoBadguyLife){
 		try {
 		lifeInt =  Integer.parseInt(badguyDefault.getText());
 		} catch(NumberFormatException e2){
 			Toolkit.getDefaultToolkit().beep();
 			JOptionPane.showMessageDialog(frame, "Please check to make sure you have a \n proper integer in the textbox in your toolbox.", "ERROR", JOptionPane.ERROR_MESSAGE);
 		}
 	} else {
 	 String life=JOptionPane.showInputDialog(frame.getContentPane(), "Please enter this BadGuy's life");
 	 Start.mythframe.repaint();
 	 lifeInt = 0;
 	 try {
 	 lifeInt=Integer.parseInt(life);
 	 } catch(NumberFormatException e1){
 		Toolkit.getDefaultToolkit().beep();
 	 JOptionPane.showMessageDialog(null, "Please Try Again, With a Number, ''"+life+"'' is not a valid number.", "ERROR", JOptionPane.ERROR_MESSAGE);
 	 break;
 	 }}
   
 	 Arrays.badGuyCoordsAdd((x1+5)/10+" "+(y1+5)/10+" "+lifeInt);
 	 Start.mythframe.repaint();
   
 	}
 	 break;
case "eraser":
  
 x=e.getX();
 y=e.getY();
  
  for (int loop=0; loop<MapMaker.badGuyCoords.size(); loop++){
  String point=MapMaker.badGuyCoords.get(loop);
  String[] pointArray = point.split(" ");
  int life=Integer.parseInt(pointArray[2]);
 	 
 while (badGuyCoords.contains( (x+5)/10+" "+(y+5)/10+" "+life )) {
 Arrays.badGuyCoordsRemove((x+5)/10+" "+(y+5)/10+" "+life);
Start.mythframe.repaint();
 }}
  
 while (blockCoords.contains( (x+5)/10+" "+(y+5)/10 )) {
 Arrays.blockCoordsRemove((x+5)/10+" "+(y+5)/10);
 Start.mythframe.repaint();
 	} 
 eraserOn=true;
 break;
 
case "finish":
	 BufferedWriter finishWriter = null;
	  try {
	 	  if (os.contains("Windows")) {
	  finishWriter= new BufferedWriter(new FileWriter((System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/Custom_Level/LevelType.map")));
	 	  } else if (os.contains("Mac")) {
	 		  finishWriter= new BufferedWriter(new FileWriter((System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/Custom_Level/LevelType.map")));
	 	  } else {
	 		 finishWriter= new BufferedWriter(new FileWriter((System.getProperty("user.home")+"/MythGame/Maps/Custom_Level/LevelType.map")));
	 	  }
	  } catch (IOException e1) {
	  
		  try {
			  if (os.contains("Windows")) {
			  new File(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/Custom_Level/LevelType.map").createNewFile();
	  finishWriter= new BufferedWriter(new FileWriter((System.getProperty("user.home")+"/AppData/Roaming/MythGame/Maps/Custom_Level/LevelType.map")));
			  } else if (os.contains("Mac")) {
				  new File(System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/Custom_Level/LevelType.map").createNewFile();
				  finishWriter= new BufferedWriter(new FileWriter((System.getProperty("user.home")+"/Library/Application Support/MythGame/Maps/Custom_Level/LevelType.map")));	  
			  } else{
				  new File(System.getProperty("user.home")+"/MythGame/Maps/Custom_Level/LevelType.map").createNewFile();
				  finishWriter= new BufferedWriter(new FileWriter((System.getProperty("user.home")+"/MythGame/Maps/Custom_Level/LevelType.map")));	  
			  }
		  } catch (IOException e2) {
	  e2.printStackTrace();
	  }
	  }

	 	  
	  Point mouse=MouseInfo.getPointerInfo().getLocation();
	  FinishX = (int)mouse.getX();
	  FinishY = (int)mouse.getY();
	 	  
	  try {
	  finishWriter.write("FinishArea");
	  finishWriter.flush();
	  finishWriter.newLine();
	 	  
	 	 
	  Start.mythframe.repaint();
	  finishWriter.write((FinishX+5)/10+" "+((FinishY+5)/10));
	  finishWriter.flush();
	 	  
	  } catch (IOException e1) {
	  
	  e1.printStackTrace();
	 	 } 
	break;
 	 }
 	 }
  frame.getContentPane().removeAll();
  frame.add(new Arrays());
  frame.revalidate();
  frame.repaint();
  }

private boolean isInArray(int x, int y) {
 boolean returnvalue=false;
  
  for (int loop=0; loop<MapMaker.badGuyCoords.size(); loop++){
 	 String point=MapMaker.badGuyCoords.get(loop);
 	 String[] pointArray = point.split(" ");
 	 int life=Integer.parseInt(pointArray[2]);
 	 if (badGuyCoords.contains(x+" "+y+" "+life)){
 	 returnvalue=true;
 	 }
  }
 return returnvalue;
}

@Override
public void mouseClicked(MouseEvent e) {
  
  
}

@Override
public void mouseReleased(MouseEvent e) {
  
  
}

@Override
public void mouseEntered(MouseEvent e) {
  
  
}

@Override
public void mouseExited(MouseEvent e) {
  
  
}});


  
 frame.addMouseMotionListener(new MouseMotionListener() {
  public void mouseDragged(MouseEvent e) {  
  if (listeners){
  if (tool == "eraser"){
 	 int x=e.getX();
 	 int y=e.getY();
 	  
 	 for (int loop=0; loop<MapMaker.badGuyCoords.size(); loop++){
 	 String point=MapMaker.badGuyCoords.get(loop);
 	 String[] pointArray = point.split(" ");
 	 int life=Integer.parseInt(pointArray[2]);
 	  
 	 while (badGuyCoords.contains( (x+5)/10+" "+(y+5)/10+" "+life )) {
 	 Arrays.badGuyCoordsRemove((x+5)/10+" "+(y+5)/10+" "+life);
 	 Start.mythframe.repaint();
 	 }}
 	  
 	 while (blockCoords.contains( (x+5)/10+" "+(y+5)/10 )) {
 	 Arrays.blockCoordsRemove((x+5)/10+" "+(y+5)/10);
 	 Start.mythframe.repaint();
 	 } 
  }
 	  
  if (tool == "draw"){
 	 int x = e.getX();
 	 int y = e.getY();
   
 	 if (blockCoords.contains((x+5)/10+" "+(y+5)/10)==false){
 	 Arrays.blockCoordsAdd((x+5)/10+" "+(y+5)/10);
 	// Start.mythframe.add(new Arrays());
 	 Start.mythframe.repaint();
 	 }
  }
   
 }
  frame.getContentPane().removeAll();
  frame.add(new Arrays());
  frame.revalidate();
  frame.repaint();
  }

 	@Override
 	public void mouseMoved(MouseEvent arg0) {
 	  
   
 	}
 });
 }
 @Override
 public void mouseDragged(MouseEvent e) {
 	 
   
 }
 @Override
 public void mouseMoved(MouseEvent e) {
 	 
   
   
 	 }
  

 	public void save(){
  
 	 try {
 	 for (int loop = 0; loop<badGuyCoords.size(); loop++){
 	 badGuyWriter.write(badGuyCoords.get(loop));
 	 badGuyWriter.flush();
 	 badGuyWriter.newLine();
 	 }
 	 for (int loop = 0; loop<blockCoords.size(); loop++){
 	 writer.write(blockCoords.get(loop));
 	 writer.flush();
 	 writer.newLine();
 	 }
 	 } catch (IOException e1) {
 	 
 	 e1.printStackTrace();
  } 

 	  
 	}
 @Override
 public void mouseClicked(MouseEvent e) {
 	 
 	 
 }
 @Override
 public void mousePressed(MouseEvent e) {
 	 
 	 
 }
 @Override
 public void mouseReleased(MouseEvent e) {
 	 
 	 
 }
 @Override
 public void mouseEntered(MouseEvent e) {
 	 
 	 
 }
 @Override
 public void mouseExited(MouseEvent e) {
 	 
 	 
 }
  
public void ToolBox(){
	 toolBox = new JFrame("Tool Box");
	 JPanel toolpanel = new JPanel();
	final JButton eraser = new JButton("Eraser");
	final JButton draw = new JButton("Draw");
	final JButton badGuy = new JButton("Bad Guy");
	final JButton finish = new JButton("Add Finish");
	final JCheckBox badguyCheck = new JCheckBox();
	badguyDefault.setText("50");
	toolpanel.add(draw);
	toolpanel.add(badGuy);
	toolpanel.add(eraser);
	toolpanel.add(finish);
	toolpanel.add(new JLabel("Auto Badguy Life:"));	
	toolpanel.add(badguyDefault);
	toolpanel.add(new JLabel("Enable:"));
	toolpanel.add(badguyCheck);
	
	toolBox.add(toolpanel);
	 toolBox.setSize(200, 125);
	 toolBox.setLocation(frame.getWidth()-200, frame.getHeight()-125);
	 toolBox.setResizable(false);
	 toolBox.setUndecorated(true);
	 toolBox.setFocusable(false);
	 try {
	 toolBox.setOpacity((float) 0.5);
	 }catch (java.lang.UnsupportedOperationException e){
		 System.out.println("translucency not supported");
		 toolBox.setOpacity((float) 1.0f);
	 }
	 toolBox.setAlwaysOnTop(true);
	 toolBox.setVisible(true);
	 toolBox.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	 
	 badguyCheck.addActionListener(new ActionListener(){
		 public void actionPerformed(ActionEvent e){
			useAutoBadguyLife = badguyCheck.isSelected(); 
			if(useAutoBadguyLife){
				autoBadguyLife = Integer.parseInt(badguyDefault.getText());
				System.out.println(autoBadguyLife);
			}
		 }
	 });
	 
	 
	ActionListener buttons = new ActionListener(){
			 public void actionPerformed(ActionEvent e){
				 draw.setEnabled(true);
				 eraser.setEnabled(true);
				 finish.setEnabled(true);
				 badGuy.setEnabled(true);
				 if (e.getSource() == draw){
					 tool = "draw";
					 draw.setEnabled(false);
				 }
				 if (e.getSource() == eraser){
					 tool = "eraser";
					 eraser.setEnabled(false);
					 }
				 if (e.getSource() == finish){
					 tool = "finish";
					 finish.setEnabled(false);
					 }
				 if (e.getSource() == badGuy){
					 tool = "badGuy";
					 badGuy.setEnabled(false);
					 }
				 
			 }};
			 
			 draw.addActionListener(buttons);
			 eraser.addActionListener(buttons);
			 finish.addActionListener(buttons);
			 badGuy.addActionListener(buttons);
	
}

 }
