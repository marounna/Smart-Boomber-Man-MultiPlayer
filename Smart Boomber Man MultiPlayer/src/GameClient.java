

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;


public class GameClient extends Thread {
	private InetAddress ipAddress;
	DatagramSocket socket;
	private MainFrame frm;
	InetAddress hostipAddress;String packetipAddress;int packetport;//for packets received from server
	public GameClient(MainFrame mainFrame,String ipAddress){
		this.frm=mainFrame;
		try {
			
			this.socket=new DatagramSocket();
			this.ipAddress=InetAddress.getByName(ipAddress);
			

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		this.sendData("000000.".getBytes());
		System.out.println("the inet ipaddress"+this.ipAddress);
		
	}
	
	public void run(){
		while(true){
			decryptString(receiveData());
		}
	}
	public void sendData(byte[] data){
	DatagramPacket packet= new DatagramPacket(data,data.length,ipAddress,1331);
	try {
		
		socket.send(packet);
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	}

	
	public String receiveData(){
		byte[] data= new byte[133];
		DatagramPacket packet = new DatagramPacket(data,data.length);
		try {
			socket.setSoTimeout(1);
			socket.receive(packet);
			packetipAddress=packet.getAddress().getHostAddress();
			 hostipAddress=packet.getAddress();
			 packetport=packet.getPort();
			 String message = new String(packet.getData());
				message=message.substring(0,message.indexOf('.'));
				return message;
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		
		return null;
		
	}
	public void decryptString(String message){
		if(message == null) return;
		System.out.println("the recived message"+message);
		int x=Integer.parseInt(message);
		//player 2 joined
		if(x/10==999999&&x%10==0){
			this.frm.PLAYER2JOINED=true;
			if(frm!=null&&frm.game!=null&&frm.isHost)
			this.frm.game.sendkeytoserver();
			try {
				frm.ps.user_name2=frm.DBC.get_username1_by_code(frm.ps.code_game+1);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
		else
			
			if(x/10==999999&&x%10==5)
				this.frm.OPPONENT_ACTION(5);
			else
				if(x/10==999999&&x%10==7)
					this.frm.OPPONENT_ACTION(7);
			else
				if(x/10==999999&&x%10==8)
					this.frm.OPPONENT_ACTION(8);
				else
					if(x/10==999999&&x%10==2)
						this.frm.OPPONENT_ACTION(2);
					else
						if(x/10==999999&&x%10==6)
							this.frm.OPPONENT_ACTION(6);
						else
							if(x/10==999999&&x%10==4)
								this.frm.OPPONENT_ACTION(4);
							else
								if(x/10==999999&&x%10==1){
									this.frm.OPPONENT_ACTION(1);
								}
									else
										if(x/1000==111111&&x%1000!=0){
											if(frm.game!=null)
												System.out.println("the key recieved pos = "+x%1000);
												this.frm.game.key.changeposition(x%1000);
											}

	}
	
	
}
