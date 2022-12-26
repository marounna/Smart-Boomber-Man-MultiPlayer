import javax.swing.ImageIcon;

public class MapObject {
	protected ImageIcon icon;
	int ImageSize=65;
	 protected int start_x,start_y,end_x,end_y;
	 int TYPE;//0 grass //1 stone //2 wood 
	 public MapObject(int x,int y){
		 start_x=x;
		 start_y=y;
		 end_x=start_x+ImageSize;
		 end_y=start_y+ImageSize;
		 TYPE=0;
	 }
	 public int getStart_x(){
		 return start_x;
	 }
	 public int getStart_y(){
		 return start_y;
	 }
	 public int getEnd_x(){
		 return end_x;
	 }
	 public int getEnd_y(){
		 return end_y;
	 }
	 
	 public ImageIcon getIcon(){
		 return icon;
	 }
}
