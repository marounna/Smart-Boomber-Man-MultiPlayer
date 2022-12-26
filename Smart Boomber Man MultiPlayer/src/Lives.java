import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import CONTROL.SpriteSheet;

public class Lives implements Runnable{
	int x, y;
	private SpriteSheet ss;
	  boolean running = false;
	private Thread t;
	private int locInSprite=-1;
	

	public Lives(int x,int y) throws IOException {
		this.ss = new SpriteSheet("pic/heart.png");
		this.x=x;this.y=y;
		locInSprite=0;
		this.start();
	}

	@Override
	public void run() {
		while(running==true)
		{
			try {
				Thread.sleep(100);
				if(locInSprite==5) locInSprite=-1;
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
			return ss.crop(30,37, 92, 85);
		case 1:
			return ss.crop(135,37, 92, 85);
		case 2:
			return ss.crop(225,37, 92, 85);
		case 3:
			return ss.crop(314,37, 55, 85);
		case 4:
			return ss.crop(371,37, 73, 85);
		case 5:
			return ss.crop(449,41, 88,81);
		}
		return ss.crop(30,37, 92, 85);

	}
	
	
	
	public void draw(Graphics g)  {
		if(locInSprite!=-1)
			g.drawImage(getImage(), this.x, this.y,80,80, null);
				
	}
}