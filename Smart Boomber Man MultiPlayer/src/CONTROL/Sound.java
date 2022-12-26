package CONTROL;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;





public class Sound {
	AudioInputStream ais;
	 	Clip clip;
	//File f=new File("open_door.wav");
	 	File f;
	 	public void DoorOpening() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
	 		f=new File("sounds/open_door.wav");
	 		ais=AudioSystem.getAudioInputStream(f);
	 		clip=AudioSystem.getClip();
	 		clip.open(ais);
	 		clip.start();
	 		//clip.setFramePosition(0);
	 	}
	 	public void Ticking_Bomb() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException{
	 		
	 		f=new File("sounds/Ticking_Bomb1.wav");
	 		ais=AudioSystem.getAudioInputStream(f);
	 		clip=AudioSystem.getClip();
	 		clip.open(ais);
	 		clip.start();
	 		//clip.setFramePosition(5);
	 		
	 	}
	
	
}
