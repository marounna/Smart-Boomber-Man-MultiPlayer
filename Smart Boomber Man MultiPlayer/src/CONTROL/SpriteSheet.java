package CONTROL;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheet implements Serializable{
	private static final long serialVersionUID = 1L;
	private BufferedImage sheet;
	
	public SpriteSheet(String path) throws IOException{
		sheet = ImageIO.read(new File(path));
	}

	public BufferedImage crop(int x, int y, int w, int h){
		return sheet.getSubimage(x , y , w, h);
	}
	
	
}
