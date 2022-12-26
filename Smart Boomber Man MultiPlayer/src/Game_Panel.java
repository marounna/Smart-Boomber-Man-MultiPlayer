import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
public class Game_Panel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	MainFrame frm;
	Map map;
	Image back_ground;
	Thread thread1;
	Player player1,player2;
	Lives live[]=new Lives[5];
	Key key;
	Arrow arrow;
	Door door;
	int counter = 0;
	boolean RESULTMESSAGE=false;
	
	public Game_Panel(MainFrame frm) throws FileNotFoundException {
		Dimension Screen_dimemsion=Toolkit.getDefaultToolkit().getScreenSize(); // get screen size 
		this.frm=frm;
		setPreferredSize(new Dimension(Screen_dimemsion));// full screen panel by screen size
		map=new Map();
		
	
		try {
			key=new Key(this);
			key.changeposition();
			System.out.println("key position = "+key.p_rand);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		try {
			arrow=new Arrow();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			thread1=new Thread(this);
			thread1.start();
			player1 = new Player(1,1,this);
			player2 = new Player(3,1,this);

			back_ground = ImageIO.read(new FileInputStream("pic/grass_bg.jpeg"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		player1.start();
		player2.start();
		
			for(int y=0,p=90;y<5;y++){
			try {
				live[y]=new Lives(1250,p);
			} catch (IOException e) {
				e.printStackTrace();
			}
			p+=80;
			}
			this.setFocusable(true);
			this.requestFocusInWindow();
		//	this.addKeyListener(this.player1);
			
			
			try {
				door=new Door(map.mapObjects[0][16].getStart_x(),map.mapObjects[0][16].getStart_y());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(frm.isHost)player1.CONTROLEDbyME=true;
			else
				player2.CONTROLEDbyME=true;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(back_ground, 0, 0,getWidth(),getHeight(),Color.WHITE, null);
		if(map.READY)//without this line it will work too !! but in case the map is not ready maybe there will be a problem !!
		map.Draw(this,g);
		
		
		
		
		if(!(player1.boomb==null ||player1.fire==null || live==null) ){
			
		if(player1.boomb.running ){
			player1.boomb.draw(g);
			}
		if(player1.fire.running && player1.isREADYTODRAWFIRE)
		{
			player1.fire.draw(g);
		}
		if(key!=null && key.VISIBLE)
		key.draw(g);

		}
		if(!(player2.boomb==null ||player2.fire==null || live==null) ){
			
			if(player2.boomb.running ){
				player2.boomb.draw(g);
				}
			if(player2.fire.running && player2.isREADYTODRAWFIRE)
			{
				player2.fire.draw(g);
			}
			if(key!=null && key.VISIBLE)
			key.draw(g);

			}
		
		if(door != null)
			door.draw(g);
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		if(!player1.LOSEING){
			if(frm.ps.user_name1!=null&&frm.ps.user_name2!=null){
			if(player1.CONTROLEDbyME)
				g.drawString(frm.ps.user_name1,player1.x, player1.y);
			else
				g.drawString(frm.ps.user_name2,player1.x, player1.y);
			}
			player1.draw(g);
		}
		
		
		
		
		if(frm.PLAYER2JOINED&&!player2.LOSEING)
		{
			if(frm.ps.user_name1!=null&&frm.ps.user_name2!=null){
			if(player2.CONTROLEDbyME)
				g.drawString(frm.ps.user_name1,player2.x, player2.y);
			else
				g.drawString(frm.ps.user_name2,player2.x, player2.y);
			}
		player2.draw(g);
		}
		
		if(frm.isHost){
		for(int y=0;y<player1.LIVES;y++){
			live[y].draw(g);
			}
		}else{
			for(int y=0;y<player2.LIVES;y++){
				live[y].draw(g);
				}
		}
			
		if(player2!=null){
		if(arrow!=null && player1!=null && !player1.isKEYCOLLECTED&&!player2.isKEYCOLLECTED)
			arrow.draw(g);
		}
		else{
		if(arrow!=null && player1!=null && !player1.isKEYCOLLECTED)
			arrow.draw(g);
		}
			
	}
	

	public void sendkeytoserver(){
		
		int x=111111000;
		//int x1=Integer.parseInt("A/SD");
		if(key!=null){
		x+=this.key.p_rand;
		this.frm.gameClient.sendData((x+".").getBytes());
		System.out.println("the key was sent from the server");
		}
	}
	
	
	public void Idle() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		try {
			check_winning();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(frm.isHost){
		if(player1!=null && arrow!=null && key!=null){
		player1.getWalkingIJ();
		arrow.ChangeArrow(player1.I_inmap, player1.J_inmap, key.i, key.j);
		}
		}
		else
			if(player2!=null && arrow!=null && key!=null){
				player2.getWalkingIJ();
				arrow.ChangeArrow(player2.I_inmap, player2.J_inmap, key.i, key.j);
				}
			
		if(frm.isHost){
		if(key!=null && player1!=null){
			player1.isKeyFound();
			if(player2!=null)
			player2.isKeyFound();
		}
		}else
			{
				if(key!=null && player2!=null)
					player1.isKeyFound();
					player2.isKeyFound();
				}
		
		
		if(player1!=null)
			player1.Blinking();
		if(player2!=null)
			player2.Blinking();
		
		
		if(player1 != null&&player1.isKEYCOLLECTED){
		door.start();
		}
		if(player2 != null&&player2.isKEYCOLLECTED){
			door.start();
			}

		
		if(player1 != null&&player1.LIVES<=0){
			player1.LOSEING=true;
			player1.stop();/////////////////////////////
			if(player2.CONTROLEDbyME&&!RESULTMESSAGE){
				JOptionPane.showMessageDialog(null, "you win", "Result ",2);
				
					frm.DBC.update_statistics(""+frm.ps.code_game, ""+frm.ps.user_name2, ""+frm.ps.kills,""+frm.ps.death, ""+frm.ps.score,""+frm.ps.boomb_used,""+frm.ps.boxies_dest, ""+frm.ps.boxies_built, "win");
				
			}
				if(player1.CONTROLEDbyME&&!RESULTMESSAGE){
					JOptionPane.showMessageDialog(null, "you lose", "Result ",2);
				
						frm.DBC.update_statistics(""+frm.ps.code_game, ""+frm.ps.user_name2, ""+frm.ps.kills,""+frm.ps.death, ""+frm.ps.score,""+frm.ps.boomb_used,""+frm.ps.boxies_dest, ""+frm.ps.boxies_built, "lose");

				}
			try {
				this.frm.changePanelEnd(new End_Game(this.frm));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RESULTMESSAGE=true;
		}
		if(player2 != null&&player2.LIVES<=0){
			player2.LOSEING=true;
			player2.stop();
			if(player1.CONTROLEDbyME&&!RESULTMESSAGE){
				JOptionPane.showMessageDialog(null, "you win", "Result ",2);
			
			frm.DBC.update_statistics(""+frm.ps.code_game, ""+frm.ps.user_name2, ""+frm.ps.kills,""+frm.ps.death, ""+frm.ps.score,""+frm.ps.boomb_used,""+frm.ps.boxies_dest, ""+frm.ps.boxies_built, "win");
		
	}
		if(player2.CONTROLEDbyME&&!RESULTMESSAGE){
			JOptionPane.showMessageDialog(null, "you lose", "Result ",2);
		
				frm.DBC.update_statistics(""+frm.ps.code_game, ""+frm.ps.user_name2, ""+frm.ps.kills,""+frm.ps.death, ""+frm.ps.score,""+frm.ps.boomb_used,""+frm.ps.boxies_dest, ""+frm.ps.boxies_built, "lose");

		}
			try {
				this.frm.changePanelEnd(new End_Game(this.frm));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RESULTMESSAGE=true;
		}
		
		
		
			if(!frm.PLAYER2JOINED){
		if(player1 != null&&player1.fire!=null&&player1.fire.running && player1.isREADYTODRAWFIRE){
			player1.fire.CheckPlayerInFire();
		}
			}else
			{
					if(player2 != null&&player2.fire!=null&&player2.fire.running && player2.isREADYTODRAWFIRE){
					player2.fire.CheckPlayerInFire();
					}
					if(player1 != null&&player1.fire!=null&&player1.fire.running && player1.isREADYTODRAWFIRE){
						player1.fire.CheckPlayerInFire();
						}
				}
				
			}
	
	public void init(){
		if(!frm.isHost){
			String s="";
			s=JOptionPane.showInputDialog("Enter the ip address of the host computer","192.168.0.105");
			 frm.setTitle("You are running on the host IpAddress: "+s);
			 frm.gameClient=new GameClient(frm,s);
			 frm.gameClient.start();
			 frm.gameClient.sendData("9999990.".getBytes());//PLAYER 2 JOINED THE GAME
			 
			}
	}

public void check_winning() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
	if(player2==null) return;
	if(player1==null) return;
	if(player2.I_inmap==0&&player2.J_inmap==16&&player2.isKEYCOLLECTED){
		if(player2.CONTROLEDbyME&&!RESULTMESSAGE){
			JOptionPane.showMessageDialog(null, "you win", "Result ",2);
			
				frm.DBC.update_statistics(""+frm.ps.code_game, ""+frm.ps.user_name2, ""+frm.ps.kills,""+frm.ps.death, ""+frm.ps.score,""+frm.ps.boomb_used,""+frm.ps.boxies_dest, ""+frm.ps.boxies_built, "win");
			
		}
			if(player1.CONTROLEDbyME&&!RESULTMESSAGE){
				JOptionPane.showMessageDialog(null, "you lose", "Result ",2);
			
					frm.DBC.update_statistics(""+frm.ps.code_game, ""+frm.ps.user_name2, ""+frm.ps.kills,""+frm.ps.death, ""+frm.ps.score,""+frm.ps.boomb_used,""+frm.ps.boxies_dest, ""+frm.ps.boxies_built, "lose");

			}
		try {
			this.frm.changePanelEnd(new End_Game(this.frm));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RESULTMESSAGE=true;
		}
	else
		if(player1.I_inmap==0&&player1.J_inmap==16&&player1.isKEYCOLLECTED){
			if(player1.CONTROLEDbyME&&!RESULTMESSAGE){
				JOptionPane.showMessageDialog(null, "you win", "Result ",2);
			
			frm.DBC.update_statistics(""+frm.ps.code_game, ""+frm.ps.user_name2, ""+frm.ps.kills,""+frm.ps.death, ""+frm.ps.score,""+frm.ps.boomb_used,""+frm.ps.boxies_dest, ""+frm.ps.boxies_built, "win");
		
	}
		if(player2.CONTROLEDbyME&&!RESULTMESSAGE){
			JOptionPane.showMessageDialog(null, "you lose", "Result ",2);
		
				frm.DBC.update_statistics(""+frm.ps.code_game, ""+frm.ps.user_name2, ""+frm.ps.kills,""+frm.ps.death, ""+frm.ps.score,""+frm.ps.boomb_used,""+frm.ps.boxies_dest, ""+frm.ps.boxies_built, "lose");

		}
			try {
				this.frm.changePanelEnd(new End_Game(this.frm));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RESULTMESSAGE=true;
		}
}

	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		init();
		
		
		while(true){
			try {
				Idle();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
	}
}