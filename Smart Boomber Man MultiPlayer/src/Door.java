import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import CONTROL.SpriteSheet;

public class Door implements Runnable{
	int x, y;
	private SpriteSheet ss;
	  boolean running = false;
	private Thread t;
	private int locInSprite=-1;

	public Door(int x,int y) throws IOException {
		this.ss = new SpriteSheet("pic/door.png");
		this.x=x;this.y=y;
		locInSprite=0;
		//this.start();
	}

	@Override
	public void run() {
		while(running==true)
		{
			try {
				Thread.sleep(200);
				if(locInSprite==3) {
					this.stop();
					}
				else
				locInSprite++;
				
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			
		}
		
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
			return ss.crop(0,0, 96, 97);
		case 1:
			return ss.crop(0,96, 96, 97);
		case 2:
			return ss.crop(0,192, 96, 97);
		case 3:
			return ss.crop(0,288, 96, 96);
		
		}
		return ss.crop(0,0, 96, 97);

	}
	
	
	
	public void draw(Graphics g)  {
		if(locInSprite!=-1)
			g.drawImage(getImage(), this.x-5, this.y-10,75,75, null);
				
	}
}