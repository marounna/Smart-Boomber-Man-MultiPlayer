import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import CONTROL.SpriteSheet;

public class Boomb implements Runnable{
	private int x, y;
	private SpriteSheet ss;
	  boolean running = false;
	private Thread t;
	Game_Panel GP;
	Player player_user;

	public Boomb(int x, int y,Game_Panel GP,Player player) throws IOException {
		this.ss = new SpriteSheet("pic/BombermanSpriteSheet.png");
		this.x = x;
		this.y = y;
		this.start();
		this.GP=GP;
		this.player_user=player;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(3000);
			if(x!=0&&y!=0)
				try {
					Destroy_Boxes();
					player_user.isREADYTODRAWFIRE=true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			this.stop();
		} catch (InterruptedException e) {
			e.printStackTrace();
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
	void Destroy_Boxes() throws IOException {
		int i=-1,j=-1;
		for(int k=0;k<10;k++)
			for(int l=0;l<18;l++)
			{
				if(x+30>GP.map.mapObjects[k][l].start_x&& x+30<GP.map.mapObjects[k][l].end_x && y+30>GP.map.mapObjects[k][l].start_y && y+30<GP.map.mapObjects[k][l].end_y){
					i=k;j=l;
					break;
				}
			}
		if(i!=-1&&j!=-1&&j!=0&&i!=0 && GP.key!=null){
			if(GP.map.mapObjects[i][j+1].TYPE==2 ){
				if(player_user.IPUTTHEBOOMB)
				GP.frm.ps.boxies_dest++;
				update_map(i,j+1);
				GP.key.isFound(i,j+1);
			}
			if(GP.map.mapObjects[i][j-1].TYPE==2 ){
				if(player_user.IPUTTHEBOOMB)
				GP.frm.ps.boxies_dest++;
				update_map(i,j-1);
				GP.key.isFound(i,j-1);
			}
			if(GP.map.mapObjects[i-1][j].TYPE==2 ){
				if(player_user.IPUTTHEBOOMB)
				GP.frm.ps.boxies_dest++;
				update_map(i-1,j);
				GP.key.isFound(i-1,j);
			}
			if(GP.map.mapObjects[i+1][j].TYPE==2 ){
				if(player_user.IPUTTHEBOOMB)
				GP.frm.ps.boxies_dest++;
				update_map(i+1,j);
				GP.key.isFound(i+1,j);
			}
			if(GP.map.mapObjects[i][j].TYPE==2 ){
				if(player_user.IPUTTHEBOOMB)
				GP.frm.ps.boxies_dest++;
				update_map(i,j);
				GP.key.isFound(i,j);
			}
		}
	}
	//get i and j and update map after boomb
	void update_map(int i,int j){
		if((i+j)%2==0){
			GP.map.mapObjects[i][j]=new Light_grass(GP.map.mapObjects[i][j].getStart_x(),GP.map.mapObjects[i][j].getStart_y());
		}
			else{
			GP.map.mapObjects[i][j]=new Dark_grass(  GP.map.mapObjects[i][j].getStart_x(),GP.map.mapObjects[i][j].getStart_y());
			}
	}

	public BufferedImage getImage(){
			return ss.crop(175,8, 21, 22);
	}

	public void draw(Graphics g)  {
			g.drawImage(getImage(), this.x, this.y,60,60, null);
	}
}
