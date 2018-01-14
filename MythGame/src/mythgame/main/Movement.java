package mythgame.main;
import javax.imageio.ImageIO;
import javax.swing.*;

import mythgame.gui.Levels;
import mythgame.gui.MainMenu;
import mythgame.multiplayer.Client;
import mythgame.multiplayer.Server;
import mythgame.objective.FinishArea;
import mythgame.objective.Win;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public final class Movement extends JPanel implements ActionListener, KeyListener {
	//TODO: Add tm.start(); to resume button;

	public boolean wPressed=false;
	public boolean aPressed=false;
	public boolean dPressed=false; 
	public int LastAttacked; 
	public static ArrayList<Integer> badGuyLife = new ArrayList<Integer>();
	public static ArrayList<Integer> badGuyLifeMax = new ArrayList<Integer>();
	public Timer tm = new Timer(10, this);
	public int x = 1;
	public int y = 1;
	public int velX = 0;
	public int velY = 0;
	boolean gravity=true;
	int lastVelX=1;
	int counter;

	public static int MaxHealth=200;
	public static int life=MaxHealth;

	public boolean beingAttacked;
	public static BufferedImage img1 = null;
	public static BufferedImage img = null;
	public static BufferedImage img12=null;
	String os = Main.os;
	public static int attackDamage;
	public static int defense;
	public int attackCount = -1;
	public static boolean multiOn = false;
	public static BufferedImage block = null; 
	public static int money = 0;

	public Movement() {
		life = MaxHealth;
		x= 1;
		y=1;

		try {
			if (os.contains("Windows")) {
				block = ImageIO.read(new File(System.getProperty("user.home")+ "/AppData/Roaming/MythGame/Textures/Texture_block.png"));
			} else  if (os.contains("Mac")){
				block = ImageIO.read(new File(System.getProperty("user.home")+ "/Library/Application Support/MythGame/Textures/Texture_block.png"));
			} else {
				block = ImageIO.read(new File(System.getProperty("user.home")+ "/MythGame/Textures/Texture_block.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  

		try {
			if (os.contains("Windows")) {
				img1 = ImageIO.read(new File(System.getProperty("user.home")+ "/AppData/Roaming/MythGame/Textures/Texture_Character.png"));
			} else if (os.contains("Mac")){
				img1 = ImageIO.read(new File(System.getProperty("user.home")+ "/Library/Application Support/MythGame/Textures/Texture_Character.png"));

			} else {
				img1 = ImageIO.read(new File(System.getProperty("user.home")+ "/MythGame/Textures/Texture_Character.png"));

			}
		} catch (IOException e) {
		}  
		try {
			if (os.contains("Windows")) {
				img12 = ImageIO.read(new File(System.getProperty("user.home")+ "/AppData/Roaming/MythGame/Textures/Texture_Character1.png"));
			} else if (os.contains("Mac")){
				img12 = ImageIO.read(new File(System.getProperty("user.home")+ "/Library/Application Support/MythGame/Textures/Texture_Character1.png"));

			} else {
				img12 = ImageIO.read(new File(System.getProperty("user.home")+ "/MythGame/Textures/Texture_Character1.png"));
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



		tm.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	//	Start.mythframe.setVisible(false);
	//	Start.mythframe.setVisible(true);


	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int loop = 0; loop<Start.blockCoordsX.size(); loop++){
			g.drawImage(block, Start.blockCoordsX.get(loop), Start.blockCoordsY.get(loop), null); 	
		}

		for (int loop=1; loop<Start.badGuyCoordsX.size(); loop++){
			int BadGuyX=Start.badGuyCoordsX.get(loop);
			int BadGuyY=Start.badGuyCoordsY.get(loop);


			g.drawImage(img,BadGuyX, BadGuyY, null);
		}






		g.setColor(Color.gray);
		g.drawRect(0, 0, MaxHealth+1, 10);

		g.setColor(Color.GREEN);
		g.fillRect(1, 1, life, 9);

		Dimension size = Toolkit.getDefaultToolkit().getScreenSize(); 
		g.setColor(Color.gray);
		try {
			g.drawRect(size.width-badGuyLifeMax.get(LastAttacked), 0, badGuyLifeMax.get(LastAttacked)+1, 10);


			g.setColor(Color.RED);
			g.fillRect((size.width-badGuyLifeMax.get(LastAttacked)+1)+badGuyLifeMax.get(LastAttacked)-badGuyLife.get(LastAttacked), 1, badGuyLife.get(LastAttacked), 9);
		} catch (IndexOutOfBoundsException e){

		}
		switch (velX){
		case 1:
			g.drawImage(img1,x, y, null);
			break;
		case -1:
			g.drawImage(img12, x, y, null);
			break;
		case 0:
			switch (lastVelX){
			case 1:
				g.drawImage(img1,x, y, null);
				break;
			case -1:
				g.drawImage(img12, x, y, null);
				break;
			}
		}

	}

	//Keeps the rectangle in the conforms of the JFrame and the repaint method
	public void actionPerformed(ActionEvent e) {
		move();
		impassable();
		FinishArea();
		if (life<1){
			gameover();
		}



		if (beingAttacked){	
			attackCount++;
			if (attackCount == defense/10){
				life--;
				attackCount = -1;
			}
		}

		beingAttacked = false;

		if (gravity){
			velY=1;
		} else {
			counter++;
		}

		if (counter==15){
			gravity=true;
			counter=0;
			velY=0;
		}


		if (x < 0) {
			velX = 0;
			x = 0;
		}


		if (x > Start.mythframe.getWidth()-30) {
			velX = 0;
			x = Start.mythframe.getWidth()-30;
		}

		if (y < 0) {
			velY = 0;
			y = 0;
		}

		if (y > Start.mythframe.getHeight()-50) {
			velY = 0;
			y = Start.mythframe.getHeight()-50;
		} 


		x = x + velX;
		y = y + velY;
		Start.mythframe.repaint();
		/*	this.repaint(x - velX, y-velY, 10, 11);
 	System.out.println("break");
 	this.repaint(x, y, 10, 10);
 	System.out.println("break");
 	this.repaint(0, 0, 10000, 11);
 	System.out.println("break");
 for (int loop=1; loop<Start.badGuyCoordsX.size(); loop++){
 	 	 int BadGuyX=Start.badGuyCoordsX.get(loop);
 	 	 int BadGuyY=Start.badGuyCoordsY.get(loop);
// 	 	 this.repaint(BadGuyX, BadGuyY, 10, 10);
 	 	System.out.println("break");
 	 	} 	 	*/
	}
	//If a key is pressed change direction
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			wPressed=true;
			break;
		case KeyEvent.VK_D:
			dPressed=true;

			break;
		case KeyEvent.VK_A:
			aPressed=true;
			lastVelX=-1;
			break;
		case KeyEvent.VK_UP:
			wPressed=true;
			break;
		case KeyEvent.VK_RIGHT:
			dPressed=true;

			break;
		case KeyEvent.VK_LEFT:
			aPressed=true;
			lastVelX=-1;
			break;

		case KeyEvent.VK_SPACE:

			for (int loop=1; loop<Start.badGuyCoordsX.size(); loop++){

				int BadGuyX=Start.badGuyCoordsX.get(loop);
				int BadGuyY=Start.badGuyCoordsY.get(loop);
				int BadLife=badGuyLife.get(loop);

				if ((BadGuyX >= x+10) && (BadGuyX <= x+15)){ //Right

					if (((BadGuyY>=y)&&
							(BadGuyY<=y+9))|| 
							((BadGuyY<=y)&&
									(BadGuyY>=y-8))){

						BadLife=BadLife-attackDamage;
						LastAttacked=loop;
						badGuyLife.set(loop, BadLife);

						if (BadLife<1){
							money = money + badGuyLifeMax.get(loop)/5;
							Start.badGuyCoordsX.remove(loop);
							Start.badGuyCoordsY.remove(loop);
							badGuyLife.remove(loop);
							badGuyLifeMax.remove(loop);

						}
					}
				}
				if ((BadGuyX <= x-10) && (BadGuyX >= x-15)) { //Left
					if	(((BadGuyY>=y)&&
							(BadGuyY<=y+9))|| 
							((BadGuyY<=y)&&
									(BadGuyY>=y-8))){

						BadLife=BadLife-attackDamage;
						LastAttacked=loop;
						badGuyLife.set(loop, BadLife);

						if (BadLife<1){
							money = money + badGuyLifeMax.get(loop)/5;
							Start.badGuyCoordsX.remove(loop);
							Start.badGuyCoordsY.remove(loop);
							badGuyLife.remove(loop);
							badGuyLifeMax.remove(loop);

						}

					}}//2nd if

			}//loop


			break;
		case KeyEvent.VK_ESCAPE:
			escape();
			break;

		}



	}
	private void escape() {
		tm.stop();
		final JFrame esc = new JFrame("Game Paused");
		JButton resume = new JButton("Resume");
		JButton mainMenu = new JButton("Main Menu");
		JButton retry = new JButton("Restart");
		JButton multiplayer = new JButton("Open Server");
		JPanel panel = new JPanel();

		panel.add(resume);
		panel.add(retry);
		panel.add(mainMenu);
		panel.add(multiplayer);
		esc.add(panel);
		esc.setVisible(true);
		esc.addWindowListener(new WindowListener(){
			public void windowOpened(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				System.out.println("Close and Game Resume");
				tm.start();
			}
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}

		});
		esc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();  
		esc.setBounds( size.width/2-250, size.height/2-100, 500, 75 );
		esc.setAlwaysOnTop(true);
		resume.addActionListener(new ActionListener() {
			public void	actionPerformed(ActionEvent e){
				tm.start();
				esc.dispose();
			}});
		mainMenu.addActionListener(new ActionListener() {
			public void	actionPerformed(ActionEvent e){
				new MainMenu();
				esc.dispose();
			}});
		retry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){

				Start.mythframe.getContentPane().removeAll();

				life=MaxHealth;
				x=1;
				y=1;

				Levels.LoadLevel(Levels.loadedLevel);


				esc.dispose();
				tm.start();
			}

		});
		multiplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					tm.start();
					esc.dispose();
					new Server();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}

	// Tells when a key is in use or not don't worry about these
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){
		switch (e.getKeyCode()){
		case KeyEvent.VK_A:
			velX=0;

			aPressed=false;
			break;
		case KeyEvent.VK_D:
			velX=0;

			dPressed=false;
			break;
		case KeyEvent.VK_W:
			velY=0;
			wPressed=false;
			break;
		case KeyEvent.VK_LEFT:
			velX=0;

			aPressed=false;
			break;
		case KeyEvent.VK_RIGHT:
			velX=0;

			dPressed=false;
			break;
		case KeyEvent.VK_UP:
			velY=0;
			wPressed=false;
			break;
		}
	}

	public void impassable() {
		for (int innerloop=1; innerloop<Start.blockCoordsX.size(); innerloop++){
			int BlockX=Start.blockCoordsX.get(innerloop);
			int BlockY=Start.blockCoordsY.get(innerloop);

			if ((BlockX==x+10)&&(BlockY==y+10)&&(gravity)){ 
				int vel=velX;
				if (velX==1){
					velX=0;
				}
				for (int loop=1; loop<Start.blockCoordsX.size(); loop++){
					int BlockXX=Start.blockCoordsX.get(loop);
					int BlockYY=Start.blockCoordsY.get(loop);
					if (BlockYY==y+10){ //Down
						if (BlockXX == x){
							velX=vel;
						}}
				}}

			if ((BlockX==x-10)&&(BlockY==y+10)&&(gravity)){ 
				int vel=velX;
				if (velX==-1){
					velX=0;
				}
				for (int loop=1; loop<Start.blockCoordsX.size(); loop++){
					int BlockXX=Start.blockCoordsX.get(loop);
					int BlockYY=Start.blockCoordsY.get(loop);
					if (BlockYY==y+10){ //Down
						if (BlockXX == x){
							velX=vel;
						}}
				}}



			if ((BlockX==x+10)){ //Right
				if (((BlockY>=y)&&
						(BlockY<=y+9))|| 
						((BlockY<=y)&&
								(BlockY>=y-8))){

					if (velX==1){
						velX=0;
					}
				}
			}
			if (BlockX==x-10) { //Left
				if	(((BlockY>=y)&&
						(BlockY<=y+9))|| 
						((BlockY<=y)&&
								(BlockY>=y-8))){
					if (velX==-1){
						velX=0;
					}
				}}

			if (BlockY==y-10){ //Up
				if (((BlockX>=x)&&
						(BlockX<=x+9))|| 
						((BlockX<=x)&&
								(BlockX>=x-9))){
					if (velY==-1) {
						velY=0;
					}
				}
			}
			if (BlockY==y+10){ //Down
				if (((BlockX >= x)&&
						(BlockX <= x+9))|| 
						((BlockX <= x)&&
								(BlockX >= x-9))){
					y--;
				}// _ _ __ _ __ _  
				// | _| _ | | _ / | | |/ _ ( ) _   
				// | | | ' \ / ` | / _ \| |_ | || |_ |/ / |
				// | |___| | | | (_| | | () | | | || _| \ \
				// |___|_| |_|\,| \__/|_| |___|_| |___/
			}
		}

		for (int loop=1; loop<Start.badGuyCoordsX.size(); loop++){
			int BadGuyX=Start.badGuyCoordsX.get(loop);
			int BadGuyY=Start.badGuyCoordsY.get(loop);
			if ((BadGuyX==x+10)){ //Right
				if (((BadGuyY>=y)&&
						(BadGuyY<=y+9))|| 
						((BadGuyY<=y)&&
								(BadGuyY>=y-8))){

					if (velX==1){
						velX=0;
					}
					beingAttacked=true;
				}
			}
			if (BadGuyX==x-10) { //Left
				if	(((BadGuyY>=y)&&
						(BadGuyY<=y+9))|| 
						((BadGuyY<=y)&&
								(BadGuyY>=y-8))){
					if (velX==-1){
						velX=0;
					}
					beingAttacked=true;
				}}

			if (BadGuyY==y-10){ //Up
				if (((BadGuyX>=x)&&
						(BadGuyX<=x+9))|| 
						((BadGuyX<=x)&&
								(BadGuyX>=x-9))){

					if (velY==-1) {
						velY=0;
					}
					beingAttacked=true;
				}
			}
			if (BadGuyY==y+10){ //Down
				if (((BadGuyX >= x)&&
						(BadGuyX <= x+9))|| 
						((BadGuyX <= x)&&
								(BadGuyX >= x-9))){

					beingAttacked=true;
					y--;
				}



			}
		}
	}

	public void gameover() {

		if (life < 1){
			tm.stop();
			JPanel gameover = new JPanel();
			JButton retry = new JButton("Retry");
			JButton mainmenu = new JButton("Return to Main Menu");
			JLabel message = new JLabel("Game Over");
			message.setForeground(Color.red);
			message.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 100));
			gameover.add(message);

			Start.mythframe.getContentPane().removeAll();
			gameover.add(retry);
			gameover.add(mainmenu);
			Start.mythframe.add(gameover);
			gameover.revalidate();

			retry.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){

					Start.mythframe.getContentPane().removeAll();



					life=MaxHealth;
					x=1;
					y=1;

					Levels.LoadLevel(Levels.loadedLevel);
				}
			});
			mainmenu.addActionListener(new ActionListener() {
				public void	actionPerformed(ActionEvent e){
					Start.mythframe.getContentPane().removeAll();
					life=MaxHealth;
					x=1;
					y=1;
					new MainMenu();
				}});
		}
	}
	public void FinishArea(){
		int FinishX = FinishArea.FinishX;
		int FinishY = FinishArea.FinishY;

		if ((FinishX==x+10)){ //Right
			if (((FinishY>=y)&&
					(FinishY<=y+9))|| 
					((FinishY<=y)&&
							(FinishY>=y-8))){
				tm.stop();
				new Win();
				//WIN
			}
		}
		if (FinishX==x-10) { //Left
			if	(((FinishY>=y)&&
					(FinishY<=y+9))|| 
					((FinishY<=y)&&
							(FinishY>=y-8))){
				tm.stop();
				new Win();
			}}

		if (FinishY==y-10){ //Up
			if (((FinishX>=x)&&
					(FinishX<=x+9))|| 
					((FinishX<=x)&&
							(FinishX>=x-9))){

				tm.stop();
				new Win();
				//WIN
			}
		}
		if (FinishY==y+10){ //Down
			if (((FinishX >= x)&&
					(FinishX <= x+9))|| 
					((FinishX <= x)&&
							(FinishX >= x-9))){
				tm.stop();
				new Win();
			}}
	}
	public void move(){

		if (dPressed){
			velX = 1;
			lastVelX=1;
		}
		if (aPressed){
			velX=-1;
			lastVelX=-1;
		}
		if (wPressed){
			for (int innerloop=1; innerloop<Start.blockCoordsX.size(); innerloop++){
				int BlockX=Start.blockCoordsX.get(innerloop);
				int BlockY=Start.blockCoordsY.get(innerloop);
				if (BlockY==y+10){
					if (((BlockX>=x)&&
							(BlockX<=x+9))|| 
							((BlockX<=x)&&
									(BlockX>=x-9))){
						gravity=false;
						velY=-1;
					}}}
			for (int loop=1; loop<Start.badGuyCoordsX.size(); loop++){
				int BadGuyX=Start.badGuyCoordsX.get(loop);
				int BadGuyY=Start.badGuyCoordsY.get(loop);
				if (BadGuyY==y+10){
					if (((BadGuyX >= x)&&
							(BadGuyX <= x+9))|| 
							((BadGuyX <= x)&&
									(BadGuyX >= x-9))){
						gravity=false;
						velY=-1;
					}}}
		}
	}
}