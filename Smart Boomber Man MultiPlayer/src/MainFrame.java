import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import CONTROL.DBControl;

public class MainFrame extends JFrame implements KeyListener{
	private static final long serialVersionUID = 1L;
	JPanel p;
	Game_Panel game;
	GameServer socketServer;
	GameClient gameClient;
	boolean PLAYER2JOINED=false,isHost=false,PSUN2UPDATED=false;
	player_statistics ps;
	DBControl DBC;
	
	public MainFrame() throws FileNotFoundException, UnknownHostException{
		super("Log In");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.ps=new player_statistics();
		DBC=new DBControl();
		 p=new MainPanel(this);
		
		add(p, BorderLayout.CENTER);
		pack(); 
		setVisible(true);
		this.setFocusable(true);
		this.addKeyListener(this);
		
		
		if(JOptionPane.showConfirmDialog(null, "Are you the host? Do you want to run the server on this computer?","Run server?", JOptionPane.YES_NO_OPTION)==0)
		{
				System.out.println("Run the server and show the ip address");
				socketServer = new GameServer();
				socketServer.start();
				this.setTitle("You are the Host, IpAddress: "+InetAddress.getLocalHost().getHostAddress());
				gameClient=new GameClient(this,InetAddress.getLocalHost().getHostAddress());
				gameClient.start();
				isHost=true;
				
		}
		else
		{
			
		}
	
		
	}
	
	void changePanel(JPanel New_p){
		invalidate();
		remove(this.p);
		this.p = New_p; 
		add(p, BorderLayout.CENTER);
		pack(); 
		revalidate();
	}
	
	void changePanel(Game_Panel New_p){
		invalidate();
		remove(this.p);
		this.p = New_p; 
		add(p, BorderLayout.CENTER);
		pack(); 
		revalidate();
	}
	
	void Start_Game() throws FileNotFoundException{
		game=new Game_Panel(this);
		
		changePanel(game);
		//changePanel(new help2());
		
	}
	
	
	
