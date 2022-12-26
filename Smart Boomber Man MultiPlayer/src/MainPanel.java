import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

public class MainPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	JLabel Message;
	JButton Login;
	JButton Signup;
	JButton guest;
	JButton ShowStatistic;
	
	MainFrame frm;
	public MainPanel(MainFrame frm){
		this.frm = frm;
		setLayout(new GridLayout(0,1));
		setPreferredSize(new Dimension(400,400));
		Message=new JLabel("Choose the most relevant option for you!");
		Message.setBackground(Color.CYAN);
		Message.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 48));
		
		
		Login = new JButton("Login");
		Login.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 34));
		Login.setForeground(Color.BLACK);
		Login.setBackground(Color.ORANGE);
		Login.addActionListener(this);
		Signup = new JButton("Sign Up");		
		Signup.setBackground(Color.ORANGE);
		Signup.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 34));
		Signup.addActionListener(this);
		guest = new JButton("AS A Guest");		
		guest.setBackground(Color.ORANGE);
		guest.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 34));
		guest.addActionListener(this);

		ShowStatistic = new JButton("ShowStatistic");
		ShowStatistic.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 34));
		ShowStatistic.setForeground(Color.BLACK);
		ShowStatistic.setBackground(Color.ORANGE);
		ShowStatistic.addActionListener(this);
		
		add(Message);
		add(Login);
		add(Signup);
		add(guest);
		add(ShowStatistic);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(ShowStatistic))
			frm.changePanel(new ShowStatistic(frm));
		
		if(e.getSource().equals(Login))
			frm.changePanel(new Login(frm));
		if(e.getSource().equals(Signup))
			frm.changePanel(new Sign_up(frm));
	
		if(e.getSource().equals(guest)){
			System.out.println("ok");
			try {
				this.setFocusable(false);
				frm.Start_Game();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
	}

}
