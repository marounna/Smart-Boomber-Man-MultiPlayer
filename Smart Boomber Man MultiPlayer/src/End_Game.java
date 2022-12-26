import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
public class End_Game extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	MainFrame frm;
	Image back_ground;
	public int X=90,Y=90;
	
	public End_Game (MainFrame frm) throws FileNotFoundException {
		Dimension Screen_dimemsion=Toolkit.getDefaultToolkit().getScreenSize(); // get screen size 
		this.frm=frm;
		setPreferredSize(new Dimension(Screen_dimemsion));// full screen panel by screen size

			try {
				back_ground = ImageIO.read(new FileInputStream("pic/grass_bg.jpeg"));
			} catch (IOException e) {
			}
			
			
		
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(back_ground, 0, 0,getWidth(),getHeight(),Color.WHITE, null);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		g.setColor(Color.WHITE);
		g.drawString("Game Over", this.X,this.Y);

		
	}
	

	
	
	public void Idle(){

			}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
			Idle();
			repaint();
		}
	}
}