package mythgame.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import mythgame.main.Main;
import mythgame.main.Start;

public class Inventory {

	 
	JFrame frame = Start.mythframe;
	JPanel weapons = new JPanel();
	JPanel armor = new JPanel();
	JPanel equipped = new JPanel();
	JPanel equippedArmor = new JPanel();
	JPanel equippedWeapon = new JPanel();
	JPanel player = new JPanel();
	JPanel menu = new JPanel();
	Buttons armorList = null;
	Buttons weaponsList;
	
	public Inventory() {
		frame.setLayout(new BorderLayout());
		frame.add(weapons, BorderLayout.LINE_START);
		frame.add(armor, BorderLayout.LINE_END);
		frame.add(equipped, BorderLayout.PAGE_END);
		frame.add(menu, BorderLayout.NORTH);
		frame.add(player, BorderLayout.CENTER);
		
		JButton mainMenu = new JButton("MainMenu");
		mainMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new MainMenu();	
			}
		});		
		JButton shop = new JButton("Back to shop");
		shop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new Shop();	
			}
		});
		menu.add(mainMenu);
		menu.add(shop);
		
		equipped.setLayout(new GridLayout(1, 2));
		equipped.add(equippedArmor);
		equipped.add(equippedWeapon);
		
		armor.setLayout(new BoxLayout(armor, BoxLayout.Y_AXIS));
		weapons.setLayout(new BoxLayout(weapons, BoxLayout.Y_AXIS));
		
		//equippedArmor.setLayout(new BoxLayout(equippedArmor, BoxLayout.Y_AXIS));
		//equippedWeapon.setLayout(new BoxLayout(equippedWeapon, BoxLayout.Y_AXIS));
		
		JLabel label = new JLabel("Armor");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		armor.add(label);

		label = new JLabel("Weapons");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		weapons.add(label);
		
		equippedArmor.add(new JLabel("Current Armor"));
		equippedWeapon.add(new JLabel("Current Weapon"));
		
		armorList = Buttonerize(armor, "Shop/Purchased/Armor.shop", new ActionListener(){
		 	 public void actionPerformed(ActionEvent e){
		 		try{
		 		 	 BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Player_Stats.stats"));
		 		 	 String line1 = reader.readLine();
		 		 	 String line2 = reader.readLine();
		 		 	  
		 		 	 BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Player_Stats.stats"));
		 		 	 writer.write(line1);
		 		 	 writer.newLine();
		 		 	 writer.write(line2);
		 		 	 writer.newLine();
		 		 	 writer.flush();
		 		 	 for(int loop = 0; loop<armorList.buttons.size(); loop++){
		 		 	 if(armorList.buttons.get(loop)==e.getSource()){
		 		 	 writer.write(String.valueOf(armorList.value.get(loop)));
		 		 	 writer.flush();
		 		 	 }
		 		 	 }
		 		 	 }catch(IOException e1){
		 		 	 e1.printStackTrace();
		 		 	 }
		 		
		 	 equippedArmor.removeAll();
		 	 equippedArmor.add(new JLabel("Current Armor"));
		 	 for (int loop = 0; loop<armorList.buttons.size(); loop++){
		 	 if (e.getSource() == armorList.buttons.get(loop)){
		 	 JButton button = new JButton(armorList.buttons.get(loop).getText());
		 	// button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		 	 button.setIcon(armorList.buttons.get(loop).getIcon());
		 	 equippedArmor.add(button);
		 	 equippedArmor.revalidate();
		 	equippedArmor.repaint();
		 	 frame.revalidate();
		 	 frame.repaint();
		 	 }
		 	 }}});
		 	 
		 	weaponsList = Buttonerize(weapons, "Shop/Purchased/Weapons.shop", new ActionListener(){
		 	 public void actionPerformed(ActionEvent e){
		 		try{
		 			String path = "";
		 			if(Main.os.contains("Windows")){
		 				path = System.getProperty("user.home")+"/AppData/Roaming/MythGame/Player_Stats.stats";
		 			} else if(Main.os.contains("Mac")){
		 				path = System.getProperty("user.home")+"/Library/Application Support/MythGame/Player_Stats.stats";
		 			} else {
		 				path = System.getProperty("user.home")+"/MythGame/Player_Stats.stats";
		 			}
		 		 	 BufferedReader reader = new BufferedReader(new FileReader(path));
		 		 	 String line1 = reader.readLine();
		 		 	 reader.readLine();
		 		 	 String line3 = reader.readLine();
		 		 	  
		 		 	 BufferedWriter writer = new BufferedWriter(new FileWriter(path));
		 		 	 writer.write(line1);
		 		 	 writer.newLine();
		 		 	 writer.flush();
		 		 	 for(int loop = 0; loop<weaponsList.buttons.size(); loop++){
		 		 	 if(weaponsList.buttons.get(loop)==e.getSource()){
		 		 	 writer.write(String.valueOf(weaponsList.value.get(loop)));
		 		 	 writer.flush();
		 		 	 }
		 		 	 }
		 		 	 writer.newLine();
		 		 	 writer.write(line3);
		 		 	 writer.flush();
		 		 	 }catch(IOException e1){
		 		 	 e1.printStackTrace();
		 		 	 }

		 		
		 	 equippedWeapon.removeAll();
		 	 equippedWeapon.add(new JLabel("Current Weapon"));
		 	 for (int loop = 0; loop<weaponsList.buttons.size(); loop++){
		 	 if (e.getSource() == weaponsList.buttons.get(loop)){
		 	 JButton button = new JButton(weaponsList.buttons.get(loop).getText());
		 	 button.setIcon(weaponsList.buttons.get(loop).getIcon());
		 	//button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		 	 equippedWeapon.add(button);
		 	 equippedWeapon.revalidate();
		 	 equippedWeapon.repaint();
		 	 frame.revalidate();
		 	 frame.repaint();
		 	 }
		 	 }
		 	 }
		 	});
		
		
		BufferedImage img1 = null;
		  try {
			  if (Main.os.contains("Windows")) {
				  img1 = ImageIO.read(new File(System.getProperty("user.home")+ "/AppData/Roaming/MythGame/Textures/Texture_Character.png"));
			  } else if (Main.os.contains("Mac")){
				  img1 = ImageIO.read(new File(System.getProperty("user.home")+ "/Library/Application Support/MythGame/Textures/Texture_Character.png"));
			  } else {
				  img1 = ImageIO.read(new File(System.getProperty("user.home")+ "/MythGame/Textures/Texture_Character.png"));
			  }
			  } catch (IOException e) {
			  }  
		 Image img = img1.getScaledInstance(100, 100, 100);
		 player.add(new JLabel(new ImageIcon(img)));
		frame.revalidate();
	}
	


