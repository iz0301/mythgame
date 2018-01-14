package mythgame.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mythgame.main.Main;
import mythgame.main.Start;

public class Shop {
 JFrame frame1 = Start.mythframe;
 JFrame frame = new JFrame();
 JPanel panel = new JPanel();
 int money;
 BufferedReader reader;
 BufferedWriter writer;
 JLabel moneyLabel = new JLabel("Money: $"+money);
 String ages[] = {"CaveMan", "Medieval", "Renaissance", "Modern", "Futuristic"};
 //EntryExample: Shop/Cave_Man/Armor/Example.png-Description-Price-armor/weapon-attack/defend
 // armor:1
 // weapon:0
 /*
  * Stats file:
  * 
  * int money 
  *	int attack
  * int defend
  * 
  */
 String line1, line2, line3;
 String os = Main.os;
 ImageIcon icon;
 Buttons buttons = null;
 
 public Shop() {
	 moneyLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
	 moneyLabel.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 16));
	 panel.add(moneyLabel);
	 frame.addWindowListener(new WindowListener(){
public void windowOpened(WindowEvent e) {}
public void windowClosing(WindowEvent e) {
			new Inventory();
			
		}
public void windowClosed(WindowEvent e) {}
public void windowIconified(WindowEvent e) {}
public void windowDeiconified(WindowEvent e) {}
public void windowActivated(WindowEvent e) {}
public void windowDeactivated(WindowEvent e) {}});
	 
	 panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	 frame.add(panel);
	 frame.setVisible(true);
	 frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	 try{
	 if (Main.os.contains("Windows")){
		reader = new BufferedReader(new FileReader(new File(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Player_Stats.stats")));
		line1 = reader.readLine();
		line2 = reader.readLine();
		line3 = reader.readLine();
		writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Player_Stats.stats")));
	 } else  if (Main.os.contains("Mac")){
		reader = new BufferedReader(new FileReader(new File( System.getProperty("user.home")+"/Library/Application Support/MythGame/Player_Stats.stats")));
		line1 = reader.readLine();
		line2 = reader.readLine();
		line3 = reader.readLine();
		writer = new BufferedWriter(new FileWriter(new File( System.getProperty("user.home")+"/Library/Application Support/MythGame/Player_Stats.stats")));
	 } else {
		 reader = new BufferedReader(new FileReader(new File( System.getProperty("user.home")+"/MythGame/Player_Stats.stats")));
		line1 = reader.readLine();
		line2 = reader.readLine();
		line3 = reader.readLine();
		writer = new BufferedWriter(new FileWriter(new File( System.getProperty("user.home")+"/MythGame/Player_Stats.stats"))); 
	 }
	 } catch(IOException e){
		 e.printStackTrace();
	 }
	 
		money = Integer.parseInt(line1);
		moneyLabel.setText("Money: $"+money);

	 
	 ActionListener action = null;
	 action = new ActionListener(){
		 public void actionPerformed(ActionEvent e){
			 for(int loop = 0; loop < buttons.buttons.size(); loop++){
				 if (e.getSource().equals(buttons.buttons().get(loop))){
					 int price = buttons.price.get(loop);
					 JButton button = buttons.buttons.get(loop);
					 int value = buttons.value.get(loop);
					 int type = buttons.type.get(loop);
					 
						if(money >= price){
							 if (JOptionPane.showConfirmDialog(frame, "Confirm purchase of "+button.getText()+"?", "Confirm Purchase", JOptionPane.YES_NO_OPTION)==0){
								 money = money - price;
								 moneyLabel.setText("Money: $"+money);
								 line1=String.valueOf(money);
								 try{
								 if (Main.os.contains("Windows")){//clear file
									writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Player_Stats.stats")));
								 } else if (Main.os.contains("Mac")){
									writer = new BufferedWriter(new FileWriter(new File( System.getProperty("user.home")+"/Library/Application Support/MythGame/Player_Stats.stats")));
								 } else {
									 writer = new BufferedWriter(new FileWriter(new File( System.getProperty("user.home")+"/MythGame/Player_Stats.stats"))); 
								 }
								 writer.write(line1);
								 writer.newLine();
								 writer.write(line2);
								 writer.newLine();
								 writer.write(line3);
								 writer.flush();
								 } catch(IOException e1){
									 e1.printStackTrace();
								 }
								 
								 moveItem(price, value, type, button);
								 
							 } else if (money < price){
								 JOptionPane.showMessageDialog(frame, "Not enough money, you have: "+money);
							 }
						 }

					 
					 
					 
				 }
			 }
		 }
	 };
	 
	 	JLabel label1 = new JLabel("Armor");
	 	label1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
	 	panel.add(label1);
	 	
		 buttons = Inventory.Buttonerize(panel, "/Shop/Cave_Man/Armor/Cave_Armor.shop", action);
		 
		 JLabel label2 = new JLabel("Weapons");
		 label2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		 panel.add(label2);
		 
		 Buttons weapons = Inventory.Buttonerize(panel, "/Shop/Cave_Man/Weapons/Cave_Weapons.shop", action);
		 buttons.buttons.addAll(weapons.buttons);//merge arrays
		 buttons.price.addAll(weapons.price);//merge arrays
		 buttons.type.addAll(weapons.type);//merge arrays
		 buttons.value.addAll(weapons.value);//merge arrays
		 
		 try{
//clear file
		 if (Main.os.contains("Windows")){
			writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Player_Stats.stats")));
		 } else if  (Main.os.contains("Mac")){
			writer = new BufferedWriter(new FileWriter(new File( System.getProperty("user.home")+"/Library/Application Support/MythGame/Player_Stats.stats")));
		 } else {
				writer = new BufferedWriter(new FileWriter(new File( System.getProperty("user.home")+"/MythGame/Player_Stats.stats")));
		 }
		 writer.write(line1);
		 writer.newLine();
		 writer.write(line2);
		 writer.newLine();
		 writer.write(line3);
		 writer.flush();
		 } catch(IOException e1){
			 e1.printStackTrace();
		 }
 	}

	public void moveItem(int price, int value, int type, JButton button) {
		BufferedWriter writer = null;
		BufferedReader reader = null;
		
		try {
		 	 if (Main.os.contains("Windows")){
		 		System.out.println(type);
		 		if(type==0 ){
		 		 writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Shop/Purchased/Weapons.shop"), true));
		 	 	} else if (type==1) {
		 		 writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Shop/Purchased/Armor.shop"), true));
		 	 	}
		 	 } else  if (Main.os.contains("Mac")){
		 		 if (type==0){
		 			 writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"/Library/Application Support/MythGame/Shop/Purchased/Weapons.shop"), true));
		 		 } else if (type==1) {
		 			 writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"/Library/Application Support/MythGame/Shop/Purchased/Armor.shop"), true));
		 		 }
		 	} else {
		 		if (type==0){
		 			 writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"/MythGame/Shop/Purchased/Weapons.shop"), true));
		 		 } else if (type==1) {
		 			 writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"/MythGame/Shop/Purchased/Armor.shop"), true));
		 		 }
		 	}
		 	 } catch (IOException e1){
		 	 e1.printStackTrace();
		 	 }
		
		String string = null;
		if(os.contains("Windows")){
			string = button.getIcon().toString().replace("\\", "/").replace(System.getProperty("user.home").replace("\\", "/")+"/AppData/Roaming/MythGame/", "");
		} else if(os.contains("Mac")){
			string = button.getIcon().toString().replace("\\", "/").replace(System.getProperty("user.home").replace("\\", "/")+"/Library/Application Support/MythGame/", "");
		} else {
			string = button.getIcon().toString().replace("\\", "/").replace(System.getProperty("user.home").replace("\\", "/")+"/MythGame/", "");	
		}
		
		string = string + "-" + button.getText().replace(" $" + price, "") + "-"+price+"-" + type + "-" + value;
		try {
			System.out.println(writer);
		writer.write(string);
		writer.newLine();
		writer.flush();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		try {
		 	 if (Main.os.contains("Windows")){
		 	 if(type==0 ){
		 	 reader = new BufferedReader(new FileReader(new File(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Shop/Cave_Man/Weapons/Cave_Weapons.shop")));//TODO: Some way to get the Age
		 	 } else if (type==1) {
		 	 reader = new BufferedReader(new FileReader(new File(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Shop/Cave_Man/Armor/Cave_Armor.shop")));//TODO: Some way to get the Age
		 	 }
		 	 } else if (Main.os.contains("Mac")) {
		 	 if (type==0){
		 	 reader = new BufferedReader(new FileReader(new File(System.getProperty("user.home")+"/Library/Application Support/MythGame/Shop/Cave_Man/Weapons/Cave_Weapons.shop")));//TODO: Some way to get the Age
		 	 } else if (type==1) {
		 	 reader = new BufferedReader(new FileReader(new File(System.getProperty("user.home")+"/Library/Application Support/MythGame/Shop/Cave_Man/Aromor/Cave_Armor.shop")));//TODO: Some way to get the Age
		 	 } 
		 	 } else {
		 		if (type==0){
				 	 reader = new BufferedReader(new FileReader(new File(System.getProperty("user.home")+"/MythGame/Shop/Cave_Man/Weapons/Cave_Weapons.shop")));//TODO: Some way to get the Age
				 	 } else if (type==1) {
				 	 reader = new BufferedReader(new FileReader(new File(System.getProperty("user.home")+"/MythGame/Shop/Cave_Man/Aromor/Cave_Armor.shop")));//TODO: Some way to get the Age
				 	 } 
		 	 }
		 	 } catch (IOException e1){
		 	 e1.printStackTrace();
		 	 }
		
		ArrayList<String> file = new ArrayList<String>();
		String line = null;
		try {
			
			while((line = reader.readLine())!=null){
				if(line != string){
					file.add(line);
					if(string.equals(line)){
						file.remove(string);
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
		 	 if (Main.os.contains("Windows")){
		 	 if(type==0){
		 	 writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Shop/Cave_Man/Weapons/Cave_Weapons.shop")));//TODO: Some way to get the Age
		 	 } else if (type==1) {
		 	 writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"/AppData/Roaming/MythGame/Shop/Cave_Man/Armor/Cave_Armor.shop")));//TODO: Some way to get the Age
		 	 }
		 	 } else  if (Main.os.contains("Mac")){
		 	 if (type==0){
		 	 writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"/Library/Application Support/MythGame/Shop/Cave_Man/Weapons/Cave_Weapons.shop")));//TODO: Some way to get the Age
		 	 } else if (type==1) {
		 	 writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"/Library/Application Support/MythGame/Shop/Cave_Man/Armor/Cave_Armor.shop")));//TODO: Some way to get the Age
		 	 }
		 	 } else {
		 		 if (type==0){
				 	 writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"/MythGame/Shop/Cave_Man/Weapons/Cave_Weapons.shop")));//TODO: Some way to get the Age
				 	 } else if (type==1) {
				 	 writer = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home")+"/MythGame/Shop/Cave_Man/Armor/Cave_Armor.shop")));//TODO: Some way to get the Age
				 	 }
		 	 }
		 	 } catch (IOException e1){
		 	 e1.printStackTrace();
		 	 }
		
		for(int loop = 0; loop<file.size(); loop++){
			try {
			writer.write(file.get(loop));
			writer.flush();
			writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		button.setEnabled(false);
		
		
	}
 	 	 
 }
