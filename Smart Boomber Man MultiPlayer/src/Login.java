
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.swing.*;

import CONTROL.DBControl;
public class Login extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	JLabel User_Name;
	JTextField field_User_Name;
	JLabel password;
	JPasswordField field_password;
	JButton Login;
	JButton BACK;
	MainFrame frm;
	public Login(MainFrame frm){
		this.frm = frm;
		setLayout(new GridLayout(0,1));
		setPreferredSize(new Dimension(400,400));
		User_Name=new JLabel("Enter your Username");
		User_Name.setFont(new Font("Serif",Font.PLAIN,20));
		password=new JLabel("Enter your Password");
		password.setFont(new Font("Serif",Font.PLAIN,20));
		field_User_Name=new JTextField();
		field_User_Name.setFont(new Font("Times New Roman", Font.PLAIN, 27));
		field_User_Name.setPreferredSize(new Dimension(100,50));
		field_password=new JPasswordField();
		field_password.setFont(new Font("Times New Roman", Font.PLAIN, 27));
		field_password.setPreferredSize(new Dimension(100,50));
		//txtName.setEnabled(false);
		
		BACK= new JButton("Back");
		BACK.setBackground(SystemColor.activeCaption);
		BACK.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 34));
		BACK.addActionListener(this);
		Login = new JButton("Login");		
		Login.setBackground(SystemColor.activeCaption);
		Login.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 34));
		Login.addActionListener(this);
		
		add(User_Name);
		add(field_User_Name);
		add(password);
		add(field_password);
		add(Login);
		add(BACK);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		DBControl db=new DBControl();
		if(e.getSource().equals(Login))
			try {
				if(db.check_user_pass(field_User_Name.getText(),field_password.getText())){
					frm.ps.user_name1=field_User_Name.getText();
				System.out.println(frm.ps.user_name1 +"joined the game");
				frm.DBC.insert_statistics(frm.ps.user_name1, "","", "", "", "","","", "");
				System.out.println("code game = "+frm.DBC.get_last_code_game());
				frm.ps.code_game=frm.DBC.get_last_code_game();
				
				frm.Start_Game();
				
				
				
				}
				else
					System.out.println("username or password are not correct!");
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		if(e.getSource().equals(BACK)){
		frm.changePanel(new MainPanel(frm));
		}
		
		
	}

}