public static Buttons Buttonerize(JPanel panel, String fileToRead, ActionListener action) {
	 ArrayList<Integer> type = new ArrayList<Integer>();
	 ArrayList<Integer> value = new ArrayList<Integer>();
	 ArrayList<Integer> price = new ArrayList<Integer>();
	 ArrayList<JButton> buttons = new ArrayList<JButton>();
	 
		 BufferedReader reader = null;
		String os = Main.os;
		ImageIcon icon;
	 	try {
	 	if (os.contains("Windows")) {
	 		reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/AppData/Roaming/MythGame/"+fileToRead));
	 	} else if ((os.contains("Mac"))){
	 		reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/Library/Application Support/MythGame/"+fileToRead));
	 	} else {
	 		reader = new BufferedReader(new FileReader(System.getProperty("user.home")+"/MythGame/"+fileToRead));
	 	}
	 	} catch (IOException e) {
	 	 // TODO Auto-generated catch block
	 	 e.printStackTrace();
	 	 }
	 	String line;
	 	 try {
	 	 while ((line=reader.readLine()) != null) {
	 	 String[] info = line.split("-");
	 	 String path = (info[0]);
	 	 if (os.contains("Windows")) {
	 	 icon = new ImageIcon(System.getProperty("user.home")+"/AppData/Roaming/MythGame/"+path);
	 	 } else if (os.contains("Mac")){
	 	 icon = new ImageIcon(System.getProperty("user.home")+"/Library/Application Support/MythGame/"+path);
	 	 } else {
	 		icon = new ImageIcon(System.getProperty("user.home")+"/MythGame/"+path);
	 	 }
	 	 String discription = (info[1]);
	  
	 	 price.add(Integer.parseInt(info[2]));
	 	 type.add(Integer.parseInt(info[3]));
	 	 value.add(Integer.parseInt(info[4]));
	 	  
	 	 buttons.add(new JButton(discription+" $"+Integer.parseInt(info[2]), icon));
	 	  
	 	 }
	 	} catch (NumberFormatException e) {
	 	 // TODO Auto-generated catch block
	 	 e.printStackTrace();
	 	} catch (IOException e) {
	 	 // TODO Auto-generated catch block
	 	 e.printStackTrace();
	 	}
	 	 
	  
	 	for (int loop=0; loop<buttons.size(); loop++){
	 	 	 buttons.get(loop).addActionListener(action);
	 	 	 buttons.get(loop).setAlignmentX(Component.CENTER_ALIGNMENT);
	 	 	 panel.add(buttons.get(loop));
	 	 	 
	 	 	  
	 	 	 }
	 	return new Buttons(price, type, value, buttons);
	 	 	  
	 	 }

	
	
	
	
	
	
	
}
