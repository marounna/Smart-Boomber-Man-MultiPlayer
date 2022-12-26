import javax.swing.ImageIcon;
public class Stone extends MapObject {
	 public Stone(int x,int y){
		 super(x,y);
		 icon = new ImageIcon("pic/stone.jpg");	
		 TYPE=1;
	 }
}
