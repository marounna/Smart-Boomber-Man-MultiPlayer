import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import CONTROL.SpriteSheet;

public class Key implements Runnable{
	int x, y,size=40,i=5,j=9;
	private SpriteSheet ss;
	boolean running = false;
	private Thread t;
	private int locInSprite=-1;
	boolean VISIBLE = false;
	int defaultX=10,defaultY=90,defaultSIZE=60;
	Game_Panel GP;
	 Random rand;
	 int p_rand;
	public Key(Game_Panel GP) throws IOException {
		this.ss = new SpriteSheet("pic/key.png");
		locInSprite=0;
		this.start();
		this.GP=GP;
		
		
	}
	public void changeposition(){
			rand=new Random();
			p_rand = rand.nextInt(GP.map.Total_Wood-2)+1;
		 i=GP.map.WoodPositions[p_rand][0];
		 j=GP.map.WoodPositions[p_rand][1];
		this.x=GP.map.mapObjects[i][j].start_x+10;
		this.y=GP.map.mapObjects[i][j].start_y+5;
		
	}
	public void changeposition(int x){
		p_rand=x;
	 i=GP.map.WoodPositions[x][0];
	 j=GP.map.WoodPositions[x][1];
	this.x=GP.map.mapObjects[i][j].start_x+10;
	this.y=GP.map.mapObjects[i][j].start_y+5;
	this.size=40;
	
}

	@Override
	public void run() {
		while(running==true)
		{
			try {
				Thread.sleep(100);
				if(locInSprite==7) locInSprite=-1;
				locInSprite++;
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			
		}
		locInSprite=-1;
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

	public BufferedImage getImage()
	{
	
		switch (locInSprite)
		{
		case 0:
			return ss.crop(20,233, 87, 158);
		case 1:
			return ss.crop(114,233, 87, 158);
		case 2:
			return ss.crop(202,233, 71, 158);
		case 3:
			return ss.crop(270,233, 73, 158);
		case 4:
			return ss.crop(353,233, 85, 158);
		case 5:
			return ss.crop(444,233, 89, 158);
		case 6:
			return ss.crop(535,233, 73, 158);
		case 7:
			return ss.crop(604,233, 73, 158);
		}
		return ss.crop(20,233, 87, 158);

	}
	
	
	//size = 60 if inside map and 80 if outside map
	public void draw(Graphics g)  {
		if(locInSprite!=-1)
			g.drawImage(getImage(), this.x, this.y,size,size, null);
				
	}
	
	
	public void setDEFALUTS(){
		x=defaultX;y=defaultY;size=defaultSIZE;
	}

	public void isFound(int i, int j) {
		if(this.i==i && this.j==j)
			this.VISIBLE=true;
		
	}
	
	
}