

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import CONTROL.Sound;
import CONTROL.SpriteSheet;


public class Player  implements Runnable{
	///
	//statistics variables 
	
	
	////

	boolean RIGHT,LEFT,UP ,DOWN;//direction
	boolean WALKING;
	int x, y;
	public int I_inmap,J_inmap;//location of player in map
	private SpriteSheet ss_left,ss_right,ss_down,ss_up;
	boolean running = false;
	private Thread t;
	int SPEED=3;
	private BufferedImage sprite;
	
	boolean Xisready=true;
	boolean isREADYTODRAWFIRE=false;
	boolean isBurned=false;
	boolean isKEYCOLLECTED=false;
	boolean IPUTTHEBOOMB=false;
	boolean OPNNTPUTTHEBOOMB=false;
	int LIVES=5;
	boolean LOSEING=false;// flag shel hevhov 
	boolean CONTROLEDbyME=false;//if true = i move this player else other move it
	Sound sound=new Sound();
	Game_Panel GP;
	Boomb boomb;
	Fire fire;
	int locInSprite_left ,locInSprite_right,locInSprite_up,locInSprite_down;//location of sprite in image
	/////////////

	
	public Player(int i,int j,Game_Panel GP) throws IOException {
		locInSprite_left =locInSprite_right=locInSprite_up=-1;
		this.ss_down = new SpriteSheet("pics/walking_down.png");
		this.ss_left = new SpriteSheet("pics/walking_left.png");
		this.ss_right = new SpriteSheet("pics/walking_right.png");
		this.ss_up = new SpriteSheet("pics/walking_up.png");
		this.x = GP.map.mapObjects[i][j].getStart_x();
		this.y = GP.map.mapObjects[i][j].getStart_y();
		RIGHT=LEFT=UP= false;
		this.GP=GP;
		DOWN=true;
		locInSprite_down=2;
		WALKING=false;
		
	}
	public int[] getWalkingIJ() {
		int i=-1,j=-1;
		for(int k=0;k<10;k++)
			for(int l=0;l<18;l++)
			{
				if(x+30>=GP.map.mapObjects[k][l].start_x&& x+30<=GP.map.mapObjects[k][l].end_x && y+20>=GP.map.mapObjects[k][l].start_y && y+20<=GP.map.mapObjects[k][l].end_y){
					i=k;j=l;
					break;
				}
				
			}
		I_inmap=i;J_inmap=j;
		int[] loc={I_inmap,J_inmap};
		return loc;
	}
	@Override
	public void run() {
		while(running==true)
		{
			try {
				Thread.sleep(150);
				if(WALKING)
				{
				
				
				if(LEFT)
				{
					if(locInSprite_left==4) locInSprite_left=-1;
					locInSprite_left++;
				}
				if(RIGHT)
				{
					if(locInSprite_right==4) locInSprite_right=-1;
					locInSprite_right++;
				}
				if(DOWN)
					{
						if(locInSprite_down==4) locInSprite_down=-1;
						locInSprite_down++;
					}
				if(UP)
						{
							if(locInSprite_up==4) locInSprite_up=-1;
							locInSprite_up++;
						}
				}
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			
		}
		locInSprite_left =locInSprite_right=locInSprite_up=locInSprite_down=-1;
		
	}
	
	public synchronized void start(){
		if(running) return;
		running = true;
		t = new Thread(this);
		t.start();
		
	}
	public synchronized void stop(){
		if(!running)return;
		running = false;
	}

	public BufferedImage getImage_down()
	{
		switch (locInSprite_down)
		{
		case 0:
			return ss_down.crop(0,0, 23, 41);
		case 1:
			return ss_down.crop(22,0, 23, 41);
		case 2:
			return ss_down.crop(42,0, 23, 41);
		case 3:
			return ss_down.crop(64,0, 23, 41);
		case 4:
			return ss_down.crop(84,0, 23, 41);
		}
		return sprite;	
	}
	
	public BufferedImage getImage_left()
	{
		switch (locInSprite_left)
		{
		case 0:
			return ss_left.crop(3,93, 21, 43);
		case 1:
			return ss_left.crop(22,93, 21, 43);
		case 2:
			return ss_left.crop(42,93, 21, 43);
		case 3:
			return ss_left.crop(63,93, 21, 43);
		case 4:
			return ss_left.crop(84,93, 21, 43);
		}
		return sprite;	
	}
	
	public BufferedImage getImage_right()
	{
		switch (locInSprite_right)
		{
		case 0:
			return ss_right.crop(4,27, 18, 46);
		case 1:
			return ss_right.crop(26,27, 18, 46);
		case 2:
			return ss_right.crop(45,27, 18, 46);
		case 3:
			return ss_right.crop(66,27, 18, 46);
		case 4:
			return ss_right.crop(86,27, 18, 46);
		}
		return sprite;	
	}
	
	public BufferedImage getImage_up()
	{
		switch (locInSprite_up)
		{
		case 0:
			return ss_up.crop(3,61, 21, 43);
		case 1:
			return ss_up.crop(23,61, 21, 43);
		case 2:
			return ss_up.crop(43,61, 21, 43);
		case 3:
			return ss_up.crop(65,61, 21, 43);
		case 4:
			return ss_up.crop(85,61, 21, 43);
		}
		return sprite;	
	}
	
	public void draw(Graphics g) {
		if(DOWN)
		if(locInSprite_down!=-1)
			g.drawImage(getImage_down(), this.x, this.y,60,60, null);
		
		if(UP)
			if(locInSprite_up!=-1)
				g.drawImage(getImage_up(), this.x, this.y,60,60, null);
		
		if(LEFT)
			if(locInSprite_left!=-1)
				g.drawImage(getImage_left(), this.x, this.y,60,60, null);
		
		if(RIGHT)
			if(locInSprite_right!=-1)
				g.drawImage(getImage_right(), this.x, this.y,60,60, null);
	}

	
	
	boolean check_boundries() {
		for(int k=0;k<10;k++){
			for(int l=0;l<18;l++){
				
					if	(GP.map.mapObjects[k][l].TYPE!=0){	
						if(LEFT){
							if(x<=GP.map.mapObjects[k][l].getEnd_x()&&x>=GP.map.mapObjects[k][l].getStart_x() && y+40>=GP.map.mapObjects[k][l].getStart_y() && y+40 <= GP.map.mapObjects[k][l].getEnd_y()) 
								return false;
						}
						if(RIGHT){
							if(  x+60>=GP.map.mapObjects[k][l].getStart_x() && x+60<=GP.map.mapObjects[k][l].getEnd_x()  && y+40>GP.map.mapObjects[k][l].getStart_y() && y+40 <= GP.map.mapObjects[k][l].getEnd_y()) 
								return false;
						}
						if(UP){
							if(  x+50>=GP.map.mapObjects[k][l].getStart_x() && x+10<=GP.map.mapObjects[k][l].getEnd_x()  && y+30<=GP.map.mapObjects[k][l].end_y && y+30 >= GP.map.mapObjects[k][l].getStart_y()) 
								return false;
						}
						if(DOWN){
							
							if(  x+50>=GP.map.mapObjects[k][l].getStart_x() && x+10<=GP.map.mapObjects[k][l].getEnd_x()  && y+50<=GP.map.mapObjects[k][l].end_y && y+50 >= GP.map.mapObjects[k][l].getStart_y()) 
								return false;
							}
						}
					}
			}
		return true;
	}

	public void Buildwood() {
		getWalkingIJ();
		if(this.UP==true &&GP.map.mapObjects[I_inmap-1][J_inmap].TYPE==0)
			GP.map.mapObjects[I_inmap-1][J_inmap]=new Wood(GP.map.mapObjects[I_inmap-1][J_inmap].getStart_x(),GP.map.mapObjects[I_inmap-1][J_inmap].getStart_y());
		else		
		if(this.DOWN==true&&GP.map.mapObjects[I_inmap+1][J_inmap].TYPE==0)
			GP.map.mapObjects[I_inmap+1][J_inmap]=new Wood(GP.map.mapObjects[I_inmap+1][J_inmap].getStart_x(),GP.map.mapObjects[I_inmap+1][J_inmap].getStart_y());
		else
		if(this.LEFT==true&&GP.map.mapObjects[I_inmap][J_inmap-1].TYPE==0)
			GP.map.mapObjects[I_inmap][J_inmap-1]=new Wood(GP.map.mapObjects[I_inmap][J_inmap-1].getStart_x(),GP.map.mapObjects[I_inmap][J_inmap-1].getStart_y());

		else
		if(this.RIGHT==true&&GP.map.mapObjects[I_inmap][J_inmap+1].TYPE==0)
			GP.map.mapObjects[I_inmap][J_inmap+1]=new Wood(GP.map.mapObjects[I_inmap][J_inmap+1].getStart_x(),GP.map.mapObjects[I_inmap][J_inmap+1].getStart_y());
	
	}
	
	public void Blinking(){
		if(isBurned)
			LOSEING=!LOSEING;
		else
			LOSEING=false;
	}

	public void isKeyFound(){
		getWalkingIJ();
		if(GP.key.i==this.I_inmap && GP.key.j== this.J_inmap){
			GP.key.setDEFALUTS();
			if(!isKEYCOLLECTED){
			try {
				sound.DoorOpening();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}
			}
			isKEYCOLLECTED=true;
		}
	}
}
