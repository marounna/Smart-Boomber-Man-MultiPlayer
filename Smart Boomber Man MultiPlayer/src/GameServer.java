

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class GameServer extends Thread {
	private DatagramSocket socket;
	InetAddress[] hostipAddress=new InetAddress[10];String packetipAddress;int[] packetport=new int[1];//for packets received from client 
	int x1=0,y1=0,KeyPosition=0;
	DatagramPacket packet;
	public GameServer(){
		
		try {
			this.socket=new DatagramSocket(1331);

		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		while(true){
			decryptString(receiveData());
		}
	}
	public void sendData(byte[] data,InetAddress ipAddress,int port){
	DatagramPacket packet= new DatagramPacket(data,data.length,ipAddress,port);
		//DatagramPacket packet= new DatagramPacket(data,data.length);
		
	try {
		//System.out.println("server sending packet");
		this.socket.send(packet);
		
		//System.out.println("packets were send from server");
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	}
	
	public String receiveData(){
		byte[] data= new byte[133];
		int[] temp;
		this.packet = new DatagramPacket(data,data.length);
		try {
			socket.setSoTimeout(1);
			socket.receive(packet);
			 if(packetport.length==1)
			 {
				 packetport=new int[2];
				 packetport[0]=packet.getPort();
				 hostipAddress[0]=packet.getAddress();
			 }
			 else
			 {
				 boolean newport=true;
				 for(int i=0;i< packetport.length;i++)
				 {
					 if(packetport[i]==packet.getPort()){
						 newport=false;
						 break;
					 }
				 }
				 
				 if(newport){
			 temp=new int[packetport.length];
			 for(int i=0;i< temp.length;i++)temp[i]=packetport[i];
			 packetport=new int[packetport.length+1];
			 for(int i=0;i< temp.length;i++)packetport[i]=temp[i];
			 packetport[packetport.length-1]=packet.getPort();
			 hostipAddress[packetport.length-1]=packet.getAddress();
				 }
			 }
			 String message = new String(packet.getData());
				message=message.substring(0,message.indexOf('.'));
				return message;
		} catch (IOException e) {
			//System.out.println("packets were not recived");
			//e.printStackTrace();
			
		}
		return null;
		
		
	}
	
	public void decryptString(String message){
		if(message == null) return;
		int x=Integer.parseInt(message);
		if(x/10==this.packet.getPort()){
			switch (x%10) {
            case 8: SendDataToAllClientsBUTONE("9999998.".getBytes(),x/10);
                     break;
            case 2: SendDataToAllClientsBUTONE("9999992.".getBytes(),x/10);
                     break;
            case 6: SendDataToAllClientsBUTONE("9999996.".getBytes(),x/10);
                     break;
            case 4: SendDataToAllClientsBUTONE("9999994.".getBytes(),x/10);
                     break;
            case 5: SendDataToAllClientsBUTONE("9999995.".getBytes(),x/10);//drop boomb
                     break;
            case 1: SendDataToAllClientsBUTONE("9999991.".getBytes(),x/10);//key release
            break;
            case 7: SendDataToAllClientsBUTONE("9999997.".getBytes(),x/10);//buildwood
            break;
			}
			
		}
		else
		//player 2 joined
		if(x/10==999999&&x%10==0)
			SendDataToAllClients((message+".").getBytes());
		else
				if(x/1000==111111&&x%1000!=0){
					this.KeyPosition=x%1000;
					SendDataToAllClients((111111000+this.KeyPosition+".").getBytes());
				}
	}

	private void SendDataToAllClients(byte[] bytes) {
		if(hostipAddress!=null){
			for(int i=0;i<packetport.length;i++)
				if(hostipAddress[i]!=null)
				sendData(bytes, hostipAddress[i],packetport[i]);
		}
		
	}
	private void SendDataToAllClientsBUTONE(byte[] bytes,int port) {
		if(hostipAddress!=null){
			for(int i=0;i<packetport.length;i++){
				if(packetport[i]!=port&&hostipAddress[i]!=null)
				sendData(bytes, hostipAddress[i],packetport[i]);
			}
		}
		
	}
	
}