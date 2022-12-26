import javax.swing.ImageIcon;

public class Wood extends MapObject {
 public Wood(int x,int y){
	 super(x,y);
	 icon = new ImageIcon("pic/wood.jpg");
	 TYPE=2;
 }
}