import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map {

	MapObject mapObjects[][];
	 char [][] myArray;
	private Score score; 
	private int start_drawing_x,start_darawing_y;
	private int rows;
	private int columns;
	int Total_Wood=0;
	int WoodPositions[][];
	int ImageSize=65;
	boolean READY=false;
	public Map() throws FileNotFoundException {
		 rows = 10;
	     columns = 18;
	     myArray = new char[rows][columns];
	      
		File f =new File("MAP.TXT");
		@SuppressWarnings("resource")
		Scanner s=new Scanner(f);
		String line;
		int j=0;
		while (s.hasNextLine()){
			line=s.nextLine();
			for(int i=0;i<columns;i++){
				myArray[j][i]=line.charAt(i);
			}
		      j++;
		}
		System.out.println(j);
		
		System.out.println("------------------------------------");
		for(int k=0;k<rows;k++){
			for(int l=0;l<columns;l++){
				System.out.print(myArray[k][l]);
			}
			System.out.println("");
		}
		

		int image_size=ImageSize;
		int default_x=70;
		int default_y=55;
		
		start_drawing_x=default_x;
		 start_darawing_y= default_y;
		mapObjects = new MapObject[rows][columns];
		
		for(int k=0;k<rows;k++){
			for(int l=0;l<columns;l++){
				if(myArray[k][l]=='0'){
					if((k+l)%2==0)
					mapObjects[k][l]=new Light_grass( start_drawing_x,start_darawing_y);
					else
						mapObjects[k][l]=new Dark_grass( start_drawing_x,start_darawing_y);
						
				}
				if(myArray[k][l]=='1')
				mapObjects[k][l]=new Stone( start_drawing_x,start_darawing_y);
				if(myArray[k][l]=='2'){
					mapObjects[k][l]=new Wood( start_drawing_x,start_darawing_y);
					Total_Wood++;
				}
				
				start_drawing_x+= image_size;
			}
			start_drawing_x=default_x;
			start_darawing_y+= image_size;
		}
		
		
		score=new Score(default_x,0);
		
		READY=true;
		
		WoodPositions=new int[Total_Wood][2];
		int i=0;
		for(int k=0;k<rows;k++){
			for(int l=0;l<columns;l++){
				if(myArray[k][l]=='2'){
					WoodPositions[i][0]=k;
					WoodPositions[i++][1]=l;
				}
					
			}
		}
		
		
		
		
		
		
		
		
		
		
	}
	


	public void Draw(Game_Panel game_Panel, Graphics g) {
		// TODO Auto-generated method stub
		score.getIcon().paintIcon(game_Panel, g,score.getStart_x() , score.getStart_y());
		
		
		for(int k=0;k<rows;k++){
			for(int l=0;l<columns;l++){
				mapObjects[k][l].getIcon().paintIcon(game_Panel, g,mapObjects[k][l].getStart_x() , mapObjects[k][l].getStart_y());
			}

		}
	}

	public void Update(char[][] myArray2) {
		// TODO Auto-generated method stub
		
		
		READY=false;
		myArray=myArray2;
		int image_size=ImageSize;
		int default_x=70;
		int default_y=55;
		
		start_drawing_x=default_x;
		 start_darawing_y= default_y;
		mapObjects = new MapObject[rows][columns];
		
		for(int k=0;k<rows;k++){
			for(int l=0;l<columns;l++){
				if(myArray[k][l]=='0'){
					if((k+l)%2==0)
					mapObjects[k][l]=new Light_grass( start_drawing_x,start_darawing_y);
					else
						mapObjects[k][l]=new Dark_grass( start_drawing_x,start_darawing_y);
						
				}
				if(myArray[k][l]=='1')
				mapObjects[k][l]=new Stone( start_drawing_x,start_darawing_y);
				if(myArray[k][l]=='2')
					mapObjects[k][l]=new Wood( start_drawing_x,start_darawing_y);
				
				start_drawing_x+= image_size;
			}
			start_drawing_x=default_x;
			start_darawing_y+= image_size;
		}

		READY=true;
	}

}
