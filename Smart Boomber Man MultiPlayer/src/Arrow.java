import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import CONTROL.SpriteSheet;

public class Arrow {
	int x=800, y=5;
	private SpriteSheet ss;
	 int locInSprite=-1;
	public Arrow() throws IOException {
		this.ss = new SpriteSheet("pic/Arrow.png");
		Dimension Screen_dimemsion=Toolkit.getDefaultToolkit().getScreenSize();
		x=(int) (Screen_dimemsion.getWidth()/2);
		//this.start();
	}

	public BufferedImage getImage()
	{
	
		switch (locInSprite)
		{
		case 0:// right                            //
			return ss.crop(215,0,73, 74);
		case 1://left
			return ss.crop(215,70,73, 74);
		case 2://down
			return ss.crop(215,143,73, 74);
		case 3://up
			return ss.crop(215,215,73, 74);
		case 4://upleft
			return ss.crop(215,289,73, 74);
		case 5://downleft
			return ss.crop(215,361,73, 74);
		case 6://upright
			return ss.crop(215,431,73, 74);
		case 7://downright
			return ss.crop(215,502,73, 74);
		
		}
		return ss.crop(215,0,73, 74);
	}

	public void draw(Graphics g)  {
		
		g.setColor(Color.CYAN);
		g.fillRect(x-5, y-5, 65, 65);
		if(locInSprite!=-1)
			g.drawImage(getImage(), this.x, this.y,60,60, null);
				
	}
	
	public void ChangeArrow(int Iplayer,int Jplayer,int Ikey,int Jkey){
			if(Iplayer==Ikey && Jplayer<Jkey )
				locInSprite=0;
			else
				if(Iplayer==Ikey && Jplayer>Jkey )
					locInSprite=1;
				else
					if(Iplayer<Ikey && Jplayer==Jkey )
						locInSprite=2;
					else
						if(Iplayer>Ikey && Jplayer==Jkey )
							locInSprite=3;
						else
							if(Iplayer>Ikey && Jplayer>Jkey )
								locInSprite=4;
							else
								if(Iplayer<Ikey && Jplayer>Jkey )
									locInSprite=5;
								else
									if(Iplayer>Ikey && Jplayer<Jkey )
										locInSprite=6;
									else
										if(Iplayer<Ikey && Jplayer<Jkey )
											locInSprite=7;
		
		
		
		
	}
	
	
}