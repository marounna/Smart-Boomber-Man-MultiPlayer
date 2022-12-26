import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import CONTROL.SpriteSheet;

public class Fire implements Runnable{
	int x, y;
	private SpriteSheet ss;
	  boolean running = false;
	private Thread t;
	Game_Panel GP;
	int i=-1,j=-1;//for map object
	Player player_user;

	public Fire(int x, int y,Game_Panel GP,Player player) throws IOException {
		this.ss = new SpriteSheet("pic/fire.png");
		this.x = x;
		this.y = y;
		this.start();
		this.GP=GP;
		this.player_user=player;
	}

	@Override
	public void run() {
		
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.stop();

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
		
		
			
		
		
		if(GP==null || GP.player1==null) return;
		this.player_user.isREADYTODRAWFIRE=false;
		if(GP.player1.isBurned){
			if(GP.player2.IPUTTHEBOOMB)
			{
				GP.frm.ps.kills++;
			}
			if(GP.player1.IPUTTHEBOOMB)
			{
				GP.frm.ps.death++;
			}
			if(GP.player2.OPNNTPUTTHEBOOMB)
			{
				GP.frm.ps.death++;
			}
			
			
			GP.player1.LIVES--;
			if(GP.player1.isKEYCOLLECTED){
				GP.key.changeposition(GP.key.p_rand);
				GP.player1.isKEYCOLLECTED=false;
			}
			GP.player1.isBurned=false;
		}
		if(GP.player2!=null&&GP.player2.isBurned){
			if(GP.player1.IPUTTHEBOOMB)
			{
				GP.frm.ps.kills++;
			}
			if(GP.player2.IPUTTHEBOOMB)
			{
				GP.frm.ps.death++;
			}
			if(GP.player1.OPNNTPUTTHEBOOMB)
			{
				GP.frm.ps.death++;
			}
			GP.player2.LIVES--;
			if(GP.player2.isKEYCOLLECTED){
				GP.key.changeposition(GP.key.p_rand);
				GP.player2.isKEYCOLLECTED=false;
			}
			GP.player2.isBurned=false;
		}
		
		player_user.Xisready=true;//the player who used this fire and boomb
		
		
		player_user.OPNNTPUTTHEBOOMB=false;
		player_user.IPUTTHEBOOMB=false;
		
		
		
	}

public void CheckPlayerInFire(){
	
		GP.player1.getWalkingIJ();
		if(i!=-1&&j!=-1){
		if(this.GP.map.mapObjects[i][j].TYPE!=1)
		if(GP.player1.I_inmap==i&&GP.player1.J_inmap==j)
			GP.player1.isBurned=true;
		if(this.GP.map.mapObjects[i][j+1].TYPE!=1)
			if(GP.player1.I_inmap==i&&GP.player1.J_inmap==j+1)
				GP.player1.isBurned=true;
		if(this.GP.map.mapObjects[i][j-1].TYPE!=1)
			if(GP.player1.I_inmap==i&&GP.player1.J_inmap==j-1)
				GP.player1.isBurned=true;
		if(this.GP.map.mapObjects[i+1][j].TYPE!=1)
			if(GP.player1.I_inmap==i+1&&GP.player1.J_inmap==j)
				GP.player1.isBurned=true;
		if(i!=0&&this.GP.map.mapObjects[i-1][j].TYPE!=1)
			if(GP.player1.I_inmap==i-1&&GP.player1.J_inmap==j)
				GP.player1.isBurned=true;
		
		}
		
		if(this.GP.frm.PLAYER2JOINED){
			GP.player2.getWalkingIJ();
			if(i!=-1&&j!=-1){
			if(this.GP.map.mapObjects[i][j].TYPE!=1)
			if(GP.player2.I_inmap==i&&GP.player2.J_inmap==j)
				GP.player2.isBurned=true;
			if(this.GP.map.mapObjects[i][j+1].TYPE!=1)
				if(GP.player2.I_inmap==i&&GP.player2.J_inmap==j+1)
					GP.player2.isBurned=true;
			if(this.GP.map.mapObjects[i][j-1].TYPE!=1)
				if(GP.player2.I_inmap==i&&GP.player2.J_inmap==j-1)
					GP.player2.isBurned=true;
			if(this.GP.map.mapObjects[i+1][j].TYPE!=1)
				if(GP.player2.I_inmap==i+1&&GP.player2.J_inmap==j)
					GP.player2.isBurned=true;
			if(i!=0&&this.GP.map.mapObjects[i-1][j].TYPE!=1)
				if(GP.player2.I_inmap==i-1&&GP.player2.J_inmap==j)
					GP.player2.isBurned=true;

			
			}
		}

}

//////////////////////////////////////



	public BufferedImage getImage()
	{
	
			return ss.crop(396,585, 173, 162);

	}
	
	
	
	public void draw(Graphics g)  {
		
		
		for(int k=0;k<10;k++)
			for(int l=0;l<18;l++)
			{
				if(x+30>GP.map.mapObjects[k][l].start_x&& x+30<GP.map.mapObjects[k][l].end_x && y+30>GP.map.mapObjects[k][l].start_y && y+30<GP.map.mapObjects[k][l].end_y){
					i=k;j=l;
					break;
				}
				
			}
			
				if(GP.map.mapObjects[i][j].TYPE!=1)
					g.drawImage(getImage(), GP.map.mapObjects[i][j].start_x, GP.map.mapObjects[i][j].start_y,60,60, null);
				if(GP.map.mapObjects[i][j+1].TYPE!=1)
					g.drawImage(getImage(), GP.map.mapObjects[i][j+1].start_x, GP.map.mapObjects[i][j+1].start_y,60,60, null);
				if(GP.map.mapObjects[i][j-1].TYPE!=1)
					g.drawImage(getImage(), GP.map.mapObjects[i][j-1].start_x, GP.map.mapObjects[i][j-1].start_y,60,60, null);
				if(GP.map.mapObjects[i+1][j].TYPE!=1)
					g.drawImage(getImage(), GP.map.mapObjects[i+1][j].start_x, GP.map.mapObjects[i+1][j].start_y,60,60, null);
				if(GP.map.mapObjects[i-1][j].TYPE!=1)
					g.drawImage(getImage(), GP.map.mapObjects[i-1][j].start_x, GP.map.mapObjects[i-1][j].start_y,60,60, null);
				
				
	}
}