	public void keyPressed(KeyEvent e) {
		if(!isHost&&!PSUN2UPDATED){//updating playerstatic class 
			String s="";
				try {
					s=DBC.get_username1_by_code(ps.code_game-1);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {

				}
				ps.user_name2=s;
				PSUN2UPDATED=true;
		}
		System.out.println("user name1 = "+ps.user_name1);
		System.out.println("user name2 = "+ps.user_name2);
		
			if(isHost){
		if (e.getKeyCode() ==  KeyEvent.getExtendedKeyCodeForChar('d')) {
			game.player1.RIGHT=game.player1.WALKING=true;
			game.player1.DOWN=game.player1.LEFT=game.player1.UP=false;
        	if(game.player1.check_boundries())
        		game.player1.x+=game.player1.SPEED;
        	this.gameClient.sendData((this.gameClient.socket.getLocalPort()+"6.").getBytes());
        }
        if (e.getKeyCode() ==  KeyEvent.getExtendedKeyCodeForChar('a')) {
        	game.player1.LEFT=game.player1.WALKING=true;
        	game.player1.DOWN=game.player1.RIGHT=game.player1.UP=false;
        	if(game.player1.check_boundries())
        		game.player1.x-=game.player1.SPEED;
        	this.gameClient.sendData((this.gameClient.socket.getLocalPort()+"4.").getBytes());

        }
        if (e.getKeyCode() ==  KeyEvent.getExtendedKeyCodeForChar('w')) {
        	game.player1.UP=game.player1.WALKING=true;
        	game.player1.DOWN=game.player1.RIGHT=game.player1.LEFT=false;
        	game.player1.getWalkingIJ();
        	if(game.player1.check_boundries() || (game.player1.isKEYCOLLECTED &&game.player1.I_inmap==0&&game.player1.J_inmap==16 ))
        		game.player1.y-=game.player1.SPEED;  
        	this.gameClient.sendData((this.gameClient.socket.getLocalPort()+"8.").getBytes());
        }
        
        
        if (e.getKeyCode() ==  KeyEvent.getExtendedKeyCodeForChar('s')) {
        	game.player1.DOWN=game.player1.WALKING=true;
        	game.player1.LEFT=game.player1.RIGHT=game.player1.UP=false;
        	if(game.player1.check_boundries())
        		game.player1.y+=game.player1.SPEED; 
        	this.gameClient.sendData((this.gameClient.socket.getLocalPort()+"2.").getBytes());
        }
        if (game.player1.Xisready && e.getKeyCode() ==  KeyEvent.getExtendedKeyCodeForChar('z')) {
        		ps.boxies_built++;
				game.player1.Buildwood();
				this.gameClient.sendData((this.gameClient.socket.getLocalPort()+"7.").getBytes());
        	}
        
        if (game.player1.Xisready && e.getKeyCode() ==  KeyEvent.getExtendedKeyCodeForChar('x')) {
        	
			try {
				ps.boomb_used++;
				game.player1.boomb = new Boomb(game.player1.x,game.player1.y,game.player1.GP,game.player1);
				game.player1.Xisready=false;
				game.player1.IPUTTHEBOOMB=true;
				
				//if(GP.fire!=null)
				//if(GP.boomb.running && !GP.fire.running)
				game.player1.fire = new Fire(game.player1.x,game.player1.y,game.player1.GP,game.player1);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				game.player1.sound.Ticking_Bomb();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e1) {
				e1.printStackTrace();
			}
			this.gameClient.sendData((this.gameClient.socket.getLocalPort()+"5.").getBytes());
		}
			}
			else{
				if (e.getKeyCode() ==  KeyEvent.getExtendedKeyCodeForChar('d')) {
					game.player2.RIGHT=game.player2.WALKING=true;
					game.player2.DOWN=game.player2.LEFT=game.player2.UP=false;
		        	if(game.player2.check_boundries())
		        		game.player2.x+=game.player2.SPEED;
		        	this.gameClient.sendData((this.gameClient.socket.getLocalPort()+"6.").getBytes());
		        }
		        if (e.getKeyCode() ==  KeyEvent.getExtendedKeyCodeForChar('a')) {
		        	game.player2.LEFT=game.player2.WALKING=true;
		        	game.player2.DOWN=game.player2.RIGHT=game.player2.UP=false;
		        	if(game.player2.check_boundries())
		        		game.player2.x-=game.player2.SPEED;
		        	this.gameClient.sendData((this.gameClient.socket.getLocalPort()+"4.").getBytes());

		        }
		        if (e.getKeyCode() ==  KeyEvent.getExtendedKeyCodeForChar('w')) {
		        	game.player2.UP=game.player2.WALKING=true;
		        	game.player2.DOWN=game.player2.RIGHT=game.player2.LEFT=false;
		        	game.player2.getWalkingIJ();
		        	if(game.player2.check_boundries() || (game.player2.isKEYCOLLECTED &&game.player2.I_inmap==0&&game.player2.J_inmap==16 ))
		        		game.player2.y-=game.player2.SPEED;  
		        	this.gameClient.sendData((this.gameClient.socket.getLocalPort()+"8.").getBytes());
		        	
		        }
		        
		        
		        if (e.getKeyCode() ==  KeyEvent.getExtendedKeyCodeForChar('s')) {
		        	game.player2.DOWN=game.player2.WALKING=true;
		        	game.player2.LEFT=game.player2.RIGHT=game.player2.UP=false;
		        	if(game.player2.check_boundries())
		        		game.player2.y+=game.player2.SPEED; 
		        	this.gameClient.sendData((this.gameClient.socket.getLocalPort()+"2.").getBytes());
		        }
		        if (game.player2.Xisready && e.getKeyCode() ==  KeyEvent.getExtendedKeyCodeForChar('z')) {
		        	 
		        	ps.boxies_built++;
		        	this.gameClient.sendData((this.gameClient.socket.getLocalPort()+"7.").getBytes());
					game.player2.Buildwood();
	        	}
		        
		        if (game.player2.Xisready && e.getKeyCode() ==  KeyEvent.getExtendedKeyCodeForChar('x')) {
		        	
					try {
						ps.boomb_used++;
						game.player2.boomb = new Boomb(game.player2.x,game.player2.y,game.player2.GP,game.player2);
						game.player2.Xisready=false;
						game.player2.IPUTTHEBOOMB=true;
						
						//if(GP.fire!=null)
						//if(GP.boomb.running && !GP.fire.running)
						game.player2.fire = new Fire(game.player2.x,game.player2.y,game.player2.GP,game.player2);

					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						game.player2.sound.Ticking_Bomb();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e1) {
						e1.printStackTrace();
					}
					this.gameClient.sendData((this.gameClient.socket.getLocalPort()+"5.").getBytes());
				}
			}
			
			
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(isHost){
		game.player1.WALKING=false;
		game.player1.locInSprite_left =game.player1.locInSprite_right=game.player1.locInSprite_up=game.player1.locInSprite_down=2;
		this.gameClient.sendData((this.gameClient.socket.getLocalPort()+"1.").getBytes());
		}
		else
		{
			game.player2.WALKING=false;
			game.player2.locInSprite_left =game.player2.locInSprite_right=game.player2.locInSprite_up=game.player2.locInSprite_down=2;
			this.gameClient.sendData((this.gameClient.socket.getLocalPort()+"1.").getBytes());
			
		}
		}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public void OPPONENT_ACTION(int x){
	if(!isHost){// you are player 2
		if (x == 7) {
			game.player1.Buildwood();
        }
		if (x == 1) {
			//stop player 1 from moving
			game.player1.WALKING=false;
			game.player1.locInSprite_left =game.player1.locInSprite_right=game.player1.locInSprite_up=game.player1.locInSprite_down=2;
  
        }
		if (x == 6) {
			game.player1.RIGHT=game.player1.WALKING=true;
			game.player1.DOWN=game.player1.LEFT=game.player1.UP=false;
        	if(game.player1.check_boundries())
        		game.player1.x+=game.player1.SPEED;
        }
        if (x==4) {
        	game.player1.LEFT=game.player1.WALKING=true;
        	game.player1.DOWN=game.player1.RIGHT=game.player1.UP=false;
        	if(game.player1.check_boundries())
        		game.player1.x-=game.player1.SPEED;
        }
        if (x==8) {
        	game.player1.UP=game.player1.WALKING=true;
        	game.player1.DOWN=game.player1.RIGHT=game.player1.LEFT=false;
        	game.player1.getWalkingIJ();
        	if(game.player1.check_boundries() || (game.player1.isKEYCOLLECTED &&game.player1.I_inmap==0&&game.player1.J_inmap==16 ))
        		game.player1.y-=game.player1.SPEED;  
        }
        if (x==2) {
        	game.player1.DOWN=game.player1.WALKING=true;
        	game.player1.LEFT=game.player1.RIGHT=game.player1.UP=false;
        	if(game.player1.check_boundries())
        		game.player1.y+=game.player1.SPEED; 
        }
        
        if (x==5) {
        	
			try {
				game.player1.OPNNTPUTTHEBOOMB=true;
				game.player1.boomb = new Boomb(game.player1.x,game.player1.y,game.player1.GP,game.player1);
				game.player1.Xisready=false;
				game.player1.fire = new Fire(game.player1.x,game.player1.y,game.player1.GP,game.player1);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				game.player1.sound.Ticking_Bomb();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e1) {
				e1.printStackTrace();
			}
		}
			}
			else{
				if (x == 7) {
					game.player2.Buildwood();
		        }
				if (x == 1) {
					game.player2.WALKING=false;
					game.player2.locInSprite_left =game.player2.locInSprite_right=game.player2.locInSprite_up=game.player2.locInSprite_down=2;
				
				}
				if (x == 6) {
					game.player2.RIGHT=game.player2.WALKING=true;
					game.player2.DOWN=game.player2.LEFT=game.player2.UP=false;
		        	if(game.player2.check_boundries())
		        		game.player2.x+=game.player2.SPEED;
		        }
		        if (x==4) {
		        	game.player2.LEFT=game.player2.WALKING=true;
		        	game.player2.DOWN=game.player2.RIGHT=game.player2.UP=false;
		        	if(game.player2.check_boundries())
		        		game.player2.x-=game.player2.SPEED;
		        }
		        if (x==8) {
		        	game.player2.UP=game.player2.WALKING=true;
		        	game.player2.DOWN=game.player2.RIGHT=game.player2.LEFT=false;
		        	game.player2.getWalkingIJ();
		        	if(game.player2.check_boundries() || (game.player2.isKEYCOLLECTED &&game.player2.I_inmap==0&&game.player2.J_inmap==16 ))
		        		game.player2.y-=game.player2.SPEED;  
		        }
		        if (x==2) {
		        	game.player2.DOWN=game.player2.WALKING=true;
		        	game.player2.LEFT=game.player2.RIGHT=game.player2.UP=false;
		        	if(game.player2.check_boundries())
		        		game.player2.y+=game.player2.SPEED; 
		        }
		        
		        if (x==5) {
		        	
					try {
						game.player2.OPNNTPUTTHEBOOMB=true;
						game.player2.boomb = new Boomb(game.player2.x,game.player2.y,game.player2.GP,game.player2);
						game.player2.Xisready=false;
						game.player2.fire = new Fire(game.player2.x,game.player2.y,game.player2.GP,game.player2);

					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						game.player2.sound.Ticking_Bomb();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e1) {
						e1.printStackTrace();
					}
				}
					}
			
}

	public void changePanelEnd(JPanel New_p){
			invalidate();
			remove(this.p);
			this.p = New_p; 
			add(p, BorderLayout.CENTER);
			pack(); 
			revalidate();
		}
	
	
	
	
	
	
	
	
	
	
	
	
}
